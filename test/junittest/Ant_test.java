package junittest;

import java.awt.Color;

import org.junit.Test;

import junit.framework.Assert;
import matrix_and_ants.Ant;
import matrix_and_ants.Ant.Directions;
import matrix_and_ants.Coords;
import matrix_and_ants.Matrix;

public class Ant_test {
	@Test
	public void testConstructor()
	{
		Ant a = new Ant(new Matrix(),null,"LR",Directions.North);
		Assert.assertEquals(Directions.North, a.getheading());
		Assert.assertEquals("LR", a.getRules());
		Assert.assertEquals(0, a.getCoords().getX());
		Assert.assertEquals(0, a.getCoords().getY());
	}
	@Test
	public void testGetMatrix()
	{
		Matrix m = new Matrix();
		Ant a = new Ant(m,null,"LR",Directions.North);
		Assert.assertEquals(m, a.getMatrix());
	}
	@Test
	public void testStep() throws InterruptedException
	{
		Ant a = new Ant(new Matrix(),null,"LR",Directions.North);
		a.step();
		Assert.assertEquals(-1, a.getCoords().getX());
		Assert.assertEquals(0, a.getCoords().getY());
		Assert.assertEquals(1,a.getMatrix().getMap().get(new Coords(0,0)).colorid);
		Assert.assertEquals(1, a.getSteps());
	}
}
