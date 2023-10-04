package model.gameobjects;

import java.awt.image.BufferedImage;

import mathgame.Vector2D;

public abstract class GameObject implements GameObjectFeatures {
	
	protected Vector2D position;
	private BufferedImage texture;
	
	public GameObject(Vector2D position, BufferedImage texture){
		this.position = position;
		this.texture = texture;
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
	public Vector2D getCenter() {
		return new Vector2D(this.position.getX() + this.getTexture().getWidth()/2, 
				this.position.getY() + this.getTexture().getHeight()/2);
	}

	public BufferedImage getTexture() {
		return this.texture;
	}
	
	public abstract void update(float dt);

}

