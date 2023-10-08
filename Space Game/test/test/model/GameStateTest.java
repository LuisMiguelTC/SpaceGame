package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import input.GameKeyboard;
import mathgame.Vector2D;
import model.Space;
import model.gameobjects.Meteor;
import model.gameobjects.MovingObject;
import model.gameobjects.Player;
import model.states.GameState;
import model.states.State;

public class GameStateTest {

	GameState gameState = new GameState("Miguel");
	float dt = 20.0f;
	
	@org.junit.Test
	public void CollisionTest1() {

		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		//creo un nemico in posizione casuale
		gameState.spawnEnemy();
		assertEquals(2,space.getMovingObjects().size());
		//setto la posizione del player a quella del nemico
		Vector2D enemyposition = space.getEnemy().getPosition();
		player.setPosition(enemyposition);
		//aggiorno lo stato del gioco e quindi ci si aspetta che il nemico sia morto
		gameState.update(dt);	
		assertTrue(space.getEnemy().isDead());
		//controlliamo il punteggio 
		assertEquals(40, gameState.getScore());
		//ci si aspetta che sia rimasto un solo oggetto ma l'aggiornamento del gamestate
		//implica l'aggiunta del primo wave e quindi di un meteorito
		assertEquals(2,space.getMovingObjects().size());
		List<String> types = space.getMovingObjects().stream()
				.map(m->m.getType()).collect(Collectors.toList());
		assertEquals(types, Arrays.asList("PLAYER", "METEOR"));
	}
	
	@org.junit.Test
	public void CollisionTest2() {
		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		//faccio partire il wave
		gameState.update(dt);
		//il player spara e quindi viene aggiunto l'oggetto laser
		GameKeyboard.SHOOT = true;
		player.setFireRate(800);
		player.setSpawning(false);
		player.update(dt, space);
		assertTrue(player.isShooting());

		//viene settato l'oggetto meteor e si verifica subito la collisione con l'oggetto laser
		Meteor meteor = (Meteor) space.getMovingObjects().get(2); 
		assertFalse(meteor.isCollides());
		//avviciniamo l'oggetto meteor a l'oggetto laser
		meteor.setPosition(new Vector2D(420,200));
		
		//si aggiorna diverse volte la posizione dell'oggetto meteor e si verifica
		//nuovamente la collisione, la scelta della direzione del meteorito è non 
		//deterministica quindi cambierà sempre.
		meteor.update(dt, space);
		meteor.update(dt, space);
		meteor.update(dt, space);
		meteor.update(dt, space);
		meteor.collidesWith(space);
		assertTrue(meteor.isCollides());
		
		//verifichiamo che il meteorito sia stato diviso a metà
		//e il punteggio aggiornato
		gameState.update(dt);
		assertTrue(meteor.isDivided());
		assertTrue(!space.getMovingObjects().contains(meteor));
		assertEquals(20,gameState.getScore());
		List<String> types = space.getMovingObjects().stream()
				.map(m->m.getType()).collect(Collectors.toList());
		assertEquals(types, Arrays.asList("PLAYER", "METEOR","METEOR"));	
	
	}
	
	@org.junit.Test
	public void CollisionTest3() {
		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		//vengnono aggiunti 3 nemici
		gameState.spawnEnemy();
		gameState.spawnEnemy();
		gameState.spawnEnemy();
		List<MovingObject> enemies = space.getMovingObjects().stream()
				.filter(m->m.getType().equals("ENEMY")).collect(Collectors.toList());
		
		//l'oggetto player collisiona con tutti e tre nemici singolarmente, e dopo ogni collisione
		//viene settata la generazione del giocatore a false, così da esser pronto 
		//a collidere immediatamente, senza dover attendere lo spawning time.
		player.objectCollision(enemies.get(0), player, space);
		player.setSpawning(false);
		player.objectCollision(enemies.get(1), player , space);
		player.setSpawning(false);
		player.objectCollision(player, enemies.get(2), space);
		player.setSpawning(false);
		gameState.CheckEvents();
		assertEquals(1, space.getMovingObjects().size());
		assertEquals(2, gameState.getNumberLives());
		assertEquals(120, gameState.getScore());
		//verifichiamo che il player muore definitivamente quando le sue 
		//vite raggiungono lo zero
		player.destroy(space);
		player.destroy(space);
		gameState.update(dt);
		assertTrue(gameState.isGameOver());
		//dal GameOver al Menu
		assertTrue(State.getCurrentState().isEmpty());
		//superiamo la soglia del GameOverTime
		gameState.update(3500);
		assertTrue(State.getCurrentState().isPresent());	
	}

}
