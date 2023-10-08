package view.objetcs;

import java.awt.image.BufferedImage;

public interface DrawAnimation {

	public void updateDrawExplosion();
	
	public void drawShield();
	
	public int getLength();
	
	public BufferedImage getBufferedImage();
}
