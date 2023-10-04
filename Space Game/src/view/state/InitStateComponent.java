package view.state;

import model.states.State;

public class InitStateComponent implements DrawStateComponent{

	@Override
	public void updateDraw(State state, DrawState s) {
		s.drawInitState(state);
	}
}
