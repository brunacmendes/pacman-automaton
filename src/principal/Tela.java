package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import Util.Audio;
import Util.Elemento;
import Util.TeclaEnum;
import Util.Util;

public class Tela extends JPanel {

	private static final long serialVersionUID = 1L;

	// Acessar variaveis de janela:
	private Janela janela;

	boolean colideBordas = false;

	// Declaracao de variaveis:
	private int FPS = 1000 / 20;
	private boolean animacao = true;

	private long duracaoVelocidade = 0;
	private long duracaoAnteriorV = duracaoVelocidade;
	private long duracaoInvencibilidade = 0;
	private long duracaoAnteriorI = duracaoInvencibilidade;
	
	private Audio pacmanComendo = new Audio("sons/pacmanComendo.wav");
	private Audio pacmanMorrendo = new Audio("sons/pacmanMorrendo.wav");
	
	private Audio correndo = new Audio("sons/sonic.wav");
	public static boolean tocandoCorrendo = false;
	private Audio estrelinha = new Audio("sons/estrelinha.wav");
	public static boolean tocandoEstrelinha = false;
	
	private Audio youWin = new Audio("sons/youWin.wav");
	private Audio gameOver = new Audio("sons/gameOver.wav");
	
	public Tela(Janela janela) {
		this.janela = janela;
	}
	
	// Anima os componentes:
	public void iniciaAnimacao() {

		long prxAtualizacao = 0;

		startaFantasmas();
		startaArbitro();

		while (animacao) {
			if (System.currentTimeMillis() > prxAtualizacao) {
				/*
				 * Atualiza o jogo e depois repinta
				 */
				this.atualizaJogo();
				/*
				 * Redesenha toda a tela com o metodo 'paintComponent'
				 */
				this.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
				atualizaBuffs();
			}
			eOFim();
			if (Janela.fimDoJogo) {
				finalizaJogo();
			}
		}
		
	}

	/*
	 * Atualiza variaveis do jogo:
	 */
	public void atualizaJogo() {

		if (janela.vidas <= 0){
			Janela.fimDoJogo = true;
		}

		if (Janela.fimDoJogo) {
			mensagemDeDerrota();
		}

		desenhaTela();
		desenhaMapa();
		
		if (janela.pacman.ativo) {
			pegaPonto();
			pegaTeclado();
			checaMovimentacao();
			if (morreu()) {
				pacmanMorrendo.tocar();
				reiniciaPacman();
				reiniciaFantasmas();
			}
		}
		
		janela.movePacman();
		desenhaPacman();
		desenhaFantasmas();
		escrevePontuacao();
		desenhaVidas();
	}
	
	private void atualizaBuffs(){
		buffVelocidade();
		buffInvencibilidade();
	}
	
	private void buffVelocidade(){
		if (duracaoVelocidade > System.currentTimeMillis() && janela.pacman.godSpeed) {
			janela.pacman.velocidade = janela.VELOCIDADE_MAXIMA;
			if(!tocandoCorrendo){
				correndo.stop();
				correndo.tocar();
				tocandoCorrendo = true;
			}
		} else if (duracaoVelocidade < System.currentTimeMillis()){
			janela.pacman.velocidade = janela.PACMAN_VELOCIDADE_NORMAL;
			janela.pacman.godSpeed = false;
			tocandoCorrendo = false;
		}
	}
	
	private void buffInvencibilidade(){
		if (duracaoInvencibilidade > System.currentTimeMillis() && janela.pacman.invencivel) {
			if(!tocandoEstrelinha){
				estrelinha.stop();
				estrelinha.tocar();
				tocandoEstrelinha = true;
			}
			int r,g,b;
			r = (int)(Math.random() * 255);
			g = (int)(Math.random() * 255);
			b = (int)(Math.random() * 255);
			Color cor = new Color(r, g, b);
			janela.g2d.setColor(cor);
			janela.texto.desenha(janela.g2d, "INVENCIVEL!!!!", janela.texto.altura, 20);
			janela.pacman.setCor(cor);
		} else if (duracaoInvencibilidade < System.currentTimeMillis()) {
			janela.pacman.invencivel = false;
			tocandoEstrelinha = false;
			janela.pacman.setCor(Color.YELLOW);
		}
	}

	private void startaFantasmas() {
		janela.tFantasma01.start();
		janela.tFantasma02.start();
		janela.tFantasma03.start();
		janela.tFantasma04.start();
	}
	
	private void startaArbitro(){
		janela.tArbitro.start();
	}

	private void desenhaTela() {
		janela.g2d.setColor(Color.BLACK);
		janela.g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	private void desenhaMapa() {
		janela.mapa.desenha(janela.g2d);
	}

	private void desenhaPacman() {
		janela.pacman.desenha(janela.g2d);
	}

	private void desenhaFantasmas() {
		if (janela.pacman.invencivel) {
			desenhaFantasmasComMedo();
		} else {
			desenhaFantasmasNormais();
		}
	}
	
	private void desenhaFantasmasNormais() {
		janela.fantasma01.desenha(janela.g2d);
		janela.fantasma02.desenha(janela.g2d);
		janela.fantasma03.desenha(janela.g2d);
		janela.fantasma04.desenha(janela.g2d);
	}
	
	private void desenhaFantasmasComMedo() {
		janela.fantasma01.desenhaComMedo(janela.g2d);
		janela.fantasma02.desenhaComMedo(janela.g2d);
		janela.fantasma03.desenhaComMedo(janela.g2d);
		janela.fantasma04.desenhaComMedo(janela.g2d);
	}

	private void escrevePontuacao() {
		janela.g2d.setColor(Color.WHITE);
		janela.texto.desenha(janela.g2d, "Pontuação: " + String.valueOf(janela.pontos), this.getWidth() - 125, 20);
	}

	private void desenhaVidas() {
		janela.g2d.setColor(Color.WHITE);
		janela.texto.desenha(janela.g2d, String.valueOf(janela.vidas), 10, this.getHeight() - janela.vida.altura);
		for (int i = 1; i <= janela.vidas; i++) {
			janela.vida.x = (i * janela.vida.largura + i * janela.espacamento) + 10;
			janela.vida.y = (int) (this.getHeight() - janela.vida.altura * 1.5);
			janela.vida.setCor(Color.YELLOW);
			janela.vida.desenha(janela.g2d,0);
		}		
	}

	private void reiniciaPacman() {
		janela.vidas--;
		janela.pacman.x = 9 * Mapa.BLOCK_SIZE;
		janela.pacman.y = 15 * Mapa.BLOCK_SIZE;
		janela.pacman.direcaoX = 0;
		janela.pacman.direcaoY = 0;
	}

	private void reiniciaFantasmas() {
		janela.fantasma01.x = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma01.y = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma01.direcaoX = 0;
		janela.fantasma01.setSaiu(false);

		janela.fantasma02.x = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma02.y = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma02.direcaoX = 0;
		janela.fantasma02.setSaiu(false);

		janela.fantasma03.x = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma03.y = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma03.direcaoX = 0;
		janela.fantasma03.setSaiu(false);

		janela.fantasma04.x = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma04.y = 9 * Mapa.BLOCK_SIZE;
		janela.fantasma04.direcaoX = 0;
		janela.fantasma04.setSaiu(false);
	}

	private void pegaPonto() {
		
		int posX, posY;
		short ch;

		posX = (janela.pacman.x + janela.pacman.altura / 2) / Mapa.BLOCK_SIZE;
		posY = (janela.pacman.y + janela.pacman.largura / 2) / Mapa.BLOCK_SIZE;

		ch = Mapa.infoMAPA[posY][posX];

		if ((ch & 16) == 16) {
			Mapa.infoMAPA[posY][posX] = (short) (ch & 15);
			ganhaPonto(5);
			Mapa.TOTAL_BOLINHAS--;
			pacmanComendo.tocar();
		}

		if ((ch & 32) == 32) {
			Mapa.infoMAPA[posY][posX] = (short) (ch & 15);
			ganhaPonto(10);
			Mapa.TOTAL_BOLINHAS--;
			janela.pacman.godSpeed = true;
			duracaoVelocidade = System.currentTimeMillis() + 6000;
			if(duracaoVelocidade > duracaoAnteriorV){
				tocandoCorrendo = false;
				duracaoAnteriorV = duracaoVelocidade;
			}
		}
		
		if ((ch & 64) == 64) {
			Mapa.infoMAPA[posY][posX] = (short) (ch & 15);
			ganhaPonto(15);
			Mapa.TOTAL_BOLINHAS--;
			janela.pacman.invencivel = true;
			duracaoInvencibilidade = System.currentTimeMillis() + 6000;
			if(duracaoInvencibilidade > duracaoAnteriorI){
				tocandoEstrelinha = false;
				duracaoAnteriorI = duracaoInvencibilidade;
			}
		}
	}

	private void pegaTeclado() {
		//MOVIMENTAÇÃO DO PACMAN
		if (janela.controleTeclas[TeclaEnum.LEFT.ordinal()]) {
			if (!colideBordas && podeVirar()) {
				janela.pacman.direcaoX = -1;
				janela.pacman.direcaoY = 0;
			}
		} else if (janela.controleTeclas[TeclaEnum.RIGHT.ordinal()]) {
			if (!colideBordas && podeVirar()) {
				janela.pacman.direcaoX = 1;
				janela.pacman.direcaoY = 0;
			}
		} else if (janela.controleTeclas[TeclaEnum.UP.ordinal()]) {
			if (!colideBordas && podeVirar()) {
				janela.pacman.direcaoX = 0;
				janela.pacman.direcaoY = -1;
			}
		} else if (janela.controleTeclas[TeclaEnum.DOWN.ordinal()]) {
			if (!colideBordas && podeVirar()) {
				janela.pacman.direcaoX = 0;
				janela.pacman.direcaoY = 1;
			}
		} else if (janela.controleTeclas[TeclaEnum.SPACE.ordinal()]) {
			if (janela.pacman.velocidade != janela.VELOCIDADE_MAXIMA) {
				janela.pacman.velocidade = janela.VELOCIDADE_MAXIMA;
			}
		} else if (janela.controleTeclas[TeclaEnum.E.ordinal()]) {
			janela.vidas++;
		}else if (janela.controleTeclas[TeclaEnum.ESCAPE.ordinal()]) {
			Janela.fimDoJogo = true;
		} 
	}

	private void checaMovimentacao() {
		int posX, posY;
		short ch2;

		posX = (int) ((janela.pacman.x + janela.pacman.largura) / Mapa.BLOCK_SIZE);
		posY = (int) ((janela.pacman.y + janela.pacman.altura) / Mapa.BLOCK_SIZE);
		if (janela.pacman.direcaoX > 0) {
			posX = (janela.pacman.x) / Mapa.BLOCK_SIZE;
		}
		if (janela.pacman.direcaoY > 0) {
			posY = (janela.pacman.y) / Mapa.BLOCK_SIZE;
		}
		ch2 = Mapa.infoMAPA[posY][posX];
		if ((janela.pacman.direcaoX == -1 && janela.pacman.direcaoY == 0 && (ch2 & 1) != 0)
				|| (janela.pacman.direcaoX == 0 && janela.pacman.direcaoY == -1 && (ch2 & 2) != 0)
				|| (janela.pacman.direcaoX == 1 && janela.pacman.direcaoY == 0 && (ch2 & 4) != 0)
				|| (janela.pacman.direcaoX == 0 && janela.pacman.direcaoY == 1 && (ch2 & 8) != 0)) {
			janela.pacman.direcaoX = 0;
			janela.pacman.direcaoY = 0;
			colideBordas = true;
		} else {
			colideBordas = false;
		}
	}

	private boolean podeVirar() {

		int posDBx, posDBy, posDTx, posDTy, posEBx, posEBy, posETx, posETy;
		short mDB, mDT, mEB, mET;

		posDBx = (janela.pacman.x + janela.pacman.largura) / Mapa.BLOCK_SIZE;
		posDBy = (janela.pacman.y + janela.pacman.altura) / Mapa.BLOCK_SIZE;
		mDB = Mapa.infoMAPA[posDBy][posDBx];

		posDTx = (janela.pacman.x + janela.pacman.largura) / Mapa.BLOCK_SIZE;
		posDTy = (janela.pacman.y) / Mapa.BLOCK_SIZE;
		mDT = Mapa.infoMAPA[posDTy][posDTx];

		posETx = (janela.pacman.x) / Mapa.BLOCK_SIZE;
		posETy = (janela.pacman.y) / Mapa.BLOCK_SIZE;
		mET = Mapa.infoMAPA[posETy][posETx];

		posEBx = (janela.pacman.x) / Mapa.BLOCK_SIZE;
		posEBy = (janela.pacman.y) / Mapa.BLOCK_SIZE;
		mEB = Mapa.infoMAPA[posEBy][posEBx];

		if ((mDB == mDT) && (mDB == mEB) && (mDB == mET) && (mDT == mEB) && (mDT == mET) && (mEB == mET)) {
			return true;
		}

		return false;
	}

	private boolean morreu() {

		if ((Util.colide(janela.pacman, janela.fantasma01)) || (Util.colide(janela.pacman, janela.fantasma02))
				|| (Util.colide(janela.pacman, janela.fantasma03))
				|| (Util.colide(janela.pacman, janela.fantasma04))) {

			if (janela.pacman.invencivel) {

				if (Util.colide(janela.pacman, janela.fantasma01)) {
					mataFantasma(janela.fantasma01);
				}
				if (Util.colide(janela.pacman, janela.fantasma02)) {
					mataFantasma(janela.fantasma02);
				}
				if (Util.colide(janela.pacman, janela.fantasma03)) {
					mataFantasma(janela.fantasma03);
				}
				if (Util.colide(janela.pacman, janela.fantasma04)) {
					mataFantasma(janela.fantasma04);
				}

				return false;
			}

			return true;
		}
		return false;
	}
	
	private void mataFantasma(Elemento e){
		e.x = 9 * Mapa.BLOCK_SIZE;
		e.y = 9 * Mapa.BLOCK_SIZE;
		e.direcaoX = 0;
		ganhaPonto(100);
	}

	private void eOFim() {
		/*
		 * COLOCAR TOTAL_BOLINHAS == 141 PARA TESTES
		 */
		if (Mapa.TOTAL_BOLINHAS == 0) {
			if((tocandoCorrendo) || (tocandoEstrelinha))
				correndo.stop();
			if(tocandoEstrelinha)
				estrelinha.stop();
			Janela.fimDoJogo = true;
			mensagemDeVitoria();
		}

	}
	
	private void mensagemDeVitoria(){
		youWin.tocar();
		int centroX = janela.JANELA_LARGURA/2 - 90;
		int centroY = janela.JANELA_ALTURA/2 - 50;
		while(!janela.controleTeclas[TeclaEnum.ESCAPE.ordinal()]){
			janela.g2d.setColor(Color.WHITE);
			Font fonte = new Font("Tahoma", Font.PLAIN,36);
			janela.texto.setFonte(fonte);
			janela.texto.desenha(janela.g2d, "YOU WIN!!!", centroX, centroY);
			this.repaint();
		}
		finalizaJogo();
	}
	
	private void mensagemDeDerrota(){
		gameOver.tocar();
		long prxFala = System.currentTimeMillis();
		boolean termina = false;
		int cont = 0;
		int centroX = janela.JANELA_LARGURA/2;
		int centroY = janela.JANELA_ALTURA/2;
		Color corFaixa = Color.BLACK;
		Color corLetra = Color.WHITE;
		Font fonte = new Font("Times New Roman", Font.PLAIN,36);
		janela.texto.setFonte(fonte);
		
		/*
		 * ==========LISTA COM TODAS FONTES==========
		 * String[] t = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		 * for(int i = 0; i< t.length; i++){
		 * 		System.out.println(t[i]);
		 * }
		 * ==========######################========== 
		 */
		
		
		while(!janela.controleTeclas[TeclaEnum.ESCAPE.ordinal()]){
			if (System.currentTimeMillis() > prxFala && !termina) {
				if(cont == 0){
					janela.g2d.setColor(corFaixa);
					janela.g2d.fillRect(0, centroY-90, janela.JANELA_LARGURA, 60);
					janela.g2d.setColor(corLetra);
					janela.texto.desenha(janela.g2d, "YOU", centroX - 40, centroY - 45);
					prxFala = prxFala+1500;
					cont++;
				}
				else if(cont == 1){
					janela.g2d.setColor(corFaixa);
					janela.g2d.fillRect(0, centroY-90, janela.JANELA_LARGURA, 60);
					janela.g2d.setColor(corLetra);
					janela.texto.desenha(janela.g2d, "SHALL NOT", centroX - 90, centroY - 45);
					prxFala = prxFala+2000;
					cont++;
				} else if(cont == 2){
					janela.g2d.setColor(corFaixa);
					janela.g2d.fillRect(0, centroY-90, janela.JANELA_LARGURA, 60);
					janela.g2d.setColor(corLetra);
					janela.texto.desenha(janela.g2d, "PAAAAAAASS!!!", centroX - 120, centroY - 45);
					termina = true;
				}
			}
			this.repaint();
		}
		finalizaJogo();
	}
	
	private void finalizaJogo(){
		janela.g2d.dispose();
		System.exit(0);
	}
	
	private void ganhaPonto(int valor){
		janela.pontos += valor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(janela.buffer, 0, 0, null);
	}

}
