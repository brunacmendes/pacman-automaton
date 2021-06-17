package principal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Util.*;

public class Teclado implements KeyListener{

	private Janela janela;
	
	public Teclado (Janela janela) {
		this.janela = janela; /* Poder usar variaveis de Janela2 */
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int tecla = e.getKeyCode(); /* Código ASCII da tecla pressionada */
		setaTecla(tecla, true);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		setaTecla(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	private void setaTecla(int tecla, Boolean pressionado) {
		
		switch (tecla) {
			case KeyEvent.VK_UP :
				janela.controleTeclas[TeclaEnum.UP.ordinal()] = pressionado;
				break;
			case KeyEvent.VK_DOWN :
				janela.controleTeclas[TeclaEnum.DOWN.ordinal()] = pressionado;
				break;
			case KeyEvent.VK_LEFT :
				janela.controleTeclas[TeclaEnum.LEFT.ordinal()] = pressionado;
				break;
			case KeyEvent.VK_RIGHT :
				janela.controleTeclas[TeclaEnum.RIGHT.ordinal()] = pressionado;
				break;
			case KeyEvent.VK_SPACE :
				janela.controleTeclas[TeclaEnum.SPACE.ordinal()] = pressionado;
				break;
			case KeyEvent.VK_ESCAPE : 
				janela.controleTeclas[TeclaEnum.ESCAPE.ordinal()] = pressionado;
				break;
			case KeyEvent.VK_E : 
				janela.controleTeclas[TeclaEnum.E.ordinal()] = pressionado;
				break;
		}
	}
	
}
