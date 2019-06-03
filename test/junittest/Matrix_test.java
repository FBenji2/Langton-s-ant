package junittest;

import org.junit.Test;

import javafx.scene.paint.Color;
import junit.framework.Assert;
import matrix_and_ants.Coords;
import matrix_and_ants.Matrix;
import matrix_and_ants.Tile;

public class Matrix_test {
	
	@Test
	public void testConstructor()
	{
		Matrix a = new Matrix();
		Coords b = new Coords(0,0);
		Assert.assertEquals(1, a.getMap().size());
		Assert.assertEquals(0, a.get(b).coords.getX());
		Assert.assertEquals(0, a.get(b).coords.getY());
		Assert.assertEquals(0, a.get(b).colorid);
	}
	@Test
	public void testAdd()
	{
		Matrix a = new Matrix();
		Tile b = new Tile(0,1);
		Tile c = new Tile(0,1);
		Tile d = new Tile(1,1);
		Tile e = new Tile(2,2);
		a.add(b,c);
		a.add(d);
		a.add(e);
		Assert.assertEquals(4, a.getMap().size()); //a mátrix létrehoz egy elemet, mellé jön még három, mert kettõ ugyanolyan
	}
	@Test
	public void testGet()
	{
		Matrix a = new Matrix();
		Tile b = new Tile(0,1);
		a.add(b);
		Assert.assertEquals(b, a.get(new Coords(0,1)));
	}
	@Test
	public void testContains()
	{
		Matrix a = new Matrix();
		Assert.assertEquals(true, a.contains(new Coords(0,0)));
		Assert.assertEquals(false, a.contains(new Coords(0,1)));
	}
	@Test
	public void testReset()
	{
		Matrix a = new Matrix();
		Tile b = new Tile(0,1);
		Tile c = new Tile(0,1);
		Tile d = new Tile(1,1);
		Tile e = new Tile(2,2);
		a.add(b,c);
		a.add(d);
		a.add(e);
		a.reset();
		Assert.assertEquals(1,a.getMap().size());
		Assert.assertEquals(true, a.contains(new Coords(0,0)));
	}

}
