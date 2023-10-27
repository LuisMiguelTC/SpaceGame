package model;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import model.gameobjects.Enemy;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.utils.Animation;
import model.utils.Message;

public class Space {

	private Optional<Player> player = Optional.empty();
	private Optional<Enemy> enemy = Optional.empty();
	private CopyOnWriteArrayList<MovingObject> movingObjects = new CopyOnWriteArrayList<MovingObject>();
	private CopyOnWriteArrayList<Animation> explosions = new CopyOnWriteArrayList<Animation>();
	private CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<Message>();
	private SpaceEventListener listener;
	
	
	public void setEventListener(SpaceEventListener l) {
		this.listener = l;
	}

	public void addMovingObject(MovingObject mo) {
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
			mo.collidesWith(this);});
			
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
			player = Optional.of((Player) p);});
		return player.get();
	}

	public Enemy getEnemy() throws IllegalStateException {
		movingObjects.forEach(e->{if(e instanceof Enemy)
			enemy = Optional.of((Enemy) e);});
		if(enemy.isEmpty())
			throw new IllegalStateException();
		return enemy.get();	
	}

	public void notifyEventListener(SpaceEvent ev) {
		listener.notifyEvent(ev);
	}
}
