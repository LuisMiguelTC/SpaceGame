package controller.engine;

import model.states.State;

public interface Controller {
	
	public void updateModelState(float dt);
	
	public void updateView();
	
	public State getModelState(State s);
}
