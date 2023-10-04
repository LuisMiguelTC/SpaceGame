package model.gameobjects;

import java.awt.image.BufferedImage;
import mathgame.Vector2D;

public interface GameObjectFeatures {

	public void setPosition(Vector2D position);
	
	public Vector2D getPosition();
	
	public Vector2D getCenter();
	
	public BufferedImage getTexture();
}
