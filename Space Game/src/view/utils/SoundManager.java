package view.utils;

public class SoundManager {

	private Sound background;
	Sound shoot = new Sound(Assets.playerShoot);
	Sound explosion = new Sound(Assets.explosion);
	Sound playerLoose = new Sound(Assets.playerLoose);
	
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
