package controller.engine;

import model.states.MenuState;
import model.states.State;
import view.GameView;
import view.GameWindow;
import view.utils.Assets;

public class GameController implements Controller{

	private GameWindow gameWindow;
	private GameView view;
	private final int FPS_SET = 60;
	private double TARGETTIME = 1000000000/FPS_SET;
	private double delta = 0;
	private int AVERAGEFPS = FPS_SET;
	private final float period = 20.0f;
	
	public GameController(){
		
		init();
		view = new GameView(this);
		gameWindow = new GameWindow(view);
		view.requestFocusInWindow();
		loopGame();
	}
	
	private void init(){
		
		Assets.init();
		State.setState(new MenuState());
	}
	
	private void loopGame() {
		
		long now = 0;
		long lastTime = System.nanoTime();
		int frames = 0;
		long time = 0;

		while(!State.getCurrentState().isEmpty()){
				
			now = System.nanoTime();
			delta += (now - lastTime)/TARGETTIME;
			time += (now - lastTime);
			lastTime = now;	
			
			if(delta >= 1){
				view.updateInput();
				updateModelState(period);
				view.render();
				delta --;
				frames ++;
			}
				
			if(time >= 1000000000)
			{
				this.AVERAGEFPS = frames;
				frames = 0;
				time = 0;	
			}
		}
		if(State.getCurrentState().isEmpty())
			System.exit(0);
	}
	
	@Override
	public void updateModelState(float dt) {
		State.getCurrentState().get().update(dt);
	}
	
	@Override
	public void updateView() {
		view.render();
	}
	
	@Override
	public State getModelState(State s) {
		return s;
	}
}
