package view.state;

import model.states.State;

public class ScoreStateComponent implements DrawStateComponent{

	@Override
	public void updateDraw(State state, DrawState s) {
		s.drawScoreState(state);
	}
}
