package matrix_and_ants;

import java.io.Serializable;

public class Coords implements Serializable {
	private int x,y; //x �s y koordin�ta mivel 2d a t�bla
	
	public Coords(int x,int y) //konstruktor�ban l�tre lehet hozni egyet
	{
		this.x=x;
		this.y=y;
	}
	
	public void set(int x,int y) //set f�ggv�nye hasonl�an m�k�dik mint a classt �tad�
	{								  //konstruktora
		this.x=x;
		this.y=y;
	}
	
	public Coords get() //vissza tudunk t�rni �nmag�val
	{
		return this;
	}
	public int getX() //x koordin�t�val visszat�r�s
	{
		return x;
	}
	public int getY() //y koordin�t�val visszat�r�s
	{
		return y;
	}
	public void increase(int x,int y) //koordin�t�k n�vel�se a param�terben megadott �rt�kekkel
	{
		this.x+=x;
		this.y+=y;
	}
	@Override
	public final boolean equals(Object obj) //a hashmap miatt fel�l kell defini�lni az egyenl�s�get n�z� f�ggv�nyt
	{
		Coords in = (Coords)obj; //a be�rkez� objektumot kasztolni kell
		return in.x == this.x && in.y == this.y; //majd visszat�r�nk boolal
	}
	@Override
	public int hashCode()
	{
		return 100000*x+y;
	}

}
