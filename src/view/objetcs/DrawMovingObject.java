package view.objetcs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import mathgame.Vector2D;
import model.gameobjects.Enemy;
import model.gameobjects.GameObjectType;
import model.gameobjects.Laser;
import model.gameobjects.Asteroid;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.gameobjects.PowerUp;
import model.gameobjects.PowerUpTypes;
import model.gameobjects.Size;
import view.utils.Assets;
import view.utils.SoundManager;

public class DrawMovingObject {

	private Graphics2D g2d;
	private AffineTransform at;
	private MovingObject mo;
	private GameObjectType typeObject;
	private SoundManager soundManager;
	
	public DrawMovingObject(MovingObject mo, Graphics2D g2, SoundManager soundManager) {
		this.g2d = g2;
		this.mo = mo;
		this.typeObject = mo.getType();
		this.soundManager = soundManager;
	}
	
	public void updateDrawMovingObject() {
		
		switch(typeObject) {
		case PLAYER -> drawPlayer();
		case ENEMY -> drawEnemy();
		case LASER -> drawLaser();
		case POWERUP -> drawPowerUp();
		case ASTEROID -> drawAsteroid();
		default -> throw new IllegalArgumentException("Entity not found");
		}
	}
	
	private void drawPlayer() {
		
		Player p = (Player) mo;
		BufferedImage texture = Assets.player;
		
		if(p.isShooting()) 
			soundManager.shootSound();
		
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
			DrawAnimation shield = new DrawAnimationImpl(p.shieldEffect(), g2d, soundManager);
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

	private void drawEnemy() {
		
		Enemy e = (Enemy) mo;
		BufferedImage texture = Assets.enemy;
		
		if(e.isShooting())
			soundManager.shootSound();
		
		Vector2D position = mo.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(mo.getAngle(),mo.getWidth()/2, mo.getHeight()/2);
		g2d.drawImage(texture, at, null);
		
	}

	private void drawPowerUp() {
		
		PowerUp p = (PowerUp) mo;
		BufferedImage texture = null;
		PowerUpTypes powerUpType = p.getPowerUpType();
		
		switch(powerUpType) {
			case LIFE -> texture = Assets.life;
			case SHIELD -> texture = Assets.shield;
			case SCORE_X2 -> texture = Assets.doubleScore;
			case FASTER_FIRE -> texture = Assets.fastFire;
			case SCORE_STACK -> texture = Assets.star;
			case DOUBLE_GUN -> texture = Assets.doubleGun;
			default -> throw new IllegalArgumentException("PowerUp not found");
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

	private void drawLaser() {

		Laser l = (Laser) mo;
		String colorLaser = l.colorLaser();
		BufferedImage texture = null;
		
		switch(colorLaser) {
			case "BLUE" -> texture = Assets.blueLaser;
			case "RED" -> texture = Assets.redLaser;
			default -> throw new IllegalArgumentException("Laser color not found");
		}
		
		Vector2D position = mo.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX() - l.getWidth()/2, position.getY());
		at.rotate(mo.getAngle(), l.getWidth()/2, 0);
		g2d.drawImage(texture, at, null);	
	}

	private void drawAsteroid() {
		
		Asteroid m = (Asteroid) mo;
		Size size = m.getSize();		
		BufferedImage texture = Assets.bigs[m.getRandomAsteroid()];

		if(size != Size.BIG) {
			
			switch(size){
				case MED -> texture = Assets.meds[m.getRandomAsteroid()];
				case SMALL -> texture = Assets.smalls[m.getRandomAsteroid()];
				case TINY -> texture = Assets.tinies[m.getRandomAsteroid()];
				default -> throw new IllegalArgumentException("Asteroid size not found");
			}
		}
		
		Vector2D position = mo.getPosition();
		at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(mo.getAngle(), m.getWidth()/2, m.getHeight()/2);
		g2d.drawImage(texture, at, null);
	}
}
