package model.gameobjects;

import mathgame.Vector2D;
import model.Space;

public abstract class GameObject implements GameObjectFeatures {
	
	protected Vector2D position;
	protected Vector2D dimension;
	protected Vector2D center;
	protected double width;
	protected double height;
	
	public GameObject(Vector2D position, Vector2D dimension){
		this.position = position;
		this.dimension = dimension;
	}
	
	@Override
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	@Override
	public Vector2D getPosition() {
		return this.position;
	}

	@Override
	public void setCenter(Vector2D center) {
		this.center = center;
	}

	@Override
	public Vector2D getCenter() {
		return this.center = new Vector2D(getPosition().getX() + getWidth()/2, 
				getPosition().getY() + getHeight()/2);
	}

	@Override
	public void setWidth(double width) {
		this.width = width;
	}
	
	@Override
	public double getWidth() {
		return this.width = dimension.getX();
	}
	@Override
	public void setHeigth(double height) {
		this.height = height;
	}
	@Override	
	public double getHeight() {
		return this.height = dimension.getY();
	}

	public abstract void update(float dt, Space s);

}

