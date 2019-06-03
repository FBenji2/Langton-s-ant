package matrix_and_ants;

import matrix_and_ants.Condition;
import javafx.scene.paint.Paint;
import java.util.HashMap;

public class Ant {
	
	private Graphics graphics; //hangyaban taroljuk a graphics classt, igy elerjuk a fuggvenyeit
	private HashMap<Paint,Condition> conditions; //HashMapben taroljuk a felteteleket (lasd masik class)
	private Colorlist colors; //letrehozunk egy uj szinlista classt, ebbõl utalunk majd a kulonbozõ szinekre
	private Directions heading,def; //beallitjuk az alap es a jelenlegi iranyt
	private Coords coords; //hangya koordinatai
	private Matrix matrix; //hangya altal hasznalt marrix amin mozog
	private int steps; //megtett lepesek szama
	private String rules; //szabalyok egyszerûsitett szoveges formatuma

	public enum Directions
	{
		North,East,South,West //a negy iranyt a negy egtajnak neveztem el
	}
	public enum Turn
	{
		Left,Right //jobb es bal kanyar van
	}

	public Ant(Matrix matrix,Graphics graphics,String rules,Directions h) //hangya konstruktor, atadunk neki mindent amit nem tudunk elõre
	{
		coords = new Coords(0,0); //0,0-ra lerakjuk
		colors = new Colorlist();
		conditions=new HashMap<Paint,Condition>();
		def = h; //alapertelmezett irany beallitasa
		this.matrix=matrix; //atadjuk a matrixot, a grafikat es a szabalyokat
		if(graphics != null) this.graphics=graphics;
		this.rules=rules;
		setConditions(rules); //meghivjuk a fuggvenyt ami beallitja a szabalyokat hozza (magyarul a conditions hashmapet feltolti)
	}
	public void step() throws InterruptedException
	{
		Turn t = null; //lepesvaltozo letrehozasa
		steps++; //lepesszamlalo novelese
		
		Paint ptemp = colors.get(matrix.get(coords).colorid); //segedvaltozo letrehozasa, ez egyenlõ azzal a szinnel ahova a hangya erkezik
		Condition temp = conditions.get(ptemp); //feltetel segedvaltozo, azert van ra szukseg mindkettõre, hogy ne kelljen annyiszor iteralni
		t = temp.getTurn(); //a fordulasi irany legyen a kondiciohoz kotott irany
		matrix.get(coords).colorid = temp.getNextID(); //a matrixban adott koordinataknal talalhato tile-ban tarolt id legyen a kovetkezõ szin id-je
		if(graphics != null) graphics.draw(coords, temp.getNext()); //rajzoljuk be az uj negyzetet a megfelelõ helyre az uj szinnel
		
		
		//kovetkezõ bekezdesben az iranyok alapjan tortenik az uj koordinatak es haladasi irany kiszamolasa
		if((heading == Directions.East && t == Turn.Left) || (heading == Directions.West && t == Turn.Right))
		{
			heading = Directions.North;
			this.coords.increase(0, 1);
		} else
		if((heading == Directions.East && t == Turn.Right) || (heading == Directions.West && t == Turn.Left))
		{
			heading = Directions.South;
			this.coords.increase(0, -1);
		} else
		if((heading == Directions.South && t == Turn.Left) || (heading == Directions.North && t == Turn.Right))
		{
			heading = Directions.East;
			this.coords.increase(1, 0);
		} else
		if((heading == Directions.North && t == Turn.Left) || (heading == Directions.South && t == Turn.Right))
		{
			heading = Directions.West;
			this.coords.increase(-1, 0);
		}
		if(matrix.contains(coords)) return;
		matrix.add(new Tile(coords.getX(),coords.getY()));
	}
	public Directions getheading() //haladasi irannyal valo visszateres
	{
		return heading;
	}
	
	public void setConditions(String rules) //szabaly szoveg alapjan toltjuk fel a hashmapet
	{
		if(rules.length()==0 || rules.length() > colors.size()) return; //ha 0 hosszu a szoveg akkor lepjunk ki a fuggvenybõl
		rules = rules.toUpperCase(); //alakitsuk nagybetûsse a beerkezõ szoveget
		for(int i=0;i<rules.length();i++)
		{
			if(rules.charAt(i)!='L' && rules.charAt(i)!='R') return; //ha barmelyik karakter nem egyenlõ R-el vagy L-el akkor kilepes a fgv-bõl
		}
		this.coords.set(0,0); //ha minden stimmel akkor a koordinatakat,iranyt,matrixot,feltetelek hashmapjet,grikat es a lepesszamlalot
		//alapertelmezettre allitjuk
		this.heading = def;
		matrix.reset();
		conditions.clear();
		if(graphics != null) graphics.clear();
		steps = 0;
		this.rules=rules; //atadjuk a szabalyokat a hangyanak
		int i;
		//i. karakter alapjan eldontjuk, hogy balra vagy jobbra forduljunk
		//a feltetel letrehozasanal a kulcs a colorlistben tarolt i. szin. Az "elõzõ szin" is ugyanez, a "kovetkezõ szin" az i+1. elem
		for(i=0;i<rules.length()-1;i++)
		{
			if(rules.charAt(i)=='L') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(i+1),Turn.Left,i+1));
			if(rules.charAt(i)=='R') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(i+1),Turn.Right,i+1));
		}
		if(rules.charAt(i)=='L') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(0),Turn.Left,0));
		if(rules.charAt(i)=='R') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(0),Turn.Right,0));
	}
	public void load(FileIO input) //betoltes fuggveny, a fajlbol beolvasott ertekeket adjuk at a hangyanak majd generaljuk a felteteleket belõles
	{
		coords = input.coords;
		heading = input.heading;
		matrix.setMap(input.matrix);
		rules = input.rules;
		steps = input.steps;
		int i;
		for(i=0;i<rules.length()-1;i++)
		{
			if(rules.charAt(i)=='L') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(i+1),Turn.Left,i+1));
			if(rules.charAt(i)=='R') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(i+1),Turn.Right,i+1));
		}
		if(rules.charAt(i)=='L') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(0),Turn.Left,0));
		if(rules.charAt(i)=='R') conditions.put(colors.get(i), new Condition(/*colors.get(i),*/colors.get(0),Turn.Right,0));
		if(graphics != null) graphics.redraw(matrix);
	}
	
	int getMaxConditionSize() //visszateres a trolt szinek szamaval (colorlist arrayben levõ cuccok szama)
	{
		return colors.size();
	}
	public Matrix getMatrix() //visszateres a hangya altal hasznalt matrixxal (teljes matrix, nem csak a tabla)
	{
		return matrix;
	}
	public Coords getCoords()
	{
		return coords; //koordinatakkal visszateres
	}
	public int getSteps()
	{
		return steps; //lepesszammal visszateres
	}
	public HashMap<Paint, Condition> getConditions()
	{
		return conditions; //feltetelekkel visszateres
	}
	public String getRules()
	{
		return rules; //feltetelek szovegesitett verziojaval visszateres
	}
}
