package model.states;

import java.util.Optional;

public abstract class State {

	private static Optional<State> currentState = Optional.empty();
	
	public static Optional<State> getCurrentState() {
		return currentState;
	}
	
	public static void setState(State newState) {
		currentState = Optional.of(newState);
	}
	
	public abstract void update(float dt);

	public abstract StateType typeState();	
}
