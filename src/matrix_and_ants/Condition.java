package matrix_and_ants;

import javafx.scene.paint.Paint;
import matrix_and_ants.Ant.Turn;

public class Condition {

	private Paint next; //sz�nek v�ltoz�ja, mivel t�bb sz�nt t�rolunk a hangy�ban ez�rt a hashmapben azonos�t�k�nt van az a sz�n amire �rkezik a hangya
	private int nextid; //k�vetkez� sz�n azonos�t�ja (teh�t a sorsz�ma a colorlistben, 0-t�l kezdve)
	private Turn turn; //miut�n �tv�ltja a sz�nt a hangya, erre forduljon
	
	Condition(Paint next,Turn turn,int nextid) //felt�tel konstruktomi legyen az �j sz�n �s merre forduljon, tov�bb� az �j sz�n azonos�t�ja
	{
		this.next=next;
		this.turn=turn;
		this.nextid=nextid;
	}
	Paint getNext() //k�vetkez� sz�nnel val� visszat�r�s
	{
		return next;
	}
	Turn getTurn() //�j ir�nnyal visszat�r�s
	{
		return turn;
	}
	int getNextID() //�j sz�n azonos�t�j�val visszat�r�s
	{
		return nextid;
	}
}
