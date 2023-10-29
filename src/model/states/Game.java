package model.states;

import mathgame.Vector2D;
import model.Space;

public interface Game {

	public Space getSpace();
	
	public void startWave();
	
	public void spawnEnemy();
	
	public void spawnPowerUp();
	
	public void addScore(int value, Vector2D position);
	
	public int getScore();
	
	public int getNumberLives();
	
	public String getNamePlayer();
	
	public boolean isNewGame();
	
	public boolean isGameOver();
}
