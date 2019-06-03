package matrix_and_ants;

import java.io.Serializable;

public class Tile implements Serializable { //a mátrix ilyen "csempéket" tárol magában
	public Coords coords; //koordináták classt tárol
	public int colorid; //a Colorlistben az adott szín sorszáma, azért ezt egy szín helyett, mivel így lehetett jól szerializálni ezt a classt
	
	public Tile(int x,int y) //az új elem fehér színû szokott lenni, ennek külön konstruktor van, ehhez elég megadni csak a koordinátát
	{
		coords=new Coords(x,y);
		colorid=0;
	}
}
