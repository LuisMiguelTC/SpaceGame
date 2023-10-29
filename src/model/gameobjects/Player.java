package model.gameobjects;

import input.GameInput;
import mathgame.Vector2D;
import model.HitEvent;
import model.Space;
import model.utils.Animation;

public class Player extends MovingObject implements PlayerFeatures{
	
	private Vector2D heading;	
	private Vector2D acceleration;
	private Vector2D initialPosition;
	private Vector2D temPosition;
	private boolean spawning, visible;
	private boolean accelerating = false;
	private boolean shooting;
	private boolean shieldOn, doubleScoreOn, fastFireOn, doubleGunOn;
	private long fireRate, fireSpeed;
	private long spawnTime, flickerTime, shieldTime, doubleScoreTime, fastFireTime, doubleGunTime;
	private long decelerationTime;
	private Animation shieldEffect;

	public Player(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel) {
		super(position, dimension, velocity, maxVel);
		
		initialPosition = position;
		heading = new Vector2D(0,1);
		acceleration = new Vector2D(0,0);
		temPosition = new Vector2D(0,0);
		fireRate = 0;
		spawnTime = 0;
		flickerTime = 0;
		shieldTime = 0;
		decelerationTime = 0;
		fastFireTime = 0;
		doubleGunTime = 0;
		shieldEffect = new Animation(80, null);
		visible = true;
	}
	
	@Override
	public void update(float dt, Space space) {

		temPosition = position;
		
		fireRate += dt;
		
		if(fireRate > dt*4) 
			shooting = false;
		
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
		
		if(GameInput.SHOOT &&  fireRate > fireSpeed && !spawning) {	
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
				
				space.getMovingObjects().add(0, new Laser(leftGun, new Vector2D(9.0,54.0), heading, Constants.LASER_VEL, angle, "BLUE"));
				space.getMovingObjects().add(0, new Laser(rightGun, new Vector2D(9.0,54.0), heading, Constants.LASER_VEL, angle,"BLUE"));
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
			shooting = true;
		}

		if(GameInput.RIGHT)
			angle += Constants.DELTAANGLE;
		if(GameInput.LEFT)
			angle -= Constants.DELTAANGLE;

		if(GameInput.UP){
			accelerating = true;
			acceleration = heading.scale(Constants.ACC);	
			velocity = velocity.add(acceleration);
			velocity = velocity.limit(maxVel);
			temPosition = temPosition.add(velocity);
			
		}else{
			accelerating = false;
			decelerationTime += dt;
			
			if(velocity.getMagnitude() != 0 ) {
				acceleration = (velocity.scale(-1).normalize()).scale(Constants.DEC);
				velocity = velocity.add(acceleration);
				temPosition = temPosition.add(velocity);
			}
		}
		
		if (decelerationTime > Constants.DECELERATION_TIME) {
			velocity = new Vector2D(0,0);
			decelerationTime = 0;
		}
		
		heading = heading.setDirection(angle - Math.PI/2);
		position = temPosition;
		
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
	public Vector2D getVelocity() {
		return this.velocity;
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
	public void setFireRate(long fireRate) {
		this.fireRate = fireRate;
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
	}

	@Override
	public void resetValues() {
		angle = 0;
		velocity = new Vector2D(0,0);
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
	public GameObjectType getType() {
		return GameObjectType.PLAYER;
	}
}