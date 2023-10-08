package model.gameobjects;

import mathgame.Vector2D;
import model.Space;
import ui.Action;
//import view.utils.Assets;
//import view.utils.Sound;

public class PowerUp extends MovingObject {

	private long duration;
	private Action action;
	//private Sound powerUpPick;
	private PowerUpTypes powerUpType;
	
	public PowerUp(Vector2D position, Vector2D dimension, Action action, PowerUpTypes powerUpType) {
		super(position, dimension, new Vector2D(), 0);
		this.action = action;
		duration = 0;
		//powerUpPick = new Sound(Assets.powerUp);
		this.powerUpType = powerUpType;
	}
	
	public void executeAction() {
		action.doAction();
		//powerUpPick.play();
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
	public String getType() {
		return "POWERUP";
	}
	
	public PowerUpTypes getPowerUpType() {
		return this.powerUpType;
	}
}
