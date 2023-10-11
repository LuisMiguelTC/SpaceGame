package controller.engine;

import mathgame.Vector2D;
import model.gameobjects.Constants;
import model.gameobjects.Enemy;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.utils.ColorMessage;
import model.utils.Message;

public class GameFactory {
	
	private Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - 38/2,
			Constants.HEIGHT/2 - 38/2);
	
	static private GameFactory instance;
		
	static public GameFactory getInstance(){
		if (instance == null){
			instance = new GameFactory();
		}
		return instance;
	}
		
	public MovingObject createPlayer(){
		return new Player(PLAYER_START_POSITION, new Vector2D(38.0,38.0),new Vector2D(), Constants.PLAYER_MAX_VEL);			
	}
	
	public Message createActionGameMessage(Vector2D pos, String text, ColorMessage c) {
		return new Message(pos, false, text, c, true, "MED");
	}
	
	public Message createGameMessage(Vector2D pos, boolean fade, String text, ColorMessage c) {
		return new Message(pos, fade, text, c, true, "BIG");
	}
	
	public Enemy createEnemy(Vector2D pos, Vector2D dimension) {
		return new Enemy(pos, dimension, new Vector2D(), Constants.ENEMY_MAXVEL);
	}
}
