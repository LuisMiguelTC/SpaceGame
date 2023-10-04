package view.state;

import model.states.State;

public class PauseStateComponent implements DrawStateComponent {

	@Override
	public void updateDraw(State state, DrawState s) {
		s.drawPauseState(state);
	}
}
