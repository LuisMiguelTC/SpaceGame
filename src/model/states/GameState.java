package model.states;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import controller.engine.GameFactory;
import input.GameInput;
import mathgame.Vector2D;
import model.*;
import model.gameobjects.*;
import model.utils.*;

public class GameState extends State implements Game,SpaceEventListener{

	private Space space;
	private int score = 0;
	private int lives = 5;
	private int waves = 1;
	private int meteors = 1;
	private long gameOverTimer;
	private long enemySpawner;
	private long powerUpSpawner;
	private String namePlayer;
	private GameFactory f;
	private boolean isNewGame;
	private LinkedList<SpaceEvent> eventQueue;
	private boolean checked;
	private int count;
	
	public GameState(String namePlayer) {
		
		this.eventQueue = new LinkedList<SpaceEvent>();
		this.namePlayer = namePlayer;
		this.space = new SpaceImpl();
		this.f = GameFactory.getInstance();
		this.space.setPlayer(f.createPlayer());
		this.space.addGameMessages(f.createGameMessage(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2),
				false, "START GAME", ColorMessage.YELLOW));
		this.space.setEventListener(this);
		this.checked = false;
		this.isNewGame = true;
		gameOverTimer  = 0;
		enemySpawner = 0;
		powerUpSpawner = 0;	
	}
	
	@Override
	public Space getSpace() {
		return this.space;
	}
	
	@Override
	public void startWave() {
		isNewGame = false;
		space.addGameMessages(f.createGameMessage(new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2 + 50), false, 
				"WAVE " + waves, ColorMessage.WHITE));

		double x, y;
		for(int i = 0; i < meteors; i++){
			
			x = i % 2 == 0 ? Math.random()*Constants.WIDTH : 0;
			y = i % 2 == 0 ? 0 : Math.random()*Constants.HEIGHT;
			
			int randomAsteroid = (int)(Math.random()*3);
			
			space.addMovingObject(new Asteroid(
					new Vector2D(x, y),
					new Vector2D(95,80),
					new Vector2D(0, 1).setDirection(Math.random()*Math.PI*2),
					Constants.ASTEROID_INIT_VEL*Math.random() + 1,
					randomAsteroid,
					Size.BIG
					));
		}
		meteors ++;
		waves++;
	}

	@Override
	public void spawnEnemy() {
		
		int rand = (int) (Math.random()*2);
		double x = rand == 0 ? (Math.random()*Constants.WIDTH): Constants.WIDTH;
		double y = rand == 0 ? Constants.HEIGHT : (Math.random()*Constants.HEIGHT);
		
		space.addMovingObject(f.createEnemy(new Vector2D(x, y), new Vector2D(55,55)));
		space.getEnemy().setpathEnemy(new BasicPathEnemy());
	}

	@Override
	public void spawnPowerUp() {
		
		final int x = (int) ((Constants.WIDTH - 20)* Math.random());
		final int y = (int) ((Constants.HEIGHT -20)* Math.random());
		int index = (int) (Math.random() * (PowerUpTypes.values().length));
		
		PowerUpTypes p = PowerUpTypes.values()[index];
		final String text = p.text;
		Action action = null;
		Vector2D position = new Vector2D(x , y);
		
		switch(p) {
			case LIFE -> action = () -> {
					lives ++;
					space.addGameMessages(f.createActionGameMessage(position, text, ColorMessage.GREEN));
			};
			
			case SHIELD -> action = () -> {
					space.getPlayer().setShield();
					space.addGameMessages(f.createActionGameMessage(position, text, ColorMessage.DARK_GRAY));
				};
			case SCORE_X2 -> action = ()-> {
					space.getPlayer().setDoubleScore();
					space.addGameMessages(f.createActionGameMessage(position, text, ColorMessage.YELLOW));
				};
			case FASTER_FIRE  -> action = ()-> {
					space.getPlayer().setFastFire();
					space.addGameMessages(f.createActionGameMessage(position, text, ColorMessage.BLUE));
				};
			case SCORE_STACK  -> action = ()-> {
					score += 1000;
					space.addGameMessages(f.createActionGameMessage(position, text, ColorMessage.MAGENTA));
				};
			case DOUBLE_GUN  -> action = ()-> {
					space.getPlayer().setDoubleGun();
					space.addGameMessages(f.createActionGameMessage(position, text, ColorMessage.ORANGE));
				};
			default -> throw new IllegalArgumentException();
		}	
		
		this.space.addMovingObject(new PowerUp(
				position,
				new Vector2D(64,64),
				action,
				p
				));
	}
	
	@Override
	public void addScore(int value, Vector2D position) {
		
		if(checked) {
			ColorMessage c = ColorMessage.WHITE; 
			String text = "+" + value + " score";
			if(space.getPlayer().isDoubleScoreOn()){
				c = ColorMessage.YELLOW;
				value = value * 2;
				text = "+" + value + " score";
			}
			score += value;
			space.addGameMessages(f.createActionGameMessage(position, text, c));
		}
		else 
			throw new IllegalStateException();
	}

	@Override
	public int getScore(){
		return this.score;
	}
	
	@Override
	public int getNumberLives() {
		return this.lives;
	}
	
	@Override
	public String getNamePlayer() {
		return this.namePlayer;
	}
	
	@Override
	public boolean isGameOver() {
		return lives == 0; 
	}
	
	@Override
	public boolean isNewGame() {
		return this.isNewGame;
	}
	
	@Override
	public void update(float dt){

		space.updateSpace(dt);
		checkEvents();
		
		if(GameInput.PAUSE) { 
			State.setState(new PauseState(this));
		}
	
		if(isGameOver())
			gameOverTimer += dt;
	
		powerUpSpawner += dt;
		enemySpawner += dt;
		
		if(gameOverTimer > Constants.GAME_OVER_TIME) {
			try {
				ArrayList<ScoreData> dataList = JSONParser.readFile();
				dataList.add(new ScoreData(score, namePlayer, count));
				JSONParser.writeFile(dataList);	
			} catch (IOException e) {
				e.printStackTrace();
			}
			State.setState(new MenuState());
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
			if(space.getMovingObjects().get(i) instanceof Asteroid)
				return;
		
		startWave();
	}
	
	private void checkEvents() {
		this.checked = true;
		Space space = this.getSpace();
		eventQueue.stream().forEach(ev->{
			if(ev instanceof HitEvent) {
				HitEvent hitevent = (HitEvent) ev;
				MovingObject mo = hitevent.getCollisionMObj();
				if(mo instanceof Player) {
					this.playExplosion(mo.getPosition());
					Player p = (Player) mo;
					if(!this.subtractLife()) {
						space.removeMovingObject(mo);
						this.gameOverMessage();
					}
					p.resetValues();
				}
				else {
					space.removeMovingObject(mo);
					if(!(mo instanceof Laser) && !(mo instanceof PowerUp)) {
						this.playExplosion(mo.getPosition());
						
						if(mo instanceof Enemy || mo instanceof Asteroid)
							this.count++;
		
						if(mo instanceof Enemy)
							this.addScore(Constants.ENEMY_SCORE, mo.getPosition());
						else if (mo instanceof Asteroid) {
							((Asteroid) mo).setIsDivided(true);
							((Asteroid) mo).divideAsteroid(space);
							this.addScore(Constants.ASTEROID_SCORE, mo.getPosition());
						}
					}
				}	
			}	
		});
		eventQueue.clear();
	}
	
	private boolean subtractLife() {
		lives --;
		if(lives > 0) {
			space.addGameMessages(f.createActionGameMessage(
					new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), "-1 LIFE", ColorMessage.RED));	
		}
		return lives > 0;
	}
	
	private void gameOverMessage() {
		space.addGameMessages(f.createGameMessage(
				new Vector2D(Constants.WIDTH/2, Constants.HEIGHT/2), true, "GAME OVER", ColorMessage.WHITE));	
	}

	private void playExplosion(Vector2D position) {
		space.addAnimations(new Animation(
				80,
				position.subtract(new Vector2D (60/2, 62/2))));
	}
	
	@Override
	public void notifyEvent(SpaceEvent ev) {
		eventQueue.add(ev);
	}	
	
	@Override
	public StateType typeState() {
		return StateType.GAME;
	}
}
