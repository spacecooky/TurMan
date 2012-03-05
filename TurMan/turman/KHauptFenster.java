package turman;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * TODO Variable Einstellung der Urkunden-Infos.
 * TODO Variable Festlegung der Prim�r-, Sekund�r- und SOS-Punkte.
 * TODO Zus�tzliche Punkte-Arten hinzuf�gen.
 * TODO Erkennung der Variablen Punkte f�r den Export.
 * TODO Turniermodus: Schweizer System, Komplett zuf�llige Paarungen, KO-System
 * TODO Siegpunkte-Matrix
 * TODO Teamturniere:
 * TODO Teamturniere Sortier-Algorithmus
 * TODO Teamturniere Einzelpaarungsfenster
 * TODO Infofenster
 * TODO Multilingualit�t
 * TODO Einf�gen eines Freilos-Spielers
 * TODO Scrollbars in der Matrix so anpassen, dass die linke Namesleiste scrollt, wenn man hoch und runter scrollt und die obere, wenn man nach links und rechts scrollt.
 * TODO Beim Hinzuf�gen neuer Spieler bereits gel�schte Spieler ausgegraut lassen. Bzw. keine neuen Spieler nach der ersten runde zulassen.
 * TODO Konfigurationsschablonen mit Einstellungen f�r jede Runde
 * TODO Anzeige von Platzabst�nden der gepaarten Spieler: Scrollbar einf�gen falls die Anzahl zu gro� ist
 * TODO Turnieragenda, mit hervorgehobenen Programmpunkten und verbleibender Zeit zum n�chsten. Optionen zum schnellen Verschieben der Zeitpunkte.
 * TODO Speicherung von Agenda-Schablonen.
 * TODO Falls Spieler genau punktgleich sind, m�ssen diese f�r die Paarung in einen Spielerpool zusammengefasst werden, der f�r diesen Platz steht. Aus diesm wird f�r die Paaruung dann immer zuf�llig einer ausgew�hlt.
 * TODO Falls Spieler genau punktgleich sind, m�ssen sie auf dem gleichen Platz eingetragen werden.
 * TODO Matrix nach Laden/Import korrekt neu zeichnen.
 *  
 * @author jk
 *
 */
public class KHauptFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4640634680204084389L;

	public KHauptFenster(){
		super("TurMan "+version);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		WindowListener meinListener=new WindowAdapter(){
			public void windowClosing(WindowEvent ereignis){
				beenden();
			}
		};
		this.addWindowListener(meinListener);


		//Hauptbereich Einzel
		sp=new JScrollPane(HauptPanel);
		HauptPanel.setLayout(new BoxLayout(HauptPanel,BoxLayout.Y_AXIS));
		//Hauptbereich Team
		spTeam=new JScrollPane(HauptPanelTeam);
		HauptPanelTeam.setLayout(new BoxLayout(HauptPanelTeam,BoxLayout.Y_AXIS));
		//Hauptbereich
		tab.addTab("Matrix",null,sp);
		tab.addTab("Punkte",null,new JScrollPane(punkteFenster.punktePanelTab));
		tab.addTab("Begegnungen",null,new JScrollPane(begegnungsFenster.begegnungsPanelTab));
		punkteFenster.updatePanel(punkteFenster.punktePanelTab);
		setContentPane(tab);
		//Menue
		setJMenuBar(menubar);
		menubar.add(datei);
		datei.add(neu);
		neu.addActionListener(this);
		datei.add(gImport);
		gImport.addActionListener(this);
		datei.add(gExport);
		gExport.addActionListener(this);
		datei.add(oeffnen);
		oeffnen.addActionListener(this);
		datei.add(speichern);
		speichern.addActionListener(this);
		datei.add(beenden);
		beenden.addActionListener(this);

		menubar.add(turnier);
		turnier.add(punkte);
		punkte.addActionListener(this);
		turnier.add(begegnungen);
		begegnungen.addActionListener(this);
		turnier.add(zeit);
		zeit.addActionListener(this);
		turnier.add(urkundenErstellen);
		urkundenErstellen.addActionListener(this);
		turnier.add(optionen);
		optionen.addActionListener(this);

		menubar.add(turnierRunde);
		turnierRunde.add(herausforderung);
		herausforderung.addActionListener(this);
		turnierRunde.add(runde);
		runde.addActionListener(this);
		turnierRunde.add(rundeWdh);
		rundeWdh.addActionListener(this);
		turnierRunde.add(rundeReset);
		rundeReset.addActionListener(this);

		menubar.add(spieler);
		spieler.add(entfernen);
		entfernen.addActionListener(this);
		spieler.add(erweitern);
		erweitern.addActionListener(this);
		spieler.add(extraPunkte);
		extraPunkte.addActionListener(this);

		// neues Turnier
		neuFrame.setContentPane(neuPanel);
		neuFrame.setSize(300,100);
		neuFrame.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().width-300)/2), (int)((Toolkit.getDefaultToolkit().getScreenSize().height-100)/2));
		neuPanel.setLayout(new GridLayout());
		((GridLayout)neuPanel.getLayout()).setColumns(2);
		((GridLayout)neuPanel.getLayout()).setRows(3);
		neuPanel.add(neuTeilnehmerLabel);
		neuPanel.add(neuTeilnehmerField);
		neuPanel.add(neuRundenLabel);
		neuPanel.add(neuRundenField);
		neuPanel.add(new JLabel());
		neuPanel.add(neuButton);
		neuButton.addActionListener(this);

		teilnehmerButton.addActionListener(this);
		setVisible(true);
		
		File f = new File(System.getProperty("user.dir")+"/autosaveQuit.sav");
		if(f.exists()){
			KSpeicherverwaltung.laden(this,f);
		}
	}

	static String version=new String("V0.0.10");

	// Hauptbereich
	JTabbedPane tab = new JTabbedPane();
	
	// Hauptbereich Einzelspieler
	JPanel HauptPanel = new JPanel();
	JScrollPane sp;
	
	// Hauptbereich Teams
	JPanel HauptPanelTeam = new JPanel();
	JScrollPane spTeam;

	//	Menue
	JMenuBar menubar = new JMenuBar();
	JMenu datei = new JMenu("Datei");
	JMenuItem neu = new JMenuItem("Neues Turnier - Manuell");
	JMenuItem gImport = new JMenuItem("Neues Turnier - G�PP Import");
	JMenuItem gExport = new JMenuItem("G�PP Export");
	JMenuItem oeffnen = new JMenuItem("Turnier �ffnen");
	JMenuItem speichern = new JMenuItem("Turnier Speichern");
	JMenuItem beenden = new JMenuItem("Beenden");

	JMenu turnier = new JMenu("Turnier");
	JMenuItem punkte = new JMenuItem("Punkte anzeigen");
	JMenuItem begegnungen = new JMenuItem("Begegnungen anzeigen");
	JMenuItem zeit = new JMenuItem("Zeit starten");
	JMenuItem urkundenErstellen = new JMenuItem("Urkunden erstellen");
	JMenuItem optionen = new JMenuItem("Optionen einstellen");

	JMenu turnierRunde = new JMenu("Turnierrunde");
	JMenuItem herausforderung = new JMenuItem("Herausforderung");
	JMenuItem runde = new JMenuItem("N�chste Runde paaren");
	JMenuItem rundeWdh = new JMenuItem("Runde erneut paaren");
	JMenuItem rundeReset = new JMenuItem("Runde Zur�cksetzen");

	JMenu spieler = new JMenu("Spieler");
	JMenuItem entfernen = new JMenuItem("Entfernen");
	JMenuItem erweitern = new JMenuItem("Hinzuf�gen");
	JMenuItem extraPunkte = new JMenuItem("Zus. Punkte eingeben");


	//neu
	JFrame neuFrame = new JFrame();
	JPanel neuPanel = new JPanel();
	JLabel neuTeilnehmerLabel = new JLabel("Anzahl der Teilnehmer:");
	JTextField neuTeilnehmerField = new JTextField();
	JLabel neuRundenLabel = new JLabel("Anzahl der Runden:");
	JTextField neuRundenField = new JTextField();
	JButton neuButton = new JButton("Anlegen");

	JFrame teilnehmerFrame;
	JPanel teilnehmerPanel;
	JButton teilnehmerButton= new JButton("Weiter");

	//Begegnungs Frame
	JFrame begegnungsFrame;
	JPanel begegnungsPanel = new JPanel();
	JLabel t1Label = new JLabel();
	JLabel t2Label = new JLabel();
	JTextField p1Field = new JTextField();
	JTextField p2Field = new JTextField();
	JTextField p12Field = new JTextField();
	JTextField p22Field = new JTextField();
	//Variablen
	int teilnehmer=0;
	int teams=0;
	int gel�schteTeilnehmer=0;
	int runden=0;
	int rundenZaehler=0;
	int mode = KPairings.RANDOM;
	String TID="0";
	Vector<KTeilnehmer> teilnehmerVector= new Vector<KTeilnehmer>();
	Vector<String> teamVector= new Vector<String>();
	Vector<KTeilnehmer> sortierterVector= new Vector<KTeilnehmer>();
	Vector<KTeilnehmer> herausforderungsVector= new Vector<KTeilnehmer>();
	Vector<KBegegnungen> begegnungsVector= new Vector<KBegegnungen>();
	Vector<KBegegnungen> alleBegegnungenVector= new Vector<KBegegnungen>();
	Vector<Vector<KTeilnehmer>> platzGruppen= new Vector<Vector<KTeilnehmer>>();

	//Fenster
	KTeilnehmerFenster spielerFenster = new KTeilnehmerFenster();
	KPunkteFenster punkteFenster = new KPunkteFenster(this);
	KEntfernenFenster entfernenFenster = new KEntfernenFenster(this);
	KErweiternFenster erweiternFenster = new KErweiternFenster(this);
	KHerausforderungsFenster herausforderungsFenster = new KHerausforderungsFenster(this);
	KBegegnungsFenster begegnungsFenster = new KBegegnungsFenster(this);
	KExtraPunkteFenster extraPunkteFenster = new KExtraPunkteFenster(this);
	KDialog dialog = new KDialog(this);
	KOptionenFeld optionenFeld = new KOptionenFeld(this);

	//Urkunden
	KUrkunde urkunde = new KUrkunde();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new KHauptFenster();
	}

	public void actionPerformed(ActionEvent arg0) {
		Object quelle= arg0.getSource();

		if(quelle == neu){
			neuFrame.setVisible(true);
		} else if(quelle == neuButton){
			teilnehmer=Integer.parseInt(neuTeilnehmerField.getText());
			gel�schteTeilnehmer=0;
			runden=Integer.parseInt(neuRundenField.getText());
			neuFrame.setVisible(false);

			teilnehmerFrame=new JFrame();
			teilnehmerPanel=new JPanel();
			teilnehmerFrame.setContentPane(new JScrollPane(teilnehmerPanel));
			teilnehmerPanel.setLayout(new GridLayout());
			((GridLayout)teilnehmerPanel.getLayout()).setColumns(3);
			((GridLayout)teilnehmerPanel.getLayout()).setRows(teilnehmer+2);
			teilnehmerPanel.add(new JLabel(""));
			teilnehmerPanel.add(new JLabel("Vorname"));
			teilnehmerPanel.add(new JLabel("Nachname"));
			for(int i=0;i<teilnehmer;i++){
				teilnehmerPanel.add(new JLabel("Teilnehmer "+(i+1)));
				teilnehmerPanel.add(new JTextField(""));
				teilnehmerPanel.add(new JTextField(""));
			}
			teilnehmerPanel.add(new JLabel());
			teilnehmerPanel.add(teilnehmerButton);
			teilnehmerFrame.setSize(500,(teilnehmer+2)*30);
			teilnehmerFrame.setVisible(true);

		} else if(quelle == teilnehmerButton){
			KSpeicherverwaltung.leeren(this);
			mode=KPairings.RANDOM;
			rundenZaehler=0;
			alleBegegnungenVector.clear();
			for(int i=4;i<3+teilnehmer*3;i+=3){
				teilnehmerVector.add(new KTeilnehmer(((JTextField)teilnehmerPanel.getComponent(i)).getText(),((JTextField)teilnehmerPanel.getComponent(i+1)).getText()));
			}
			teilnehmerFrame.setVisible(false);
			//System.out.println(teilnehmerVector.size());

			fillPanels();
			fillTeamPanels();


		}else if(quelle == speichern){
			KSpeicherverwaltung.speichernWrap(this);
		}else if(quelle == oeffnen){
			KSpeicherverwaltung.ladenWrap(this);
		} else if(quelle == punkte){
			punkteFenster.init(null);
		} else if(quelle == runde){
			File f = new File(System.getProperty("user.dir")+"/autosaveRunde"+rundenZaehler+"Ende.sav");
			KSpeicherverwaltung.speichern(this,f);
			KPairings.runde(this);
			f = new File(System.getProperty("user.dir")+"/autosaveRunde"+rundenZaehler+"Start.sav");
			KSpeicherverwaltung.speichern(this,f);
		}else if(quelle == rundeWdh){
			KPairings.rundeReset(this);
			KPairings.runde(this);
			File f = new File(System.getProperty("user.dir")+"/autosaveRunde"+rundenZaehler+"Start.sav");
			KSpeicherverwaltung.speichern(this,f);
		}else if(quelle == rundeReset){
			KPairings.rundeReset(this);
		}else if(quelle==beenden){
			beenden();
		}else if(quelle==gImport){
			KPort.gImport(this);
		} else if(quelle==gExport){
			KPort.gExport(this);
		} else if(quelle==entfernen){
			entfernenFenster.init();
		} else if(quelle==erweitern){
			erweiternFenster.init();
		} else if(quelle==herausforderung){
			herausforderungsFenster.init();
		} else if(quelle==zeit){
			new TTimer(this);
		} else if(quelle==urkundenErstellen){
			sortieren(true, true);
			urkunde.urkundeErstellen(sortierterVector);
		}else if(quelle==begegnungen){
			begegnungsFenster.init(null);
		}else if(quelle==optionen){
			setContentPane(optionenFeld);
			validate();
		} else if(quelle==extraPunkte){
			extraPunkteFenster.init(null);
		}

	}

	public void fillPanels(){
		HauptPanel.removeAll();
		
		teilnehmer=teilnehmerVector.size();
		JPanel header= new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));
		JLabel jL= new JLabel("");
		jL.setMaximumSize(new Dimension(150,150));
		jL.setMinimumSize(new Dimension(150,150));
		jL.setPreferredSize(new Dimension(150,150));
		jL.setBorder(BorderFactory.createRaisedBevelBorder());
		header.add(jL);

		for(int i=0;i<teilnehmerVector.size();i++){
			JLabel jp= new JLabel(teilnehmerVector.get(i).vorname+ " "+teilnehmerVector.get(i).nachname);
			jp.setMaximumSize(new Dimension(20,150));
			jp.setMinimumSize(new Dimension(20,150));
			jp.setPreferredSize(new Dimension(20,150));
			jp.setUI( new VerticalLabelUI(false));
			jp.setBorder(BorderFactory.createRaisedBevelBorder());
			header.add(jp);
		}
		HauptPanel.add(header);

		for(int i=0;i<teilnehmerVector.size();i++){
			HauptPanel.add(new KTeilnehmerPanel(teilnehmerVector.get(i).vorname+" "+teilnehmerVector.get(i).nachname,teilnehmerVector.size(),i,this));
		}

		
		setVisible(true);
	}

	public void fillTeamPanels(){
		HauptPanelTeam.removeAll();
		
		teams=teamVector.size();
		JPanel header= new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));
		JLabel jL= new JLabel("");
		jL.setMaximumSize(new Dimension(250,250));
		jL.setMinimumSize(new Dimension(250,250));
		jL.setPreferredSize(new Dimension(250,250));
		jL.setBorder(BorderFactory.createRaisedBevelBorder());
		header.add(jL);

		for(int i=0;i<teamVector.size();i++){
			JLabel jp= new JLabel(teamVector.get(i));
			jp.setMaximumSize(new Dimension(20,250));
			jp.setMinimumSize(new Dimension(20,250));
			jp.setPreferredSize(new Dimension(20,250));
			jp.setUI( new VerticalLabelUI(false));
			jp.setBorder(BorderFactory.createRaisedBevelBorder());
			header.add(jp);
		}
		HauptPanelTeam.add(header);

		for(int i=0;i<teamVector.size();i++){
			HauptPanelTeam.add(new KTeamPanel(teamVector.get(i),teamVector.size(),i,this));
		}
	}


	public void sortieren(boolean ab,boolean bm){
		KTeilnehmer t;
		//Prim�r und Sekund�rpunkte f�r alle berechnen
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			t.prim�r=0;
			t.sekund�r=0;
			t.sos=0;
			for(int j=0;j<t.paarungen.size();j++){
				t.prim�r+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p1;
				if(optionenFeld.PSS.isSelected()){
					t.sekund�r+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p12;
				} else if(optionenFeld.TS.isSelected()){
					t.sekund�r+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p12-((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p22;
				}
			}
			if(ab){
				if(optionenFeld.PSS.isSelected()){
					t.sekund�r+=t.armeeliste;
				} else if(optionenFeld.TS.isSelected()){
					t.prim�r+=t.armeeliste;
				}
			}
			if(bm){
				t.prim�r+=t.bemalwertung;
			}
		}
		//SOS f�r alle berechnen
		if(optionenFeld.PSS.isSelected()){
			for(int i=0;i<teilnehmer;i++){
				t=teilnehmerVector.get(i);
				for(int j=0;j<t.paarungen.size();j++){
					t.sos += teilnehmerVector.get(t.paarungen.get(j)).prim�r+(bm?-teilnehmerVector.get(t.paarungen.get(j)).bemalwertung:0);
	
				}
			}
		}

		//Sortiern nach prim�r
		sortierterVector.clear();
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			int j=0;
			while(j<sortierterVector.size()&&t.prim�r>sortierterVector.get(j).prim�r){
				j++;
			}
			//Sortieren nach Sekund�r
			while(j<sortierterVector.size()&&t.prim�r==sortierterVector.get(j).prim�r&&teilnehmerVector.get(i).sekund�r>sortierterVector.get(j).sekund�r){
				j++;
			}
			//Sortieren nach sos
			if(optionenFeld.PSS.isSelected()){
				while(j<sortierterVector.size()&&t.prim�r==sortierterVector.get(j).prim�r&&t.sekund�r==sortierterVector.get(j).sekund�r&&t.sos>sortierterVector.get(j).sos){
					j++;
				}
			}
			sortierterVector.insertElementAt(t,j);	
		}
		
		//TODO Platzgruppen berechnen
		platzGruppen.clear();
	}

	public void updatePanels(){
		validate();
		
		if(punkteFenster.isVisible()){
			punkteFenster.init(punkteFenster.getSize());
		} else{
			punkteFenster.updatePanel(punkteFenster.punktePanelTab);
		}
		
		if(begegnungsFenster.isVisible()){
			begegnungsFenster.init(begegnungsFenster.getSize());
		} else{
			begegnungsFenster.updatePanel(begegnungsFenster.begegnungsPanelTab);	
		}
	}
	
	/**
	 * Beendet das Programm
	 */
	protected void beenden(){
		File f = new File(System.getProperty("user.dir")+"/autosaveQuit.sav");
		KSpeicherverwaltung.speichern(this,f);
		System.exit(0);
	}

}
