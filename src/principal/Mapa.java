package principal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Mapa {
	
	public static final int BLOCK_SIZE = 30; //Tamanho da "CASA" da matriz
	public static final int N_BLOCKS = 20;//Tamanho da matriz. Nesse caso será uma 20 por 20
	public static final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
	
	public static int TOTAL_BOLINHAS = 0;
	private static final Color COR_DO_PONTO = Color.WHITE;
	
	private static final Color COR_DO_MAPA = Color.BLUE;
	
	public static short[][] infoMAPA;
	
	private final short MAPA[][] = {
        	{0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0},
        	{0,	35,	26,	26,	18,	26,	26,	26,	22,	0,	19,	26,	26,	26,	18,	26,	26,	38,	0,	0},
        	{0,	21,	0,	0,	21,	0,	0,	0,	21,	0,	21,	0,	0,	0,	21,	0,	0,	21,	0,	0},
        	{0,	17,	26,	26,	16,	26,	18,	26,	24,	74,	24,	26,	18,	26,	16,	26,	26,	20,	0,	0},
        	{0,	21,	0,	0,	21,	0,	21,	0,	0,	0,	0,	0,	21,	0,	21,	0,	0,	21,	0,	0},
        	{0,	25,	26,	26,	20,	0,	25,	26,	22,	0,	19,	26,	28,	0,	17,	26,	26,	28,	0,	0},
        	{0,	0,	0,	0,	21,	0,	0,	0,	5,	0,	5,	0,	0,	0,	21,	0,	0,	0,	0,	0},
        	{0,	0,	0,	0,	21,	0,	3,	10,	8,	10,	8,	10,	6,	0,	21,	0,	0,	0,	0,	0},
        	{0,	0,	0,	0,	21,	0,	5,	0,	0,	5,	0,	0,	5,	0,	21,	0,	0,	0,	0,	0},
        	{0,	0,	0,	0,	17,	10,	4,	0,	0,	13,	0,	0,	1,	10,	20,	0,	0,	0,	0,	0},
        	{0,	0,	0,	0,	21,	0,	5,	0,	0,	0,	0,	0,	5,	0,	21,	0,	0,	0,	0,	0},
        	{0,	0,	0,	0,	21,	0,	1,	10,	10,	10,	10,	10,	4,	0,	21,	0,	0,	0,	0,	0},
        	{0,	0,	0,	0,	21,	0,	5,	0,	0,	0,	0,	0,	5,	0,	21,	0,	0,	0,	0,	0},
        	{0,	19,	26,	26,	16,	26,	24,	26,	22,	0,	19,	26,	24,	26,	16,	26,	26,	22,	0,	0},
        	{0,	21,	0,	0,	21,	0,	0,	0,	21,	0,	21,	0,	0,	0,	21,	0,	0,	21,	0,	0},
        	{0,	25,	22,	0,	17,	26,	18,	10,	8,	10,	8,	10,	18,	26,	20,	0,	19,	28,	0,	0},
        	{0,	0,	21,	0,	21,	0,	21,	0,	0,	0,	0,	0,	21,	0,	21,	0,	21,	0,	0,	0},
        	{0,	19,	24,	26,	28,	0,	25,	26,	22,	0,	19,	26,	28,	0,	25,	26,	24,	22,	0,	0},
        	{0,	21,	0,	0,	0,	0,	0,	0,	21,	0,	21,	0,	0,	0,	0,	0,	0,	21,	0,	0},
        	{0,	41,	26,	26,	26,	26,	26,	26,	24,	26,	24,	26,	26,	26,	26,	26,	26,	44,	0,	0},
        };
	/*
     * Mapa desenhado com números binários:
     * 
     * 1 = ESQUERDA	|	0000001
     * 2 = CIMA		|	0000010
     * 4 = DIREITA	|	0000100
     * 8 = BAIXO	|	0001000
     * 16 = BOLINHA	|	0010000
     * 32 = ARGOLA	|	0100000
     * 64 = ESTRELA |	1000000
     * 
     * Com isso as casas são desenhadas com a soma dos números.
     * Exemplo:
     * 26 = BOLINHA(16), BAIXO(8), CIMA(2)	|	11010
     */
	
	public Mapa() {
		infoMAPA = new short[N_BLOCKS][N_BLOCKS];
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < MAPA.length; j++) {
				infoMAPA[i][j] = MAPA[i][j];
				if(((infoMAPA[i][j] & 16) != 0) || ((infoMAPA[i][j] & 32) != 0) || ((infoMAPA[i][j] & 64) != 0))
					TOTAL_BOLINHAS++;
			}
        }
	}
	
	 public void desenha(Graphics2D g2d) {

	        short i = 0;
	        short j = 0;
	        int x, y;

	        for (x = 1; x < SCREEN_SIZE; x+=BLOCK_SIZE) {
	        	for (y = 1; y < SCREEN_SIZE; y+=BLOCK_SIZE) {
	        		g2d.setColor(COR_DO_MAPA);
	                g2d.setStroke(new BasicStroke(2));
	                
	                if ((infoMAPA[i][j] & 1) == 1) { 
	                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
	                }
	                
	                if ((infoMAPA[i][j] & 2) == 2) { 
	                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
	                }

	                if ((infoMAPA[i][j] & 4) == 4) {
	                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
	                            y + BLOCK_SIZE - 1);
	                }

	                if ((infoMAPA[i][j] & 8) == 8) { 
	                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
	                            y + BLOCK_SIZE - 1);
	                }

	                if ((infoMAPA[i][j] & 16) == 16) { 
	                    g2d.setColor(COR_DO_PONTO);
	                    g2d.fillRect(x + 11, y + 11, 2, 2);
	                }
	                
	                if ((infoMAPA[i][j] & 32) == 32) {
	                	Image img = Toolkit.getDefaultToolkit().getImage("imagens/argola.png");
	                    g2d.drawImage(img, x+3, y+1, 24, 24, null);
	                }
	                
	                if ((infoMAPA[i][j] & 64) == 64) { 
	                    Image img = Toolkit.getDefaultToolkit().getImage("imagens/estrelinha.png");
	                    g2d.drawImage(img, x+3, y+1, 24, 24, null);
	                }
	                
	                i++;
	    		}
	        	i=0;
	        	j++;
			}
	    }
	
}
