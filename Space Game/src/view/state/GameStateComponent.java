package view.state;

import model.states.State;

public class GameStateComponent implements DrawStateComponent {

	@Override
	public void updateDraw(State state, DrawState s) {
		s.drawGameState(state);
	}
}
