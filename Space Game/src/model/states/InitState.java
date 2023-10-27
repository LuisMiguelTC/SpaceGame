package model.states;

import java.util.Optional;
import model.gameobjects.Constants;
import ui.Button;

public class InitState extends State{
	
	static Optional<String> namePlayer = Optional.empty();
	private Button startButton;
	
	public InitState() {
		
		startButton = new Button.Builder()
				.posX(Constants.BUTTON_HEIGHT)
				.posY(Constants.HEIGHT - Constants.BUTTON_HEIGHT * 2)
				.text(Constants.STARTGAME)
				.action(()-> State.setState(new GameState(getNamePlayer().get())))
				.build();		
	}
	
	@Override
	public void update(float dt) {
		startButton.update();
	}
	
	public Button getButton() {
		return startButton;
	}
	
	public static void setNamePlayer(String name) {
		namePlayer = Optional.of(name);
	}
	
	public Optional<String> getNamePlayer() {
		if(namePlayer.isEmpty()|| namePlayer.get().equals(""))
			namePlayer = Optional.of("UnknownPlayer");
		return namePlayer;
	}
	
	@Override
	public String typeState() {
		return "INIT";
	}
}

