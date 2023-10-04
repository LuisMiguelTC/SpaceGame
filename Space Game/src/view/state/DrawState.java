package view.state;

import model.states.State;

public interface DrawState {
	
	public void drawInitState(State s);
	
	public void drawMenuState(State s);
	
	public void drawGameState(State  s);
	
	public void drawPauseState(State s);
	
	public void drawScoreState(State s);
}
