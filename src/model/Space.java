package model;

import java.util.concurrent.CopyOnWriteArrayList;
import model.gameobjects.Enemy;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.utils.Animation;
import model.utils.Message;

public interface Space {

	public void setEventListener(SpaceEventListener l);
	
	public void setPlayer(Player p);

	public void addMovingObject(MovingObject mo);

	public void removeMovingObject(MovingObject collisionMObj);
	
	public void addGameMessages(Message m);
	
	public void addAnimations(Animation a);

	public void updateSpace(float dt);
	
	public CopyOnWriteArrayList<MovingObject> getMovingObjects();
	
	public CopyOnWriteArrayList<Message> getMessages();
	
	public CopyOnWriteArrayList<Animation> getExplosions();
	
	public Player getPlayer();
	
	public Enemy getEnemy();

	public void notifyEventListener(SpaceEvent ev);

	
}
