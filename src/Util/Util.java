package Util;

public class Util {

	public static boolean colide(Elemento a, Elemento b) {

		if (!a.ativo || !b.ativo)
			return false;

		if(colideX(a,b) && colideY(a,b))
			return true;

		return false;
	}

	public static boolean colideX(Elemento a, Elemento b) {

		if ((a.x <= b.x && a.x + a.largura >= b.x) || (a.x >= b.x && a.x <= b.x + b.largura))
			return true;

		return false;
	}

	public static boolean colideY(Elemento a, Elemento b) {

		if (((a.y <= b.y) && (a.y + a.altura >= b.y))
				|| ((a.y <= b.y + b.altura) && (a.y + a.altura >= b.y + b.altura)))
			return true;

		return false;
	}

}
