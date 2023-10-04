package model.states;

import java.util.Optional;

import view.state.DrawState;
import view.state.DrawStateComponent;

public abstract class State {

	private static Optional<State> currentState = Optional.empty();
	private DrawStateComponent drawState;
	
	public State(DrawStateComponent drawState) {
		this.drawState = drawState;
	}
	
	public static Optional<State> getCurrentState() {
		return currentState;
	}
	
	public static void setState(State newState) {
		currentState = Optional.of(newState);
	}
	
	public void updateStateDraw(DrawState s) {
		drawState.updateDraw(this, s);
	}
	
	public abstract void update(float dt);	
}
