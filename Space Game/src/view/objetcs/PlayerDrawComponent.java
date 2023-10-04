package view.objetcs;

import model.gameobjects.MovingObject;

public class PlayerDrawComponent implements DrawComponent{

	@Override
	public void update(MovingObject obj, DrawMovingObject c) {
		c.drawPlayer(obj);
	}
}
