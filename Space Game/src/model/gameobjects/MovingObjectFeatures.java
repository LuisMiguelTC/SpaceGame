package model.gameobjects;

import model.Space;

public interface MovingObjectFeatures {
	
	public void collidesWith(Space s);
	
	public boolean isCollides();
	
	public void destroy(Space space);
	
	public boolean isDead();
	
}
