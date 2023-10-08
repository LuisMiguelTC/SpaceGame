package model;

import java.util.concurrent.CopyOnWriteArrayList;
import model.gameobjects.Enemy;
import model.gameobjects.Meteor;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.utils.Animation;
import model.utils.Message;

public class Space {

	private Player player;
	private Enemy enemy;
	private CopyOnWriteArrayList<MovingObject> movingObjects = new CopyOnWriteArrayList<MovingObject>();
	private CopyOnWriteArrayList<Animation> explosions = new CopyOnWriteArrayList<Animation>();
	private CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<Message>();
	private int count;
	private SpaceEventListener listener;
	
	public void setEventListener(SpaceEventListener l) {
		this.listener = l;
	}

	public void addGameObject(MovingObject mo) {
		movingObjects.add(mo);
	}

	public void removeMovingObject(MovingObject collisionMObj) {
		movingObjects.remove(collisionMObj);
	}
	
	public void addGameMessages(Message m) {
		messages.add(m);
	}
	
	public void addAnimations(Animation a) {
		explosions.add(a);
	}

	public void updateSpace(float dt) {
		
		movingObjects.forEach(mo->{
			mo.update(dt, this);
			mo.collidesWith(this);
			if(mo.isDead() && (mo instanceof Enemy || mo instanceof Meteor))
				this.count++;});
			
		explosions.forEach(anim ->{
			anim.updateAnimation(dt);
			if(!anim.isRunning()){
				explosions.remove(0);}});
		
		messages.forEach(mess->{
			if(mess.isDead())
				messages.remove(0);});
	}
	
	public CopyOnWriteArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	public CopyOnWriteArrayList<Message> getMessages() {
		return messages;
	}
	
	public CopyOnWriteArrayList<Animation> getExplosions() {
		return explosions;
	}

	public Player getPlayer() {
		movingObjects.forEach(p->{if(p instanceof Player)
			player = (Player) p;});
		return player;
	}

	public Enemy getEnemy() {
		movingObjects.forEach(e->{if(e instanceof Enemy)
			enemy = (Enemy) e;});
		return enemy;
	}
	
	public int getCountEnemyDied() {
		return count;
	}
	
	public void notifyEventListener(SpaceEvent ev) {
		listener.notifyEvent(ev);
	}
}
