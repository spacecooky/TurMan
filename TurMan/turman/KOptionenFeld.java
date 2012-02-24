package turman;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class KOptionenFeld extends JTabbedPane implements ActionListener{

	public KOptionenFeld(KHauptFenster hf){
		this.hf=hf;
		init();
	}
	
	public void init(){
		addTab("Turnier",null,turnierPanel);
		addTab("Paarungen",null,paarungsPanel);
		addTab("Wertung",null,wertungsPanel);
		initPaarungsPanel();
	}
	
	KHauptFenster hf;
	
	JPanel turnierPanel = new JPanel();
	JPanel paarungsPanel = new JPanel();
	JPanel wertungsPanel = new JPanel();
	
	//TurnierOptionen
	
	JRadioButton einzel = new JRadioButton("Einzelspieler-Turnier");
	JRadioButton team = new JRadioButton("Team-Turnier");
	ButtonGroup typ = new ButtonGroup();
	
	JRadioButton schweizer = new JRadioButton("Schweizer System");
	JRadioButton zufall = new JRadioButton("Zufall");
	JRadioButton ko = new JRadioButton("K.O.");
	ButtonGroup modus = new ButtonGroup();
	
	JButton turnierZurueck= new JButton("Zurück zur Matrix");
	
	//PaarungsOptionen
	
	JCheckBox teams = new JCheckBox("Keine Mitglieder des selben Teams paaren");
	JCheckBox orte = new JCheckBox("Keine Mitglieder des selben Ortes paaren");
	JCheckBox armeen = new JCheckBox("Nicht mehrmals gegen die selbe Armee paaren");
	JCheckBox mirror = new JCheckBox("Keine Mirrormatches");
	JCheckBox tisch = new JCheckBox("Nicht mehrmals am selben Tisch spielen");
	
	JTextField teamsField = new JTextField("0");
	JTextField orteField = new JTextField("0");
	JTextField armeenField = new JTextField("0");
	JTextField mirrorField = new JTextField("0");
	JTextField tischField = new JTextField("0");
	
	JButton paarungsZurueck= new JButton("Zurück zur Matrix");
	
	//WertungsOptionen
	JRadioButton PSS = new JRadioButton("Primärpunkte, Sekundärpunkte, SOS");
	JRadioButton TS = new JRadioButton("Turnierpunkte, Siegpunktedifferenz");
	ButtonGroup wertung = new ButtonGroup();
	
	JButton wertungZurueck= new JButton("Zurück zur Matrix");
	
	public void initPaarungsPanel(){
		//TurnierOptionen
		turnierPanel.setLayout(new BoxLayout(turnierPanel, BoxLayout.Y_AXIS));
		
		JPanel p4 = new JPanel();
		p4.setBorder(BorderFactory.createTitledBorder("Typ"));
		p4.setLayout(new GridLayout(2,1));
		p4.add(einzel);
		p4.add(team);
		typ.add(einzel);
		typ.add(team);
		einzel.setSelected(true);
		
		turnierPanel.add(p4);
		
		JPanel p5 = new JPanel();
		p5.setBorder(BorderFactory.createTitledBorder("Modus"));
		p5.setLayout(new GridLayout(40,1));
		p5.add(schweizer);
		p5.add(zufall);
		p5.add(ko);
		modus.add(schweizer);
		modus.add(zufall);
		modus.add(ko);
		p5.add(turnierZurueck);
		turnierZurueck.addActionListener(this);
		schweizer.setSelected(true);
		zufall.setEnabled(false);
		ko.setEnabled(false);
		
		turnierPanel.add(p5);
		
		//PaarungsOptionen
		paarungsPanel.setLayout(new BoxLayout(paarungsPanel, BoxLayout.X_AXIS));
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(30,1));
		p1.add(teams);
		p1.add(orte);
		p1.add(armeen);
		p1.add(mirror);
		p1.add(tisch);
		p1.add(paarungsZurueck);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(30,1));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel(""));
		
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(30,1));
		p3.add(teamsField);
		p3.add(orteField);
		p3.add(armeenField);
		p3.add(mirrorField);
		p3.add(tischField);
		p3.add(new JLabel(""));
		
		paarungsPanel.add(p1);
		paarungsPanel.add(p2);
		paarungsPanel.add(p3);
		
		teams.setSelected(true);
		tisch.setSelected(true);
		paarungsZurueck.addActionListener(this);
		
		//WertungsOptionen
		wertungsPanel.setLayout(new BoxLayout(wertungsPanel, BoxLayout.Y_AXIS));
		
		JPanel p6 = new JPanel();
		p6.setBorder(BorderFactory.createTitledBorder("Typ"));
		p6.setLayout(new GridLayout(40,1));
		p6.add(PSS);
		p6.add(TS);
		wertung.add(PSS);
		wertung.add(TS);
		p6.add(wertungZurueck);
		wertungZurueck.addActionListener(this);
		PSS.setSelected(true);
		
		wertungsPanel.add(p6);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==paarungsZurueck || arg0.getSource()==turnierZurueck || arg0.getSource()==wertungZurueck){
			if(einzel.isSelected()){
				//hf.setContentPane(hf.sp);
				hf.tab.remove(0);
				hf.tab.insertTab("Matrix", null, hf.sp, null, 0);
			} else if(team.isSelected()){
				//hf.setContentPane(hf.spTeam);
				hf.tab.remove(0);
				hf.tab.insertTab("Matrix", null, hf.spTeam, null, 0);
			}
			hf.setContentPane(hf.tab);
			hf.validate();
		}
		
	}
	
}
