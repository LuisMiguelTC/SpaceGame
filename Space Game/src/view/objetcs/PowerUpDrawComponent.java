package view.objetcs;

import model.gameobjects.MovingObject;

public class PowerUpDrawComponent implements DrawComponent{

	@Override
	public void update(MovingObject obj, DrawMovingObject c) {
		c.drawPowerUp(obj);
	}
}
