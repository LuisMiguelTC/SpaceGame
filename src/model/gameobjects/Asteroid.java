package model.gameobjects;

import java.util.Optional;

import mathgame.Vector2D;
import model.Space;

public class Asteroid extends MovingObject implements AsteroidFeatures{

	private Size size;
	private int randomAsteroid;
	private boolean isDivided;
	
	public Asteroid(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel, int randomAsteroid, Size size) {
		super(position, dimension, velocity, maxVel);
		this.randomAsteroid = randomAsteroid;
		this.size = size;
		this.velocity = velocity.scale(maxVel);
		this.isDivided = false;
		
	}

	@Override
	public Vector2D fleeForce(Space space) {
		Vector2D desiredVelocity = space.getPlayer().getCenter().subtract(getCenter());
		desiredVelocity = (desiredVelocity.normalize()).scale(Constants.ASTEROID_MAX_VEL);
		Vector2D v = new Vector2D(velocity);
		return v.subtract(desiredVelocity);
	}
	
	@Override
	public void update(float dt, Space space) {

		Vector2D playerPos = new Vector2D(space.getPlayer().getCenter());
		int distanceToPlayer = (int) playerPos.subtract(getCenter()).getMagnitude();
		
		if(distanceToPlayer < Constants.SHIELD_DISTANCE / 2 + width / 2) {
			
			if(space.getPlayer().isShieldOn()) {
				Vector2D fleeForce = fleeForce(space);
				velocity = velocity.add(fleeForce);
			}
		}
		
		if(velocity.getMagnitude() >= this.maxVel) {
			Vector2D reversedVelocity = new Vector2D(-velocity.getX(), -velocity.getY());
			velocity = velocity.add(reversedVelocity.normalize().scale(0.01f));
		}
		
		velocity = velocity.limit(Constants.ASTEROID_MAX_VEL);
		position = position.add(velocity);
		
		if(position.getX() > Constants.WIDTH)
			position.setX(-width);
		if(position.getY() > Constants.HEIGHT)
			position.setY(-height);
	
		if(position.getX() < -width)
			position.setX(Constants.WIDTH);
		if(position.getY() < -height)
			position.setY(Constants.HEIGHT);

		angle += Constants.DELTAANGLE/2;
	}
	
	public void divideAsteroid(Space space) {
		
		size = this.getSize();
		Optional<Size> newSize = Optional.empty();
		
		switch(size){
			case BIG -> newSize =  Optional.of(Size.MED);
			case MED -> newSize =  Optional.of(Size.SMALL);
			case SMALL -> newSize =  Optional.of(Size.TINY);
			default -> newSize = Optional.empty();
		}
			
		for(int i = 0; i < size.quantity; i++) {
			space.addMovingObject(new Asteroid(
					this.getPosition(),
					new Vector2D(newSize.get().dimension),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.ASTEROID_INIT_VEL*Math.random() + 1,
					(int)(Math.random()),
					newSize.get()
					));
		}
	}
	
	@Override
	public int getRandomAsteroid() {
		return this.randomAsteroid;
	}
	
	@Override
	public Size getSize(){
		return size;
	}
	
	@Override
	public void setIsDivided(boolean isDivided) {
		this.isDivided  = isDivided;
	}

	@Override
	public boolean isDivided() {
		return this.isDivided;
	}
	
	@Override
	public GameObjectType getType() {
		return GameObjectType.ASTEROID;
	}
}
