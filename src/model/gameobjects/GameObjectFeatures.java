package model.gameobjects;

import mathgame.Vector2D;

public interface GameObjectFeatures {

	public void setPosition(Vector2D position);
	
	public Vector2D getPosition();
	
	public void setCenter(Vector2D center);
	
	public Vector2D getCenter();

	public void setWidth(double width);

	public double getWidth();

	public void setHeigth(double height);

	public double getHeight();
}
