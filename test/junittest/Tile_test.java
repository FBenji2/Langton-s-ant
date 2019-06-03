package junittest;


import org.junit.Assert;
import org.junit.Test;

import matrix_and_ants.Tile;

public class Tile_test {
	
	@Test
	public void testConstructor() //leteszteljük a tile konstruktorát
	{
		Tile a = new Tile(8,16);
		Assert.assertEquals(8,a.coords.getX());
		Assert.assertEquals(16,a.coords.getY());
		Assert.assertEquals(0,a.colorid);
	}

}
