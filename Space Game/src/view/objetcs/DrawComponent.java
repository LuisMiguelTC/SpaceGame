package view.objetcs;

import model.gameobjects.MovingObject;

public interface DrawComponent {
	
	public void update(MovingObject obj, DrawMovingObject c);
}
