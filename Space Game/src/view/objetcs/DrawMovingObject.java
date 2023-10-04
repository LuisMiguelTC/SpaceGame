package view.objetcs;

import model.gameobjects.MovingObject;

public interface DrawMovingObject{
	
	public void drawPlayer(MovingObject obj);
	
	public void drawEnemy(MovingObject obj);
	
	public void drawPowerUp(MovingObject obj);
	
	public void drawLaser(MovingObject obj);
}
