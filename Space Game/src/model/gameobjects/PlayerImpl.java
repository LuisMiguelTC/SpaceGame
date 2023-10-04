package model.gameobjects;

import java.awt.image.BufferedImage;

import input.GameKeyboard;
import mathgame.Vector2D;
import model.states.GameState;
import view.objetcs.DrawComponent;
import view.objetcs.LaserDrawComponent;
import view.utils.Animation;
import view.utils.Assets;
import view.utils.Sound;

public class PlayerImpl extends MovingObject implements Player{
	
	private Vector2D heading;	
	private Vector2D acceleration;

	private boolean accelerating = false;
	private long fireRate, fireSpeed;
	private boolean spawning, visible;
	private long spawnTime, flickerTime, shieldTime, doubleScoreTime, fastFireTime, doubleGunTime;
	private Sound shoot, loose;
	private boolean shieldOn, doubleScoreOn, fastFireOn, doubleGunOn;;
	private Animation shieldEffect;
	
	
	
	public PlayerImpl(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, DrawComponent draw, GameState gameState) {
		super(position, velocity, maxVel, texture, draw, gameState);
		
		heading = new Vector2D(0, 1);
		acceleration = new Vector2D();
		fireRate = 0;
		spawnTime = 0;
		flickerTime = 0;
		shieldTime = 0;
		fastFireTime = 0;
		doubleGunTime = 0;
		
		shoot = new Sound(Assets.playerShoot);
		loose = new Sound(Assets.playerLoose);
		
		shieldEffect = new Animation(Assets.shieldEffect, 80, null);
		visible = true;
	}
	
	@Override
	public void update(float dt) {
		
		fireRate += dt;
		
		if(shieldOn)
			shieldTime += dt;
		
		if(doubleScoreOn)
			doubleScoreTime += dt;
		
		if(fastFireOn) {
			fireSpeed = Constants.FIRERATE / 2;
			fastFireTime += dt;
		}else {
			fireSpeed = Constants.FIRERATE;
		}
		
		if(doubleGunOn)
			doubleGunTime += dt;
		
		if(shieldTime > Constants.SHIELD_TIME) {
			shieldTime = 0;
			shieldOn = false;
		}
		
		if(doubleScoreTime > Constants.DOUBLE_SCORE_TIME) {
			doubleScoreOn = false;
			doubleScoreTime = 0;
		}
		
		if(fastFireTime > Constants.FAST_FIRE_TIME) {
			fastFireOn = false;
			fastFireTime = 0;
		}
		
		if(doubleGunTime > Constants.DOUBLE_GUN_TIME) {
			doubleGunOn = false;
			doubleGunTime = 0;
		}
		
		if(spawning) {
			
			flickerTime += dt;
			spawnTime += dt;
			
			if(flickerTime > Constants.FLICKER_TIME) {
				
				visible = !visible;
				flickerTime = 0;
			}
			
			if(spawnTime > Constants.SPAWNING_TIME) {
				spawning = false;
				visible = true;
			}
			
		}
		
		if(GameKeyboard.SHOOT &&  fireRate > fireSpeed  && !spawning) {
			
			if(doubleGunOn) {
				Vector2D leftGun = getCenter();
				Vector2D rightGun = getCenter();
				
				Vector2D temp = new Vector2D(heading);
				temp.normalize();
				temp = temp.setDirection(angle - 1.3f);
				temp = temp.scale(width);
				rightGun = rightGun.add(temp);
				
				temp = temp.setDirection(angle - 1.9f);
				leftGun = leftGun.add(temp);
				
				Laser l = new Laser(leftGun, heading, Constants.LASER_VEL, angle, Assets.blueLaser, new LaserDrawComponent(), gameState);
				Laser r = new Laser(rightGun, heading, Constants.LASER_VEL, angle, Assets.blueLaser, new LaserDrawComponent(), gameState);
				
				gameState.getSpace().getMovingObjects().add(0, l);
				gameState.getSpace().getMovingObjects().add(0, r);
			}
			else{		
				gameState.getSpace().getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					heading,
					Constants.LASER_VEL,
					angle,
					Assets.blueLaser,
					new LaserDrawComponent(),
					gameState
					));
			}
			fireRate = 0;
			shoot.play();
		}
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		
		if(GameKeyboard.RIGHT)
			angle += Constants.DELTAANGLE;
		if(GameKeyboard.LEFT)
			angle -= Constants.DELTAANGLE;
		
		if(GameKeyboard.UP){
			acceleration = heading.scale(Constants.ACC);
			accelerating = true;
		}else{
			if(velocity.getMagnitude() != 0)
				acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC/15);
			accelerating = false;
		}
		
		velocity = velocity.add(acceleration);
		
		velocity = velocity.limit(maxVel);
		
		heading = heading.setDirection(angle - Math.PI/2);
		position = position.add(velocity);
		
		
		if(position.getX() > Constants.WIDTH)
			position.setX(0);
		if(position.getY() > Constants.HEIGHT)
			position.setY(0);
		
		if(position.getX() < -width)
			position.setX(Constants.WIDTH);
		if(position.getY() < -height)
			position.setY(Constants.HEIGHT);
		
		if(shieldOn)
			shieldEffect.updateAnimation(dt);
		
		collidesWith();
	}
	
	@Override
	public void setShield() {
		if(shieldOn)
			shieldTime = 0;
		shieldOn = true;
	}
	
	@Override
	public void setDoubleScore() {
		if(doubleScoreOn)
			doubleScoreTime = 0;
		doubleScoreOn = true;
	}
	
	@Override
	public void setFastFire() {
		if(fastFireOn)
			fastFireTime = 0;
		fastFireOn = true;
	}
	
	@Override
	public void setDoubleGun() {
		if(doubleGunOn)
			doubleGunTime = 0;
		doubleGunOn = true;
	}

	@Override
	public void destroy() {
		spawning = true;
		gameState.playExplosion(position);
		spawnTime = 0;
		loose.play();
		if(!gameState.subtractLife()) {
			gameState.gameOver();
			super.destroy();
		}
		resetValues();
		
	}
	
	@Override
	public void resetValues() {
		angle = 0;
		velocity = new Vector2D();
		setPosition(GameState.PLAYER_START_POSITION);
	}
	
	@Override
	public boolean isSpawning(){
		return spawning;
	}
	@Override
	
	public boolean isShieldOn(){
		return shieldOn;
	}
	@Override
	public boolean isDoubleScoreOn(){
		return doubleScoreOn;
	}
	
	@Override
	public boolean isAccelerating() {
		return this.accelerating;
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}
	
	@Override
	public boolean isDoubleGunOn() {
		return this.doubleGunOn;
	}
	
	@Override
	public Animation shieldEffect() {
		return this.shieldEffect;
	}
	
}