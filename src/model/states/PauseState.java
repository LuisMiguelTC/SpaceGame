package model.states;

import model.gameobjects.Constants;

public class PauseState extends MenuState{
	
	public PauseState(GameState currentGame) {
		
		super.button1.setString(Constants.RESUME);
		super.button1.setAction(()-> State.setState(currentGame));
				
		super.button2.setString(Constants.RESTART);
		super.button2.setAction(()->
					State.setState(new GameState(currentGame.getNamePlayer())));
	}
	
	@Override
	public StateType typeState() {
		return StateType.PAUSE;
	}
}
