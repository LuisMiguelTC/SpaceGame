package model.gameobjects;

import view.objetcs.DrawMovingObject;

public interface MovingObjectFeatures {
	
	void updateDraw(DrawMovingObject g);
	
	void collidesWith();
	
	boolean isDead();
}
