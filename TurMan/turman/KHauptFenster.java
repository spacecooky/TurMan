package turman;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JTextField;

/**
 * TODO Variable Einstellung der Urkunden-Infos.
 * TODO Variable Festlegung der Prim�r-, Sekund�r- und SOS-Punkte.
 * TODO Erkennung der Variablen Punkte f�r den Export.
 * TODO Turniermodus: Schweizer System, Komplett zuf�llige Paarungen, KO-System
 * TODO Siegpunkte-Matrix
 * TODO Teamturniere
 * TODO Infofenster
 * TODO Druckfunktionen
 * TODO Multilingualit�t
 * TODO Einf�gen eines Freilos-Spielers
 * TODO Mehrere �bersicht-Tabs
 * TODO Scrollbars in der Matrix so anpassen, dass die linke Namesleiste scrollt, wenn man hoch und runter scrollt und die obere, wenn man nach links und rechts scrollt.
 * TODO Beim Hinzuf�gen neuer Spieler bereits gel�schte Spieler ausgegraut lassen.
 * TODO Maximalgr��e von Elementen im Punkte-/Extrapunkte-/Begegnungsfenster, falls zu wenige eingetragen sind
 * TODO Anzeige von Fehlerdialogen, z.B beim Speichern und Laden
 * TODO Konfigurationsschablonen mit Einstellungen f�r jede Runde
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
		
		
		//Hauptbereich
		sp=new JScrollPane(HauptPanel);
		setContentPane(sp);
		HauptPanel.setLayout(new BoxLayout(HauptPanel,BoxLayout.Y_AXIS));
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
	}
	
	static String version=new String("V0.0.6");

// Hauptbereich
	JPanel HauptPanel = new JPanel();
	JScrollPane sp;
	
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
	JMenuItem punkte = new JMenuItem("Punkte");
	JMenuItem begegnungen = new JMenuItem("Begegnungen");
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
	int gel�schteTeilnehmer=0;
	int runden=0;
	int rundenZaehler=0;
	int mode = KPairings.RANDOM;
	String TID="0";
	Vector<KTeilnehmer> teilnehmerVector= new Vector<KTeilnehmer>();
	Vector<KTeilnehmer> sortierterVector= new Vector<KTeilnehmer>();
	Vector<KTeilnehmer> herausforderungsVector= new Vector<KTeilnehmer>();
	Vector<KBegegnungen> begegnungsVector= new Vector<KBegegnungen>();
	Vector<KBegegnungen> alleBegegnungenVector= new Vector<KBegegnungen>();
	
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
			//KPairings.team=true; Haken muss manuell entfernt werden
			rundenZaehler=0;
			alleBegegnungenVector.clear();
			for(int i=4;i<3+teilnehmer*3;i+=3){
				teilnehmerVector.add(new KTeilnehmer(((JTextField)teilnehmerPanel.getComponent(i)).getText(),((JTextField)teilnehmerPanel.getComponent(i+1)).getText()));
			}
			teilnehmerFrame.setVisible(false);
			System.out.println(teilnehmerVector.size());
			
			fillPanels();
			
			
		}else if(quelle == speichern){
			KSpeicherverwaltung.speichern(this);
		}else if(quelle == oeffnen){
			KSpeicherverwaltung.laden(this);
		} else if(quelle == punkte){
			punkteFenster.init(null);
		} else if(quelle == runde){
			KPairings.runde(this);
		}else if(quelle == rundeWdh){
			KPairings.rundeReset(this);
			KPairings.runde(this);
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
				t.sekund�r+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p12;
			}
			if(ab){
				t.sekund�r+=t.armeeliste;
			}
			if(bm){
				t.prim�r+=t.bemalwertung;
			}
		}
		//SOS f�r alle berechnen
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			for(int j=0;j<t.paarungen.size();j++){
				t.sos += teilnehmerVector.get(t.paarungen.get(j)).prim�r+(bm?-teilnehmerVector.get(t.paarungen.get(j)).bemalwertung:0);
				
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
			while(j<sortierterVector.size()&&t.prim�r==sortierterVector.get(j).prim�r&&t.sekund�r==sortierterVector.get(j).sekund�r&&t.sos>sortierterVector.get(j).sos){
				j++;
			}
			sortierterVector.insertElementAt(t,j);	
		}
	}
	
	/**
	 * Beendet das Programm
	 */
	protected void beenden(){
		System.exit(0);
	}
	
}
