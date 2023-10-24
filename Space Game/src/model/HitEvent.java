package model;

import model.gameobjects.MovingObject;

public class HitEvent implements SpaceEvent{

	private MovingObject mo;
	
	public HitEvent(MovingObject mo) {
		this.mo = mo;
	}
	
	public MovingObject getCollisionMObj() {
		return this.mo;
	}
}
