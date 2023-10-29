package view.objetcs;

import java.awt.Color;
import java.awt.Graphics2D;
import mathgame.Vector2D;
import model.gameobjects.Constants;
import model.utils.Button;
import view.utils.Assets;
import view.utils.Text;

public class DrawButton{

	private Graphics2D g2d;
	private Button button;
	
	public DrawButton(Button button, Graphics2D g2) {
		this.button = button;
		this.g2d = g2;
	}
	
	public void updateDrawButton() {
		
		if(button.isMouseIn()) 
			g2d.drawImage(Assets.yellowBtn, this.button.getPosX(), this.button.getPosY(), null);
		else 
			g2d.drawImage(Assets.greyBtn, this.button.getPosX(), this.button.getPosY(), null);

		Text.drawText(
				g2d,
				button.getText(),
				new Vector2D(
						this.button.getPosX() + Constants.BUTTON_WIDTH/ 2,
						this.button.getPosY() + Constants.BUTTON_HEIGHT),
				true,
				Color.BLACK,
				Assets.fontMed);
	}
}
