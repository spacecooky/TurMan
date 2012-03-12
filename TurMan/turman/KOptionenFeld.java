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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	JRadioButton bemalPri = new JRadioButton("Bemalwertung");
	JRadioButton bemalSek = new JRadioButton("Bemalwertung");
	JRadioButton bemalNo = new JRadioButton("Bemalwertung");
	ButtonGroup bemalGroup = new ButtonGroup();
	
	JRadioButton armeePri = new JRadioButton("Armeebewertung");
	JRadioButton armeeSek = new JRadioButton("Armeebewertung");
	JRadioButton armeeNo = new JRadioButton("Armeebewertung");
	ButtonGroup armeeGroup = new ButtonGroup();
	
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
		team.setEnabled(false);
		
		turnierPanel.add(p4);
		
		JPanel p5 = new JPanel();
		p5.setBorder(BorderFactory.createTitledBorder("Modus"));
		p5.setLayout(new GridLayout(3,1));
		p5.add(schweizer);
		p5.add(zufall);
		p5.add(ko);
		modus.add(schweizer);
		modus.add(zufall);
		modus.add(ko);
		
		turnierZurueck.addActionListener(this);
		schweizer.setSelected(true);
		zufall.setEnabled(false);
		ko.setEnabled(false);
		
		turnierPanel.add(p5);
		
		JPanel p11 = new JPanel();
		p11.setLayout(new GridLayout(40,1));
		p11.add(turnierZurueck);
		
		turnierPanel.add(p11);
		
		//PaarungsOptionen
		paarungsPanel.setLayout(new BoxLayout(paarungsPanel, BoxLayout.Y_AXIS));
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(5,1));
		p1.add(teams);
		p1.add(orte);
		p1.add(armeen);
		p1.add(mirror);
		p1.add(tisch);
		
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(5,1));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		p2.add(new JLabel("Ausnahmen:"));
		
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(5,1));
		p3.add(teamsField);
		p3.add(orteField);
		p3.add(armeenField);
		p3.add(mirrorField);
		p3.add(tischField);
		
		
		JPanel p9 = new JPanel();
		p9.setLayout(new GridLayout(1,3));
		
		p9.add(p1);
		p9.add(p2);
		p9.add(p3);
		
		JPanel p10 = new JPanel();
		p10.setLayout(new GridLayout(40,1));
		p10.add(paarungsZurueck);
		
		paarungsPanel.add(p9);
		paarungsPanel.add(p10);
		
		teams.setSelected(true);
		tisch.setSelected(true);
		paarungsZurueck.addActionListener(this);
		
		//WertungsOptionen
		wertungsPanel.setLayout(new BoxLayout(wertungsPanel, BoxLayout.Y_AXIS));
		
		JPanel p6 = new JPanel();
		p6.setBorder(BorderFactory.createTitledBorder("Typ"));
		p6.setLayout(new GridLayout(2,1));
		p6.add(PSS);
		p6.add(TS);
		wertung.add(PSS);
		wertung.add(TS);
		PSS.setSelected(true);
		PSS.addActionListener(this);
		TS.addActionListener(this);
		
		wertungsPanel.add(p6);
		
		JPanel p7 = new JPanel();
		JPanel p7pri = new JPanel();
		JPanel p7sek = new JPanel();
		JPanel p7no = new JPanel();
		
		p7.setBorder(BorderFactory.createTitledBorder("Zusatzpunkte"));
		p7pri.setBorder(BorderFactory.createTitledBorder("Primär"));
		p7sek.setBorder(BorderFactory.createTitledBorder("Sekundär"));
		p7no.setBorder(BorderFactory.createTitledBorder("keine Wertung"));
		p7.setLayout(new GridLayout(1,3));
		p7pri.setLayout(new GridLayout(2,1));
		p7sek.setLayout(new GridLayout(2,1));
		p7no.setLayout(new GridLayout(2,1));
		p7.add(p7pri);
		p7.add(p7sek);
		p7.add(p7no);
		
		p7pri.add(bemalPri);
		p7pri.add(armeePri);
		p7sek.add(bemalSek);
		p7sek.add(armeeSek);
		p7no.add(bemalNo);
		p7no.add(armeeNo);
		
		bemalGroup.add(bemalPri);
		bemalGroup.add(bemalSek);
		bemalGroup.add(bemalNo);
		
		armeeGroup.add(armeePri);
		armeeGroup.add(armeeSek);
		armeeGroup.add(armeeNo);
		
		bemalNo.setSelected(true);
		armeeNo.setSelected(true);
		
		wertungsPanel.add(p7);
		
		/*bemalNo.setEnabled(false);
		armeeNo.setEnabled(false);
		bemalPri.setEnabled(false);
		armeePri.setEnabled(false);
		bemalSek.setEnabled(false);
		armeeSek.setEnabled(false);*/
		
		JPanel p8 = new JPanel();
		p8.setLayout(new GridLayout(40,1));
		p8.add(wertungZurueck);
		wertungZurueck.addActionListener(this);
		
		wertungsPanel.add(p8);
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
		} else if(arg0.getSource()==PSS || arg0.getSource()==TS){
			if(PSS.isSelected()){
				bemalSek.setEnabled(true);
				armeeSek.setEnabled(true);
			} else if(TS.isSelected()){
				bemalSek.setEnabled(false);
				armeeSek.setEnabled(false);
				if(bemalSek.isSelected()){
					bemalSek.setSelected(false);
					bemalNo.setSelected(true);
				}
				if(armeeSek.isSelected()){
					armeeSek.setSelected(false);
					armeeNo.setSelected(true);
				}
			}
		}
		
	}
	
}
