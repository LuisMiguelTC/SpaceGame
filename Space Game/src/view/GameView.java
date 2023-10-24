package view;

import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import controller.engine.Controller;
import input.GameInput;
import input.MouseInput;
import model.gameobjects.Constants;
import model.states.State;
import view.state.DrawState;
import view.state.DrawStateImpl;
import view.utils.Assets;
import view.utils.SoundManager;

public class GameView implements View{
	
	private JFrame frame;
	private Controller gameController;
	private GameInput gameInput;
	private MouseInput mouseInput;
	private SoundManager soundManager;
	
	public GameView(Controller gameController)
	{
		this.gameController = gameController;
		this.gameInput = new GameInput(VK_UP,VK_LEFT,VK_RIGHT,VK_SPACE,VK_ESCAPE);
		this.mouseInput = new MouseInput(MouseEvent.BUTTON1);
		this.soundManager = new SoundManager();
		
		frame = new JFrame();
		frame.setTitle("SPACE GAME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.getContentPane().add(new GamePanel());
		
		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	@Override
	public void updateInput() {
		gameInput.update();	
	}	
	
	@Override
	public void render(){
    	try {
	    	frame.repaint();
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
	
	public class GamePanel extends JPanel implements KeyListener, MouseInputListener{

		private static final long serialVersionUID = 1L;

		public GamePanel() {

			setPreferredSize(new Dimension(Constants.WIDTH,Constants.HEIGHT));
			
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			setFocusable(true);
	        setFocusTraversalKeysEnabled(false);
	        requestFocusInWindow(); 
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
			
			
			State current = gameController.getModelState();
			DrawState drawState = new DrawStateImpl(current,g2, soundManager);
			drawState.updateStateDraw();
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			gameInput.notifyKeyPressed(e.getKeyCode());
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			gameInput.notifyKeyReleased(e.getKeyCode());
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			mouseInput.notifyMousePressed(e.getButton());	
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mouseInput.notifyMouseReleased(e.getButton());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseInput.notifyMouseDragged(e.getX(), e.getY());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseInput.notifyMouseMoved(e.getX(), e.getY());
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {	
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}