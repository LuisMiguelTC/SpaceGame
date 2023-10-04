package view.objetcs;

import model.gameobjects.MovingObject;

public class EnemyDrawComponent implements DrawComponent{

	@Override
	public void update(MovingObject obj, DrawMovingObject c) {
		c.drawEnemy(obj);
	}
}
