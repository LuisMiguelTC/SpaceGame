package model.gameobjects;

import mathgame.Vector2D;
import model.Space;
import model.utils.Action;

public class PowerUp extends MovingObject {

	private long duration;
	private Action action;
	private PowerUpTypes powerUpType;
	
	public PowerUp(Vector2D position, Vector2D dimension, Action action, PowerUpTypes powerUpType) {
		super(position, dimension, new Vector2D(0,0), 0);
		this.action = action;
		duration = 0;
		this.powerUpType = powerUpType;
		
	}
	
	public void executeAction() {
		action.doAction();
	}

	@Override
	public void update(float dt, Space space) {
		angle += 0.1;
		duration += dt;
		
		if(duration > Constants.POWER_UP_DURATION) {
			this.destroy(space);
		}
	}
	
	@Override
	public GameObjectType getType() {
		return GameObjectType.POWERUP;
	}
	
	public PowerUpTypes getPowerUpType() {
		return this.powerUpType;
	}
}
