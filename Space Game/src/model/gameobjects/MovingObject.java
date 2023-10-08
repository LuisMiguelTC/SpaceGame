package model.gameobjects;

import java.awt.geom.AffineTransform;
import java.util.concurrent.CopyOnWriteArrayList;
import mathgame.Vector2D;
import model.HitEvent;
import model.Space;
//import view.utils.Assets;
//import view.utils.Sound;

public abstract class MovingObject extends GameObject implements MovingObjectFeatures{
	
	protected Vector2D velocity;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	//private Sound explosion;
	protected boolean Dead;
	private boolean isCollides;
	
	public MovingObject(Vector2D position, Vector2D dimension, Vector2D velocity, double maxVel) {
		super(position, dimension);
		this.velocity = velocity;
		this.maxVel = maxVel;
		angle = 0;
		//explosion = new Sound(Assets.explosion);
		Dead = false;
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
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.Dead && !Dead){	
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
		
		if(a instanceof Meteor && b instanceof Meteor)
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
		Dead = true;
		space.notifyEventListener(new HitEvent(this));
		//if(!(this instanceof Laser) && !(this instanceof PowerUp))
			//explosion.play();
	}
	
	@Override
	public boolean isDead() {
		return Dead;
	}
	
	public double getAngle() {
		return this.angle;
	}

	public abstract String getType();
}