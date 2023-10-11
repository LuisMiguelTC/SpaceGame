package model.states;

import model.gameobjects.Constants;
import ui.*;

public class PauseState extends MenuState{
		
	public PauseState(GameState currentGame) {
	
		super.button1.setString(Constants.RESUME);
		super.button1.setAction(new Action() {
					@Override
					public void doAction() {
						State.setState(currentGame);
					}
				});
				
		super.button2.setString(Constants.RESTART);
		super.button2.setAction(new Action() {
					@Override
					public void doAction() {
						State.setState(new GameState(currentGame.getNamePlayer()));
					}
				});
	}
	
	@Override
	public String typeState() {
		return "PAUSE";
	}
}
