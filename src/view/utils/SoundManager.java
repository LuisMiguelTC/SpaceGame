package view.utils;

public class SoundManager {

	private Sound background;
	private Sound shoot = new Sound(Assets.playerShoot);
	private Sound explosion = new Sound(Assets.explosion);
	
	public SoundManager() {
		background = new Sound(Assets.backgroundMusic);
		background.loop();
		background.changeVolume(-5.0f);
	}
	
	public void restartGame() {
		background.loop();
	}
	
	public void shootSound() {
		shoot.play();
	}
	
	public void shootStop() {
		if(shoot.getFramePosition() > 8500)
			shoot.stop();
	}
	
	public void explosionSound() {
		explosion.changeVolume(-18.0f);
		explosion.play();
	}
}
