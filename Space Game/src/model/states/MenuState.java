package model.states;

import java.util.ArrayList;

import model.gameobjects.Constants;
import ui.Button;

public class MenuState extends State{

	protected Button button1, button2, button3;
	protected ArrayList<Button> buttons;
	
	public MenuState() {
		
		buttons = new ArrayList<Button>();
		
		buttons.add(button1 = new Button.Builder()
				.posX(Constants.WIDTH / 2 - Constants.BUTTON_WIDTH/2)
				.posY(Constants.HEIGHT / 2 - Constants.BUTTON_HEIGHT * 2)
				.text(Constants.PLAY)
				.action(()-> {
						State.setState(new InitState());})
				.build());
		
		buttons.add(button2 = new Button.Builder()
				.posX(Constants.WIDTH / 2 - Constants.BUTTON_WIDTH/2)
				.posY(Constants.HEIGHT / 2)
				.text(Constants.HIGH_SCORES)
				.action(()-> State.setState(new ScoreState()))
				.build());
		
		buttons.add(button3 = new Button.Builder()
				.posX(Constants.WIDTH / 2 - Constants.BUTTON_WIDTH/2)
				.posY(Constants.HEIGHT / 2 + Constants.BUTTON_HEIGHT * 2)
				.text(Constants.EXIT)
				.action(() -> System.exit(0))
				.build());
	}
	
	@Override
	public void update(float dt) {
		buttons.forEach( b->b.update());
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	@Override
	public String typeState() {
		return "MENU";
	}
}