package model.states;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import view.state.DrawStateComponent;
import view.state.MenuStateComponent;
import view.utils.Assets;
import model.JSONParser;
import model.ScoreData;
import model.gameobjects.Constants;
import ui.Action;
import ui.Button;

public class ScoreState extends MenuState{

	private Button returnButton;
	private PriorityQueue<ScoreData> highScores;	
	private Comparator<ScoreData> scoreComparator;
	private ScoreData[] auxArray;

	
	public ScoreState(DrawStateComponent drawState) {
		super(drawState);
		
		returnButton = super.generalButton
				.posX(Assets.greyBtn.getHeight())
				.posY(Constants.HEIGHT - Assets.greyBtn.getHeight() * 2)
				.text(Constants.RETURN)
				.action(
				new Action() {
					@Override
					public void doAction() {
						State.setState(new MenuState(new MenuStateComponent()));
					}
				})
				.build();
		
		scoreComparator = new Comparator<ScoreData>() {
			@Override
			public int compare(ScoreData e1, ScoreData e2) {
				return e1.getScore() < e2.getScore() ? -1: e1.getScore() > e2.getScore() ? 1: 0;
			}
		};
		
		highScores = new PriorityQueue<ScoreData>(10, scoreComparator);
		
		try {
			ArrayList<ScoreData> dataList = JSONParser.readFile();
			for(ScoreData d: dataList) {
				highScores.add(d);
			}
			
			while(highScores.size() > 10) {
				highScores.poll();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void update(float dt) {
		returnButton.update();
	}
	
	public Button getReturnButton() {
		return this.returnButton;
	}
	public PriorityQueue<ScoreData> getHighScores() {
		return highScores;
	}
	public Comparator<ScoreData> getScoreComparator() {
		return scoreComparator;
	}
	public ScoreData[] getAuxArray() {
		return auxArray;
	}
	
	
	
}
