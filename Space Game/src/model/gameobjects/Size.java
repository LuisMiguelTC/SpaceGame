package model.gameobjects;

import mathgame.Vector2D;

public enum Size {
	
	BIG(2, new Vector2D(95,80)), MED(2, new Vector2D(43,43)), SMALL(2, new Vector2D(28,28)), TINY(0, new Vector2D(18,18));
	
	public int quantity;
	public Vector2D dimension;
	
	private Size(int quantity, Vector2D dimension){
		this.quantity = quantity;
		this.dimension = dimension;
	}
	
}
