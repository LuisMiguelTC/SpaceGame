package view.state;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.gameobjects.Constants;
import model.states.InitState;
import view.utils.Assets;

public class SingletonInsertNamePlayer extends JFrame{

	private static final long serialVersionUID = 1L;
	private static SingletonInsertNamePlayer instance;
	
	private SingletonInsertNamePlayer() {
		
		setPreferredSize(new Dimension(Constants.WIDTH/2, Constants.HEIGHT/4));
		
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		
		JLabel label = new JLabel("INSERT NAME PLAYER: ");
		label.setFont(Assets.fontMed);
		label.setForeground(Color.YELLOW);
		JTextField textEmail = new JTextField(20);
		textEmail.setFont(Assets.fontMed);
		JButton button = new JButton("SAVE");
		button.setFont(Assets.fontMed);
		button.setBackground(Color.YELLOW);
		button.setForeground(Color.BLACK);
		
		panel.add(label);
		panel.add(textEmail);
		panel.add(button);
		getContentPane().add(panel);
		
		button.addActionListener(e->{
				String namePlayer = textEmail.getText();
				setVisible(false);
				InitState.setNamePlayer(namePlayer);
			});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static SingletonInsertNamePlayer getInstance() {
		if(instance == null) {
			instance = new SingletonInsertNamePlayer();
		}
		return instance;
	}
	
	public static void resetSingleton() {
		instance = null;
	}
}
