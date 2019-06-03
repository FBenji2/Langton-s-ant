package matrix_and_ants;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Colorlist {

	private static ArrayList<Paint> colors; //sima arraylistben torolom a szineket

	public Colorlist()
	{
		colors = new ArrayList<Paint>(); //konnyen bovotheto lista, alapertelmezetten 20 szin talalhato benne, amik ebben a sorrenben lesznek
		colors.add(Color.WHITE);
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.YELLOW);
		colors.add(Color.BROWN);
		colors.add(Color.CYAN);
		colors.add(Color.LIME);
		colors.add(Color.MAGENTA);
		colors.add(Color.GOLD);
		colors.add(Color.VIOLET);
		colors.add(Color.BLACK);
		colors.add(Color.PINK);
		colors.add(Color.PURPLE);
		colors.add(Color.OLIVE);
		colors.add(Color.ORANGE);
		colors.add(Color.INDIGO);
		colors.add(Color.SILVER);
		colors.add(Color.SLATEBLUE);
		colors.add(Color.TEAL);
		colors.add(Color.ALICEBLUE);
		colors.add(Color.ANTIQUEWHITE);
		colors.add(Color.AQUA);
		colors.add(Color.AQUAMARINE);
		colors.add(Color.AZURE);
		colors.add(Color.BEIGE);
		colors.add(Color.BISQUE);
		colors.add(Color.BLANCHEDALMOND);
		colors.add(Color.BLUEVIOLET);
		colors.add(Color.BURLYWOOD);
	}
	public Paint get(int ret) //adott indexu szinnel valo visszateres
	{
		return colors.get(ret);
	}
	public int size() //szinek szamaval valo visszateres
	{
		return colors.size();
	}
	
}
