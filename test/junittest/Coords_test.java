package junittest;

import org.junit.Assert;
import org.junit.Test;

import matrix_and_ants.Coords;

public class Coords_test {
	@Test
	public void testConstructor() //letesztelj�k, hogy a konstruktor j�l l�tre hozza-e a koordin�taoszt�lyunk
	{
		Coords a = new Coords(8,16);
		Assert.assertEquals(8, a.getX());
		Assert.assertEquals(16, a.getY());
	}
}
