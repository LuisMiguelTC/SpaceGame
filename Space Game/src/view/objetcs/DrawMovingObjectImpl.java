package view.objetcs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import mathgame.Vector2D;
import model.gameobjects.Laser;
import model.gameobjects.Meteor;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.gameobjects.PowerUp;
import model.gameobjects.PowerUpTypes;
import model.gameobjects.Size;
import view.utils.Assets;

public class DrawMovingObjectImpl implements DrawMovingObject{

	private Graphics2D g2d;
	private AffineTransform at;
	private MovingObject mo;
	private String typeObject;
	
	public DrawMovingObjectImpl(MovingObject mo, Graphics2D g2) {
		this.g2d = g2;
		this.mo = mo;
		this.typeObject = mo.getType();
	}
	
	@Override
	public void updateDrawMovingObject() {
		
		switch(typeObject) {
		
		case "PLAYER":
			drawPlayer(mo,Assets.player);
			break;
		case "ENEMY":
			drawEnemy(mo, Assets.enemy);
			break;
		case "LASER":
			drawLaser(mo);
			break;
		case "POWERUP":
			drawPowerUp(mo);
			break;
		case "METEOR":
			drawMeteor(mo);
			break;
		default:
			return;
		}
	}
	
	@Override
	public void drawPlayer(MovingObject obj, BufferedImage texture) {
		
		Player p = (Player) obj;
		
		if(!p.isVisible())
			return;
		
		Vector2D position = p.getPosition();
		AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + p.getWidth()/2,
				position.getY() + p.getHeight()/2 + 10);
		
		AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX()+ 1, position.getY() + p.getHeight()/2 +10);
		
		at1.rotate(p.getAngle(), 0, -10);
		at2.rotate(p.getAngle(), p.getWidth()/2 -1, -10);
		
		if(p.isAccelerating()){
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}
		
		if(p.isShieldOn()) {
			DrawAnimation shield = new DrawAnimationImpl(p.shieldEffect(), g2d);
			shield.drawShield();
			p.shieldEffect().setLenght(shield.getLength());
			BufferedImage currentFrame = shield.getBufferedImage();
			AffineTransform at3 = AffineTransform.getTranslateInstance(
					position.getX() - currentFrame.getWidth() / 2 + p.getWidth()/2,
					position.getY() - currentFrame.getHeight() / 2 + p.getHeight()/2);
			
			at3.rotate(p.getAngle(), currentFrame.getWidth() / 2, currentFrame.getHeight() / 2);
			g2d.drawImage(shield.getBufferedImage(), at3, null);
		}
		
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(p.getAngle(),p.getWidth()/2, p.getHeight()/2);
		g2d.drawImage(texture, at, null);
	}

	@Override
	public void drawEnemy(MovingObject obj, BufferedImage texture) {
		
		Vector2D position = obj.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(obj.getAngle(),obj.getWidth()/2, obj.getHeight()/2);
		g2d.drawImage(texture, at, null);
	}

	@Override
	public void drawPowerUp(MovingObject obj) {
		
		PowerUp p = (PowerUp) obj;
		BufferedImage texture = null;
		
		PowerUpTypes powerUpType = p.getPowerUpType();
		
		switch(powerUpType) {
		case LIFE:
			texture = Assets.life;
			break;
		case SHIELD:
			texture = Assets.shield;
			break;
		case SCORE_X2:
			texture = Assets.doubleScore;
			break;
		case FASTER_FIRE:
			texture = Assets.fastFire;
			break;
		case SCORE_STACK:
			texture = Assets.star;
			break;
		case DOUBLE_GUN:
			texture = Assets.doubleGun;
			break;
		default:
			break;
		}

		Vector2D position = p.getPosition();
		at = AffineTransform.getTranslateInstance(
				position.getX() + p.getWidth()/2 - texture.getWidth()/2,
				position.getY() + p.getHeight()/2 - texture.getHeight()/2);

		at.rotate(p.getAngle(),
				texture.getWidth()/2, 
				texture.getHeight()/2);
		
		g2d.drawImage(Assets.orb, (int)position.getX(), (int)position.getY(), null);
		g2d.drawImage(texture, at, null);	
	}

	@Override
	public void drawLaser(MovingObject obj) {
		
		Laser l = (Laser) obj;
		String colorLaser = l.colorLaser();
		BufferedImage texture = null;
		
		switch(colorLaser) {
		case "BLUE":
			texture = Assets.blueLaser;
			break;
		case "RED":
			texture = Assets.redLaser;
			break;
		default:
			break;
		}
		
		Vector2D position = obj.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX() - l.getWidth()/2, position.getY());
		at.rotate(obj.getAngle(), l.getWidth()/2, 0);
		g2d.drawImage(texture, at, null);	
	}

	@Override
	public void drawMeteor(MovingObject obj) {
		
		Meteor m = (Meteor) obj;
		Size size = m.getSize();		
		BufferedImage texture = Assets.bigs[m.getRandomMeteor()];

		if(size != Size.BIG) {
			
			switch(size){
			case MED:
				texture = Assets.meds[m.getRandomMeteor()];
				break;
			case SMALL:
				texture = Assets.smalls[m.getRandomMeteor()];
				break;
			case TINY:
				texture = Assets.tinies[m.getRandomMeteor()];
				break;
			default:
				return;
			}
		}
		
		Vector2D position = obj.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(obj.getAngle(), m.getWidth()/2, m.getHeight()/2);
		g2d.drawImage(texture, at, null);
	
	}
}
