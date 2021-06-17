package principal;

import java.util.ArrayList;

public class Arbitro implements Runnable {

	Janela janela;
	
	private final String BAIXO = "baixo";
	private final String CIMA = "cima";
	private final String ESQUERDA = "esquerda";
	private final String DIREITA = "direita";
	
	public Arbitro(Janela janela) {
		this.janela = janela;
	}

	@Override
	public void run() {
		while (!Janela.fimDoJogo) {
			if (janela.fantasma01.getSaiu()){
				moveFantasmaAzul();
				moveFantasmaLaranja();
				moveFantasmaRosa();
				moveFantasmaVermelho();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * =========================================================================================
	 * ==========================INICIO DA MOVIMENTAÇÃO DOS FANTASMAS===========================
	 * =========================================================================================
	 */
	
	/*
	 * INICIO
	 * MOVIMENTAÇÃO DO FANTASMA AZUL============================================================
	 */

	private void moveFantasmaAzul() {
		janela.fantasma01.setPesoX(0);
		janela.fantasma01.setPesoY(0);
		int casaXPacman = janela.pacman.x / Mapa.BLOCK_SIZE;
		int casaYPacman = janela.pacman.y / Mapa.BLOCK_SIZE;

		int casaXFantasma = janela.fantasma01.x / Mapa.BLOCK_SIZE;
		int casaYFantasma = janela.fantasma01.y / Mapa.BLOCK_SIZE;

		boolean podeDescer = podeDescer(janela.fantasma01.dirY);
		boolean podeDireita = podeDireita(janela.fantasma01.dirX);
		boolean podeEsquerda = podeEsquerda(janela.fantasma01.dirX);
		boolean podeSubir = podeSubir(janela.fantasma01.dirY);
		
		if (casaXPacman > casaXFantasma) {
			if (podeDireita) {
				janela.fantasma01.recebePalavra(DIREITA);
			} else if (podeDescer) {
				janela.fantasma01.recebePalavra(BAIXO);
			} else if (podeSubir) {
				janela.fantasma01.recebePalavra(CIMA);
			}
		}

		if (casaYPacman > casaYFantasma) {
			if (podeDescer) {
				janela.fantasma01.recebePalavra(BAIXO);
			} else if (podeEsquerda) {
				janela.fantasma01.recebePalavra(ESQUERDA);
			} else if (podeDireita) {
				janela.fantasma01.recebePalavra(DIREITA);
			}
		}

		if (casaXPacman < casaXFantasma) {
			if (podeEsquerda) {
				janela.fantasma01.recebePalavra(ESQUERDA);
			} else if (podeDescer) {
				janela.fantasma01.recebePalavra(BAIXO);
			} else if (podeSubir) {
				janela.fantasma01.recebePalavra(CIMA);
			}
		}

		if (casaYPacman < casaYFantasma) {
			if (podeSubir) {
				janela.fantasma01.recebePalavra(CIMA);
			} else if (podeEsquerda) {
				janela.fantasma01.recebePalavra(ESQUERDA);
			} else if (podeDireita) {
				janela.fantasma01.recebePalavra(DIREITA);
			}
		}
	}
	
	/*
	 * FIM
	 * MOVIMENTAÇÃO DO FANTASMA AZUL============================================================
	 */
	
	/*
	 * =========================================================================================
	 */
	
	/*
	 * INICIO
	 * MOVIMENTAÇÃO DO FANTASMA LARANJA=========================================================
	 */

	private void moveFantasmaLaranja() {
		janela.fantasma02.setPesoX(0);
		janela.fantasma02.setPesoY(0);
		int casaXPacman = janela.pacman.x / Mapa.BLOCK_SIZE;
		int casaYPacman = janela.pacman.y / Mapa.BLOCK_SIZE;

		int casaXFantasma = janela.fantasma02.x / Mapa.BLOCK_SIZE;
		int casaYFantasma = janela.fantasma02.y / Mapa.BLOCK_SIZE;

		boolean podeDescer = podeDescer(janela.fantasma02.dirY);
		boolean podeDireita = podeDireita(janela.fantasma02.dirX);
		boolean podeEsquerda = podeEsquerda(janela.fantasma02.dirX);
		boolean podeSubir = podeSubir(janela.fantasma02.dirY);
		
		if (casaXPacman < casaXFantasma) {
			if (podeEsquerda) {
				janela.fantasma02.recebePalavra(ESQUERDA);
			} else if (podeDescer) {
				janela.fantasma02.recebePalavra(BAIXO);
			} else if (podeSubir) {
				janela.fantasma02.recebePalavra(CIMA);
			}
		}

		if (casaYPacman > casaYFantasma) {
			if (podeDescer) {
				janela.fantasma02.recebePalavra(BAIXO);
			} else if (podeEsquerda) {
				janela.fantasma02.recebePalavra(ESQUERDA);
			} else if (podeDireita) {
				janela.fantasma02.recebePalavra(DIREITA);
			}
		}
		
		if (casaXPacman > casaXFantasma) {
			if (podeDireita) {
				janela.fantasma02.recebePalavra(DIREITA);
			} else if (podeDescer) {
				janela.fantasma02.recebePalavra(BAIXO);
			} else if (podeSubir) {
				janela.fantasma02.recebePalavra(CIMA);
			}
		}

		if (casaYPacman < casaYFantasma) {
			if (podeSubir) {
				janela.fantasma02.recebePalavra(CIMA);
			} else if (podeEsquerda) {
				janela.fantasma02.recebePalavra(ESQUERDA);
			} else if (podeDireita) {
				janela.fantasma02.recebePalavra(DIREITA);
			}
		}
	}
	
	/*
	 * FIM
	 * MOVIMENTAÇÃO DO FANTASMA LARANJA=========================================================
	 */
	
	/*
	 * =========================================================================================
	 */
	
	/*
	 * INICIO
	 * MOVIMENTAÇÃO DO FANTASMA ROSA============================================================
	 */

	private void moveFantasmaRosa() {
		janela.fantasma03.setPesoX(0);
		janela.fantasma03.setPesoY(0);
		int casaXPacman = janela.pacman.x / Mapa.BLOCK_SIZE;
		int casaYPacman = janela.pacman.y / Mapa.BLOCK_SIZE;

		int casaXFantasma = janela.fantasma03.x / Mapa.BLOCK_SIZE;
		int casaYFantasma = janela.fantasma03.y / Mapa.BLOCK_SIZE;

		boolean podeDescer = podeDescer(janela.fantasma03.dirY);
		boolean podeDireita = podeDireita(janela.fantasma03.dirX);
		boolean podeEsquerda = podeEsquerda(janela.fantasma03.dirX);
		boolean podeSubir = podeSubir(janela.fantasma03.dirY);
		
		if (casaXPacman > casaXFantasma) {
			if (podeDireita) {
				janela.fantasma03.recebePalavra(DIREITA);
			} else if (podeDescer) {
				janela.fantasma03.recebePalavra(BAIXO);
			} else if (podeSubir) {
				janela.fantasma03.recebePalavra(CIMA);
			}
		}
		
		else if (casaXPacman < casaXFantasma) {
			if (podeEsquerda) {
				janela.fantasma03.recebePalavra(ESQUERDA);
			} else if (podeDescer) {
				janela.fantasma03.recebePalavra(BAIXO);
			} else if (podeSubir) {
				janela.fantasma03.recebePalavra(CIMA);
			}
		}

		if (casaYPacman < casaYFantasma) {
			if (podeSubir) {
				janela.fantasma03.recebePalavra(CIMA);
			} else if (podeEsquerda) {
				janela.fantasma03.recebePalavra(ESQUERDA);
			} else if (podeDireita) {
				janela.fantasma03.recebePalavra(DIREITA);
			}
		}
		
		else if (casaYPacman > casaYFantasma) {
			if (podeDescer) {
				janela.fantasma03.recebePalavra(BAIXO);
			} else if (podeEsquerda) {
				janela.fantasma03.recebePalavra(ESQUERDA);
			} else if (podeDireita) {
				janela.fantasma03.recebePalavra(DIREITA);
			}
		}
	}
	
	/*
	 * FIM
	 * MOVIMENTAÇÃO DO FANTASMA ROSA============================================================
	 */
	
	/*
	 * =========================================================================================
	 */
	
	/*
	 * INICIO
	 * MOVIMENTAÇÃO DO FANTASMA VERMELHO========================================================
	 */

	private void moveFantasmaVermelho() {
		janela.fantasma04.setPesoX(0);
		janela.fantasma04.setPesoY(0);
		
		try {
			int randomico = (int) (Math.random() * janela.fantasma04.dirX.size());
			int valorDeX = janela.fantasma04.dirX.get(randomico);
			int valorDeY = janela.fantasma04.dirY.get(randomico);

			if (valorDeX == -1 && valorDeY == 0) {
				janela.fantasma04.recebePalavra(ESQUERDA);
			} else if (valorDeX == 0 && valorDeY == -1) {
				janela.fantasma04.recebePalavra(CIMA);
			} else if (valorDeX == 1 && valorDeY == 0) {
				janela.fantasma04.recebePalavra(DIREITA);
			} else if (valorDeX == 0 && valorDeY == 1) {
				janela.fantasma04.recebePalavra(BAIXO);
			}
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			// System.out.println(e.getMessage());
		}
	}
	
	/*
	 * FIM
	 * MOVIMENTAÇÃO DO FANTASMA VERMELHO========================================================
	 */
	
	/*
	 * =========================================================================================
	 * ============================FIM DA MOVIMENTAÇÃO DOS FANTASMAS============================
	 * =========================================================================================
	 */

	/*
	 * Como são duas THREADS trabalhando separado na CRIAÇÂO e UTILIZAÇÃO do ArrayList,
	 * foi utilizado o TRY/CATCH para que, caso ocorra algum erro, a execução não fique
	 * afetada.
	 */
	
	/*
	 * INICIO DAS FUNÇÕES DE POSSIVEIS MOVIMENTOS===============================================
	 */
	
	private boolean podeDescer(ArrayList<Integer> lista) {
		if (lista.size() == 0 || lista.isEmpty())
			return false;

		try {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i) == 1) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			//System.out.println(e.getMessage());
		}
		return false;
	}

	private boolean podeDireita(ArrayList<Integer> lista) {
		if (lista.size() == 0 || lista.isEmpty())
			return false;

		try {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i) == 1) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			// System.out.println(e.getMessage());
		}
		return false;
	}

	private boolean podeEsquerda(ArrayList<Integer> lista) {
		if (lista.size() == 0 || lista.isEmpty())
			return false;

		try {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i) == -1) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			// System.out.println(e.getMessage());
		}
		return false;
	}

	private boolean podeSubir(ArrayList<Integer> lista) {
		if (lista.size() == 0 || lista.isEmpty())
			return false;

		try {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i) == -1) {
					return true;
				}
			}
		} catch (IndexOutOfBoundsException | NullPointerException e) {
			// System.out.println(e.getMessage());
		}
		return false;
	}
	
	/*
	 * FIM DAS FUNÇÕES DE POSSIVEIS MOVIMENTOS==================================================
	 */

}
