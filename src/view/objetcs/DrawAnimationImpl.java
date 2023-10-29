package view.objetcs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import model.utils.Animation;
import view.utils.Assets;
import view.utils.SoundManager;


public class DrawAnimationImpl implements DrawAnimation{

	private Graphics2D g2d;
	private Animation animation;
	private int length;
	private BufferedImage anim;
	private SoundManager soundManager;
	
	public DrawAnimationImpl(Animation animation, Graphics2D g2, SoundManager soundManager) {
		this.animation = animation;
		this.g2d = g2;
		this.soundManager = soundManager;
	}

	@Override
	public void updateDrawExplosion() {
		
		if(animation.isExplosion())
			soundManager.explosionSound();
		
		BufferedImage[] frames = Assets.exp;
		this.length = frames.length;
		this.anim = frames[animation.getIndex()];
		g2d.drawImage(anim, (int)animation.getPosition().getX(), (int)animation.getPosition().getY(),
					null);
	}

	@Override
	public void drawShield() {
		BufferedImage[] frames = Assets.shieldEffect;
		this.length = frames.length;
		this.anim = frames[animation.getIndex()];
	}
	
	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return this.anim;
	}

}
