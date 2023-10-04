package view.state;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.gameobjects.Constants;
import model.states.GameState;
import model.states.State;
import view.utils.Assets;

public class InsertNamePlayer extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String namePlayerDefault = "UknownPlayer";
	
	public InsertNamePlayer() {
		
		setPreferredSize(new Dimension(Constants.WIDTH/2, Constants.HEIGHT/4));
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String namePlayer = textEmail.getText();
				if(namePlayer.isEmpty())
					namePlayer = namePlayerDefault;
				setVisible(false);
				State.setState(new GameState(namePlayer,new GameStateComponent()));
			}
			
		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
}
