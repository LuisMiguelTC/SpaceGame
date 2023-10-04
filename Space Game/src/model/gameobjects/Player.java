package model.gameobjects;

import view.utils.Animation;

public interface Player {

	public void setShield();
	
	public void setDoubleScore();
		
	public void setFastFire();
	
	public void setDoubleGun();
	
	public void resetValues();
	
	public boolean isSpawning();
	
	public boolean isShieldOn();
	
	public boolean isDoubleScoreOn();
	
	public boolean isAccelerating();
	
	public boolean isVisible();
	
	public boolean isDoubleGunOn();
	
	public Animation shieldEffect();
	
}
