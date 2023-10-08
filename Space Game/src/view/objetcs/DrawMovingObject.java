package view.objetcs;

import java.awt.image.BufferedImage;
import model.gameobjects.MovingObject;

public interface DrawMovingObject{
	
	public void updateDrawMovingObject();
	
	public void drawPlayer(MovingObject obj, BufferedImage texture);
	
	public void drawEnemy(MovingObject obj, BufferedImage texture);
	
	public void drawMeteor(MovingObject obj);
	
	public void drawPowerUp(MovingObject obj);
	
	public void drawLaser(MovingObject obj);
	
}
