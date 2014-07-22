package turman;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
	
	JLabel teams = new JLabel("Keine Mitglieder des selben Teams paaren");
	JLabel orte = new JLabel("Keine Mitglieder des selben Ortes paaren");
	JLabel armeen = new JLabel("Nicht mehrmals gegen die selbe Armee paaren");
	JLabel mirror = new JLabel("Keine Mirrormatches");
	JLabel tisch = new JLabel("Nicht mehrmals am selben Tisch spielen");
	
	Vector<JCheckBox> teamsBoxes = new Vector<JCheckBox>();
	Vector<JCheckBox> orteBoxes = new Vector<JCheckBox>();
	Vector<JCheckBox> armeenBoxes = new Vector<JCheckBox>();
	Vector<JCheckBox> mirrorBoxes = new Vector<JCheckBox>();
	Vector<JCheckBox> tischBoxes = new Vector<JCheckBox>();
	
	JRadioButton r1ntr = new JRadioButton("NTR");
	JRadioButton r1zufall = new JRadioButton("Zufall");
	ButtonGroup r1Group = new ButtonGroup();
	
	//WertungsOptionen Erstwertung
	JRadioButton pPunkte = new JRadioButton("Punkteeingabe (Primärpunkte)");
	JRadioButton pRPI = new JRadioButton("RPI (Kombination von Primärpunkten, SOS und SOOS anhand einer Formel)");
	JRadioButton pStrength = new JRadioButton("Strength of Schedule (Kombination von Primärpunkten, SOS und SOOS anhand einer Formel)");
	//WertungsOptionen Zweitwertung
	JRadioButton sPunkte = new JRadioButton("Punkteeingabe (Sekundärpunkte)");
	JRadioButton sSOS = new JRadioButton("SOS (Summe aller Gegnerpunkte)");
	JRadioButton sSOOS = new JRadioButton("SOOS (Summe aller Gegnerpunkte aller Gegner)");
	JRadioButton sRPI = new JRadioButton("RPI (Kombination von Primärpunkten, SOS und SOOS anhand einer Formel)");
	JRadioButton sStrength = new JRadioButton("Strength of Schedule (Kombination von Primärpunkten, SOS und SOOS anhand einer Formel)");
	JRadioButton sKeine = new JRadioButton("Keine Zweitwertung");
	//WertungsOptionen Drittwertung
	JRadioButton tPunkte = new JRadioButton("Punkteeingabe (Tertiärpunkte)");
	JRadioButton tSOS = new JRadioButton("SOS (Summe aller Gegnerpunkte)");
	JRadioButton tSOOS = new JRadioButton("SOOS (Summe aller Gegnerpunkte aller Gegner)");
	JRadioButton tRPI = new JRadioButton("RPI (Kombination von Primärpunkten, SOS und SOOS anhand einer Formel)");
	JRadioButton tStrength = new JRadioButton("Strength of Schedule (Kombination von Primärpunkten, SOS und SOOS anhand einer Formel)");
	JRadioButton tKeine = new JRadioButton("Keine Drittwertung");
	
	
	ButtonGroup erstWertung = new ButtonGroup();
	ButtonGroup zweitWertung = new ButtonGroup();
	ButtonGroup drittWertung = new ButtonGroup();
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
	JRadioButton bemalTer = new JRadioButton("Bemalwertung");
	JRadioButton bemalNo = new JRadioButton("Bemalwertung");
	ButtonGroup bemalGroup = new ButtonGroup();
	
	JRadioButton armeePri = new JRadioButton("Armeebewertung");
	JRadioButton armeeSek = new JRadioButton("Armeebewertung");
	JRadioButton armeeTer = new JRadioButton("Armeebewertung");
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
		//zufall.setEnabled(false);
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
		p2.add(new JLabel("Runde:"));
		p2.add(new JLabel("Runde:"));
		p2.add(new JLabel("Runde:"));
		p2.add(new JLabel("Runde:"));
		p2.add(new JLabel("Runde:"));
		
		
		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(1,10));
		for(int i=0;i<10;i++){
			JPanel arrP = new JPanel();
			arrP.setLayout(new GridLayout(5,1));
			teamsBoxes.add(new JCheckBox(Integer.toString(i+1)));
			orteBoxes.add(new JCheckBox(Integer.toString(i+1)));
			armeenBoxes.add(new JCheckBox(Integer.toString(i+1)));
			mirrorBoxes.add(new JCheckBox(Integer.toString(i+1)));
			tischBoxes.add(new JCheckBox(Integer.toString(i+1)));
			arrP.add(teamsBoxes.lastElement());
			arrP.add(orteBoxes.lastElement());
			arrP.add(armeenBoxes.lastElement());
			arrP.add(mirrorBoxes.lastElement());
			arrP.add(tischBoxes.lastElement());
			p3.add(arrP);
		}
		
		JPanel p9 = new JPanel();
		p9.setLayout(new BoxLayout(p9, BoxLayout.X_AXIS));
		p9.setBorder(BorderFactory.createTitledBorder("Paarungseinschränkungen"));
		
		p9.add(p1);
		p9.add(p2);
		p9.add(p3);
		
		JPanel p12 = new JPanel();
		p12.setLayout(new GridLayout(2,3));
		p12.setBorder(BorderFactory.createTitledBorder("Paarung in der ersten Runde"));
		p12.add(r1zufall);
		p12.add(new JLabel());
		p12.add(new JLabel());
		p12.add(r1ntr);
		p12.add(new JLabel());
		p12.add(new JLabel());
		r1Group.add(r1ntr);
		r1Group.add(r1zufall);
		
		JPanel p10 = new JPanel();
		p10.setLayout(new GridLayout(50,1));
		p10.add(new JLabel("a"));
		p10.getComponent(0).setForeground(p10.getBackground());
		
		paarungsPanel.add(p12);
		paarungsPanel.add(p9);
		paarungsPanel.add(p10);
		
		teamsBoxes.get(0).setSelected(true);
		tischBoxes.get(0).setSelected(true);
		
		//WertungsOptionen
		wertungsPanel.setLayout(new BoxLayout(wertungsPanel, BoxLayout.Y_AXIS));
		
		JPanel p6 = new JPanel();
		p6.setLayout(new GridLayout(4,1));
		
		JPanel p62 = new JPanel();
		p62.setLayout(new GridLayout(3,1));
		p62.setBorder(BorderFactory.createTitledBorder("Erstwertung"));
		addWertung(p62, pPunkte, erstWertung);
		addWertung(p62, pRPI, erstWertung);
		addWertung(p62, pStrength, erstWertung);
		pPunkte.setSelected(true);
		
		JPanel p63 = new JPanel();
		p63.setLayout(new GridLayout(6,1));
		p63.setBorder(BorderFactory.createTitledBorder("Zweitwertung"));
		addWertung(p63, sKeine, zweitWertung);
		addWertung(p63, sPunkte, zweitWertung);
		addWertung(p63, sRPI, zweitWertung);
		addWertung(p63, sStrength, zweitWertung);
		addWertung(p63, sSOS, zweitWertung);
		addWertung(p63, sSOOS, zweitWertung);
		sKeine.setSelected(true);
		
		JPanel p64 = new JPanel();
		p64.setLayout(new GridLayout(6,1));
		p64.setBorder(BorderFactory.createTitledBorder("Drittwertung"));
		addWertung(p64, tKeine, drittWertung);
		addWertung(p64, tPunkte, drittWertung);
		addWertung(p64, tRPI, drittWertung);
		addWertung(p64, tStrength, drittWertung);
		addWertung(p64, tSOS, drittWertung);
		addWertung(p64, tSOOS, drittWertung);
		tKeine.setSelected(true);
		
		JPanel p65 = new JPanel();
		p65.setLayout(new GridLayout(3,1));
		p65.add(matrixBenutzen);
		p65.add(matrix);
		matrix.addActionListener(this);
		matrixBenutzen.setEnabled(false);
		matrix.setEnabled(false);
		
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
		p65.add(p61);
		
		SUN2_1_0.setSelected(true);
		sunGroup.add(SUN2_1_0);
		sunGroup.add(SUN3_1_0);
		sunGroup.add(SUN20_10_1);
		sunGroup.add(SUN_frei);
		
		p6.add(p62);
		p6.add(p63);
		p6.add(p64);
		p6.add(p65);
		wertungsPanel.add(p6);
		
		JPanel p7 = new JPanel();
		JPanel p7pri = new JPanel();
		JPanel p7sek = new JPanel();
		JPanel p7ter = new JPanel();
		JPanel p7no = new JPanel();
		
		p7.setBorder(BorderFactory.createTitledBorder("Zusatzpunkte"));
		p7pri.setBorder(BorderFactory.createTitledBorder("Primär"));
		p7sek.setBorder(BorderFactory.createTitledBorder("Sekundär"));
		p7ter.setBorder(BorderFactory.createTitledBorder("Tertiär"));
		p7no.setBorder(BorderFactory.createTitledBorder("keine Wertung"));
		p7.setLayout(new GridLayout(1,4));
		p7pri.setLayout(new GridLayout(2,1));
		p7sek.setLayout(new GridLayout(2,1));
		p7ter.setLayout(new GridLayout(2,1));
		p7no.setLayout(new GridLayout(2,1));
		p7.add(p7pri);
		p7.add(p7sek);
		p7.add(p7ter);
		p7.add(p7no);
		
		p7pri.add(bemalPri);
		p7pri.add(armeePri);
		p7sek.add(bemalSek);
		p7sek.add(armeeSek);
		p7ter.add(bemalTer);
		p7ter.add(armeeTer);
		p7no.add(bemalNo);
		p7no.add(armeeNo);
		
		bemalGroup.add(bemalPri);
		bemalGroup.add(bemalSek);
		bemalGroup.add(bemalTer);
		bemalGroup.add(bemalNo);
		
		bemalPri.addActionListener(this);
		bemalSek.addActionListener(this);
		bemalTer.addActionListener(this);
		bemalNo.addActionListener(this);
		
		armeeGroup.add(armeePri);
		armeeGroup.add(armeeSek);
		armeeGroup.add(armeeTer);
		armeeGroup.add(armeeNo);
		
		bemalPri.addActionListener(this);
		armeeSek.addActionListener(this);
		armeeTer.addActionListener(this);
		armeeNo.addActionListener(this);
		
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
		Object src = arg0.getSource();
		
		if(src==pPunkte || src==pRPI || src==pStrength){
			hf.updatePanels();
		}
		else if(src==sPunkte || src==sRPI || src==sStrength || src==sSOS || src==sSOOS || src==sKeine){
			if(src==sPunkte){
				matrixBenutzen.setEnabled(true);
				matrix.setEnabled(true);
			} else{
				matrixBenutzen.setEnabled(false);
				matrix.setEnabled(false);
			}
			if(src==sKeine){
				System.out.println("sKeine");
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

				tKeine.doClick();
			} else {
				bemalSek.setEnabled(true);
				armeeSek.setEnabled(true);
			}
			tPunkte.setEnabled(src!=sKeine);
			tRPI.setEnabled(src!=sKeine);
			tStrength.setEnabled(src!=sKeine);
			tSOS.setEnabled(src!=sKeine);
			tSOOS.setEnabled(src!=sKeine);
			hf.updatePanels();
		}
		else if(src==tPunkte || src==tRPI || src==tStrength || src==tSOS || src==tSOOS || src==tKeine 
				|| src==bemalNo || src==bemalPri || src==bemalSek || src==bemalTer
				|| src==armeeNo || src==armeePri || src==armeeSek || src==armeeTer){
			hf.updatePanels();
		}
		else if(arg0.getSource()==matrix){
			hf.matrix.init();
		}
		
	}
	
	public void clear(){
		
		 einzel.setSelected(false);
		 team.setSelected(false);
		
		 schweizer.setSelected(false);
		 zufall.setSelected(false);
		 ko.setSelected(false);
		
		 for(int i=0;i<teamsBoxes.size();i++){
			 teamsBoxes.get(i).setSelected(false);
			 orteBoxes.get(i).setSelected(false);
			 armeenBoxes.get(i).setSelected(false);
			 mirrorBoxes.get(i).setSelected(false);
			 tischBoxes.get(i).setSelected(false);
		 }
		 
		 r1zufall.setSelected(false);
		 r1ntr.setSelected(false);
		
		 pPunkte.setSelected(false);
		 pRPI.setSelected(false);
		 pStrength.setSelected(false);
		 
		 sPunkte.setSelected(false);
		 sRPI.setSelected(false);
		 sStrength.setSelected(false);
		 sSOS.setSelected(false);
		 sSOOS.setSelected(false);
		 sKeine.setSelected(false);

		 tPunkte.setSelected(false);
		 tRPI.setSelected(false);
		 tStrength.setSelected(false);
		 tSOS.setSelected(false);
		 tSOOS.setSelected(false);
		 tKeine.setSelected(false);
		 
		 bemalPri.setSelected(false);
		 bemalSek.setSelected(false);
		 bemalNo.setSelected(false);
		
		 armeePri.setSelected(false);
		 armeeSek.setSelected(false);
		 armeeNo.setSelected(false);
		 
	}
	
	public void addWertung(JPanel p, JRadioButton b, ButtonGroup bg){
		p.add(b);
		bg.add(b);
		b.addActionListener(this);
	}
	
}
