package Util;

import java.awt.Color;
import java.awt.Graphics2D;

public class Elemento {
	public int x;
	public int y;
	public int direcaoX;
	public int direcaoY;
	public int largura;
	public int altura;
	public Color cor;
	
	public boolean invencivel = false;
	public boolean godSpeed = false;
	
	public float velocidade;
	public boolean ativo;
	
	public Elemento(){
		this(0,0,0,0);
	}
	
	public Elemento(int x, int y, int largura, int altura) {
		this.x = x;
		this.y = y;
		this.largura = largura;
		this.altura = altura;
	}
	
	public void desenha(Graphics2D g){
		if(cor != null)
			g.setColor(cor);
		
		g.drawRect(x, y, largura, largura);
		
	}
	
	public void setCor(Color cor){
		this.cor = cor;
	}
	
	public Color getCor(){
		return this.cor;
	}
	
	public int getDirecaoX(){
		return this.direcaoX;
	}
	
	public int getDirecaoY(){
		return this.direcaoY;
	}
	
	public void incX(int i){
		this.x += i;
	}
	
	public void incY(int i){
		this.y += i;
	}
	
}
