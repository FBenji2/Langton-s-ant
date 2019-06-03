package matrix_and_ants;

import java.io.Serializable;

public class Tile implements Serializable { //a m�trix ilyen "csemp�ket" t�rol mag�ban
	public Coords coords; //koordin�t�k classt t�rol
	public int colorid; //a Colorlistben az adott sz�n sorsz�ma, az�rt ezt egy sz�n helyett, mivel �gy lehetett j�l szerializ�lni ezt a classt
	
	public Tile(int x,int y) //az �j elem feh�r sz�n� szokott lenni, ennek k�l�n konstruktor van, ehhez el�g megadni csak a koordin�t�t
	{
		coords=new Coords(x,y);
		colorid=0;
	}
}
