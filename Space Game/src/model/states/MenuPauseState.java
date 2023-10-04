package model.states;

import model.gameobjects.Constants;
import ui.*;
import view.state.DrawStateComponent;
import view.state.GameStateComponent;

public class MenuPauseState extends MenuState{
		
	
	public MenuPauseState(GameState currentGame, DrawStateComponent drawState) {
		super(drawState);
	
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
						State.setState(new GameState(currentGame.getNamePlayer(),new GameStateComponent()));
					}
				});
	}
}
