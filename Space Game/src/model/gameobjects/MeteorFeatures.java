package model.gameobjects;

import mathgame.Vector2D;
import model.Space;

public interface MeteorFeatures {

	public Vector2D fleeForce(Space space);

	public int getRandomMeteor();
	
	public Size getSize();
	
	public void setIsDivided(boolean isDivided);

	public boolean isDivided();
}
