package principal;

import java.awt.Color;
import java.awt.Graphics2D;

import Util.Elemento;

public class Pacman extends Elemento {

	private Color cor = Color.YELLOW;

	public Pacman() {
		largura = 22;
		altura = 22;
	}

	public static void atualiza() {
		
	}

	public void desenha(Graphics2D g2d) {

		if (!ativo)
			return;

		float angulo, ultimoAngulo = 0;

		if (this.direcaoX == 1) {
			g2d.setColor(cor);
			angulo = (float) (30 * (Math.sin((x + y) * 2 * Math.PI / 50) + 1));
			ultimoAngulo = angulo;
			g2d.fillArc(x, y, largura, altura, ((int) angulo / 2), (int) (360 - angulo));
		} 
		else if (this.direcaoX == -1) {
			g2d.setColor(cor);
			angulo = (float) (30 * (Math.sin((x + y) * 2 * Math.PI / 50) + 1));
			ultimoAngulo = angulo;
			g2d.fillArc(x, y, largura, altura, ((int) angulo / 2) + 180, (int) (360 - angulo));
		}
		else if (this.direcaoY == -1) {
			g2d.setColor(cor);
			angulo = (float) (30 * (Math.sin((x + y) * 2 * Math.PI / 50) + 1));
			ultimoAngulo = angulo;
			g2d.fillArc(x, y, largura, altura, ((int) angulo / 2) + 90, (int) (360 - angulo));
		}
		else if (this.direcaoY == 1) {
			g2d.setColor(cor);
			angulo = (float) (30 * (Math.sin((x + y) * 2 * Math.PI / 50) + 1));
			ultimoAngulo = angulo;
			g2d.fillArc(x, y, largura, altura, ((int) angulo / 2) + 270, (int) (360 - angulo));
		}
		else {
			g2d.setColor(cor);
			angulo = ultimoAngulo;
			g2d.fillArc(x, y, largura, altura, ((int) angulo / 2), (int) (360 - angulo));
		} 
	}

	public void desenha(Graphics2D g2d, int i) {

		if (!ativo)
			return;

		float angulo;

		g2d.setColor(Color.YELLOW);
		/*
		 * X=84 e Y=618 é para manter a boca desenhada nas vidas sempre aberta
		 */
		angulo = (float) (30 * (Math.sin((84 + 618) * 2 * Math.PI / 50) + 1));
		g2d.fillArc(x, y, largura, altura, ((int) angulo / 2), (int) (360 - angulo));

	}
	
	public void setCor(Color cor){
		this.cor = cor;
	}

}
