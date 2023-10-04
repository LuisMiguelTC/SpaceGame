package view.objetcs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import mathgame.Vector2D;
import model.gameobjects.MovingObject;
import model.gameobjects.PlayerImpl;
import model.gameobjects.PowerUp;
import view.utils.Assets;

public class DrawMovingObjectImpl implements DrawMovingObject{

	private Graphics2D g2d;
	private AffineTransform at;
	
	public DrawMovingObjectImpl(Graphics2D g2) {
		this.g2d = g2;
	}
	
	@Override
	public void drawPlayer(MovingObject obj) {
		
		PlayerImpl p = (PlayerImpl) obj;
		
		if(!p.isVisible())
			return;
		
		Vector2D position = p.getPosition();
		AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + p.getTexture().getWidth()/2,
				position.getY() + p.getTexture().getHeight()/2 + 10);
		
		AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX()+ 1, position.getY() + p.getTexture().getHeight()/2 +10);
		
		at1.rotate(p.getAngle(), 0, -10);
		at2.rotate(p.getAngle(), p.getTexture().getWidth()/2 -1, -10);
		
		if(p.isAccelerating()){
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}
		
		if(p.isShieldOn()) {
			BufferedImage currentFrame = p.shieldEffect().getCurrentFrame();
			AffineTransform at3 = AffineTransform.getTranslateInstance(
					position.getX() - currentFrame.getWidth() / 2 + p.getTexture().getWidth()/2,
					position.getY() - currentFrame.getHeight() / 2 + p.getTexture().getHeight()/2);
			
			at3.rotate(p.getAngle(), currentFrame.getWidth() / 2, currentFrame.getHeight() / 2);
			g2d.drawImage(p.shieldEffect().getCurrentFrame(), at3, null);
		}
		
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(p.getAngle(),p.getTexture().getWidth()/2,p.getTexture().getHeight()/2);
		
		g2d.drawImage(p.getTexture(), at, null);
	}

	@Override
	public void drawEnemy(MovingObject obj) {
		
		Vector2D position = obj.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(obj.getAngle(),obj.getTexture().getWidth()/2,obj.getTexture().getHeight()/2);
		g2d.drawImage(obj.getTexture(), at, null);
		
	}

	@Override
	public void drawPowerUp(MovingObject obj) {
		
		PowerUp p = (PowerUp) obj;
		
		Vector2D position = p.getPosition();
		at = AffineTransform.getTranslateInstance(
				position.getX() + Assets.orb.getWidth()/2 - p.getTypeTexture().getWidth()/2,
				position.getY() + Assets.orb.getHeight()/2 - p.getTypeTexture().getHeight()/2);

		at.rotate(p.getAngle(),
				p.getTypeTexture().getWidth()/2, 
				p.getTypeTexture().getHeight()/2);
		
		g2d.drawImage(Assets.orb, (int)position.getX(), (int)position.getY(), null);
		g2d.drawImage( p.getTypeTexture(), at, null);	
	}

	@Override
	public void drawLaser(MovingObject obj) {
		
		Vector2D position = obj.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX() - obj.getTexture().getWidth()/2, position.getY());
		at.rotate(obj.getAngle(), obj.getTexture().getWidth()/2, 0);
		g2d.drawImage(obj.getTexture(), at, null);
	}
}
