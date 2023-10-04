package model;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import model.gameobjects.Enemy;
import model.gameobjects.MeteorImpl;
import model.gameobjects.MovingObject;
import model.gameobjects.PlayerImpl;
import view.utils.Animation;
import view.utils.Message;

public class Space {

	private PlayerImpl playerImpl;
	private Enemy enemy;
	private CopyOnWriteArrayList<MovingObject> movingObjects = new CopyOnWriteArrayList<MovingObject>();
	private CopyOnWriteArrayList<Animation> explosions = new CopyOnWriteArrayList<Animation>();
	private ArrayList<Message> messages = new ArrayList<Message>();
	private int count;
	
	public void setPlayer(PlayerImpl p) {
		playerImpl = p;
	}

	public void addGameObject(MovingObject mo) {
		movingObjects.add(mo);
	}

	public void addGameMessages(Message m) {
		messages.add(m);
	}
	
	public void addAnimations(Animation a) {
		explosions.add(a);
	}

	public void updateSpace(float dt) {
		
		for(int i = 0; i < movingObjects.size(); i++) {
			
			MovingObject mo = movingObjects.get(i);
			mo.update(dt);
			if(mo.isDead()) {
				movingObjects.remove(i);
				i--;
			}
			
			if(mo.isDead() && (mo instanceof Enemy || mo instanceof MeteorImpl))
				this.count++;
		}
			
		for(int i = 0; i < explosions.size(); i++){
			Animation anim = explosions.get(i);
			anim.updateAnimation(dt);
			if(!anim.isRunning()){
				explosions.remove(i);
			}
		}
		
		for(int i = 0; i < messages.size(); i++) {
			Message mess = messages.get(i);
			if(mess.isDead()) {
				messages.remove(i);
			}
		}
	}
	
	public CopyOnWriteArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	
	public CopyOnWriteArrayList<Animation> getExplosions() {
		return explosions;
	}

	public PlayerImpl getPlayer() {
		for(MovingObject p: movingObjects) {
			if(p instanceof PlayerImpl) {
				playerImpl = (PlayerImpl) p;
			}
		}
		return playerImpl;
	}

	public Enemy getEnemy() {
		
		for(MovingObject e: movingObjects) {
			if(e instanceof Enemy) {
				enemy = (Enemy) e;
			}
		}
		return enemy;
	}
	
	public int getCountEnemyDied() {
		return count;
	}
	
}
