package view.state;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import mathgame.Vector2D;
import model.gameobjects.Constants;
import model.states.GameState;
import model.states.InitState;
import model.states.MenuState;
import model.states.PauseState;
import model.states.ScoreState;
import model.states.State;
import model.utils.ScoreData;
import view.objetcs.DrawAnimation;
import view.objetcs.DrawAnimationImpl;
import view.objetcs.DrawButton;
import view.objetcs.DrawButtonImpl;
import view.objetcs.DrawMessage;
import view.objetcs.DrawMessageImpl;
import view.objetcs.DrawMovingObject;
import view.objetcs.DrawMovingObjectImpl;
import view.utils.Assets;
import view.utils.SoundManager;
import view.utils.Text;

public class DrawStateImpl implements DrawState{

	private Graphics2D g2;
	private String type;
	private State currentState;
	private SoundManager soundManager;
	
	public DrawStateImpl(State s, Graphics2D g2, SoundManager soundManager) {
		this.g2 = g2;
		this.currentState = s;
		this.type = this.currentState.typeState();
		this.soundManager = soundManager;
	}

	@Override
	public void updateStateDraw() {
		
		switch(type) {
		
		case "MENU" -> drawMenuState();
		case "GAMESTATE"-> drawGameState();
		case "INIT"-> drawInitState();
		case "SCORE" -> drawScoreState();
		case "PAUSE" -> drawPauseState();
		default-> throw new IllegalStateException();
		}
	}
	

	private void drawInitState() {
		
		InitState i = (InitState) currentState;
		g2.drawImage(Assets.inittext, Constants.WIDTH/2 - Assets.inittext.getWidth()/2, 
				Constants.HEIGHT/2 - Assets.inittext.getHeight()/2 - 150, null);
		
		DrawButton drawButton = new DrawButtonImpl(i.getButton(),g2);
		drawButton.updateDrawButton();
		
		SingletonInsertNamePlayer.getInstance();
	}
	

	private void drawMenuState() {

		MenuState m = (MenuState) currentState;
		g2.drawImage(Assets.menutext, Constants.WIDTH/2 - Assets.menutext.getWidth()/2,
				Constants.HEIGHT/2 - Assets.menutext.getHeight()/2 - 150, null);
	
		m.getButtons().forEach( b -> {
			DrawButton drawButton = new DrawButtonImpl(b,g2);
			drawButton.updateDrawButton();
		});
		SingletonInsertNamePlayer.resetSingleton();
	}


	private void drawGameState() {

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		GameState g = (GameState) currentState;
	
		if(g.isNewGame())
			soundManager.restartGame();
		
		g.getSpace().getExplosions().forEach(
				
				e -> { DrawAnimation drawAnimation = new DrawAnimationImpl(e,g2,soundManager);
					drawAnimation.updateDrawExplosion();
					e.setLenght(drawAnimation.getLength());
					});
		
		g.getSpace().getMovingObjects().forEach(m->{ 
			DrawMovingObject drawObj = new DrawMovingObjectImpl(m,g2, this.soundManager);
			drawObj.updateDrawMovingObject();});
		
		g.getSpace().getMessages().forEach(m->{ DrawMessage drawMessage = new DrawMessageImpl(m,g2);
			drawMessage.updateDrawMessage();});
		
		Vector2D scorePos = new Vector2D(850, 25);
		String scoreToString = Integer.toString(g.getScore());
		for(int i = 0; i < scoreToString.length(); i++) {
			
			g2.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))],
					(int)scorePos.getX(), (int)scorePos.getY(), null);
			scorePos.setX(scorePos.getX() + 20);	
		}

		if(g.getNumberLives() < 1)
			return;
		
		Vector2D livePosition = new Vector2D(25, 25);
		g2.drawImage(Assets.life, (int)livePosition.getX(), (int)livePosition.getY(), null);
		g2.drawImage(Assets.numbers[10], (int)livePosition.getX() + 40,
				(int)livePosition.getY() + 5, null);
		
		String livesToString = Integer.toString(g.getNumberLives());
		Vector2D livesPos = new Vector2D(livePosition.getX(), livePosition.getY());
		
		for(int i = 0; i < livesToString.length(); i ++){
			int number = Integer.parseInt(livesToString.substring(i, i+1));
			
			if(number <= 0)
				break;
			g2.drawImage(Assets.numbers[number],
					(int)livesPos.getX() + 60, (int)livesPos.getY() + 5, null);
			livesPos.setX(livesPos.getX() + 20);
		}
	}


	private void drawPauseState() {	
		
		PauseState p = (PauseState) currentState;
	
		p.getButtons().forEach( b -> {
			DrawButton drawButton = new DrawButtonImpl(b,g2);
			drawButton.updateDrawButton();
		});
	}

	private void drawScoreState() {
	
		ScoreState sc = (ScoreState) currentState;
		DrawButton drawButton = new DrawButtonImpl(sc.getButton(),g2);
		drawButton.updateDrawButton();

		ScoreData[] auxArray = sc.getScoreArraySorted();
	
		Vector2D namePos = new Vector2D(Constants.WIDTH / 2 - 200, 100);
		Vector2D scorePos = new Vector2D(Constants.WIDTH / 2, 100);
		Vector2D countPos = new Vector2D(Constants.WIDTH / 2 + 200, 100);
		
		Text.drawText(g2, Constants.NAME, namePos, true, Color.YELLOW, Assets.fontBig);
		Text.drawText(g2, Constants.SCORE, scorePos, true, Color.YELLOW, Assets.fontBig);
		Text.drawText(g2, Constants.COUNT, countPos, true, Color.YELLOW, Assets.fontBig);
		
		namePos.setY(namePos.getY() + 40);
		scorePos.setY(scorePos.getY() + 40);
		countPos.setY(countPos.getY() + 40);
	
		for(int i = auxArray.length - 1; i > -1; i--) {
			
			ScoreData d = auxArray[i];
		
			Text.drawText(g2, d.getName(), namePos, true, Color.WHITE, Assets.fontMed);
			Text.drawText(g2, Integer.toString(d.getScore()), scorePos, true, Color.WHITE, Assets.fontMed);
			Text.drawText(g2, Integer.toString(d.getEnemydestroyed()), countPos, true, Color.WHITE, Assets.fontMed);
			
			namePos.setY(namePos.getY() + 40);
			scorePos.setY(scorePos.getY() + 40);
			countPos.setY(countPos.getY() + 40);
		}
	}	
}
