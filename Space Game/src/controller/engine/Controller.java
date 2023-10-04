package controller.engine;

import model.states.State;

public interface Controller {
	
	public void updateState(float dt);
	
	public void updateView();
	
	public State getState(State s);
}
