package model.gameobjects;

import java.util.ArrayList;
import mathgame.Vector2D;
import model.Space;


public class Enemy extends MovingObject implements EnemyFeatures{

	private ArrayList<Vector2D> path;
	private boolean following;
	private long fireRate;
	private EnemyPathStrategy enemyPathStrategy;
	private boolean isShooting;
	
	public Enemy(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel) {
		super(position, dimension, velocity, maxVel);
		
		path = new ArrayList<Vector2D>();
		following = true;
		fireRate = 0;
	}
	
	@Override
	public void setpathEnemy(EnemyPathStrategy enemyPath) {
		this.enemyPathStrategy = enemyPath;
		enemyPath.pathEnemy(path);
	}
	
	@Override
	public Vector2D getpathFollowing() {
		return this.enemyPathStrategy.pathFollowing(this);
	}
	
	@Override
	public void update(float dt, Space space) {
		
		fireRate += dt;
		
		if(fireRate > dt*2) 
			isShooting = false;
		Vector2D pathFollowing;
		
		if(following)
			pathFollowing = getpathFollowing();
		else
			pathFollowing = new Vector2D();
			
		pathFollowing = pathFollowing.scale(1/Constants.ENEMY_MASS);
		velocity = velocity.add(pathFollowing);
		velocity = velocity.limit(maxVel);
		position = position.add(velocity);
		
		//shoot
		if(fireRate > Constants.ENEMY_FIRERATE) {
			
			Vector2D toPlayer = space.getPlayer().getCenter().subtract(getCenter());
			toPlayer = toPlayer.normalize();
			
			double currentAngle = toPlayer.getAngle();
			currentAngle += Math.random()*Constants.ENEMY_ANGLE_RATE - Constants.ENEMY_ANGLE_RATE / 2;
			
			if(toPlayer.getX() < 0)
				currentAngle = -currentAngle + Math.PI;
			
			toPlayer = toPlayer.setDirection(currentAngle);
			Laser laser = new Laser(
					getCenter().add(toPlayer.scale(width)),
					new Vector2D(9.0,54.0),
					toPlayer,
					Constants.LASER_VEL_ENEMY,
					currentAngle + Math.PI/2,
					"RED"
					);
			space.getMovingObjects().add(0, laser); 
			fireRate = 0;
			isShooting = true;
		}
		
		angle += 0.05;
	}
	
	@Override
	public ArrayList<Vector2D> getPath() {
		return path;
	}

	@Override
	public void setFollowing(boolean following) {
		this.following = following;
	}

	@Override
	public boolean isShooting() {
		return this.isShooting;
	}
	
	@Override
	public String getType() {
		return "ENEMY";
	}
}
