package model.states;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import controller.engine.GameFactory;
import input.GameKeyboard;
import mathgame.Vector2D;
import model.JSONParser;
import model.ScoreData;
import model.Space;
import model.gameobjects.BasicPathEnemy;
import model.gameobjects.Constants;
import model.gameobjects.MeteorImpl;
import model.gameobjects.PowerUp;
import model.gameobjects.PowerUpTypes;
import model.gameobjects.Size;
import ui.Action;
import view.objetcs.EnemyDrawComponent;
import view.objetcs.PowerUpDrawComponent;
import view.state.DrawStateComponent;
import view.state.MenuStateComponent;
import view.state.PauseStateComponent;
import view.utils.Animation;
import view.utils.Assets;
import view.utils.Sound;

public class GameState extends State {
	
	public static final Vector2D PLAYER_START_POSITION = new Vector2D(Constants.WIDTH/2 - Assets.player.getWidth()/2,
			Constants.HEIGHT/2 - Assets.player.getHeight()/2);
	private Space space;
	private int score = 0;
	private int lives = 5;
	private int waves = 1;
	private int meteors = 1;
	private Sound backgroundMusic;
	private long gameOverTimer;
	private long enemySpawner;
	private long powerUpSpawner;
	private String namePlayer;
	private GameFactory f;
	
	public GameState(String namePlayer, DrawStateComponent drawState) {
		super(drawState);
		
		this.f = GameFactory.getInstance();
		this.namePlayer = namePlayer;
		this.space = new Space();
		this.space.addGameObject(f.createPlayer(PLAYER_START_POSITION, this));
		this.space.addGameMessages(f.createGameMessage(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2),false, "START GAME", Color.YELLOW));
		
		backgroundMusic = new Sound(Assets.backgroundMusic);
		backgroundMusic.loop();
		backgroundMusic.changeVolume(-20.0f);
		
		gameOverTimer  = 0;
		enemySpawner = 0;
		powerUpSpawner = 0;	
	}
	
	
	public void addScore(int value, Vector2D position) {
		
		Color c = Color.WHITE;
		String text = "+" + value + " score";
		if(space.getPlayer().isDoubleScoreOn()){
			c = Color.YELLOW;
			value = value * 2;
			text = "+" + value + " score";
		}
		score += value;
		space.addGameMessages(f.createActionGameMessage(position, text, c));
	}
	
	public void divideMeteor(MeteorImpl meteorImpl) {
		
		Size size = meteorImpl.getSize();
		BufferedImage[] textures = size.textures;
		Size newSize = null;
		
		switch(size){
		case BIG:
			newSize =  Size.MED;
			break;
		case MED:
			newSize = Size.SMALL;
			break;
		case SMALL:
			newSize = Size.TINY;
			break;
		default:
			return;
		}
			
		for(int i = 0; i < size.quantity; i++) {
			space.addGameObject(new MeteorImpl(
					meteorImpl.getPosition(),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_INIT_VEL*Math.random() + 1,
					textures[(int)(Math.random()*textures.length)],
					new EnemyDrawComponent(),
					this,
					newSize
					));
		}
	}
	
	private void startWave() {
		space.addGameMessages(f.createGameMessage(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2 + 50), false, 
				"WAVE " + waves, Color.WHITE));

		double x, y;
		for(int i = 0; i < meteors; i++){
			
			x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;
			
			BufferedImage texture = Assets.bigs[(int)(Math.random()*Assets.bigs.length)];
			
			space.addGameObject(new MeteorImpl(
					new Vector2D(x, y),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.METEOR_INIT_VEL*Math.random() + 1,
					texture,
					new EnemyDrawComponent(),
					this,
					Size.BIG
					));
		}
		meteors ++;
		waves++;
	}
	
	public void playExplosion(Vector2D position) {
		space.addAnimations((new Animation(
				Assets.exp,
				50,
				position.subtract(new Vector2D(Assets.exp[0].getWidth()/2, Assets.exp[0].getHeight()/2))
				)));
	}
	
	private void spawnEnemy() {
		
		int rand = (int) (Math.random()*2);
		double x = rand == 0 ? (Math.random()*Constants.WIDTH): Constants.WIDTH;
		double y = rand == 0 ? Constants.HEIGHT : (Math.random()*Constants.HEIGHT);
		
		space.addGameObject(f.createEnemy(new Vector2D(x, y),this));
		space.getEnemy().pathEnemy(new BasicPathEnemy());	
	}

	private void spawnPowerUp() {
		
		final int x = (int) ((Constants.WIDTH - Assets.orb.getWidth()) * Math.random());
		final int y = (int) ((Constants.HEIGHT - Assets.orb.getHeight()) * Math.random());
		int index = (int) (Math.random() * (PowerUpTypes.values().length));
		
		PowerUpTypes p = PowerUpTypes.values()[index];
		final String text = p.text;
		Action action = null;
		Vector2D position = new Vector2D(x , y);
		
		switch(p) {
		case LIFE:
			action = new Action() {
				@Override
				public void doAction() {
					lives ++;
					space.addGameMessages(f.createActionGameMessage(position, text, Color.GREEN));
				}
			};
			break;
		case SHIELD:
			action = new Action() {
				@Override
				public void doAction() {
					space.getPlayer().setShield();
					space.addGameMessages(f.createActionGameMessage(position, text, Color.DARK_GRAY));
				}
			};
			break;
		case SCORE_X2:
			action = new Action() {
				@Override
				public void doAction() {
					space.getPlayer().setDoubleScore();
					space.addGameMessages(f.createActionGameMessage(position, text, Color.YELLOW));
				}
			};
			break;
		case FASTER_FIRE:
			action = new Action() {
				@Override
				public void doAction() {
					space.getPlayer().setFastFire();
					space.addGameMessages(f.createActionGameMessage(position, text, Color.BLUE));
				}
			};
			break;
		case SCORE_STACK:
			action = new Action() {
				@Override
				public void doAction() {
					score += 1000;
					space.addGameMessages(f.createActionGameMessage(position, text, Color.MAGENTA));
				}
			};
			break;
		case DOUBLE_GUN:
			action = new Action() {
				@Override
				public void doAction() {
					space.getPlayer().setDoubleGun();
					space.addGameMessages(f.createActionGameMessage(position, text, Color.ORANGE));
				}
			};
			break;
		default:
			break;
		}
		
		this.space.addGameObject(new PowerUp(
				position,
				p.texture,
				action, 
				new PowerUpDrawComponent(),
				this
				));

	}
	
	public void update(float dt){
		
		if(GameKeyboard.PAUSE) 
			State.setState(new MenuPauseState(this, new PauseStateComponent()));
		
		space.updateSpace(dt);
		//checkEvents.checkEvent(this, events);
		if(isGameOver())
			gameOverTimer += dt;
	
		powerUpSpawner += dt;
		enemySpawner += dt;
		
		if(gameOverTimer > Constants.GAME_OVER_TIME) {
			try {
				ArrayList<ScoreData> dataList = JSONParser.readFile();
				dataList.add(new ScoreData(score, this.namePlayer, space.getCountEnemyDied()));
				JSONParser.writeFile(dataList);	
			} catch (IOException e) {
				e.printStackTrace();
			}
			backgroundMusic.stop();
			State.setState(new MenuState(new MenuStateComponent()));
		}
		
		if(powerUpSpawner > Constants.POWER_UP_SPAWN_TIME) {
			spawnPowerUp();
			powerUpSpawner = 0;
		}
		
		if(enemySpawner > Constants.ENEMY_SPAWN_RATE){
			spawnEnemy();
			enemySpawner = 0;
		}
		
		for(int i = 0; i < space.getMovingObjects().size(); i++)
			if(space.getMovingObjects().get(i) instanceof MeteorImpl)
				return;
		
		startWave();
	}

	public int getScore(){
		return this.score;
	}
	
	public int getNumberLives() {
		return this.lives;
	}
	
	public Space getSpace() {
		return this.space;
	}
	
	public String getNamePlayer() {
		return this.namePlayer;
	}
	
	public boolean subtractLife() {
		lives --;
		if(lives > 0) {
			space.addGameMessages(f.createActionGameMessage(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), "-1 LIFE", Color.RED));	
		}
		return lives > 0;
	}
	
	public void gameOver() {
		space.addGameMessages(f.createGameMessage(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), true, "GAME OVER", Color.WHITE));	
	}
	
	public boolean isGameOver() {
		return lives == 0; 
	}
}
