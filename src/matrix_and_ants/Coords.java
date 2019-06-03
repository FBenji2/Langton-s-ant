package matrix_and_ants;

import java.io.Serializable;

public class Coords implements Serializable {
	private int x,y; //x és y koordináta mivel 2d a tábla
	
	public Coords(int x,int y) //konstruktorában létre lehet hozni egyet
	{
		this.x=x;
		this.y=y;
	}
	
	public void set(int x,int y) //set függvénye hasonlóan mûködik mint a classt átadó
	{								  //konstruktora
		this.x=x;
		this.y=y;
	}
	
	public Coords get() //vissza tudunk térni önmagával
	{
		return this;
	}
	public int getX() //x koordinátával visszatérés
	{
		return x;
	}
	public int getY() //y koordinátával visszatérés
	{
		return y;
	}
	public void increase(int x,int y) //koordináták növelése a paraméterben megadott értékekkel
	{
		this.x+=x;
		this.y+=y;
	}
	@Override
	public final boolean equals(Object obj) //a hashmap miatt felül kell definiálni az egyenlõséget nézõ függvényt
	{
		Coords in = (Coords)obj; //a beérkezõ objektumot kasztolni kell
		return in.x == this.x && in.y == this.y; //majd visszatérünk boolal
	}
	@Override
	public int hashCode()
	{
		return 100000*x+y;
	}

}
