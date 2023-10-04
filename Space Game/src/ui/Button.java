package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import input.MouseInput;
import mathgame.Vector2D;
import view.utils.Assets;
import view.utils.Text;

public class Button {
	
	private BufferedImage mouseOutImg;
	private BufferedImage mouseInImg;
	private boolean mouseIn;
	private Rectangle boundingBox;
	private Action action;
	private String text;
	
	private Button() {
	}
	
	public void update() {
		
		if(boundingBox.contains(MouseInput.X, MouseInput.Y)) {
			mouseIn = true;
		}else {
			mouseIn = false;
		}
		
		if(mouseIn && MouseInput.MLB) {
			action.doAction();
		}
	}
	
	public void draw(Graphics g) {
		
		if(mouseIn) {
			g.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
		}else {
			g.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
		}
		
		Text.drawText(
				g,
				text,
				new Vector2D(
						boundingBox.getX() + boundingBox.getWidth() / 2,
						boundingBox.getY() + boundingBox.getHeight()),
				true,
				Color.BLACK,
				Assets.fontMed);
	}
	
	public void setString(String newString) {
		this.text = newString;
	}
	
	public void setAction(Action newAction) {
		this.action = newAction;
	}
	
	public static class Builder{
		
		private BufferedImage mouseOutImg;
		private BufferedImage mouseInImg;
		private Action action;
		private String text;
		private int posX;
		private int posY;
		
		public Builder mouseOutImg(final BufferedImage mouseOutImg) {
			this.mouseOutImg = mouseOutImg;
			return this;
		}
		
		public Builder mouseInImg(final BufferedImage mouseInImg) {
			this.mouseInImg = mouseInImg;
			return this;
		}
		
		public Builder action(final Action action) {
			this.action = action;
			return this;
		}
		public Builder text(String text) {
			this.text = text;
			return this;
		}
		public Builder posX(final int posX) {
			this.posX = posX;
			return this;
		}
		
		public Builder posY(final int posY) {
			this.posY = posY;
			return this;
		}
		
		public Button build() {
			Button button = new Button();
			
			button.mouseInImg = this.mouseInImg;
			button.mouseOutImg = this.mouseOutImg;
			button.text = this.text;
			button.action = this.action;
			button.boundingBox = new Rectangle(this.posX, this.posY, 
					this.mouseInImg.getWidth(), this.mouseInImg.getHeight());

			return button;
		}
	}
}
