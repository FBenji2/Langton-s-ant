package matrix_and_ants;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

class Graphics {
	
	private int csize=500; //rajzter�lete teljes m�rete csizexcsize
	private Group root; //ebbe pakoljuk majd bele a canvast
	private Canvas canvas; //erre rajzolunk grafikai dolgokat
	private GraphicsContext gc; //ennek a seg�ts�g�vel rajzolunk
	private double ratio; //megadja a rajzoland� n�gyzetek m�ret�t, "zoomol�s" hat�s�t keltve
	Button tsubmit,bsubmit,stop,random; //a k�perny�re helyezett gombok, fels�, als� submit, stop gomb, random gener�l� gomb
	TextField textinput, speedtext; //sz�vegmez� ahova a szab�lyrendszert tudjuk megadni
	Label err,steps; //cimke amin visszajelezz�k a felhaszn�l�nak a helyes ment�st/bet�lt�st, emellett a l�p�sek sz�m�nak a cimk�je
	Boolean pause; //�ll-e a hangya, vagy mozog
	private MenuBar menu; //az ablak tetej�n tal�lhat� men�, ennek a seg�ts�g�vel tudunk menteni �s bet�lteni p�ly�t
	private Menu file; //"file" cimk�j� men�gomb
	MenuItem save,load; //a file c�mk�n bel�l elhelyezett "save" �s "load" felirat� gombok
	private Colorlist colorlist; //a haszn�lhat� sz�nek statikus t�mbje
	ChoiceBox<String> nice_rules; //�rdekes szab�lyok list�ja
	private Matrix matrix; //odaadjuk neki a mátrixot
	private double centreX, centreY;
	private double dragx, dragy;

	Graphics(Stage primaryStage, Matrix matrix) //grafika konstruktor, ennek a seg�ts�g�vel �ll�tunk mindent alaphelyzetbe
	{
		this.matrix = matrix;
		centreX = 245;
		centreY = 245;
		colorlist = new Colorlist();
		nice_rules = new ChoiceBox<String>();
		ratio = 500; //"zoom" m�rt�ke
		pause = true; //alap�rtelmezetten a hangya nem mozog
		canvas = new Canvas(csize,csize); //rajzter�let inicializ�l�sa
		root = new Group();
		BorderPane borderlayout = new BorderPane(); //a k�perny� egy borderpane seg�ts�g�vel van felosztva
		primaryStage.setTitle("Langton's Ant"); //az ablak c�m�nek be�ll�t�sa
		gc = canvas.getGraphicsContext2D(); //grafikai konstextus inicializ�l�sa
		clear();
		root.getChildren().add(canvas); //canvas hozz�ad�sa roothoz
	
		borderlayout.setCenter(root); //root elhelyez�se a borderlayout (borderpane) k�zep�n, �gy a teljes ablak k�zep�n is
		
		menu = new MenuBar(); //men�gombok list�j�nak inicializ�l�sa
		file = new Menu("File"); //"file" cimk�j� gomb inicializ�l�sa
		menu.getMenus().add(file); //majd hozz�ad�s a gombokhoz
		save = new MenuItem("Save"); //"save" cimk�j� gomb l�trehoz�sa
		load = new MenuItem("Load"); //"load" cimk�j� gomb l�trehoz�sa
		file.getItems().addAll(save,load); //a men�hoz hozz�adjuk az �j gombokat
		HBox row0 = new HBox(menu); //egy vizszintes elrendez�s� "doboz" l�trehoz�sa majd az elk�sz�lt men� belerak�sa
		
		textinput = new TextField(); //sz�vegdoboz inicializ�l�sa
		tsubmit = new Button("Submit"); //als� submit gomb inicializ�l�sa
		random = new Button("Random"); //random gomb inicializ�l�sa
		stop = new Button("Start"); //start gomb inicializ�l�sa
		textinput.setPrefWidth(195); ///a sz�vegdoboz hossz�nak meghat�roz�sa(valami�rt nem j� amikor a nice_rules sz�less�g�t k�rem le)
		steps = new Label("Steps: " + 0); //l�p�sek cimke inicializ�l�sa
		HBox row1 = new HBox(textinput,tsubmit,random,stop,steps); //az el�z� bekezd�sben inicializ�lt dolgok elhelyez�se egy vizszintes
		//elrendez�s� "dobozban"
		
		nice_rules.getItems().add("Default langton's ant: LR");
		nice_rules.getItems().add("Chaotic growth: RLR");
		nice_rules.getItems().add("Symmetric growth: LLRR");
		nice_rules.getItems().add("Chaotic square-filling\ngrowth: LRRRRRLLR");
		nice_rules.getItems().add("Convoluted highway:\nLLRRRLRLRLLR");
		nice_rules.getItems().add("Growing triangle:\nRRLLLRLLLRRR");
		nice_rules.getItems().add("Thick highway: RRLRLLRLRR");
		nice_rules.getItems().add("Fills half sheet: RRLRLLRRRRRR");
		nice_rules.getItems().add("Fills whole sheet: LLRLL");
		nice_rules.getItems().add("Spiral: LRRRRLLLRRR");
		nice_rules.getItems().add("Fibonacci: RLLLLRRRLLLR");
		nice_rules.getItems().add("Triangles: RRLRRRLLLLL");
		nice_rules.getItems().add("Apple: LLRRLLLRLRRR");
		nice_rules.getItems().add("Weird square: RRLLRLLLLLLL");
		nice_rules.getItems().add("WTF: RRRLLRLL");
		nice_rules.getItems().add("Katzen: RRRRLRRRLRRRRR");
		nice_rules.getItems().add("B�la: LLLLLRRRRRRRRRRLLLLL"); //�rdekes szab�lyok le�r�s�nak �s k�dj�nak a felv�tele
		nice_rules.getSelectionModel().select(0); //alap�rtelmezetten a legels� szab�lyt kiv�lasztjuk
		bsubmit = new Button("Submit"); //inicializ�ljuk az als� submit gombot
		err = new Label(); //inicializ�ljuk az err (error) cimk�t amivel a jelz�nk a felhaszn�l�nak ment�st/bet�lt�st
		HBox row2 = new HBox(nice_rules,bsubmit,err); //az el�z� bekezd�sben l�trehozott elemek elhelyez�se egy "dobozban"
		
		
		
		VBox top = new VBox(row0,row1,row2); //f�gg�leges elrendez�s� doboz l�trehoz�sa, majd a h�rom vizszintes doboz elhelyez�se benne
		borderlayout.setTop(top); //az el�z� doboz elhelyez�se a k�perny� elrendez�s�nek (borderlayout) tetej�n
		//ez�ltal az ablak tetej�n h�rom sorban vannak a k�v�nt elemek
		
		borderlayout.setMinSize(500, 560); //ablak m�ret�nek be�ll�t�sa
		
		primaryStage.setResizable(false); //ablak nem �tm�retezhet�
		primaryStage.setScene(new Scene(borderlayout)); //az ablakhoz be�ll�tjuk az el�bbi elrendez�st
		primaryStage.show(); //az ablakot l�that�v� tessz�k a k�perny�n

		root.setOnScroll((ScrollEvent event) -> //amikor görgetünk akkor tudunk zoomolni
		{
			if(event.getDeltaY() > 0)
			{
				ratio *= 0.9;

			} else if(event.getDeltaY() < 0)
			{
				ratio *= 1.1;
			}

			System.out.println(centreX + " " + centreY);
			redraw(matrix);
		});


		root.setOnMousePressed(new EventHandler<MouseEvent>() { //ha lenyomjuk a gombot akkor megnézzük, hogy hol lett lenyomva
			@Override
			public void handle(MouseEvent event) {
				dragx = event.getX();
				dragy = event.getY();
			}
		});

		root.setOnMouseDragged(new EventHandler<MouseEvent>() { //a lemonyott hely alapján pedig "deltát" számolunk, hogy merre mozgott az egér
			@Override
			public void handle(MouseEvent event) {
				if(event.isPrimaryButtonDown())
				{
					centreX -= dragx - event.getX();
					centreY -= dragy - event.getY();

					dragx = event.getX();
					dragy = event.getY();

					redraw(matrix);
				}
			}
		});

	}
	void draw(Coords coords,Paint p) //adott koordin�ta kisz�nez�se adott sz�nnel
	{
		gc.setFill(p); //sz�n be�ll�t�sa
		gc.fillRect(centreX+coords.getX()*csize/ratio, centreY-coords.getY()*csize/ratio, csize/ratio, csize/ratio); //sz�nez�s
	}
	void redraw(Matrix matrix) //rajzt�r �jrarajzol�sa megl�v� m�trix alapj�n
	{
		clear(); //rajzt�r "t�rl�se"
		
		for(Tile value : matrix.getMap().values())
		{
			draw(value.coords.get(), colorlist.get(value.colorid)); //m�trix �sszes elem�re megh�vjuk a draw f�ggv�nyt
		}
	}
	void clear()
	{
		gc.setFill(Color.LIGHTGRAY);
		gc.fillRect(0, 0, csize, csize); //�t�ll�tjuk a sz�nt sz�rk�re �s az eg�sz rajzteret takarjuk egy sz�rke n�gyzettel
	}
	void resetcentre()
	{
		centreX = 245;
		centreY = 245;
	}
}
