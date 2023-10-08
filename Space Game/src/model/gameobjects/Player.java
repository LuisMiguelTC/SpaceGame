package model.gameobjects;

import input.GameKeyboard;
import mathgame.Vector2D;
import model.HitEvent;
import model.Space;
import model.utils.Animation;
//import view.utils.Assets;
//import view.utils.Sound;

public class Player extends MovingObject implements PlayerFeatures{
	
	private Vector2D heading;	
	private Vector2D acceleration;
	private Vector2D initialPosition;
	private boolean accelerating = false;
	private boolean shooting;
	private long fireRate, fireSpeed;
	private boolean spawning, visible;
	private long spawnTime, flickerTime, shieldTime, doubleScoreTime, fastFireTime, doubleGunTime;
	private boolean shieldOn, doubleScoreOn, fastFireOn, doubleGunOn;
	private Animation shieldEffect;
	
	
	
	public Player(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel) {
		super(position, dimension, velocity, maxVel);
		
		initialPosition = position;
		heading = new Vector2D(0, 1);
		acceleration = new Vector2D();
		fireRate = 0;
		spawnTime = 0;
		flickerTime = 0;
		shieldTime = 0;
		fastFireTime = 0;
		doubleGunTime = 0;
		shooting = false;
		/*shoot = new Sound(Assets.playerShoot);
		loose = new Sound(Assets.playerLoose);*/
		
		shieldEffect = new Animation(80, null);
		visible = true;
	}
	
	@Override
	public void update(float dt, Space space) {
		
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
			shieldOn = false;
			shieldTime = 0;
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
			
			shooting = true;
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
				
				Laser l = new Laser(leftGun, new Vector2D(9.0,54.0), heading, Constants.LASER_VEL, angle, "BLUE");
				Laser r = new Laser(rightGun, new Vector2D(9.0,54.0), heading, Constants.LASER_VEL, angle,"BLUE");
				
				space.getMovingObjects().add(0, l);
				space.getMovingObjects().add(0, r);
			}
			else{		
				space.getMovingObjects().add(0,new Laser(
					getCenter().add(heading.scale(width)),
					new Vector2D(9.0,54.0),
					heading,
					Constants.LASER_VEL,
					angle,
					"BLUE"
					));
			}	
			fireRate = 0;	
			//shoot.play();
		}
		//System.out.println(shoot.getFramePosition());
		
		/*if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}*/
		
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
	public void destroy(Space space) {
		spawning = true;
		spawnTime = 0;
		space.notifyEventListener(new HitEvent(this));
		//this.loose.play();
	}
	
	@Override
	public void resetValues() {
		angle = 0;
		velocity = new Vector2D();
		setPosition(initialPosition);
	}
	
	@Override
	public boolean isSpawning(){
		return spawning;
	}
	
	@Override
	public void setSpawning(boolean spawning){
		this.spawning = spawning;
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
	
	@Override
	public boolean isShooting() {
		return this.shooting;
	}

	@Override
	public String getType() {
		return "PLAYER";
	}

	@Override
	public void setFireRate(long fireRate) {
		this.fireRate = fireRate;
	}
}