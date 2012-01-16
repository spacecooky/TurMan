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
 * TODO Variable Festlegung der Primär-, Sekundär- und SOS-Punkte.
 * TODO Erkennung der Variablen Punkte für den Export.
 * TODO Turniermodus: Schweizer System, Komplett zufällige Paarungen, KO-System
 * TODO Hinzufügen von Paarungsparametern: Teamkollegen, Tische, Armeen, Orte, Mirrormatches.
 * TODO Auswahl der Paarungsparameter in jeder Runde.
 * TODO Siegpunkte-Matrix
 * TODO Teamturniere
 * TODO Konfigurationsfenster
 * TODO Infofenster
 * TODO Druckfunktionen
 * TODO Multilingualität
 * TODO Einfügen eines Freilos-Spielers
 * TODO Speichern,Laden,Import und Export nur nach erfolgreicher Bestätigung des Dialoges
 * TODO Mehrere Übersicht-Tabs
 * TODO Scrollbars in der Matrix so anpassen, dass die linke Namesleiste scrollt, wenn man hoch und runter scrollt und die obere, wenn man nach links und rechts scrollt.
 * TODO Beim Hinzufügen neuer Spieler bereits gelöschte Spieler ausgegraut lassen.
 * TODO Übersichtliches Eingabefenster für Bemalwertung/Listenpunkte/usw aller Spieler
 * TODO Punktefenster nach jeder Punkteingabe aktualisieren, falls es offen ist.
 * TODO Maximalgröße von Elementen im Punkte/Begegnungsfenster, falls zu wenige eingetragen sind
 * TODO Funktion zum Rückgängig machen einer Paarungsrunde
 * TODO Urkunden dürfen entfernte Spieler nicht mehr entalten
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
		turnier.add(herausforderung);
		herausforderung.addActionListener(this);
		turnier.add(runde);
		runde.addActionListener(this);
		turnier.add(zeit);
		zeit.addActionListener(this);
		turnier.add(urkundenErstellen);
		urkundenErstellen.addActionListener(this);
		turnier.add(optionen);
		optionen.addActionListener(this);
		
		menubar.add(spieler);
		spieler.add(entfernen);
		entfernen.addActionListener(this);
		spieler.add(erweitern);
		erweitern.addActionListener(this);
		
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
	
	static String version=new String("V0.0.3");

// Hauptbereich
	JPanel HauptPanel = new JPanel();
	JScrollPane sp;
	
//	Menue
	JMenuBar menubar = new JMenuBar();
	JMenu datei = new JMenu("Datei");
	JMenuItem neu = new JMenuItem("Neues Turnier - Manuell");
	JMenuItem gImport = new JMenuItem("Neues Turnier - GöPP Import");
	JMenuItem gExport = new JMenuItem("GöPP Export");
	JMenuItem oeffnen = new JMenuItem("Turnier Öffnen");
	JMenuItem speichern = new JMenuItem("Turnier Speichern");
	JMenuItem beenden = new JMenuItem("Beenden");
	
	JMenu turnier = new JMenu("Turnier");
	JMenuItem punkte = new JMenuItem("Punkte");
	JMenuItem begegnungen = new JMenuItem("Begegnungen");
	JMenuItem herausforderung = new JMenuItem("Herausforderung");
	JMenuItem runde = new JMenuItem("Nächste Runde");
	JMenuItem zeit = new JMenuItem("Zeit starten");
	JMenuItem urkundenErstellen = new JMenuItem("Urkunden erstellen");
	JMenuItem optionen = new JMenuItem("Optionen einstellen");
	
	JMenu spieler = new JMenu("Spieler");
	JMenuItem entfernen = new JMenuItem("Entfernen");
	JMenuItem erweitern = new JMenuItem("Hinzufügen");
	
	
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
	int gelöschteTeilnehmer=0;
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
			punkteFenster.init();
		} else if(quelle == runde){
			KPairings.runde(this);
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
			urkunde.urkundeErstellen(teilnehmerVector);
		}else if(quelle==begegnungen){
			begegnungsFenster.init();
		}else if(quelle==optionen){
			setContentPane(optionenFeld);
			validate();
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
		//Primär und Sekundärpunkte für alle berechnen
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			t.primär=0;
			t.sekundär=0;
			t.sos=0;
			for(int j=0;j<t.paarungen.size();j++){
				t.primär+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p1;
				t.sekundär+=((KBegegnungen)((JPanel)HauptPanel.getComponent(i+1)).getComponent(t.paarungen.get(j)+1)).p12;
			}
			if(ab){
				t.sekundär+=t.armeeliste;
			}
			if(bm){
				t.primär+=t.bemalwertung;
			}
		}
		//SOS für alle berechnen
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			for(int j=0;j<t.paarungen.size();j++){
				t.sos += teilnehmerVector.get(t.paarungen.get(j)).primär+(bm?-teilnehmerVector.get(t.paarungen.get(j)).bemalwertung:0);
				
			}
		}
		
		//Sortiern nach primär
		sortierterVector.clear();
		for(int i=0;i<teilnehmer;i++){
			t=teilnehmerVector.get(i);
			int j=0;
			while(j<sortierterVector.size()&&t.primär>sortierterVector.get(j).primär){
				j++;
			}
			//Sortieren nach Sekundär
			while(j<sortierterVector.size()&&t.primär==sortierterVector.get(j).primär&&teilnehmerVector.get(i).sekundär>sortierterVector.get(j).sekundär){
				j++;
			}
			//Sortieren nach sos
			while(j<sortierterVector.size()&&t.primär==sortierterVector.get(j).primär&&t.sekundär==sortierterVector.get(j).sekundär&&t.sos>sortierterVector.get(j).sos){
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
