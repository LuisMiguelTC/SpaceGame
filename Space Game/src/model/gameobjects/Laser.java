package model.gameobjects;

import mathgame.Vector2D;
import model.Space;

public class Laser extends MovingObject{

	private String colorLaser;
	
	public Laser(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel, double angle, String colorLaser) {
		super(position, dimension, velocity, maxVel);
		
		this.angle = angle;
		this.velocity = velocity.scale(maxVel);
		this.colorLaser = colorLaser;
	}

	@Override
	public void update(float dt, Space space) {
		position = position.add(velocity);
		if(position.getX() < 0 || position.getX() > Constants.WIDTH ||
				position.getY() < 0 || position.getY() > Constants.HEIGHT){
			destroy(space);
		}

	}
	
	@Override
	public Vector2D getCenter(){
		return new Vector2D(position.getX() + width/2, position.getY() + width/2);
	}

	@Override
	public String getType() {
		return "LASER" ;
	}
	
	public String colorLaser() {
		return this.colorLaser;
	}
}
