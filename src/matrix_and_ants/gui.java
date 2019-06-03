package matrix_and_ants;

import matrix_and_ants.Ant.Directions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

public class gui extends Application implements EventHandler<ActionEvent> {
	private Ant ant; //elt�rolunk mindent amit haszn�lhat m�sik f�ggv�ny a classon bel�l
	private Graphics graphics;
	private FileIO fileio;
	
	public static void main(String[] args) {
	 launch(args); //ezt h�vja meg az application oszt�ly
    }

	@Override
	public void start(Stage primaryStage) throws Exception {

		Matrix matrix = new Matrix(); //l�trehozzuk a m�trixot �s �tadjuk neki a m�r megl�v� grafik�nk
		graphics = new Graphics(primaryStage,matrix); //l�trehozunk egy �j grafikai fel�letet
		matrix.addGraph(graphics);

		graphics.tsubmit.setOnAction(this);
		graphics.bsubmit.setOnAction(this);
		graphics.stop.setOnAction(this);
		graphics.random.setOnAction(this);
		graphics.save.setOnAction(this);
		graphics.load.setOnAction(this); //be�ll�tjuk a gombokhoz, hogy forduljanak a handlerhez, ha aktiv�l�dnak
		ant = new Ant(matrix,graphics,"rllrrrrrllrrlrrrrrr",Directions.North); //l�trehozunk egy hangy�t ami f�lfele n�z alap�rtelmezetten �s "LR" szab�ly�
		//tov�bb� �t is adjuk neki a grafikai f�ggv�nyeinket �s a m�trixot is
		fileio = new FileIO(); //l�trehozunk egy f�jlkezel� classt

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10),ae -> { //x miliszekundumonk�nt csin�lja a megadott utas�t�st
			try {
				if(!graphics.pause) for(int i=0;i<50;i++) //t�zszer l�p, ut�na friss�ti a rajzteret
				{
					ant.step(); //hangya l�p�sf�ggv�nye
					graphics.steps.setText("Steps: " + ant.getSteps()); //�t�rjuk a l�p�ssz�ml�l� cimk�t
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE); //v�gtelenszer ism�telj�k
		timeline.play(); //elind�tjuk az id�z�t�t*/
		//http://tomasmikula.github.io/blog/2014/06/04/timers-in-javafx-and-reactfx.html innen van a delay

	}

	@Override
	public void handle(ActionEvent event) { //itt mondjuk meg, hogy a k�l�nb�z� gombnyom�sokn�l mi t�rt�njen
		if(event.getSource() == graphics.tsubmit) //ha a fels� submit gombot nyomjuk meg
		{
			if(graphics.textinput.getText().length() == 0) //hogyha a sz�vegdobozba nincs �rva semmi akkor a leg�rd�l� list�ban l�v� elem
				//szab�ly�t �rjuk oda, amelyet �gy szed�nk ki bel�le, hogy a sz�vegb�l a kett�spont ut�ni m�sodik karaktert�l a sz�veg
				//v�g�ig tart a szab�ly
			{
				ant.setConditions(graphics.nice_rules.getValue().substring(graphics.nice_rules.getValue().indexOf(':')+2, graphics.nice_rules.getValue().length()));
			}
			ant.setConditions(graphics.textinput.getText()); //a sz�vegdobozban l�v� adatok alapj�n �tadjuk a szab�lyt a hangy�nak
			graphics.steps.setText("Steps: " + ant.getSteps()); //a l�p�ssz�ml�l� dobozt is friss�tj�k (0-t fog �rni)
			graphics.resetcentre();
		}
		if(event.getSource() == graphics.bsubmit) //als� submit gomb megnyom�sa
		{
			//hogyha a fels� sz�vegdoboz �res, akkor bele�rjuk azt a szab�lyt ami ki van v�lasztva a list�ban
			if(graphics.textinput.getText().length()==0) graphics.textinput.setText(graphics.nice_rules.getValue().substring(graphics.nice_rules.getValue().indexOf(':')+2, graphics.nice_rules.getValue().length()));
			ant.setConditions(graphics.nice_rules.getValue().substring(graphics.nice_rules.getValue().indexOf(':')+2, graphics.nice_rules.getValue().length()));
			graphics.steps.setText("Steps: " + ant.getSteps()); //a szab�lyt �tadjuk a hangy�nak majd friss�tj�k a l�p�ssz�ml�l�t
			graphics.resetcentre();
		}
		if(event.getSource() == graphics.stop) //stop/start gomb megnyom�sa
			{
			graphics.err.setText("");
			if(graphics.pause == true) //ha m�r �ll a  j�t�k
			{
				graphics.pause = false; //akkor induljon el
				graphics.stop.setText("Stop"); //�s a gomb sz�vege legyen az, hogy "Stop"
			} else {
				graphics.pause = true; //ha nem �ll a j�t�k, akkor �lljon meg
				graphics.stop.setText("Start"); //�s a sz�veg legyen az, hogy "Start"
			}
			}
		if(event.getSource() == graphics.random) //random gomb megnyom�sas
		{
			Random a = new Random(); //random oszt�ly l�trehoz�sa
			int r = a.nextInt(ant.getMaxConditionSize()-1) + 1; //l�trehozunk egy sz�mot 1 �s 19 k�z�tt, ez lesz az �j szab�ly hossza
			String s; //l�trehozunk egy string v�ltoz�t, ezt adjuk majd a hangy�nak
			if(a.nextBoolean() == false) s = "R"; else s = "L"; //eld�ntj�k, hogy az els� karakter R vagy L legyen
			for(int i=0;i<r;i++)
			{
				if(a.nextBoolean() == true) s = s.concat("L"); else s = s.concat("R"); //majd gener�lunk r darab m�sik random (R/L) karaktert
			}
			graphics.textinput.setText(s); //a sz�vegdobozba be�rjuk a most gener�lt random szab�lyt
			ant.setConditions(s); //majd �tadjuk a hangy�nak
			graphics.steps.setText("Steps: " + ant.getSteps()); //a l�p�ssz�ml�l�t is friss�tjuk
		}
		if(event.getSource() == graphics.save) //a men�n bel�li ment�s gomb hat�sa
		{
			try {
				fileio.save(ant); //megh�vjuk a f�jlkezel�nknek a ment�s f�ggv�ny�ts
				graphics.err.setText("Ant saved!"); //�t�ll�tjuk az "err" cimk�j� mez�t arra, hogy be lett t�ltve a hangya
			} catch (FileNotFoundException e) {
				graphics.err.setText("File not found when saving!"); //ha nincs f�jl akkor ezt �rja ki
				e.printStackTrace();
			} catch (IOException e) {
				graphics.err.setText("IO excepion when saving!"); //IO hib�n�l pedig ezt
				e.printStackTrace();
			}
		}
		if(event.getSource() == graphics.load) //men�n bel�li bet�lt�s gomb hat�sa
		{
			try {
				graphics.pause = true; //meg�ll�tjuk a hangy�t
				graphics.stop.setText("Start"); //�t�rjuk az elind�t� gombot "start"-ra
				fileio.load(ant); //megh�vjuk a f�jlkezel�nk bet�lt f�ggv�ny�t
				graphics.steps.setText("Steps: " + ant.getSteps()); //friss�tj�k a l�p�sek sz�m�nak a cimk�j�t
				graphics.textinput.setText(ant.getRules()); //a sz�vegdobozunkban �t�rjuk a sz�veget a hangya �ltal k�vetett szab�lyra
				graphics.err.setText("Ant loaded!"); //ki�rjuk a felhaszn�l�nak, hogy be lett t�ltve a hangya
			} catch (FileNotFoundException e) {
				graphics.err.setText("File not found when loading!"); //nem tal�lhat� file hiba ki�r�sa
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				graphics.err.setText("Class not found when loading!"); //nem tal�lhat� oszt�ly hiba ki�r�sa
				e.printStackTrace();
			} catch (IOException e) {
				graphics.err.setText("IO excepion when loading!"); //IO hiba ki�r�sa
				e.printStackTrace();
			}
		}
	}
}