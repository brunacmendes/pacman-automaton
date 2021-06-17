package Util;

import java.awt.Color;

public enum FantasmasEnum {
	AZUL(Color.BLUE), LARANJA(Color.ORANGE), ROSA(Color.PINK), VERMELHO(Color.RED);
	
	public Color cor;
	FantasmasEnum(Color cor) {
		this.cor = cor;
	}
	
	public Color getCor(){
		return cor;
	}
}
