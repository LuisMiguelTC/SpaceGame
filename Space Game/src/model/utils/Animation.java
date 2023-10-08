package model.utils;

import mathgame.Vector2D;

public class Animation {
	
	private int length;
	private int velocity;
	private int index;
	private boolean running;
	private Vector2D position;
	private long time;
	
	public Animation(int velocity, Vector2D position){
		this.velocity = velocity;
		this.position = position;
		index = 0;
		running = true;
	}
	
	public void updateAnimation(float dt){
		time += dt;
		
		if(time > velocity){
			time  = 0;
			index ++;
			if(index >= length){
				running = false;
				index = 0;
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public Vector2D getPosition() {
		return position;
	}
	
	public void setLenght(int length) {
		this.length = length;
	}
	
	public int getIndex() {
		return this.index;
	}
}
