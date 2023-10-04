package model.gameobjects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mathgame.Vector2D;
import model.states.GameState;
import view.objetcs.DrawComponent;
import view.objetcs.LaserDrawComponent;
import view.utils.Assets;
import view.utils.Sound;

public class Enemy extends MovingObject {

	private ArrayList<Vector2D> path;
	private boolean following;
	private long fireRate;
	private Sound shoot;
	private EnemyPathStrategy enemyPathStrategy;
	
	public Enemy(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, DrawComponent drawObj, GameState gameState) {
		super(position, velocity, maxVel, texture, drawObj, gameState);
		
		path = new ArrayList<Vector2D>();
		following = true;
		fireRate = 0;
		shoot = new Sound(Assets.enemyShoot);
	}
	
	public void pathEnemy(EnemyPathStrategy enemyPath) {
		this.enemyPathStrategy = enemyPath;
		enemyPath.pathEnemy(path);
	}
	
	public Vector2D pathFollowing() {
		return this.enemyPathStrategy.pathFollowing(this);
	}

	public Vector2D seekForce(Vector2D target) {
		return this.enemyPathStrategy.seekForce(this, target);
	}
	
	@Override
	public void update(float dt) {
		
		fireRate += dt;
		
		Vector2D pathFollowing;
		
		if(following)
			pathFollowing = pathFollowing();
		else
			pathFollowing = new Vector2D();
			
		pathFollowing = pathFollowing.scale(1/Constants.ENEMY_MASS);
		velocity = velocity.add(pathFollowing);
		velocity = velocity.limit(maxVel);
		position = position.add(velocity);
		
		//shoot
		if(fireRate > Constants.ENEMY_FIRERATE) {
			
			Vector2D toPlayer = gameState.getSpace().getPlayer().getCenter().subtract(getCenter());
			
			toPlayer = toPlayer.normalize();
			
			double currentAngle = toPlayer.getAngle();
			
			currentAngle += Math.random()*Constants.ENEMY_ANGLE_RATE - Constants.ENEMY_ANGLE_RATE / 2;
			
			if(toPlayer.getX() < 0)
				currentAngle = -currentAngle + Math.PI;
			
			toPlayer = toPlayer.setDirection(currentAngle);
			
			Laser laser = new Laser(
					getCenter().add(toPlayer.scale(width)),
					toPlayer,
					Constants.LASER_VEL_ENEMY,
					currentAngle + Math.PI/2,
					Assets.redLaser,
					new LaserDrawComponent(),
					gameState
					);

			gameState.getSpace().getMovingObjects().add(0, laser); 
			fireRate = 0;
			shoot.play();
		}
		
		if(shoot.getFramePosition() > 8500) {
			shoot.stop();
		}
		angle += 0.05;
		collidesWith();
	}
	

	@Override
	public void destroy() {
		gameState.addScore(Constants.ENEMY_SCORE,getPosition());
		gameState.playExplosion(position);
		super.destroy();
	}
	
	public ArrayList<Vector2D> getPath() {
		return path;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}		
}
