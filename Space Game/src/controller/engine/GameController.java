package controller.engine;

import model.states.MenuState;
import model.states.State;
import view.GameView;
import view.View;
import view.utils.Assets;

public class GameController implements Controller{

	private View gameView;
	private final int FPS_SET = 60;
	private double TARGETTIME = 1000000000/FPS_SET;
	private double delta = 0;
	private final float period = 20.0f;
	
	public GameController(){
		
		init();
		gameView = new GameView(this);
		loopGame();
	}
	
	private void init(){
		
		Assets.init();
		State.setState(new MenuState());
	}
	
	private void loopGame() {
		
		long now = 0;
		long lastTime = System.nanoTime();
		long time = 0;

		while(!State.getCurrentState().isEmpty()){
				
			now = System.nanoTime();
			delta += (now - lastTime)/TARGETTIME;
			time += (now - lastTime);
			lastTime = now;	
			
			if(delta >= 1){
				gameView.updateInput();
				updateModelState(period);
				updateView();
				delta --;
			}
			
			if(time >= 1000000000)
				time = 0;	
		}
	}
	
	@Override
	public void updateModelState(float dt) {
		State.getCurrentState().get().update(dt);
	}
	
	@Override
	public void updateView() {
		gameView.render();
	}
	
	@Override
	public State getModelState() {
		return State.getCurrentState().get();
	}
}
