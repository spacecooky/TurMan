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
	
	//WertungsOptionen
	JRadioButton PSS = new JRadioButton("Primärpunkte, Sekundärpunkte, SOS");
	JRadioButton TS = new JRadioButton("Turnierpunkte, Siegpunktedifferenz");
	JRadioButton RPI = new JRadioButton("RPI (Kombination von Primärpunkten, SOS und SOSOS anhand einer Formel)");
	ButtonGroup wertung = new ButtonGroup();
	JCheckBox matrixBenutzen= new JCheckBox("Matrix benutzen");
	JButton matrix= new JButton("Siegpunktematrix");
	JCheckBox SUN = new JCheckBox("SUN statt Primärpunkteeingabe (Einheitspunkte für Sieg-Unentschieden-Niederlage)");
	JRadioButton SUN2_1_0 = new JRadioButton("2-1-0");
	JRadioButton SUN3_1_0 = new JRadioButton("3-1-0");
	JRadioButton SUN20_10_1 = new JRadioButton("20-10-1");
	JRadioButton SUN_frei = new JRadioButton("Freie Wahl:");
	JTextField SUN_S = new JTextField("2");
	JTextField SUN_U = new JTextField("1");
	JTextField SUN_N = new JTextField("0");
	ButtonGroup sunGroup = new ButtonGroup();
	
	JRadioButton bemalPri = new JRadioButton("Bemalwertung");
	JRadioButton bemalSek = new JRadioButton("Bemalwertung");
	JRadioButton bemalNo = new JRadioButton("Bemalwertung");
	ButtonGroup bemalGroup = new ButtonGroup();
	
	JRadioButton armeePri = new JRadioButton("Armeebewertung");
	JRadioButton armeeSek = new JRadioButton("Armeebewertung");
	JRadioButton armeeNo = new JRadioButton("Armeebewertung");
	ButtonGroup armeeGroup = new ButtonGroup();
	
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
		
		schweizer.setSelected(true);
		zufall.setEnabled(false);
		ko.setEnabled(false);
		
		turnierPanel.add(p5);
		
		JPanel p11 = new JPanel();
		p11.setLayout(new GridLayout(40,1));
		p11.add(new JLabel("a"));
		p11.getComponent(0).setForeground(p11.getBackground());
		
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
		p10.add(new JLabel("a"));
		p10.getComponent(0).setForeground(p10.getBackground());
		
		paarungsPanel.add(p9);
		paarungsPanel.add(p10);
		
		teams.setSelected(true);
		tisch.setSelected(true);
		
		//WertungsOptionen
		wertungsPanel.setLayout(new BoxLayout(wertungsPanel, BoxLayout.Y_AXIS));
		
		JPanel p6 = new JPanel();
		p6.setBorder(BorderFactory.createTitledBorder("Typ"));
		p6.setLayout(new GridLayout(6,1));
		p6.add(PSS);
		p6.add(RPI);
		p6.add(TS);
		p6.add(matrixBenutzen);
		p6.add(matrix);
		matrix.addActionListener(this);
		matrixBenutzen.setEnabled(false);
		matrix.setEnabled(false);
		wertung.add(PSS);
		wertung.add(TS);
		wertung.add(RPI);
		PSS.setSelected(true);
		PSS.addActionListener(this);
		TS.addActionListener(this);
		RPI.addActionListener(this);
		
		JPanel p61= new JPanel();
		p61.add(SUN);
		p61.setLayout(new BoxLayout(p61, BoxLayout.X_AXIS));
		p61.add(SUN2_1_0);
		p61.add(SUN3_1_0);
		p61.add(SUN20_10_1);
		p61.add(SUN_frei);
		p61.add(SUN_S);
		p61.add(SUN_U);
		p61.add(SUN_N);
		p6.add(p61);
		SUN2_1_0.setSelected(true);
		sunGroup.add(SUN2_1_0);
		sunGroup.add(SUN3_1_0);
		sunGroup.add(SUN20_10_1);
		sunGroup.add(SUN_frei);
		
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
		
		JPanel p8 = new JPanel();
		p8.setLayout(new GridLayout(40,1));
		p8.add(new JLabel("a"));
		p8.getComponent(0).setForeground(p8.getBackground());
		
		wertungsPanel.add(p8);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==PSS || arg0.getSource()==TS || arg0.getSource()==RPI){
			if(PSS.isSelected()){
				bemalSek.setEnabled(true);
				armeeSek.setEnabled(true);
				matrixBenutzen.setEnabled(false);
				matrix.setEnabled(false);
			} else if(RPI.isSelected()){
				bemalSek.setEnabled(false);
				armeeSek.setEnabled(false);
				matrixBenutzen.setEnabled(false);
				matrix.setEnabled(false);
				if(bemalSek.isSelected()){
					bemalSek.setSelected(false);
					bemalNo.setSelected(true);
				}
				if(armeeSek.isSelected()){
					armeeSek.setSelected(false);
					armeeNo.setSelected(true);
				}
			}else if(TS.isSelected()){
				bemalSek.setEnabled(false);
				armeeSek.setEnabled(false);
				matrixBenutzen.setEnabled(true);
				matrix.setEnabled(true);
				if(bemalSek.isSelected()){
					bemalSek.setSelected(false);
					bemalNo.setSelected(true);
				}
				if(armeeSek.isSelected()){
					armeeSek.setSelected(false);
					armeeNo.setSelected(true);
				}
			}
			hf.updatePanels();
		}else if(arg0.getSource()==matrix){
			hf.matrix.init();
		}
		
	}
	
	public void clear(){
		 einzel.setSelected(false);
		 team.setSelected(false);
		
		 schweizer.setSelected(false);
		 zufall.setSelected(false);
		 ko.setSelected(false);
		
		 teams.setSelected(false);
		 orte.setSelected(false);
		 armeen.setSelected(false);
		 mirror.setSelected(false);
		 tisch.setSelected(false);
		
		 teamsField.setText("0");
		 orteField.setText("0");
		 armeenField.setText("0");
		 mirrorField.setText("0");
		 tischField.setText("0");
		
		 PSS.setSelected(false);
		 TS.setSelected(false);
		 RPI.setSelected(false);
		
		 bemalPri.setSelected(false);
		 bemalSek.setSelected(false);
		 bemalNo.setSelected(false);
		
		 armeePri.setSelected(false);
		 armeeSek.setSelected(false);
		 armeeNo.setSelected(false);
	}
	
}
