package Util;

import java.awt.Font;
import java.awt.Graphics2D;

public class Texto extends Elemento {
	
	private Font fonte;
	
	public Texto() {
		super();
		fonte = new Font("Tahoma", Font.PLAIN,16);
	}
	
	public void desenha(Graphics2D g2d, String texto){
		
		if(cor != null)
			g2d.setColor(cor);
		
		g2d.setFont(fonte);
		g2d.drawString(texto, x, y);
		
	}
	
	public void desenha(Graphics2D g2d, String texto, int x, int y){
		
		if(cor != null)
			g2d.setColor(cor);
		
		g2d.setFont(fonte);
		g2d.drawString(texto, x, y);
		
	}

	public Font getFonte() {
		return fonte;
	}

	public void setFonte(Font fonte) {
		this.fonte = fonte;
	}

}
