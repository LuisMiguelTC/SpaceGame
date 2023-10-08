package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

import controller.engine.GameController;
import input.GameKeyboard;
import input.MouseInput;
import model.gameobjects.Constants;
import model.states.State;
import view.state.DrawState;
import view.state.DrawStateImpl;
import view.utils.Assets;


public class GameView extends JPanel implements View{

	private static final long serialVersionUID = 1L;
	
	private MouseInput mouseInput;
	private GameController gameController;
	private GameKeyboard gameKeyboard;

	public GameView(GameController gameController) {
		
		mouseInput = new MouseInput();
		gameKeyboard = new GameKeyboard();
		this.gameController = gameController;
		setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
		
		addKeyListener(gameKeyboard);
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);
		setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow(); 
	}
	
	@Override
	public void updateControllerInput() {
		gameKeyboard.update();	
	}	
	
	@Override
	public void render(){
    	try {
	    	repaint();
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
	
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		          RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
		          RenderingHints.VALUE_RENDER_QUALITY);
		g2.clearRect(0,0,this.getWidth(),this.getHeight());
		
		g2.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
		g2.setColor(Color.BLACK);
		g2.drawImage(Assets.background, 0,0, null);
		
		
		State current = gameController.getState(State.getCurrentState().get());
		DrawState drawState = new DrawStateImpl(current,g2);
		drawState.updateStateDraw();
	}
}
