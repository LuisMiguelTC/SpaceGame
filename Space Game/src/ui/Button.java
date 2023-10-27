package ui;

import input.MouseInput;
import model.gameobjects.Constants;

public class Button {
	
	private boolean mouseIn;
	private Action action;
	private String text;
	private int posX;
	private int posY;
	
	private Button() {
	}
	
	public void update() {
		
		
		if(this.posX < MouseInput.X && 
				this.posX + Constants.BUTTON_WIDTH > MouseInput.X &&
				this.posY < MouseInput.Y &&
				this.posY + Constants.BUTTON_HEIGHT  > MouseInput.Y)
			
			mouseIn = true;
		else 
			mouseIn = false;
		
		if(mouseIn && MouseInput.MLB) {
			action.doAction();	
		}
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public Action getAction() {
		return action;
	}

	public String getText() {
		return text;
	}

	public void setString(String newString) {
		this.text = newString;
	}
	
	public void setAction(Action newAction) {
		this.action = newAction;
	}
	
	public boolean isMouseIn() {
		return mouseIn;
	}
	
	public static class Builder{
		
		private int posX;
		private int posY;
		private Action action;
		private String text;

		public Builder posX(final int posX) {
			this.posX = posX;
			return this;
		}
		
		public Builder posY(final int posY) {
			this.posY = posY;
			return this;
		}
		
		public Builder action(final Action action) {
			this.action = action;
			return this;
		}
		public Builder text(final String text) {
			this.text = text;
			return this;
		}
		
		public Button build() {
			Button button = new Button();
			
			button.posX = this.posX;
			button.posY = this.posY;
			button.text = this.text;
			button.action = this.action;

			return button;
		}
	}
}
