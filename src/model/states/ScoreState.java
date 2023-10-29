package model.states;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import model.gameobjects.Constants;
import model.utils.Button;
import model.utils.JSONParser;
import model.utils.ScoreData;

public class ScoreState extends State{

	private Button returnButton;
	private PriorityQueue<ScoreData> highScores;	
	private ScoreData[] auxArray;

	
	public ScoreState() {
		
		returnButton = new Button.ButtonBuilder()
				.posX(Constants.BUTTON_HEIGHT)
				.posY(Constants.HEIGHT - Constants.BUTTON_HEIGHT * 2)
				.text(Constants.RETURN)
				.action(()-> State.setState(new MenuState()))
				.build();
		
		highScores = new PriorityQueue<ScoreData>(10, Comparator.comparing(ScoreData::getScore));
		
		try {
			ArrayList<ScoreData> dataList = JSONParser.readFile();
			dataList.forEach( d -> {
				highScores.add(d);
			});
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
	
	public Button getButton() {
		return returnButton;
	}
	
	public ScoreData[] getScoreArraySorted() {
		auxArray = highScores.toArray(new ScoreData[highScores.size()]);
		Arrays.sort(auxArray, Comparator.comparing(ScoreData::getScore));
		return auxArray;
	}
	
	public PriorityQueue<ScoreData> getHighScores() {
		return highScores;
	}
	
	@Override
	public StateType typeState() {
		return StateType.SCORE;
	}
}
