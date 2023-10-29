package model.gameobjects;

import mathgame.Vector2D;
import model.utils.Animation;

public interface PlayerFeatures {
	
	public boolean isAccelerating();
	
	public Vector2D getVelocity();
	
	public boolean isVisible();
	
	public boolean isSpawning();
	
	public void setSpawning(boolean spawning);
	
	public void resetValues();
	
	public void setShield();
	
	public boolean isShieldOn();

	public Animation shieldEffect();

	public void setDoubleScore();
	
	public boolean isDoubleScoreOn();
		
	public void setFastFire();
	
	public void setFireRate(long fireRate);
	
	public boolean isShooting();
	
	public void setDoubleGun();
	
	public boolean isDoubleGunOn();
	
}
