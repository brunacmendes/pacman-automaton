package principal;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import Util.Elemento;
import Util.FantasmasEnum;
import Util.Texto;

public class Janela extends JFrame {

	private static final long serialVersionUID = 1L;

	final public int JANELA_ALTURA = 680;
	final public int JANELA_LARGURA = 580;
	final public int VELOCIDADE_MAXIMA = 12;
	final public int PACMAN_VELOCIDADE_NORMAL = 6;
	
	public int espacamento = 15;

	private Tela tela;

	public Mapa mapa;

	public Arbitro arbitro;
	public Thread tArbitro;
	
	public Elemento pacman;
	public Fantasma01 fantasma01;
	public Thread tFantasma01;
	public Fantasma02 fantasma02;
	public Thread tFantasma02;
	public Fantasma03 fantasma03;
	public Thread tFantasma03;
	public Fantasma04 fantasma04;
	public Thread tFantasma04;

	public Pacman vida;
	public Texto texto = new Texto();

	public Graphics2D g2d;
	public BufferedImage buffer;

	public int vidas = 3;
	public int pontos;

	public boolean[] controleTeclas = new boolean[7];
	public static boolean fimDoJogo = false;

	public Janela() {
		iniciaTela();
		// Configura a janela:
		configuracaoDeTela();
		// Inicia a animacao:
		carregaJogo();
		tela.iniciaAnimacao();
	}

	public void carregaJogo() {
		iniciaMapa();
		iniciaPacman();
		iniciaFantasmas();
		iniciaArbitro();
	}
	
	private void iniciaTela() {
		// Adiciona tela a janela:
		tela = new Tela(this);
		getContentPane().add(tela);

		buffer = new BufferedImage(JANELA_LARGURA, JANELA_ALTURA, BufferedImage.TYPE_INT_RGB);
		g2d = buffer.createGraphics();

		// Adiciona o leitor de teclado:
		addKeyListener(new Teclado(this));
	}
	
	private void configuracaoDeTela(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setSize(JANELA_LARGURA, JANELA_ALTURA);
		setResizable(false);
		setLocationRelativeTo(null); /* Centraliza a tela */
		setVisible(true);
	}
	
	private void iniciaPacman(){
		pacman = new Pacman();
		pacman.velocidade = PACMAN_VELOCIDADE_NORMAL;
		pacman.ativo = true;
		pacman.x = 9 * Mapa.BLOCK_SIZE;
		pacman.y = 15 * Mapa.BLOCK_SIZE;
		
		vida = new Pacman();
		vida.ativo = true;
	}
	
	private void iniciaFantasmas(){
		fantasma01 = new Fantasma01(FantasmasEnum.AZUL.getCor());
		tFantasma01 = new Thread(fantasma01);
		fantasma01.ativo = true;
		
		fantasma02 = new Fantasma02(FantasmasEnum.LARANJA.getCor());
		tFantasma02 = new Thread(fantasma02);
		fantasma02.ativo = true;
		
		fantasma03 = new Fantasma03(FantasmasEnum.ROSA.getCor());
		tFantasma03 = new Thread(fantasma03);
		fantasma03.ativo = true;
		
		fantasma04 = new Fantasma04(FantasmasEnum.VERMELHO.getCor());
		tFantasma04 = new Thread(fantasma04);
		fantasma04.ativo = true;
	}
	
	private void iniciaMapa(){
		mapa = new Mapa();
	}
	
	private void iniciaArbitro(){
		arbitro = new Arbitro(this);
		tArbitro = new Thread(arbitro);
		tArbitro.setPriority(6);
	}

	public void movePacman() {

		pacman.incX((int) (pacman.direcaoX * pacman.velocidade));
		pacman.incY((int) (pacman.direcaoY * pacman.velocidade));
	}

	
	public static void main(String[] args) {
		new Janela();
	}

}
