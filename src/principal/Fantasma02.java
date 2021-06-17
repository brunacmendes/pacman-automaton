package principal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import Util.Elemento;

public class Fantasma02 extends Elemento implements Runnable{
	
	ArrayList<Integer> dirX = new ArrayList<>();
	ArrayList<Integer> dirY = new ArrayList<>();
	public boolean colide = false;
	private boolean saiu = false;
	
	private static int pesoX;
	private static int pesoY;
	
	private static final int TAM_X = 27;
	private static final int TAM_Y = 27;
	
	public Fantasma02(Color cor) {
		this.x = 9 * Mapa.BLOCK_SIZE;
		this.y = 9 * Mapa.BLOCK_SIZE;
		this.cor = cor;
		this.velocidade = 6;
		this.largura = TAM_X;
		this.altura = TAM_Y;
	}
	
	public void desenha(Graphics2D g2d) {
		Image img = Toolkit.getDefaultToolkit().getImage("imagens/laranja.png");
        g2d.drawImage(img, x+5, y+3, 22, 22, null);
        /*
        g2d.setColor(cor);
		g2d.fillOval(x, y, TAM_X, TAM_Y);
		g2d.fillRect(x, (y+(TAM_Y/2)), TAM_X, (TAM_Y/2));
		g2d.setColor(Color.WHITE);
		g2d.fillOval((x+(int)(TAM_X/4)), (y+(int)(TAM_Y/8)), (int)(TAM_X/5), (int)(TAM_Y/5));
		g2d.fillOval((x+(int)(TAM_X/3)*2), (y+(int)(TAM_Y/8)), (int)(TAM_X/5), (int)(TAM_Y/5));
		*/
	}
	
	public void desenhaComMedo(Graphics2D g2d){
		Image img = Toolkit.getDefaultToolkit().getImage("imagens/comMedo.png");
		g2d.drawImage(img, x+2, y, 25, 25, null);
	}

	@Override
	public void run() {
		while (!Janela.fimDoJogo) {
			move();
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void move() {
		
		if(x == 9 * Mapa.BLOCK_SIZE && y == 9 * Mapa.BLOCK_SIZE)
			setSaiu(false);
		
		//Enquanto o fantasma estiver na "prisão" dele
		if(!getSaiu()){
			sairDaPrisao();
		} else {
			/*
			 * TREINANDO ARBITRO
			 */
			praOnde();
			
			if (dirX.size() > 0) {
				direcaoX = getPesoX();
				direcaoY = getPesoY();
			}
			colideBordas();
			
			andaFantasma();
		}
		
	}
	
	public void sairDaPrisao(){
		//Ele apenas vai subir
		if (Mapa.infoMAPA[(this.y+this.altura+2)/Mapa.BLOCK_SIZE][this.x/Mapa.BLOCK_SIZE] != 10) {
			direcaoX = 0;
			direcaoY = -1;
			andaFantasma();
		}
		//Quando sair
		else{
			setSaiu(true);
			direcaoY = 0;
		}
	}
	
	private void andaFantasma() {
		this.incX((int) (this.direcaoX * this.velocidade));
		this.incY((int) (this.direcaoY * this.velocidade));
	}

	//Função para checar se todo o fantasma está em uma unica casa do MAPA
	public boolean podeVirar() {

		int posDBx, posDBy, posDTx, posDTy, posEBx, posEBy, posETx, posETy;
		short mDB, mDT, mEB, mET;

		posDBx = (x + largura) / Mapa.BLOCK_SIZE;
		posDBy = (y + altura) / Mapa.BLOCK_SIZE;
		mDB = Mapa.infoMAPA[posDBy][posDBx];

		posDTx = (x + largura) / Mapa.BLOCK_SIZE;
		posDTy = y / Mapa.BLOCK_SIZE;
		mDT = Mapa.infoMAPA[posDTy][posDTx];

		posETx = x / Mapa.BLOCK_SIZE;
		posETy = y / Mapa.BLOCK_SIZE;
		mET = Mapa.infoMAPA[posETy][posETx];

		posEBx = x / Mapa.BLOCK_SIZE;
		posEBy = y / Mapa.BLOCK_SIZE;
		mEB = Mapa.infoMAPA[posEBy][posEBx];

		if ((mDB == mDT) && (mDB == mEB) && (mDB == mET) && (mDT == mEB) && (mDT == mET) && (mEB == mET)) {
			return true;
		}

		return false;
	}

	public void praOnde() {
		int posX = x / Mapa.BLOCK_SIZE;
		int posY = y / Mapa.BLOCK_SIZE;
		short map;
		
		dirX.clear();
		dirY.clear();
		
		if (podeVirar()) {
			map = Mapa.infoMAPA[posY][posX];
			//Se o fantasma pode andar para a Esquerda
			if ((map & 1) == 0) {
				dirX.add(-1);
				dirY.add(0);
			}
			//Se o fantasma pode andar para Cima
			if ((map & 2) == 0) {
				dirX.add(0);
				dirY.add(-1);
			}
			//Se o fantasma pode andar para a Direita
			if ((map & 4) == 0) {
				dirX.add(1);
				dirY.add(0);
			}
			//Se o fantasma pode andar para Baixo
			if ((map & 8) == 0) {
				dirX.add(0);
				dirY.add(1);
			}
		}
	}
	
	public void colideBordas() {
		int posX = x / Mapa.BLOCK_SIZE;
		int posY = y / Mapa.BLOCK_SIZE;
		/*
		 * Se o FANTASMA estiver andando para a DIREITA.
		 * O ponto de COLISÃO será o X ORIGEM do FANTASMA.
		 * Isso fará com que o FANTASMA entre por completo na casa da DIREITA.
		 */
		if (this.direcaoX > 0) {
			posX = (this.x) / Mapa.BLOCK_SIZE;
		}
		/*
		 * Se o FANTASMA estiver andando para BAIXO.
		 * O ponto de COLISÃO será o Y ORIGEM do FANTASMA.
		 * Isso fará com que o FANTASMA entre por completo na casa de BAIXO.
		 */
		if (this.direcaoY > 0) {
			posY = (this.y) / Mapa.BLOCK_SIZE;
		}
		/*
		 * Se o FANTASMA estiver andando para a ESQUERDA.
		 * O ponto de COLISÃO será o X ORIGEM + a LARGURA do FANTASMA.
		 * Isso fará com que o FANTASMA entre por completo na casa da ESQUERDA.
		 */
		if (this.direcaoX < 0) {
			posX = (this.x + this.largura) / Mapa.BLOCK_SIZE;
		}
		/*
		 * Se o FANTASMA estiver andando para CIMA.
		 * O ponto de COLISÃO será o Y ORIGEM + a ALTURA do FANTASMA.
		 * Isso fará com que o FANTASMA entre por completo na casa de CIMA.
		 */
		if (this.direcaoY < 0) {
			posY = (this.y + this.altura) / Mapa.BLOCK_SIZE;
		}
		short mapa = Mapa.infoMAPA[posY][posX];
		if ((this.direcaoX == -1 && this.direcaoY == 0 && (mapa & 1) == 1)
				|| (this.direcaoX == 0 && this.direcaoY == -1 && (mapa & 2) == 2)
				|| (this.direcaoX == 1 && this.direcaoY == 0 && (mapa & 4) == 4)
				|| (this.direcaoX == 0 && this.direcaoY == 1 && (mapa & 8) == 8)) {
			this.direcaoX = 0;
			this.direcaoY = 0;
			colide = true;
		} else {
			colide = false;
		}
	}
	
	public void recebePalavra(String movimento){
		if(movimento.equals("esquerda")){
			setPesoX(-1);
			setPesoY(0);
		}
		else if(movimento.equals("cima")){
			setPesoX(0);
			setPesoY(-1);
		}
		else if(movimento.equals("direita")){
			setPesoX(1);
			setPesoY(0);
		}
		else if(movimento.equals("baixo")){
			setPesoX(0);
			setPesoY(1);
		}
	}
	
	public void setPesoX(int peso){
		Fantasma02.pesoX = peso;
	}
	
	public int getPesoX(){
		return Fantasma02.pesoX;
	}
	
	public void setPesoY(int peso){
		Fantasma02.pesoY = (peso);
	}
	
	public int getPesoY(){
		return Fantasma02.pesoY;
	}
	
	public void setSaiu(boolean saiu){
		this.saiu = saiu;
	}
	
	public boolean getSaiu(){
		return this.saiu;
	}
	
}
