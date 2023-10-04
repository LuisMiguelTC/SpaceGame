package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyboard implements KeyListener{
	
	private boolean[] keys = new boolean[512];
	public static boolean UP, DOWN, LEFT, RIGHT, SHOOT, PAUSE;
	
	public GameKeyboard()
	{
		UP = false;
		DOWN = false;
		LEFT = false;
		RIGHT = false;
		SHOOT = false;
		PAUSE = false;
	}
	
	public void update()
	{
		UP = keys[KeyEvent.VK_UP];
		DOWN = keys[KeyEvent.VK_DOWN];
		LEFT = keys[KeyEvent.VK_LEFT];
		RIGHT = keys[KeyEvent.VK_RIGHT];
		SHOOT = keys[KeyEvent.VK_SPACE]; 
		PAUSE = keys[KeyEvent.VK_ESCAPE];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		 keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}
