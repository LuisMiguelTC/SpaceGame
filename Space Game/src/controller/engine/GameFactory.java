package controller.engine;

import java.awt.Color;

import mathgame.Vector2D;
import model.gameobjects.Constants;
import model.gameobjects.Enemy;
import model.gameobjects.MovingObject;
import model.gameobjects.PlayerImpl;
import model.states.GameState;
import view.objetcs.EnemyDrawComponent;
import view.objetcs.PlayerDrawComponent;
import view.utils.Assets;
import view.utils.Message;

public class GameFactory {
	
	static private GameFactory instance;
		
	static public GameFactory getInstance(){
		if (instance == null){
			instance = new GameFactory();
		}
		return instance;
	}
		
	public MovingObject createPlayer(Vector2D pos, GameState g){
		return new PlayerImpl(pos, new Vector2D(), Constants.PLAYER_MAX_VEL, Assets.player, new PlayerDrawComponent(), g);			
	}
	
	public Message createActionGameMessage(Vector2D pos, String text, Color c) {
		return new Message(pos, false, text, c, true, Assets.fontMed);
	}
	
	public Message createGameMessage(Vector2D pos, boolean fade, String text, Color c) {
		return new Message(pos, fade, text, c, true, Assets.fontBig);
	}
	
	public Enemy createEnemy(Vector2D pos, GameState g) {
		return new Enemy(pos, new Vector2D(), Constants.ENEMY_MAXVEL, Assets.enemy,new EnemyDrawComponent(), g);
	}
}
