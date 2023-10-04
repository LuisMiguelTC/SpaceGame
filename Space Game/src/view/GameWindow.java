package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
public class GameWindow {
	
	private JFrame frame;
	
	public GameWindow(GameView gameView)
	{
		frame = new JFrame();
		
		frame.setTitle("SPACE GAME");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.getContentPane().add(gameView);
		
		
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
}