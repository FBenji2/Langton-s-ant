package matrix_and_ants;

import java.util.HashMap;
import javafx.scene.paint.Color;


public class Matrix {
	private HashMap<Coords,Tile> table;
	//gyors el�r�s �rdek�ben hasmmapben t�rolom a csemp�ket �s koordin�t�kkal azonos�tom �ket
	private Graphics graphics; //�tadjuk neki az aktu�lis rajzt�r adatait, hogy hozz�f�rjen a f�ggv�nyeihez
	
	public Matrix() //konstruktor�ban �tadjuk a grafik�t
	{
		table = new HashMap<Coords,Tile>();
		Tile t = new Tile(0,0); //k�z�pre helyez�nk egy csemp�t (alap�rtelmezetten feh�r)
		table.put(t.coords.get(), t); //elt�roljuk a hashmapben a csemp�t, a kulcs a koordin�ta
	}
	
	public void addGraph(Graphics graphics)
	{
		this.graphics=graphics;
		graphics.redraw(this);//kirajzoljuk a csemp�ket
	}
	
	public void add(Tile... newTiles) //egy vagy t�bb csempe hozz�ad�sa a hashmapbe, amikor �jat adunk hozz�, akkor mindig feh�rek lesznek
	//szab�ly szerint
	{
		for(int i=0;i<newTiles.length;i++)
		{
			table.put(newTiles[i].coords, newTiles[i]); 
			if(graphics!=null) graphics.draw(newTiles[i].coords,Color.WHITE); //egy�ttal ki is rajzoljuk az �j csemp�ket
		}
	}
	public HashMap<Coords,Tile> getMap()
	{
		return table; //visszat�r�s a hashmappel ahol t�roljuk a csemp�ket
	}
	public void setMap(HashMap<Coords,Tile> table)
	{
		this.table = table; //a hashmap �t�r�sa, f�jlb�l beolvas�s miatt fontos
	}
	public Tile get(Coords key) //adott csemp�vel val� visszat�r�s koordin�ta szerint
	{
		return table.get(key);
	}
	public boolean contains(Coords key) //megn�zz�k, hogy van-e ilyen koordin�t�j� elem�nk
	{
		return table.containsKey(key);
	}
	public void reset() //gyakorlatilag azt csin�lja mint a konstruktor, de nem kell �jra mem�ri�t foglalni
	{
		table.clear();
		Tile t = new Tile(0,0);
		table.put(t.coords.get(), t);
		if(graphics!=null) graphics.draw(t.coords.get(),Color.WHITE);
	}
}