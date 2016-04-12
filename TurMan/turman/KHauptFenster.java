﻿package turman;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Random;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * TODO Zusätzliche Turniermodi:  KO-System
 * TODO Teamturniere:
 * TODO Teamturniere Sortier-Algorithmus
 * TODO Teamturniere Einzelpaarungsfenster
 * TODO Multilingualität
 * TODO Speicherung von Agenda-Schablonen.
 * TODO Maximalhöhen der Zeilen in den verschiedenen Sichten.
 * TODO Freilos-Spieler: Punktwerte speichern.
 * TODO Freilos-Spieler/Punkte über Konfiguration aktivieren? 
 * TODO Ordnerstruktur. Speicherstände, Konfigurationen, Schablonen in eigenen Ordnern. (Versionspakete)
 * TODO Bessere Sichtbarkeit für Beamer.
 * TODO Herausforderungen jeder Runde merken, falls Paaren wiederholt wird
 * TODO Freie Tischzuweisung
 * TODO Größeren Tischpool anlegen, aus dem zufällig gewählt wird
 * TODO Anmeldeverwaltung Druck der sortierten Liste
 * TODO Anmeldeverwaltung Zweiseitiger Druck druckt nochmal den Inhalt der ersten Seite
 * TODO Anmeldeverwaltung geänderte Daten übernehmen
 * TODO Abgespeckte Version der Anmeldeverwaltung als Spielerliste nach der ersten Paarung
 * TODO Suchfunktionenen in der Spielerliste (auf alle Spalten)
 * TODO aktuelle Spielinformationen in der Spielerliste. Wo spielt er gerade. Gegen wen/was spielt er gerade 
 * ("Wer hat in Runde X höher als 12 Punkte gewonnen?" naja, sowas halt.
 * TODO ausdruckbarer Ergebniszettel mit Tischnummer
 * TODO Suchfunktion (nach Tischnummern) auf Paarungsseite, die gleich die Ergebniseingabe öffnet
 * TODO Option zum Ausblenden eingetragener Ergebnisse auf der Paarungsseite
 * TODO Freie Eingabe der Wertungsnamen (statt Primär usw.)
 * TODO Größenänderungsoptionen in Timer/Agenda
 * 
 * @author jk
 *
 */
public class KHauptFenster extends JFrame implements ActionListener,ComponentListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4640634680204084389L;

	public KHauptFenster(){
		super("TurMan "+version);
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		//setSize(800,600);
		//setLocation(0, 500);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		WindowListener meinListener=new WindowAdapter(){
			public void windowClosing(WindowEvent ereignis){
				beenden();
			}
		};
		this.addWindowListener(meinListener);

		this.addComponentListener(this);

		//Hauptbereich Einzel
		sp=new JScrollPane(HauptPanel);
		//Hauptbereich Team
		spTeam=new JScrollPane(HauptPanelTeam);
		HauptPanelTeam.setLayout(new BoxLayout(HauptPanelTeam,BoxLayout.Y_AXIS));
		//Hauptbereich
		tab.addTab("Matrix",null,sp);
		tab.addTab("Rangliste",null,punkteFenster.punktePanelTab);
		tab.addTab("Begegnungen",null,begegnungsFenster.begegnungsPanelTab);
		punkteFenster.updatePanel(punkteFenster.punktePanelTab);
		tab.setBackground(Color.white);
		tab.addTab("Timer/Agenda",null,new JScrollPane(dynamischerTimerFeld));
		punkteFenster.updatePanel(punkteFenster.punktePanelTab);
		tab.setBackground(Color.white);
		tab.addTab("Konfiguration",null,new JScrollPane(optionenFeldVar));
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
		datei.add(eloExport);
		eloExport.addActionListener(this);
		datei.add(oeffnen);
		oeffnen.addActionListener(this);
		datei.add(speichern);
		speichern.addActionListener(this);
		datei.add(beenden);
		beenden.addActionListener(this);

		menubar.add(turnier);
		turnier.add(anmeldung);
		anmeldung.addActionListener(this);
		turnier.add(punkte);
		punkte.addActionListener(this);
		turnier.add(begegnungen);
		begegnungen.addActionListener(this);
		turnier.add(zeit);
		zeit.addActionListener(this);
		turnier.add(agenda);
		agenda.addActionListener(this);
		turnier.add(urkundenInfos);
		urkundenInfos.addActionListener(this);
		turnier.add(urkundenErstellen);
		urkundenErstellen.addActionListener(this);
		turnier.add(urkundenParsehtml);
		urkundenParsehtml.addActionListener(this);

		menubar.add(turnierRunde);
		turnierRunde.add(herausforderung);
		herausforderung.addActionListener(this);
		turnierRunde.add(herausforderungDel);
		herausforderungDel.addActionListener(this);
		turnierRunde.add(paarungTauschen);
		paarungTauschen.addActionListener(this);
		turnierRunde.add(runde);
		runde.addActionListener(this);
		turnierRunde.add(rundeWdh);
		rundeWdh.addActionListener(this);
		turnierRunde.add(rundeReset);
		rundeReset.addActionListener(this);

		menubar.add(spieler);
		spieler.add(spielerliste);
		spielerliste.addActionListener(this);
		spieler.add(entfernen);
		entfernen.addActionListener(this);
		spieler.add(wiederherstellen);
		wiederherstellen.addActionListener(this);
		spieler.add(erweitern);
		erweitern.addActionListener(this);
		spieler.add(extraPunkte);
		extraPunkte.addActionListener(this);
		spieler.add(freilos);
		freilos.addActionListener(this);

		menubar.add(optionen);
		optionen.add(optionenSpeichern);
		optionenSpeichern.addActionListener(this);
		optionen.add(optionenLaden);
		optionenLaden.addActionListener(this);

		menubar.add(hilfe);
		hilfe.add(info);
		info.addActionListener(this);
		hilfe.add(hilfeDatei);
		hilfeDatei.addActionListener(this);

		// neues Turnier
		neuFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		neuFrame.setTitle("Neues Turnier");
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

		File f2 = new File(System.getProperty("user.dir")+"/auto.cfg");
		if(f2.exists()){
			KSpeicherverwaltung.ladenKonfig(this,f2);
		}

		p1sekField.addKeyListener(this);
		p2sekField.addKeyListener(this);

		setSelectAll(p1priField);
		setSelectAll(p2priField);
		setSelectAll(p1sekField);
		setSelectAll(p2sekField);
		setSelectAll(p1terField);
		setSelectAll(p2terField);

	}

	static String version=new String("V0.0.33");

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
	JMenuItem gImport = new JMenuItem("Neues Turnier - GöPP Import");
	JMenuItem gExport = new JMenuItem("GöPP Export");
	JMenuItem eloExport = new JMenuItem("Elo Export");
	JMenuItem oeffnen = new JMenuItem("Turnier Öffnen");
	JMenuItem speichern = new JMenuItem("Turnier Speichern");
	JMenuItem beenden = new JMenuItem("Beenden");

	JMenu turnier = new JMenu("Turnier");
	JMenuItem anmeldung = new JMenuItem("Anmeldeverwaltung");
	JMenuItem punkte = new JMenuItem("Rangliste");
	JMenuItem begegnungen = new JMenuItem("Begegnungen anzeigen");
	JMenuItem zeit = new JMenuItem("Zeit starten");
	JMenuItem agenda = new JMenuItem("Agenda");
	JMenuItem urkundenErstellen = new JMenuItem("Urkunden erstellen");
	JMenuItem urkundenInfos = new JMenuItem("Urkunden-Einstellungen");
	JMenuItem urkundenParsehtml = new JMenuItem("Urkunden-Export für ParseHtml");

	JMenu turnierRunde = new JMenu("Turnierrunde");
	JMenuItem herausforderung = new JMenuItem("Herausforderung");
	JMenuItem herausforderungDel = new JMenuItem("Herausforderung entfernen");
	JMenuItem paarungTauschen = new JMenuItem("Paarung tauschen");
	JMenuItem runde = new JMenuItem("Nächste Runde paaren");
	JMenuItem rundeWdh = new JMenuItem("Runde erneut paaren");
	JMenuItem rundeReset = new JMenuItem("Runde Zurücksetzen");

	JMenu spieler = new JMenu("Spieler");
	JMenuItem spielerliste = new JMenuItem("Spielerliste");
	JMenuItem entfernen = new JMenuItem("Entfernen");
	JMenuItem wiederherstellen = new JMenuItem("Wiederherstellen");
	JMenuItem erweitern = new JMenuItem("Hinzufügen");
	JMenuItem extraPunkte = new JMenuItem("Zus. Punkte eingeben");
	JMenuItem freilos = new JMenuItem("Freilos-Platzhalter einfügen");

	JMenu optionen = new JMenu("Optionen");
	JMenuItem optionenSpeichern = new JMenuItem("Konfiguration speichern");
	JMenuItem optionenLaden = new JMenuItem("Konfiguration laden");

	JMenu hilfe = new JMenu("Hilfe");
	JMenuItem info = new JMenuItem("Info");
	JMenuItem hilfeDatei = new JMenuItem("Hilfe-Datei");


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
	JTextField p1priField = new JTextField();
	JTextField p2priField = new JTextField();
	JTextField p1sekField = new JTextField();
	JTextField p2sekField = new JTextField();
	JTextField p1terField = new JTextField();
	JTextField p2terField = new JTextField();
	JRadioButton siegP1 = new JRadioButton("Sieg");
	JRadioButton siegP2 = new JRadioButton("Sieg");
	JRadioButton unentschieden = new JRadioButton("Unentschieden");
	JRadioButton unsichtbar = new JRadioButton();
	//Variablen
	int teilnehmer=0;
	int teams=0;
	int gelöschteTeilnehmer=0;
	int runden=0;
	int rundenZaehler=0; //Tatsächliche Runde
	int rundenAnzeige=0; //Runde, die zur Betrachtung ausgewählt wurde
	String turniername="Turniername";
	String TID="0";
	Vector<KTeilnehmer> teilnehmerVector= new Vector<KTeilnehmer>();
	Vector<String> teamVector= new Vector<String>();
	Vector<KTeilnehmer> sortierterVector= new Vector<KTeilnehmer>();
	Vector<KTeilnehmer> herausforderungsVector= new Vector<KTeilnehmer>();
	Vector<KBegegnungen> begegnungsVector= new Vector<KBegegnungen>();
	Vector<KBegegnungen> alleBegegnungenVector= new Vector<KBegegnungen>();
	Vector<Vector<KTeilnehmer>> platzGruppe= new Vector<Vector<KTeilnehmer>>();
	int platzgruppe=-1;
	int freilosPrim=0;
	int freilosSek=0;

	//Fenster
	KTeilnehmerFenster spielerFenster = new KTeilnehmerFenster(this);
	KPunkteFenster punkteFenster = new KPunkteFenster(this);
	KEntfernenFenster entfernenFenster = new KEntfernenFenster(this);
	KFreilosFenster freilosFenster = new KFreilosFenster(this);
	KErweiternFenster erweiternFenster = new KErweiternFenster(this);
	KHerausforderungsFenster herausforderungsFenster = new KHerausforderungsFenster(this);
	KHerausforderungsDelFenster herausforderungsDelFenster = new KHerausforderungsDelFenster(this);
	KPaarungTauschenFenster paarungTauschenFenster = new KPaarungTauschenFenster(this);
	KBegegnungsFenster begegnungsFenster = new KBegegnungsFenster(this);
	KExtraPunkteFenster extraPunkteFenster = new KExtraPunkteFenster(this);
	KAnmeldeFenster anmeldeFenster = new KAnmeldeFenster(this);
	KDialog dialog = new KDialog(this);
	KOptionenFeld optionenFeldVar = new KOptionenFeld(this);
	KDynamischerTimerFeld dynamischerTimerFeld = new KDynamischerTimerFeld(this);
	KInfoFenster infoFenster = new KInfoFenster();
	KSiegpunkteMatrix matrix = new KSiegpunkteMatrix(this);
	KUrkundenFenster urkundenFenster = new KUrkundenFenster(this);

	//Urkunden
	KUrkunde urkunde = new KUrkunde();
	//PDF-/TXT-Erstellug
	KPDFErstellung pdf = new KPDFErstellung();
	KTextErstellung txt = new KTextErstellung();

	//Agenda
	Vector<KAgendaEintrag> agendaVector=new Vector<KAgendaEintrag>();
	String [] agendaHeaders={"Ereignis","TT","MM","JJJJ","HH","MM"};
	String [][] agendaEintraege={{"Anmeldung","13","07","2012","09","00"},
			{"Infos","13","07","2012","09","45"},
			{"Turnierrunde 1","13","07","2012","10","00"},
			{"Mittagspause","13","07","2012","12","00"}};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new KHauptFenster();
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent arg0) {
		Object quelle= arg0.getSource();

		if(quelle == neu){
			neuFrame.setVisible(true);
		} else if(quelle == neuButton){
			teilnehmer=Integer.parseInt(neuTeilnehmerField.getText());
			gelöschteTeilnehmer=0;
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
			rundenZaehler=0;
			alleBegegnungenVector.clear();
			for(int i=4;i<3+teilnehmer*3;i+=3){
				teilnehmerVector.add(new KTeilnehmer(((JTextField)teilnehmerPanel.getComponent(i)).getText(),((JTextField)teilnehmerPanel.getComponent(i+1)).getText(),this));
			}
			teilnehmerFrame.setVisible(false);
			//System.out.println(teilnehmerVector.size());

			fillPanels();
			fillTeamPanels();
			anmeldung.setEnabled(true);

		}else if(quelle == speichern){
			KSpeicherverwaltung.speichernWrap(this);
		}else if(quelle == oeffnen){
			KSpeicherverwaltung.ladenWrap(this);
		} else if(quelle == anmeldung){
			anmeldeFenster.anmeldesicht=true;
			anmeldeFenster.sortLocal=(Vector<KTeilnehmer>)teilnehmerVector.clone();
//			anmeldeFenster.sortLocal=(Vector<KTeilnehmer>)sortierterVector.clone();
//			anmeldeFenster.sortLocal=sortierterVector;
			anmeldeFenster.initList();
			anmeldeFenster.init(null);
		} else if(quelle == spielerliste){
			anmeldeFenster.anmeldesicht=false;
			anmeldeFenster.sortLocal=(Vector<KTeilnehmer>)teilnehmerVector.clone();
//			anmeldeFenster.sortLocal=(Vector<KTeilnehmer>)sortierterVector.clone();
//			anmeldeFenster.sortLocal=sortierterVector;
			anmeldeFenster.initList();
			anmeldeFenster.init(null);
		} else if(quelle == punkte){
			//punkteFenster.init(null);
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
		} else if(quelle==eloExport){
			KPort.eloExport(this);
		}
		else if(quelle==entfernen){
			entfernenFenster.init();
		} else if(quelle==wiederherstellen){
			entfernenFenster.initRestore();
		}else if(quelle==erweitern){
			erweiternFenster.init();
		} else if(quelle==herausforderung){
			herausforderungsFenster.init();
		} else if(quelle==herausforderungDel){
			herausforderungsDelFenster.init();
		}else if(quelle==paarungTauschen){
			paarungTauschenFenster.init();
		}else if(quelle==zeit){
			new TTimer(this);
		}else if(quelle==agenda){
			new TAgenda(this);
		} else if(quelle==urkundenInfos){
			urkundenFenster.anzeigen();
		}else if(quelle==urkundenErstellen){
			sortierenVar(true, true,rundenZaehler);
			urkunde.urkundeErstellen(sortierterVector,urkundenFenster.zeile1Area.getText(),urkundenFenster.zeile2Area.getText());
		}else if(quelle==urkundenParsehtml){
			sortierenVar(true, true,rundenZaehler);
			new KHtmlErstellung(this,sortierterVector,optionenFeldVar);
		}else if(quelle==begegnungen){
			begegnungsFenster.init(null);
		}else if(quelle==extraPunkte){
			extraPunkteFenster.init(null);
		}else if(quelle==optionenSpeichern){
			KSpeicherverwaltung.speichernKonfigWrap(this);
		}else if(quelle==optionenLaden){
			KSpeicherverwaltung.ladenKonfigWrap(this);
		}else if(quelle==info){
			infoFenster.setVisible(true);
		}else if(quelle==hilfeDatei){
			try{ 
				Desktop d = java.awt.Desktop.getDesktop();
				d.open(new java.io.File("TurMan-Hilfe.pdf")); 
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else if(quelle==freilos){
			freilosFenster.init();
		}
	}

	public void fillPanels(){
		HauptPanel.removeAll();
		alleBegegnungenVector.clear();
		teilnehmer=teilnehmerVector.size();
		int restHeight=this.getHeight()/25-teilnehmer;
		if(restHeight<0){
			restHeight=0;
		}
//		HauptPanel.setLayout(new GridLayout(teilnehmer+restHeight, 1,0,0));
		HauptPanel.setLayout(new BoxLayout(HauptPanel,BoxLayout.Y_AXIS));
		JPanel header= new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));

		JPanel sider= new JPanel();
		sider.setLayout(new BoxLayout(sider,BoxLayout.Y_AXIS));

		for(int i=0;i<teilnehmerVector.size();i++){
			String vn = teilnehmerVector.get(i).vornameAlter.equals("")?teilnehmerVector.get(i).vorname:teilnehmerVector.get(i).vornameAlter;
			String nn = teilnehmerVector.get(i).nachnameAlter.equals("")?teilnehmerVector.get(i).nachname:teilnehmerVector.get(i).nachnameAlter;
			JLabel jp= new JLabel(vn+ " "+nn);
			jp.setMaximumSize(new Dimension(20,150));
			jp.setMinimumSize(new Dimension(20,150));
			jp.setPreferredSize(new Dimension(20,150));
			jp.setUI( new VerticalLabelUI(false));
			jp.setBorder(BorderFactory.createRaisedBevelBorder());
			jp.setForeground(Color.black);
			header.add(jp);
		}
		//HauptPanel.add(header);

		for(int i=0;i<teilnehmerVector.size();i++){
			String vn = teilnehmerVector.get(i).vornameAlter.equals("")?teilnehmerVector.get(i).vorname:teilnehmerVector.get(i).vornameAlter;
			String nn = teilnehmerVector.get(i).nachnameAlter.equals("")?teilnehmerVector.get(i).nachname:teilnehmerVector.get(i).nachnameAlter;
			KTeilnehmerPanel tp=new KTeilnehmerPanel(vn+" "+nn,teilnehmerVector.size(),i,this);
			HauptPanel.add(tp);
			sider.add(tp.nameLabel);
		}

		JLabel jL= new JLabel("");
		jL.setMaximumSize(new Dimension(150,150));
		jL.setMinimumSize(new Dimension(150,150));
		jL.setPreferredSize(new Dimension(150,150));
		jL.setBorder(BorderFactory.createRaisedBevelBorder());

		sp.setColumnHeaderView(header);
		sp.setRowHeaderView(sider);
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER,
				jL);


		this.repaint();
	}

	public void refillPanels(){
		for(int i=0;i<begegnungsVector.size();i++){
			KBegegnungen b = begegnungsVector.get(i);
			((JPanel)HauptPanel.getComponent(b.xPos)).remove(b.yPos);
			((JPanel)HauptPanel.getComponent(b.xPos)).add(b,b.yPos);
			alleBegegnungenVector.remove(((KBegegnungen)((JPanel)HauptPanel.getComponent(b.xPos)).getComponent(b.yPos)));
			alleBegegnungenVector.remove(((KBegegnungen)((JPanel)HauptPanel.getComponent(b.yPos)).getComponent(b.xPos)));
			KBegegnungen b2 = ((KBegegnungen)((JPanel)HauptPanel.getComponent(b.yPos)).getComponent(b.xPos));

			b2.p1pri=b.p2pri;
			b2.p2pri=b.p1pri;
			b2.p1sek=b.p2sek;
			b2.p2sek=b.p1sek;
			b2.runde=b.runde;
			b2.tisch=b.tisch;
			b2.xPos=b.yPos;
			b2.yPos=b.xPos;

			b.setEnabled(true);
			b2.setEnabled(true);
			b.setText(""+b.runde);
			b2.setText(""+b2.runde);

			if(b.p1pri>0 || b.p2pri>0){
				b.setBackground(Color.green);
				b2.setBackground(Color.green);
			} else{
				b.setBackground(Color.orange);
				b2.setBackground(Color.orange);
			}	
		}

		gelöschteTeilnehmer=0;
		for(int i=0;i<teilnehmerVector.size();i++){
			if(teilnehmerVector.get(i).deleted==true){
				entfernenFenster.entfernen(i);
			}
		}
	}

	public void adaptPanel(){
		teilnehmer=teilnehmerVector.size();
		int restHeight=this.getHeight()/25-teilnehmer;
		if(restHeight<0){
			restHeight=0;
		}
//		HauptPanel.setLayout(new GridLayout(teilnehmer+restHeight, 1,0,0));
		HauptPanel.setLayout(new BoxLayout(HauptPanel,BoxLayout.PAGE_AXIS));
		updatePanels();
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

		this.repaint();
	}

	public void sortierenVar(boolean ab,boolean bm,int lokRunde){
		//System.out.println(lokRunde);
		KTeilnehmer t;
		//Primär und Sekundärpunkte und Tertiär für alle berechnen
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			t.primaer=0;
			t.sekundaer=0;
			t.sekundaerDiff=0;
			t.tertiaer=0;
			t.tertiaerDiff=0;
			t.sos=0;
			t.sosos=0;
			t.sos_sog=0;
			t.sosos_sog=0;
			t.rpi=0;
			t.strengthOfSchedulde=0;
			t.platzGruppe=-1;
			t.platz=0;
			for(int j=1;j<=rundenZaehler;j++){
				if(t.paarungen.get(j)!=null){//Bei Hash. Bei Vector entfernen
					if(((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).runde<=lokRunde){
						t.primaer+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p1pri;
						t.sekundaer+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p1sek;
						t.sekundaerDiff+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p1sek-((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p2sek;
						t.tertiaer+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p1sek;
						t.tertiaerDiff+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p1sek-((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p2sek;
					}
				}
			}

		}
		
		boolean strength= optionenFeldVar.pStrength.isSelected() || optionenFeldVar.sStrength.isSelected() || optionenFeldVar.tStrength.isSelected();
		boolean rpi= optionenFeldVar.pRPI.isSelected() || optionenFeldVar.sRPI.isSelected() || optionenFeldVar.tRPI.isSelected();
		boolean sos = optionenFeldVar.sSOS.isSelected() || optionenFeldVar.tSOS.isSelected();
		boolean soos = /*optionenFeldVar.sSOOS.isSelected() ||*/ optionenFeldVar.tSOOS.isSelected();
		
		

		//SOS berechnen
		if(sos || rpi){
			calcOS(lokRunde);
		}

		//SOSOS berechnen
		if(soos || rpi){
			calcOOS(lokRunde);
		}

		//veiovisScore berechnen
		if(rpi){
			calcVeiovis(lokRunde);
			System.out.println();
		}
		
		//Strength of schedule berechnen
		if(strength){
			calcOS_SOG(lokRunde);
			calcOOS_SOG(lokRunde);
			calcStrengthOfSchedule(lokRunde);
			System.out.println("\n\n");
		}

		//Zuweisung der Wertungen
		sortierterVector.clear();
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);

			//Erstwertung
			if(lokRunde==0 && optionenFeldVar.r1ntr.isSelected()){
				t.erstwertung=t.ntr;
			}else if(optionenFeldVar.pPunkte.isSelected()){
				t.erstwertung=t.primaer;
			}else if(optionenFeldVar.pRPI.isSelected()){
				t.erstwertung=t.rpi;
			}else if(optionenFeldVar.pStrength.isSelected()){
				t.erstwertung=t.strengthOfSchedulde;
			}

			//Zweitwertung	
			if(lokRunde==0 && optionenFeldVar.r1ntr.isSelected()){
				t.zweitwertung=0.0;
			}else if(optionenFeldVar.sPunkte.isSelected()){
				t.zweitwertung=t.sekundaer;
			}else if(optionenFeldVar.sRPI.isSelected()){
				t.zweitwertung=t.rpi;
			}else if(optionenFeldVar.sStrength.isSelected()){
				t.zweitwertung=t.strengthOfSchedulde;
			}else if(optionenFeldVar.sSOS.isSelected()){
				t.zweitwertung=t.sos;
			}/*else if(optionenFeldVar.sSOOS.isSelected()){
				t.zweitwertung=t.sosos;
			}*/else if(optionenFeldVar.sKeine.isSelected()){
				t.zweitwertung=0.0;
			}	

			//Drittwertung	
			if(lokRunde==0 && optionenFeldVar.r1ntr.isSelected()){
				t.drittwertung=0.0;
			}if(optionenFeldVar.tPunkte.isSelected()){
				t.drittwertung=t.sekundaer;
			}else if(optionenFeldVar.tRPI.isSelected()){
				t.drittwertung=t.rpi;
			}else if(optionenFeldVar.tStrength.isSelected()){
				t.drittwertung=t.strengthOfSchedulde;
			}else if(optionenFeldVar.tSOS.isSelected()){
				t.drittwertung=t.sos;
			}else if(optionenFeldVar.tSOOS.isSelected()){
				t.drittwertung=t.sosos;
			}else if(optionenFeldVar.tKeine.isSelected()){
				t.drittwertung=0.0;
			}

			//TODO unterscheidung Diff
			t.erstwertungEinzel=t.erstwertung;
			t.zweitwertungEinzel=t.zweitwertung;
			t.drittwertungEinzel=t.drittwertung;

			if(ab){
				if(optionenFeldVar.armeePri.isSelected()){
					t.erstwertung+=t.armeeliste;
				}
				if(optionenFeldVar.armeeSek.isSelected()){
					t.zweitwertung+=t.armeeliste;
				}
				if(optionenFeldVar.armeeTer.isSelected()){
					t.drittwertung+=t.armeeliste;
				}
			}
			if(bm){
				if(optionenFeldVar.bemalPri.isSelected()){
					t.erstwertung+=t.bemalwertung;
				}
				if(optionenFeldVar.bemalSek.isSelected()){
					t.zweitwertung+=t.bemalwertung;
				}
				if(optionenFeldVar.bemalTer.isSelected()){
					t.drittwertung+=t.bemalwertung;
				}
			}

		}

		//Sortieren
		sortierterVector.clear();
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			if(t.deleted==false){
				int j=0;
				//Sortieren nach Erstwertung
				while(j<sortierterVector.size() && t.erstwertung>sortierterVector.get(j).erstwertung){
					j++;
				}
				//Sortieren nach Zweitwertung
				if(!optionenFeldVar.sKeine.isSelected()){
					while(j<sortierterVector.size() && t.erstwertung==sortierterVector.get(j).erstwertung && t.zweitwertung>sortierterVector.get(j).zweitwertung){
						j++;
					}
				}
				//Sortieren nach Drittwerttng
				if(!optionenFeldVar.sKeine.isSelected()){
					while(j<sortierterVector.size() && t.erstwertung==sortierterVector.get(j).erstwertung && t.zweitwertung==sortierterVector.get(j).zweitwertung && t.drittwertung>sortierterVector.get(j).drittwertung){
						j++;
					}
				}	

				sortierterVector.insertElementAt(t,j);	
			}
		}

		//Platzgruppen berechnen
		platzgruppe=-1;
		platzGruppe.clear();
		for(int i=1;i<sortierterVector.size();i++){
			KTeilnehmer t1=sortierterVector.get(i);
			KTeilnehmer t2=sortierterVector.get(i-1);
			if(t1.erstwertung==t2.erstwertung && t1.zweitwertung==t2.zweitwertung && t1.drittwertung==t2.drittwertung){
				if(t2.platzGruppe==-1){
					platzgruppe++;
					t2.platzGruppe=platzgruppe;
					t1.platzGruppe=platzgruppe;
					platzGruppe.add(new Vector<KTeilnehmer>());
					platzGruppe.get(platzgruppe).add(t2);
					platzGruppe.get(platzgruppe).add(t1);
				} else{
					t1.platzGruppe=t2.platzGruppe;
					platzGruppe.get(platzgruppe).add(t1);
				}
			} 
		}
		//Plätze eintragen
		for(int i=sortierterVector.size()-1;i>=0;i--){
			if(i<sortierterVector.size()-1 && sortierterVector.get(i).platzGruppe>-1 && sortierterVector.get(i).platzGruppe==sortierterVector.get(i+1).platzGruppe){
				sortierterVector.get(i).platz=sortierterVector.get(i+1).platz;
			} else{
				sortierterVector.get(i).platz=sortierterVector.size()-i;
			}
			sortierterVector.get(i).tabellenPosition=sortierterVector.size()-i;
		}
		
		//Paarungsmodus = immer Zufall
		if(optionenFeldVar.zufall.isSelected()){
			platzgruppe=0;
			platzGruppe.clear();
			platzGruppe.add(new Vector<KTeilnehmer>());
			for(int i=1;i<sortierterVector.size();i++){
				KTeilnehmer t1=sortierterVector.get(i);
				t1.platzGruppe=platzgruppe;
				platzGruppe.get(platzgruppe).add(t1);
			}
		}
	}
	
	public void calcOS_SOG(int lokRunde){//Punkte der Gegner minus die Spiele gegen den Spieler selbst
		KTeilnehmer t;
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			//for(int j=0;j<t.paarungen.size();j++){
			for(int j=1;j<=rundenZaehler;j++){
				if(t.paarungen.get(j)!=null){//Bei Hash. Bei Vector entfernen
					if(((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).runde<=lokRunde){
						int diff=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).p2pri;//gegnerische Punkte des eigenen Spiels
						int allSOS=teilnehmerVector.get(t.paarungen.get(j)).primaer;//Werden abgezogen, da nur die Spiele gegen andere Spieler eingerechnet werden sollen
						t.sos_sog += (allSOS-diff);
					}
				}
			}
		}
	}
	
	public void calcOS(int lokRunde){//Punkte der Gegner mit den Spielen gegen den Spieler selbst
		KTeilnehmer t;
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			//for(int j=0;j<t.paarungen.size();j++){
			for(int j=1;j<=rundenZaehler;j++){
				if(t.paarungen.get(j)!=null){//Bei Hash. Bei Vector entfernen
					if(((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).runde<=lokRunde){
						int allSOS=teilnehmerVector.get(t.paarungen.get(j)).primaer;//Werden abgezogen, da nur die Spiele gegen andere Spieler eingerechnet werden sollen
						t.sos += allSOS;
					}
				}
			}
		}
	}

	public void calcOOS_SOG(int lokRunde){
		KTeilnehmer t;
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			//for(int j=0;j<t.paarungen.size();j++){
			for(int j=1;j<=rundenZaehler;j++){
				if(t.paarungen.get(j)!=null){//Bei Hash. Bei Vector entfernen
					if(((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).runde<=lokRunde){
						KTeilnehmer gegner=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).t2;
						t.sosos_sog += gegner.sos_sog;
					}
				}
			}
		}
	}

	public void calcOOS(int lokRunde){
		KTeilnehmer t;
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			//for(int j=0;j<t.paarungen.size();j++){
			for(int j=1;j<=rundenZaehler;j++){
				if(t.paarungen.get(j)!=null){//Bei Hash. Bei Vector entfernen
					if(((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).runde<=lokRunde){
						KTeilnehmer gegner=((KBegegnungen)((JPanel)HauptPanel.getComponent(i)).getComponent(t.paarungen.get(j))).t2;
						t.sosos += gegner.sos;
					}
				}
			}
		}
	}
	
	public void calcVeiovis(int lokRunde){
		if(lokRunde>0){
			KTeilnehmer t;
			for(int i=0;i<teilnehmer;i++){
				t=teilnehmerVector.get(i);

				t.rpi = t.primaer*0.25*(lokRunde*Math.sqrt(lokRunde)+3)/(lokRunde*Math.sqrt(lokRunde)) 
						+ (t.sos/lokRunde)*(1-0.25*(lokRunde*Math.sqrt(lokRunde)+3)/(lokRunde*Math.sqrt(lokRunde)))/1.5 
						+ (t.sosos/(lokRunde*lokRunde))*(1-0.25*(lokRunde*Math.sqrt(lokRunde)+3)/(lokRunde*Math.sqrt(lokRunde)))/3;

				System.out.println(t.vorname+" "+t.nachname+": RPI_gesamt: "+t.rpi);
				System.out.println("RPI_pri("+(t.primaer)+"): "+(t.primaer*0.25*(lokRunde*Math.sqrt(lokRunde)+3)/(lokRunde*Math.sqrt(lokRunde)) ));
				System.out.println("RPI_sos("+(t.sos)+"): "+((t.sos/lokRunde)*(1-0.25*(lokRunde*Math.sqrt(lokRunde)+3)/(lokRunde*Math.sqrt(lokRunde)))/1.5));
				System.out.println("RPI_soos("+(t.sosos)+"): "+((t.sosos/(lokRunde*lokRunde))*(1-0.25*(lokRunde*Math.sqrt(lokRunde)+3)/(lokRunde*Math.sqrt(lokRunde)))/3));
				
			}
		}
	}
	
	public void calcStrengthOfSchedule(int lokRunde){
		if(lokRunde>0){
			KTeilnehmer t;
			for(int i=0;i<teilnehmer;i++){
				t=teilnehmerVector.get(i);

				t.strengthOfSchedulde = ((double)t.primaer/3.0) + ((double)t.sos_sog/6.0) + ((double)t.sosos_sog/18.0);

				System.out.println(teilnehmerVector.get(i).vorname+" "+teilnehmerVector.get(i).nachname+": Strength of Schedule: "+t.strengthOfSchedulde+ " Primär: "+t.primaer+ " SOS: "+(t.sos_sog)+ " SOSOS: "+(t.sosos_sog));
			}
		}

	}

	public void platzGruppenMischen(Vector<KTeilnehmer> tVector){
		System.out.println("Alt");
		for(int i=0;i<tVector.size();i++){
			System.out.println(tVector.get(i).platz + " "+ tVector.get(i).primaer + " "+ tVector.get(i).sekundaer + " "+ tVector.get(i).sos + " "+ tVector.get(i).vorname+" "+tVector.get(i).nachname);
		}
		Random randomGenerator = new Random();
		for(int i=0;i<platzGruppe.size();i++){
			int startpos= tVector.indexOf(platzGruppe.get(i).get(0));
			for(int j=0; j<platzGruppe.get(i).size();j++){
				tVector.remove(platzGruppe.get(i).get(j));
			}
			while(platzGruppe.get(i).size()>0){
				int rand =randomGenerator.nextInt(platzGruppe.get(i).size());
				tVector.add(startpos,platzGruppe.get(i).get(rand));
				platzGruppe.get(i).remove(rand);
			}
		}
		System.out.println("Neu");
		for(int i=0;i<tVector.size();i++){
			System.out.println(tVector.get(i).platz + " "+ tVector.get(i).primaer + " "+ tVector.get(i).sekundaer + " "+ tVector.get(i).sos + " "+ tVector.get(i).vorname+" "+tVector.get(i).nachname);
			tVector.get(i).tabellenPosition=sortierterVector.size()-i;
		}

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

		File f2 = new File(System.getProperty("user.dir")+"/auto.cfg");
		KSpeicherverwaltung.speichernKonfig(this,f2);

		System.exit(0);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		System.out.println("Hauptfenster component hidden");
		adaptPanel();
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
//		System.out.println("Hauptfenster component moved");
//		adaptPanel();
//		repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("Hauptfenster component resized");
		adaptPanel();
		repaint();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("Hauptfenster component shown");
		adaptPanel();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getSource()==p1sekField || arg0.getSource()==p2sekField){
			if(optionenFeldVar.sPunkte.isSelected() && optionenFeldVar.matrixBenutzen.isSelected()){
				try{
					p1priField.setText(""+matrix.getTP(Integer.parseInt(p1sekField.getText())-Integer.parseInt(p2sekField.getText())));
					p2priField.setText(""+matrix.getTP(Integer.parseInt(p2sekField.getText())-Integer.parseInt(p1sekField.getText())));

				}catch(NumberFormatException e){

				}
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	/*
	 * Komplettauswahl eines Textfeldes, sobald der Fokus erlangt wurde
	 */
	public void setSelectAll(final JTextField tf){
		tf.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				tf.selectAll();
			}
		});
		tf.addActionListener(enterListener);
	}
	
	
	
	ActionListener enterListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Command: "+e.getActionCommand());
			try{
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
			}catch(AWTException ex){
				ex.printStackTrace();
			}
		}
	};
}
