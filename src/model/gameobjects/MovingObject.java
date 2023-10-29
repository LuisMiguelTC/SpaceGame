package model.gameobjects;

import java.util.concurrent.CopyOnWriteArrayList;
import mathgame.Vector2D;
import model.HitEvent;
import model.Space;

public abstract class MovingObject extends GameObject implements MovingObjectFeatures{
	
	protected Vector2D velocity;
	protected double angle;
	protected double maxVel;
	protected boolean dead;
	private boolean isCollides;
	
	public MovingObject(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel) {
		super(position, dimension);
		this.velocity = velocity;
		this.maxVel = maxVel;
		this.angle = 0;
		dead = false;
		isCollides = false;
	}
	
	@Override
	public void collidesWith(Space space){
		
		CopyOnWriteArrayList<MovingObject> movingObjects = space.getMovingObjects(); 
		
		for(int i = 0; i < movingObjects.size(); i++){
			MovingObject m  = movingObjects.get(i);
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().subtract(getCenter()).getMagnitude();
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.dead && !dead){	
				objectCollision(m, this, space);
			}	
		}
	}
	
	public void objectCollision(MovingObject a, MovingObject b, Space space) {
		
		isCollides = true;
		Player p = null;
		
		if(a instanceof Player)
			p = (Player)a;
		else if(b instanceof Player)
			p = (Player)b;
		
		if(p != null && p.isSpawning()) 
			return;
		
		if(a instanceof Asteroid && b instanceof Asteroid)
			return;
		
		if(!(a instanceof PowerUp || b instanceof PowerUp)){
			a.destroy(space);
			b.destroy(space);
			return;
		}
		
		if(p != null){
			if(a instanceof Player){
				((PowerUp)b).executeAction();
				b.destroy(space);
			}else if(b instanceof Player){
				((PowerUp)a).executeAction();
				a.destroy(space);
			}	
		}
	}
	
	@Override
	public boolean isCollides() {
		return this.isCollides;
	}
	
	@Override
	public void destroy(Space space){
		dead = true;
		space.notifyEventListener(new HitEvent(this));
	}
	
	@Override
	public boolean isDead(){	
		return dead;
	}
	
	public double getAngle() {
		return this.angle;
	}
}