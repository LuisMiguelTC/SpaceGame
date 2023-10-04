package view.objetcs;

import model.gameobjects.MovingObject;

public class LaserDrawComponent implements DrawComponent{

	@Override
	public void update(MovingObject obj, DrawMovingObject c) {
		c.drawLaser(obj);
	}
}
