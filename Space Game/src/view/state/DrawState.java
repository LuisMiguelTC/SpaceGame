package view.state;

public interface DrawState {
	
	public void updateStateDraw();
	
	public void drawInitState();
	
	public void drawMenuState();
	
	public void drawGameState();
	
	public void drawPauseState();
	
	public void drawScoreState();

}
