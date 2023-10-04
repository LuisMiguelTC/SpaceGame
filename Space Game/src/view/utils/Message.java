package view.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import mathgame.Vector2D;

public class Message {
	private float alpha;
	private String text;
	private Vector2D position;
	private Color color;
	private boolean center;
	private boolean fade;
	private Font font;
	private final float deltaAlpha = 0.025f;
	private boolean dead;
	private double initialYPosition;
	
	public Message(Vector2D position, boolean fade, String text, Color color,
			boolean center, Font font) {
		this.font = font;
		this.text = text;
		this.position = position;
		this.fade = fade;
		this.color = color;
		this.center = center;
		this.dead = false;
		this.initialYPosition = this.position.getY();
		
		if(fade)
			alpha = 1;
		else
			alpha = 0;	
	}
	
	public void updateDrawMessage(Graphics2D g2d) {

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
		Text.drawText(g2d, text, position, center, color, font);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		position.setY(position.getY() - 1);
		
		if(fade)
			alpha -= deltaAlpha;
		else
			alpha += deltaAlpha;
		
		if(fade && alpha < 0) {
			dead = true;
			alpha = 0;
			setInitialYPosition();
		}
		
		if(!fade && alpha > 1) {
			fade = true;
			alpha = 1;
		}
	}
	
	public boolean isDead(){
		return dead;
	}

	private void setInitialYPosition() {
		position.setY(initialYPosition);
	}
	
}
