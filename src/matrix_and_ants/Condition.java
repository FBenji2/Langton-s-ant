package matrix_and_ants;

import javafx.scene.paint.Paint;
import matrix_and_ants.Ant.Turn;

public class Condition {

	private Paint next; //színek változója, mivel több színt tárolunk a hangyában ezért a hashmapben azonosítóként van az a szín amire érkezik a hangya
	private int nextid; //következõ szín azonosítója (tehát a sorszáma a colorlistben, 0-tól kezdve)
	private Turn turn; //miután átváltja a színt a hangya, erre forduljon
	
	Condition(Paint next,Turn turn,int nextid) //feltétel konstruktomi legyen az új szín és merre forduljon, továbbá az új szín azonosítója
	{
		this.next=next;
		this.turn=turn;
		this.nextid=nextid;
	}
	Paint getNext() //következõ színnel való visszatérés
	{
		return next;
	}
	Turn getTurn() //új iránnyal visszatérés
	{
		return turn;
	}
	int getNextID() //új szín azonosítójával visszatérés
	{
		return nextid;
	}
}
