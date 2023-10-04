package view.state;

import model.states.State;

public class MenuStateComponent implements DrawStateComponent{

	@Override
	public void updateDraw(State state, DrawState s) {
		s.drawMenuState(state);
	}
}
