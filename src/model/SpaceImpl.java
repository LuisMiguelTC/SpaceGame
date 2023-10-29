package model;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import model.gameobjects.Enemy;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.utils.Animation;
import model.utils.Message;

public class SpaceImpl implements Space{

	private Optional<Player> player = Optional.empty();
	private Optional<Enemy> enemy = Optional.empty();
	private CopyOnWriteArrayList<MovingObject> movingObjects = new CopyOnWriteArrayList<MovingObject>();
	private CopyOnWriteArrayList<Animation> explosions = new CopyOnWriteArrayList<Animation>();
	private CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<Message>();
	private SpaceEventListener listener;
	
	@Override
	public void setEventListener(SpaceEventListener l) {
		this.listener = l;
	}
	
	@Override
	public void setPlayer(Player p) {
		player = Optional.of(p);
		movingObjects.add(player.get());
	}
	
	@Override
	public void addMovingObject(MovingObject mo) {
		movingObjects.add(mo);
	}

	@Override
	public void removeMovingObject(MovingObject collisionMObj) {
		movingObjects.remove(collisionMObj);
	}
	
	@Override
	public void addGameMessages(Message m) {
		messages.add(m);
	}
	
	@Override
	public void addAnimations(Animation a) {
		explosions.add(a);
	}
	
	@Override
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
	
	@Override
	public CopyOnWriteArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	@Override
	public CopyOnWriteArrayList<Message> getMessages() {
		return messages;
	}
	
	@Override
	public CopyOnWriteArrayList<Animation> getExplosions() {
		return explosions;
	}

	@Override
	public Player getPlayer() {
		return player.get();
	}

	@Override
	public Enemy getEnemy() throws IllegalStateException {
		movingObjects.forEach(e->{if(e instanceof Enemy)
			enemy = Optional.of((Enemy) e);});
		if(enemy.isEmpty())
			throw new IllegalStateException();
		return enemy.get();	
	}

	@Override
	public void notifyEventListener(SpaceEvent ev) {
		listener.notifyEvent(ev);
	}
}
