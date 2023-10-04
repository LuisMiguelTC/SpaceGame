package model.states;

import java.util.ArrayList;

import model.gameobjects.Constants;
import ui.Action;
import ui.Button;
import ui.Button.Builder;
import view.state.DrawStateComponent;
import view.state.InitStateComponent;
import view.state.InsertNamePlayer;
import view.state.ScoreStateComponent;
import view.utils.Assets;

public class MenuState extends State{

	protected Button button1, button2, button3;
	protected Builder generalButton;
	protected ArrayList<Button> buttons;
	
	public MenuState(DrawStateComponent drawState) {
		super(drawState);
		
		generalButton = new Button.Builder().mouseInImg(Assets.yellowBtn)
				.mouseOutImg(Assets.greyBtn);
		
		buttons = new ArrayList<Button>();
		
		buttons.add(button1 = generalButton
				.posX(Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2)
				.posY(Constants.HEIGHT / 2 - Assets.greyBtn.getHeight() * 2)
				.text(Constants.PLAY)
				.action(new Action() {
					
					@Override
					public void doAction() {
						new InsertNamePlayer();
						State.setState(new InitState(new InitStateComponent()));
					}
				})
				.build());
		
		buttons.add(button2 = generalButton
				.posX(Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2)
				.posY(Constants.HEIGHT / 2)
				.text(Constants.HIGH_SCORES)
				.action(new Action() {
					@Override
					public void doAction() {
						State.setState(new ScoreState(new ScoreStateComponent()));
					}
				})
				.build());
		
		buttons.add(button3 = generalButton
				.posX(Constants.WIDTH / 2 - Assets.greyBtn.getWidth()/2)
				.posY(Constants.HEIGHT / 2 + Assets.greyBtn.getHeight() * 2)
				.text(Constants.EXIT)
				.action(new Action() {
					@Override
					public void doAction() {
						System.exit(0);
					}
				})
				.build());
	}
	
	@Override
	public void update(float dt) {
		for(Button b: buttons) {
			b.update();
		}
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}
}