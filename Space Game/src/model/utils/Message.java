package model.utils;

import mathgame.Vector2D;

public class Message {
	private float alpha;
	private String text;
	private Vector2D position;
	private ColorMessage color;
	private boolean center;
	private boolean fade;
	private String sizeFont;
	private final float deltaAlpha = 0.025f;
	private boolean dead;
	private double initialYPosition;
	
	public Message(Vector2D position, boolean fade, String text, ColorMessage color,
			boolean center, String sizeFont) {
		this.sizeFont = sizeFont;
		this.text = text;
		this.position = position;
		this.fade = fade;
		this.color = color;
		this.center = center;
		this.dead = false;
		this.initialYPosition = this.position.getY();
		
		if(fade)
			alpha = 1;
		else
			alpha = 0;	
	}

	public float getAlpha() {
		return alpha;
	}

	public float setAlpha(Float a) {
		return alpha = a;
	}
	
	public String getText() {
		return text;
	}

	public Vector2D getPosition() {
		return position;
	}

	public ColorMessage getColor() {
		return color;
	}


	public boolean isCenter() {
		return center;
	}


	public boolean isFade() {
		return fade;
	}


	public String getSizeFont() {
		return sizeFont;
	}


	public float getDeltaAlpha() {
		return deltaAlpha;
	}

	public boolean isDead(){
		return dead;
	}

	public void setFade(boolean fade) {
		this.fade = fade;
	}


	public void setDead(boolean dead) {
		this.dead = dead;
	}


	public void setInitialYPosition() {
		position.setY(initialYPosition);
	}
	
}
