package model.gameobjects;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import mathgame.Vector2D;
import model.states.GameState;
import view.objetcs.DrawComponent;
import view.objetcs.DrawMovingObject;
import view.utils.Assets;
import view.utils.Sound;

public abstract class MovingObject extends GameObject implements MovingObjectFeatures{
	
	protected Vector2D velocity;
	protected AffineTransform at;
	protected double angle;
	protected double maxVel;
	protected int width;
	protected int height;
	protected GameState gameState;
	protected DrawComponent drawObj;
	private Sound explosion;
	protected boolean Dead;
	
	public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, DrawComponent drawObj, GameState gameState) {
		super(position, texture);
		this.velocity = velocity;
		this.maxVel = maxVel;
		this.gameState = gameState;
		width = texture.getWidth();
		height = texture.getHeight();
		angle = 0;
		this.drawObj = drawObj;
		explosion = new Sound(Assets.explosion);
		Dead = false;
	}
	
	@Override
	public void updateDraw(DrawMovingObject g) {
		drawObj.update(this, g);
	}
	
	@Override
	public void collidesWith(){
		
		CopyOnWriteArrayList<MovingObject> movingObjects = gameState.getSpace().getMovingObjects(); 
		
		for(int i = 0; i < movingObjects.size(); i++){
			
			MovingObject m  = movingObjects.get(i);
			
			if(m.equals(this))
				continue;
			
			double distance = m.getCenter().subtract(getCenter()).getMagnitude();
			
			if(distance < m.width/2 + width/2 && movingObjects.contains(this) && !m.Dead && !Dead){
				objectCollision(m, this);
			}
		}
	}
	
	private void objectCollision(MovingObject a, MovingObject b) {
		
		PlayerImpl p = null;
		
		if(a instanceof PlayerImpl)
			p = (PlayerImpl)a;
		else if(b instanceof PlayerImpl)
			p = (PlayerImpl)b;
		
		if(p != null && p.isSpawning()) 
			return;
		
		if(a instanceof MeteorImpl && b instanceof MeteorImpl)
			return;
		
		if(!(a instanceof PowerUp || b instanceof PowerUp)){
			a.destroy();
			b.destroy();
			
			return;
		}
		
		if(p != null){
			if(a instanceof PlayerImpl){
				((PowerUp)b).executeAction();
				b.destroy();
			}else if(b instanceof PlayerImpl){
				((PowerUp)a).executeAction();
				a.destroy();
			}	
		}
		
	}
	
	
	protected void destroy(){
		Dead = true;
		if(!(this instanceof Laser) && !(this instanceof PowerUp))
			explosion.play();

	}
	
	@Override
	public boolean isDead() {
		return Dead;
	}
	
	public double getAngle() {
		return this.angle;
	}
}