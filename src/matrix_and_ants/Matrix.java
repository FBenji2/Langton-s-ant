package matrix_and_ants;

import java.util.HashMap;
import javafx.scene.paint.Color;


public class Matrix {
	private HashMap<Coords,Tile> table;
	//gyors elérés érdekében hasmmapben tárolom a csempéket és koordinátákkal azonosítom õket
	private Graphics graphics; //átadjuk neki az aktuális rajztér adatait, hogy hozzáférjen a függvényeihez
	
	public Matrix() //konstruktorában átadjuk a grafikát
	{
		table = new HashMap<Coords,Tile>();
		Tile t = new Tile(0,0); //középre helyezünk egy csempét (alapértelmezetten fehér)
		table.put(t.coords.get(), t); //eltároljuk a hashmapben a csempét, a kulcs a koordináta
	}
	
	public void addGraph(Graphics graphics)
	{
		this.graphics=graphics;
		graphics.redraw(this);//kirajzoljuk a csempéket
	}
	
	public void add(Tile... newTiles) //egy vagy több csempe hozzáadása a hashmapbe, amikor újat adunk hozzá, akkor mindig fehérek lesznek
	//szabály szerint
	{
		for(int i=0;i<newTiles.length;i++)
		{
			table.put(newTiles[i].coords, newTiles[i]); 
			if(graphics!=null) graphics.draw(newTiles[i].coords,Color.WHITE); //egyúttal ki is rajzoljuk az új csempéket
		}
	}
	public HashMap<Coords,Tile> getMap()
	{
		return table; //visszatérés a hashmappel ahol tároljuk a csempéket
	}
	public void setMap(HashMap<Coords,Tile> table)
	{
		this.table = table; //a hashmap átírása, fájlból beolvasás miatt fontos
	}
	public Tile get(Coords key) //adott csempével való visszatérés koordináta szerint
	{
		return table.get(key);
	}
	public boolean contains(Coords key) //megnézzük, hogy van-e ilyen koordinátájú elemünk
	{
		return table.containsKey(key);
	}
	public void reset() //gyakorlatilag azt csinálja mint a konstruktor, de nem kell újra memóriát foglalni
	{
		table.clear();
		Tile t = new Tile(0,0);
		table.put(t.coords.get(), t);
		if(graphics!=null) graphics.draw(t.coords.get(),Color.WHITE);
	}
}