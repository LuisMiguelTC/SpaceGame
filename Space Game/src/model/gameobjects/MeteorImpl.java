package model.gameobjects;

import java.awt.image.BufferedImage;
import mathgame.Vector2D;
import model.states.GameState;
import view.objetcs.DrawComponent;

public class MeteorImpl extends MovingObject implements Meteor{

	private Size size;	
	
	public MeteorImpl(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, DrawComponent drawObj, GameState gameState, Size size) {
		super(position, velocity, maxVel, texture, drawObj, gameState);
		
		this.size = size;
		this.velocity = velocity.scale(maxVel);
		
	}

	@Override
	public void update(float dt) {
		
		Vector2D playerPos = new Vector2D(gameState.getSpace().getPlayer().getCenter());
		
		int distanceToPlayer = (int) playerPos.subtract(getCenter()).getMagnitude();
		
		if(distanceToPlayer < Constants.SHIELD_DISTANCE / 2 + width / 2) {
			
			if(gameState.getSpace().getPlayer().isShieldOn()) {
				Vector2D fleeForce = fleeForce();
				velocity = velocity.add(fleeForce);
			}
			

		}
		
		if(velocity.getMagnitude() >= this.maxVel) {
			Vector2D reversedVelocity = new Vector2D(-velocity.getX(), -velocity.getY());
			velocity = velocity.add(reversedVelocity.normalize().scale(0.01f));
		}
		
		velocity = velocity.limit(Constants.METEOR_MAX_VEL);
		
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
	
	@Override
	public Vector2D fleeForce() {
		Vector2D desiredVelocity = gameState.getSpace().getPlayer().getCenter().subtract(getCenter());
		desiredVelocity = (desiredVelocity.normalize()).scale(Constants.METEOR_MAX_VEL);
		Vector2D v = new Vector2D(velocity);
		return v.subtract(desiredVelocity);
	}
	
	@Override
	public void destroy(){
		gameState.divideMeteor(this);
		gameState.playExplosion(position);
		gameState.addScore(Constants.METEOR_SCORE, position);
		//gameState.getSpace().notifySpaceEvent(new HitEvent(this));
		super.destroy();
	}

	public Size getSize(){
		return size;
	}

}
