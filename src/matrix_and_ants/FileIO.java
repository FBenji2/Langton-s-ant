package matrix_and_ants;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import matrix_and_ants.Ant.Directions;

public class FileIO implements Serializable { //fajlkezelo class, ezt szeretnenk majd kimenteni egy fajlba ezert implementalja a serializablet
	
	HashMap<Coords, Tile> matrix; //tarol magaban egy olyan HashMapet mint ami a matrixban is van (tabla pillanatnyi allasa)
	Directions heading; //tarolja a hangya pillanatnyi iranyat
	Coords coords; //tarolja a hangya pillanatnyi koordinatait
	int steps; //tarolja a hangya altal eddig megtett lepeseket
	String rules; //tarolja a hangya altal követett szabalyok leegyszerusitett szoveges modelljet
	
	public void save(Ant ant) throws FileNotFoundException, IOException //mentes fuggveny, atadjuk a menteni kivant hangyat
	{
		matrix = ant.getMatrix().getMap(); //a hangyaban eltarolt matrix tipusu objektum HashMap-ja kerul bele ennek a classnak a HashMapjebe
		heading = ant.getheading(); //az ir�nyt,l�p�ssz�mot,szab�lyokat megszerezz�k a hangy�t�l
		coords = ant.getCoords();
		steps = ant.getSteps();
		rules = ant.getRules();
		
		ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("save.ant")); //save.ant nev� f�jlba szeretn�nk �rni a classunk
		file.writeObject(this); //ezt a classt bele�rjuk
		file.close(); //majd bez�rjuk a f�jlt
	}
	public void load(Ant ant) throws FileNotFoundException, IOException, ClassNotFoundException //bet�lt�s f�ggv�ny
	{
		ObjectInputStream file = new ObjectInputStream(new FileInputStream("save.ant")); //save.ant f�jlb�l szeretn�nk bet�lteni
		FileIO temp = new FileIO(); //l�trehozunk egy ideiglenes v�ltoz�t amibe beolvassuk az adatokat
		temp = (FileIO)file.readObject();
		ant.load(temp); //majd megh�vjuk a hangya load f�ggv�ny�t, ami be�ll�tja a hangya �rt�keit a f�jlb�l beolvasottakra
		file.close(); //v�g�l bez�rjuk a f�jlt
		
	}
}
