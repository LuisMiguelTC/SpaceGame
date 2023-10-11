package test.model;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
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
import model.states.MenuState;
import model.states.ScoreState;
import model.states.State;
import model.utils.JSONParser;

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
		assertTrue(space.getMovingObjects().stream()
				.map(m->m.getType()).collect(Collectors.toList())
				.containsAll(Arrays.asList("PLAYER", "METEOR")));
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
		//posizione centrale player
		assertEquals(new Vector2D(500,300).toString(),player.getCenter().toString());
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
		assertTrue(space.getMovingObjects().stream()
				.map(m-> m.getType()).collect(Collectors.toList())
				.containsAll(Arrays.asList("PLAYER", "METEOR","METEOR")));	
	
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
		//superiamo la soglia del GameOverTime
		gameState.update(3500);
		assertTrue(State.getCurrentState().isPresent());	
	}
	
	@org.junit.Test
	public void StateTest() {
		
		MenuState menuState = new MenuState();
		ScoreState scoreState = null;
		
		State.setState(menuState);
		assertTrue(menuState.getButtons().stream()
				.map(b->b.getText())
				.collect(Collectors.toList())
				.containsAll(Arrays.asList("PLAY", "HIGHEST SCORES", "EXIT")));
		
		State.setState(new GameState("Luis Miguel"));
		gameState = (GameState) State.getCurrentState().get();
		assertEquals("Luis Miguel", gameState.getNamePlayer());
		Space space = gameState.getSpace();
		space.getPlayer().destroy(space);
		space.getPlayer().destroy(space);
		space.getPlayer().destroy(space);
		space.getPlayer().destroy(space);
		space.getPlayer().destroy(space);
		gameState.update(3500);
		assertTrue("MENU".equals(State.getCurrentState().get().typeState()));
		menuState.getButtons().get(1).getAction().doAction();
		assertTrue("SCORE".equals(State.getCurrentState().get().typeState()));
		scoreState = (ScoreState) State.getCurrentState().get();
		scoreState.getButton().getAction().doAction();
		assertTrue("MENU".equals(State.getCurrentState().get().typeState()));
	}
	
	@org.junit.Test
	public void TestFail(){
		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		gameState.update(dt);
		try {
			space.getEnemy().isDead();	
		}catch(IllegalStateException e) {
		}catch (Exception e) {
			fail("not enemy yet");
		}
		player.destroy(space);
		try {
			assertEquals(5,gameState.getNumberLives());
		}catch(IllegalStateException e) {
		}catch(Exception ex) {
			fail("space not updated");
		}
		//corretta esecuzione e aggiunta del primo wave
		gameState.update(dt);
		assertEquals(4,gameState.getNumberLives());
		assertEquals(0,gameState.getScore());
		//distruzione dell'oggetto meteorito e aggiunta dello score
		gameState.getSpace().getMovingObjects().get(1).destroy(space);
		gameState.update(dt);
		assertEquals(20,gameState.getScore());
		
	}
	
	@org.junit.Test
    public void TestException() {
		assertThrows(IllegalStateException.class, () -> gameState.getNumberLives());
		assertThrows(IllegalStateException.class, () -> gameState.addScore(20, new Vector2D(400,300)));		
	}
	
	@org.junit.Test
	public void TestPlayer() {
		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		//aggiorniamo lo stato del player
		player.update(dt, space);
		//verifichiamo che il player stia al centro dello schermo come posizione
		//iniziale
		assertEquals(new Vector2D(500,300)
				.subtract(new Vector2D(player.getWidth()/2,player.getHeight()/2)).toString()
				,player.getPosition().toString());
		
		//lo spostiamo in verticale quindi acceleriamo
		GameKeyboard.UP = true;
		player.update(dt, space);
		assertTrue(player.isAccelerating());
		//verifichiamo lo spostamento calcolato con la costante di accelerazione
		//di 0.25, ci si aspetta un valore di 281 - 0.25 = 280.75 della componente Y
		assertEquals(new Vector2D(481,280.75).toString(),player.getPosition().toString());
		//posizione attuale Y  -0.5 
		player.update(dt, space);
		//pozione attuale Y -0.75 e verifichiamo nuovamente la posizione del player
		player.update(dt, space);
		assertEquals(new Vector2D(481,279.5).toString(),player.getPosition().toString());
		//aggiorniamo nuovamente e settiamo la posizione ad una nuova variabile temp
		player.update(dt, space);
		Vector2D tempPosition = player.getPosition();
		//controlliamo la velocità aggiornata
		//dato che stiamo andando in direzione verticale ci si aspetta
		//che la Y del vettore velocità sia -1 mentre la X è sempre la stessa
		assertEquals(-1.0, player.getVelocity().getY());
		
		//inizia la decelarazione quindi ci si aspetta che la velocità non continui ad aumentare di -0.25
		//ma a diminuire di +0.025(scelta come costante di decelerazione e quindi -1 + 0.025 = -0.975
		GameKeyboard.UP = false;
		player.update(dt, space);
		assertFalse(player.isAccelerating());
		assertEquals(player.getPosition().toString(), tempPosition.add(player.getVelocity()).toString());
		//aggiorniamo la posizione temp
		tempPosition = tempPosition.add(player.getVelocity());
		//a -0.975 +0.025 = -0.95 e verifichiamo nuovamente la posizione
		player.update(dt, space);
		assertEquals(player.getPosition().toString(), tempPosition.add(player.getVelocity()).toString());	
	}
	
	@org.junit.Test
	public void TestPowerUp(){
		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		//immaginiamo che il powerUp raccolto sia lo Scorex2
		player.setDoubleScore();
		gameState.spawnEnemy();
		assertEquals(2, space.getMovingObjects().size());
		player.objectCollision(player, space.getEnemy(), space);
		gameState.update(dt);
		assertEquals(80,gameState.getScore());
		//ci si aspetta che il numero di elementi sia pari a 2
		// il player + il meteorito del primo wave
		assertEquals(2, space.getMovingObjects().size());
		
		
		//supponiamo che il powerUp raccolto sia lo scudo(Shield)
		player.setShield();
		//simuliamo la collisione con l'oggetto meteorito
		Meteor meteor = (Meteor) space.getMovingObjects().get(1);
		player.objectCollision(player, meteor, space);
		gameState.update(dt);
		//il numero di elementi sarà lo stesso poichè il meteorito non verrà
		//distrutto ma respinto
		assertEquals(2, space.getMovingObjects().size());
		assertEquals(80, gameState.getScore());
	}
	
	/*@org.junit.Test
	public void TestCasualPowerUp() {
		//IL TEST POTREBBE FALLIRE PERCHE' IL POWERUP VIENE GENERATO IN MANIERA CASUALE
		Space space = gameState.getSpace();
		Player player = space.getPlayer();
		//supponiamo di generare un powerUp casuale
		gameState.spawnPowerUp();
		PowerUp powerUp = space.getPowerUp();
		//controlliamo il tipo di powerUp
		player.objectCollision(player, powerUp, space);
		gameState.update(dt);
		assertEquals(1000, gameState.getScore());
	}*/
	
	@org.junit.Test
	public void JsonTest(){
		
		Space space = gameState.getSpace();
		Player player = gameState.getSpace().getPlayer();
		assertEquals("Miguel", gameState.getNamePlayer());
		gameState.update(dt);
		//setto lo score
		gameState.addScore(500, new Vector2D(100,200));
		//provochiamo il GameOver
		player.destroy(space);
		player.destroy(space);
		player.destroy(space);
		player.destroy(space);
		player.destroy(space);
		gameState.update(3500);
		assertEquals(0, gameState.getNumberLives());
		//avendo generato il gameOver si suppone che i dati siano
		//presenti nel file data.Json
		MenuState menuState = (MenuState) State.getCurrentState().get();
		menuState.getButtons().get(1).getAction().doAction();
		ScoreState scoreState = (ScoreState) State.getCurrentState().get();
		try {
			assertTrue(JSONParser.readFile().toString().contains(scoreState.getHighScores().element().toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
