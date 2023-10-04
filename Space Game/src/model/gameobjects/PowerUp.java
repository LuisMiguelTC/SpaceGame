package model.gameobjects;

import java.awt.image.BufferedImage;

import mathgame.Vector2D;
import model.states.GameState;
import ui.Action;
import view.objetcs.DrawComponent;
import view.utils.Assets;
import view.utils.Sound;

public class PowerUp extends MovingObject {

	private long duration;
	private Action action;
	private Sound powerUpPick;
	private BufferedImage typeTexture;
	
	public PowerUp(Vector2D position, BufferedImage texture, Action action, DrawComponent drawObj, GameState gameState) {
		super(position, new Vector2D(), 0, Assets.orb, drawObj, gameState);
		this.action = action;
		this.typeTexture = texture;
		duration = 0;
		powerUpPick = new Sound(Assets.powerUp);
	}
	
	void executeAction() {
		action.doAction();
		powerUpPick.play();
	}

	@Override
	public void update(float dt) {
		angle += 0.1;
		duration += dt;
		
		if(duration > Constants.POWER_UP_DURATION) {
			this.destroy();
		}
		collidesWith();
	}

	public BufferedImage getTypeTexture() {
		return typeTexture;
	}
}
