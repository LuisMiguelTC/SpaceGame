package view.state;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Arrays;
import java.util.PriorityQueue;
import mathgame.Vector2D;
import model.ScoreData;
import model.gameobjects.Constants;
import model.states.GameState;
import model.states.MenuState;
import model.states.MenuPauseState;
import model.states.ScoreState;
import model.states.State;
import ui.Button;
import view.objetcs.DrawMovingObject;
import view.objetcs.DrawMovingObjectImpl;
import view.utils.Assets;
import view.utils.Text;

public class DrawStateImpl implements DrawState{

	Graphics2D g2;
	
	public DrawStateImpl(Graphics2D g2) {
		this.g2 = g2;
	}

	@Override
	public void drawInitState(State s) {
		g2.drawImage(Assets.inittext, Constants.WIDTH/2 - Assets.inittext.getWidth()/2, 
				Constants.HEIGHT/2 - Assets.inittext.getHeight()/2 - 150, null);
	}
	
	@Override
	public void drawMenuState(State s) {
		MenuState m = (MenuState) s;
		g2.drawImage(Assets.menutext, Constants.WIDTH/2 - Assets.menutext.getWidth()/2,
				Constants.HEIGHT/2 - Assets.menutext.getHeight()/2 - 150, null);
		for(Button b: m.getButtons()) {
			b.draw(g2);
		}
	}

	@Override
	public void drawGameState(State s) {
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		GameState g = (GameState) s;
		DrawMovingObject gr = new DrawMovingObjectImpl(g2);
		g.getSpace().getMovingObjects().forEach(m->{ m.updateDraw(gr);});
		g.getSpace().getMessages().forEach(m->{m.updateDrawMessage(g2);});
		g.getSpace().getExplosions().forEach(e -> {g2.drawImage(e.getCurrentFrame(), (int)e.getPosition().getX(), (int)e.getPosition().getY(),
					null);});
		
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

	@Override
	public void drawPauseState(State s) {	
		MenuPauseState p = (MenuPauseState) s;
		for(Button b: p.getButtons()) {
			b.draw(g2);
		}
	}

	@Override
	public void drawScoreState(State s) {
	
		ScoreState sc = (ScoreState) s;
		sc.getReturnButton().draw(g2);
		PriorityQueue<ScoreData> highScores = sc.getHighScores();
		ScoreData[] auxArray = highScores.toArray(new ScoreData[highScores.size()]);
		Arrays.sort(auxArray, sc.getScoreComparator());
	
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
