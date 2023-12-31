package view.objetcs;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import model.utils.ColorMessage;
import model.utils.Message;
import view.utils.Assets;
import view.utils.Text;

public class DrawMessage {

	private Graphics2D g2d;
	private Message m;
	private Font font;
	private Color color;
	
	public DrawMessage(Message m, Graphics2D g2) {
		this.m = m;
		this.g2d = g2;
	}
	
	public void updateDrawMessage() {
		
		selectTypeMessage(m.getColor(), m.getSizeFont());
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, m.getAlpha()));
		Text.drawText(g2d, m.getText(), m.getPosition(), m.isCenter(), color, font);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		m.getPosition().setY(m.getPosition().getY() - 1);
		
		if(m.isFade())
			m.setAlpha(m.getAlpha() - m.getDeltaAlpha());
		else
			m.setAlpha(m.getAlpha() + m.getDeltaAlpha());
		
		if(m.isFade() && m.getAlpha() < 0) {
			m.setDead(true);
			m.setAlpha(0f);
			m.setInitialYPosition();
		}
		
		if(!(m.isFade()) && m.getAlpha() > 1) {
			m.setFade(true);
			m.setAlpha(1f);
		}
	}
	
	private void selectTypeMessage(ColorMessage colorType, String sizeFont) {
		
		if(sizeFont.equals("MED"))
			font = Assets.fontMed;
		else
			font = Assets.fontBig;
		
		switch(colorType) {
			case WHITE -> color = Color.WHITE;
			case YELLOW ->color = Color.YELLOW;
			case BLUE -> color = Color.BLUE;
			case DARK_GRAY -> color = Color.DARK_GRAY;
			case GREEN -> color = Color.GREEN;
			case MAGENTA -> color = Color.MAGENTA;
			case ORANGE -> color = Color.ORANGE;
			case RED -> color = Color.RED;
			default -> throw new IllegalArgumentException("Color not found");
		}
	}
}
