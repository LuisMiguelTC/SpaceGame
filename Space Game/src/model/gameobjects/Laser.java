package model.gameobjects;

import java.awt.image.BufferedImage;
import mathgame.Vector2D;
import model.states.GameState;
import view.objetcs.DrawComponent;

public class Laser extends MovingObject{

	public Laser(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture, DrawComponent drawObj, GameState gameState) {
		super(position, velocity, maxVel, texture, drawObj, gameState);
		
		this.angle = angle;
		this.velocity = velocity.scale(maxVel);
	}

	@Override
	public void update(float dt) {
		position = position.add(velocity);
		if(position.getX() < 0 || position.getX() > Constants.WIDTH ||
				position.getY() < 0 || position.getY() > Constants.HEIGHT){
			destroy();
		}
		
		collidesWith();
	}
	
	@Override
	public Vector2D getCenter(){
		return new Vector2D(position.getX() + width/2, position.getY() + width/2);
	}
	
}
