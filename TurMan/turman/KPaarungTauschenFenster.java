package turman;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;


public class KPaarungTauschenFenster extends JFrame implements ActionListener, ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KPaarungTauschenFenster(KHauptFenster hf){
		super("Herausforderung erstellen");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JLabel altLabel = new JLabel("Alte Paarungen",SwingConstants.CENTER);
	JLabel neuLabel =new JLabel("Neue Paarungen",SwingConstants.CENTER);
	JLabel fehlerLabel =new JLabel("Fehler",SwingConstants.CENTER);
	JComboBox<String> comboPaarung1 = new JComboBox<String>();
	JComboBox<String> comboPaarung2 = new JComboBox<String>();
	JComboBox<String> comboPaarungNeu = new JComboBox<String>();
	JLabel labelPaarungNeu2 = new JLabel();
	JTextArea errorPaarungNeu1 = new JTextArea();
	JTextArea errorPaarungNeu2 = new JTextArea();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Tauschen");
	
	
	KBegegnungen bg1;
	KBegegnungen bg2;
	KBegegnungen bg1Neu;
	KBegegnungen bg2Neu;
	KTeilnehmer tn1;
	KTeilnehmer tn2;
	KTeilnehmer tn3;
	KTeilnehmer tn4;
	
	
	public void init(){
		Font f = new Font("Dialog", Font.BOLD, 16);
		altLabel.setFont(f);
		neuLabel.setFont(f);
		fehlerLabel.setFont(f);
		altLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		neuLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		fehlerLabel.setBorder(BorderFactory.createRaisedBevelBorder());
		errorPaarungNeu2.setBorder(BorderFactory.createEtchedBorder());
		errorPaarungNeu1.setBorder(BorderFactory.createEtchedBorder());
		labelPaarungNeu2.setBorder(BorderFactory.createEtchedBorder());
		p.removeAll();
		setSize(800,300);
		p.setLayout(new GridLayout(4,3,2,2));
		p.setBackground( Color.WHITE );
        p.setBorder( new MatteBorder(2, 2, 2, 2, Color.BLACK) );
		
		comboPaarung1=new JComboBox<String>();
		fillCombo1("");
		comboPaarung2=new JComboBox<String>();
		fillCombo2(comboPaarung1.getSelectedItem().toString());
		fillComboNeu();
		fillLabel();
		comboPaarung1.addItemListener(this);
		comboPaarung2.addItemListener(this);
		comboPaarungNeu.addItemListener(this);
		errorPaarungNeu1.setFocusable(false);
		errorPaarungNeu2.setFocusable(false);
		
		p.add(altLabel);
		p.add(neuLabel);
		p.add(fehlerLabel);
		
		p.add(comboPaarung1);
		p.add(comboPaarungNeu);
		p.add(errorPaarungNeu1);
		
		p.add(comboPaarung2);
		p.add(labelPaarungNeu2);
		p.add(errorPaarungNeu2);
		
		p.add(new JLabel());
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	public void fillCombo1(String s){
		
		for(int i=0;i<hf.begegnungsVector.size();i++){
			KBegegnungen bg = hf.begegnungsVector.get(i);
			if(bg.runde==hf.rundenAnzeige){
				
				KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
				KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
				
				
				String vn1 = tn1.vornameAlter.equals("")?tn1.vorname:tn1.vornameAlter;
				String nn1 = tn1.nachnameAlter.equals("")?tn1.nachname:tn1.nachnameAlter;
				String vn2 = tn2.vornameAlter.equals("")?tn2.vorname:tn2.vornameAlter;
				String nn2 = tn2.nachnameAlter.equals("")?tn2.nachname:tn2.nachnameAlter;
				String BID = "BID:"+i;
				
				if(!s.equals(vn1+" "+nn1 +" : "+vn2+" "+nn2+" "+BID)){
					comboPaarung1.addItem(vn1+" "+nn1 +" : "+vn2+" "+nn2+" "+BID);
				}
			}
		}
	}
	
	public void fillCombo2(String s){
		for(int i=0;i<hf.begegnungsVector.size();i++){
			KBegegnungen bg = hf.begegnungsVector.get(i);
			if(bg.runde==hf.rundenAnzeige){
				
				KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
				KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
				
				
				String vn1 = tn1.vornameAlter.equals("")?tn1.vorname:tn1.vornameAlter;
				String nn1 = tn1.nachnameAlter.equals("")?tn1.nachname:tn1.nachnameAlter;
				String vn2 = tn2.vornameAlter.equals("")?tn2.vorname:tn2.vornameAlter;
				String nn2 = tn2.nachnameAlter.equals("")?tn2.nachname:tn2.nachnameAlter;
				String BID = "BID:"+i;
				
				if(!s.equals(vn1+" "+nn1 +" : "+vn2+" "+nn2+" "+BID)){
					comboPaarung2.addItem(vn1+" "+nn1 +" : "+vn2+" "+nn2+" "+BID);
				}
			}
		}
	}
	
	public void fillComboNeu(){
		((DefaultComboBoxModel<String>)comboPaarungNeu.getModel()).removeAllElements();
		
		String b1=((String)comboPaarung1.getSelectedItem()).split(":")[2];
		int b1ID=Integer.parseInt(b1);
		bg1 = hf.begegnungsVector.get(b1ID);
		
		String b2=((String)comboPaarung2.getSelectedItem()).split(":")[2];
		int b2ID=Integer.parseInt(b2);
		bg2 = hf.begegnungsVector.get(b2ID);
		
		
		tn1 = hf.teilnehmerVector.get(bg1.xPos);
		tn2 = hf.teilnehmerVector.get(bg1.yPos);
		tn3 = hf.teilnehmerVector.get(bg2.xPos);
		tn4 = hf.teilnehmerVector.get(bg2.yPos);
		String vn1 = tn1.vornameAlter.equals("")?tn1.vorname:tn1.vornameAlter;
		String nn1 = tn1.nachnameAlter.equals("")?tn1.nachname:tn1.nachnameAlter;
		String vn2 = tn2.vornameAlter.equals("")?tn2.vorname:tn2.vornameAlter;
		String nn2 = tn2.nachnameAlter.equals("")?tn2.nachname:tn2.nachnameAlter;
		String vn3 = tn3.vornameAlter.equals("")?tn3.vorname:tn3.vornameAlter;
		String nn3 = tn3.nachnameAlter.equals("")?tn3.nachname:tn3.nachnameAlter;
		String vn4 = tn4.vornameAlter.equals("")?tn4.vorname:tn4.vornameAlter;
		String nn4 = tn4.nachnameAlter.equals("")?tn4.nachname:tn4.nachnameAlter;
		
		comboPaarungNeu.addItem(vn1+" "+nn1 +" : "+vn3+" "+nn3);
		comboPaarungNeu.addItem(vn1+" "+nn1 +" : "+vn4+" "+nn4);
		comboPaarungNeu.addItem(vn2+" "+nn2 +" : "+vn3+" "+nn3);
		comboPaarungNeu.addItem(vn2+" "+nn2 +" : "+vn4+" "+nn4);	
		
	}
	
	public void fillLabel(){		
		String vn1 = tn1.vornameAlter.equals("")?tn1.vorname:tn1.vornameAlter;
		String nn1 = tn1.nachnameAlter.equals("")?tn1.nachname:tn1.nachnameAlter;
		String vn2 = tn2.vornameAlter.equals("")?tn2.vorname:tn2.vornameAlter;
		String nn2 = tn2.nachnameAlter.equals("")?tn2.nachname:tn2.nachnameAlter;
		String vn3 = tn3.vornameAlter.equals("")?tn3.vorname:tn3.vornameAlter;
		String nn3 = tn3.nachnameAlter.equals("")?tn3.nachname:tn3.nachnameAlter;
		String vn4 = tn4.vornameAlter.equals("")?tn4.vorname:tn4.vornameAlter;
		String nn4 = tn4.nachnameAlter.equals("")?tn4.nachname:tn4.nachnameAlter;
		
		switch(comboPaarungNeu.getSelectedIndex()){
		case 0:
			labelPaarungNeu2.setText(vn2+" "+nn2 +" : "+vn4+" "+nn4);
			bg1Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn1))).getComponent(hf.teilnehmerVector.indexOf(tn3)));
			bg2Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn2))).getComponent(hf.teilnehmerVector.indexOf(tn4)));
			break;
		case 1:
			labelPaarungNeu2.setText(vn2+" "+nn2 +" : "+vn3+" "+nn3);
			bg1Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn1))).getComponent(hf.teilnehmerVector.indexOf(tn4)));
			bg2Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn2))).getComponent(hf.teilnehmerVector.indexOf(tn3)));
			break;
		case 2:
			labelPaarungNeu2.setText(vn1+" "+nn1 +" : "+vn4+" "+nn4);
			bg1Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn2))).getComponent(hf.teilnehmerVector.indexOf(tn3)));
			bg2Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn1))).getComponent(hf.teilnehmerVector.indexOf(tn4)));
			break;
		case 3:
			labelPaarungNeu2.setText(vn1+" "+nn1 +" : "+vn3+" "+nn3);
			bg1Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn2))).getComponent(hf.teilnehmerVector.indexOf(tn4)));
			bg2Neu = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tn1))).getComponent(hf.teilnehmerVector.indexOf(tn3)));
			break;
		}
		
		errorPaarungNeu1.setText("");
		errorPaarungNeu1.setBackground(new Color(255, 255, 255));
		ok.setEnabled(true);
		//Anzeigen  unerlaubten Paarungen
		if(hf.begegnungsVector.contains(bg1Neu)){
			errorPaarungNeu1.setBackground(new Color(255, 0, 0));
			errorPaarungNeu1.append("Paarung existiert bereits\r\n");
			ok.setEnabled(false);
		}
		if(hf.optionenFeldVar.armeen.isSelected() && bg1Neu.armee()){
			errorPaarungNeu1.append("Erneutes Spiel gegen die gleiche Armee\r\n");
		}
		if(hf.optionenFeldVar.mirror.isSelected() && bg1Neu.mirror()){
			errorPaarungNeu1.append("Mirrormatch\r\n");
		}
		if(hf.optionenFeldVar.orte.isSelected() && bg1Neu.ort()){
			errorPaarungNeu1.append("Spiel gegen Person aus dem gleichen Ort\r\n");
		}
		if(hf.optionenFeldVar.teams.isSelected() && bg1Neu.team()){
			errorPaarungNeu1.append("Teamschutz\r\n");
		}
		
		errorPaarungNeu2.setText("");
		errorPaarungNeu2.setBackground(new Color(255, 255, 255));
		//Anzeigen  unerlaubten Paarungen
		if(hf.begegnungsVector.contains(bg2Neu)){
			errorPaarungNeu2.setBackground(new Color(255, 0, 0));
			errorPaarungNeu2.append("Paarung existiert bereits\r\n");
			ok.setEnabled(false);
		}
		if(hf.optionenFeldVar.armeen.isSelected() && bg2Neu.armee()){
			errorPaarungNeu2.append("Erneutes Spiel gegen die gleiche Armee\r\n");
		}
		if(hf.optionenFeldVar.mirror.isSelected() && bg2Neu.mirror()){
			errorPaarungNeu2.append("Mirrormatch\r\n");
		}
		if(hf.optionenFeldVar.orte.isSelected() && bg2Neu.ort()){
			errorPaarungNeu2.append("Spiel gegen Person aus dem gleichen Ort\r\n");
		}
		if(hf.optionenFeldVar.teams.isSelected() && bg2Neu.team()){
			errorPaarungNeu2.append("Teamschutz\r\n");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			//Identische Daten übertragen
			bg1Neu.tisch=bg1.tisch;
			bg2Neu.tisch=bg2.tisch;
			//Alte Paarungen entfernen
			begegnungenEntfernen(tn1);
			begegnungenEntfernen(tn2);
			begegnungenEntfernen(tn3);
			begegnungenEntfernen(tn4);
			//Neue Paarungen erstellen
			//////////////////////////////////////////////////////////////////////////////////////////////////
			begegnungErstellen(bg1Neu);
			begegnungErstellen(bg2Neu);
			hf.updatePanels();
			setVisible(false);
		}	
	}

public void begegnungenEntfernen(KTeilnehmer t){	
	int tnNmbr=hf.teilnehmerVector.indexOf(t);
	for(int j=1;j<=hf.rundenZaehler;j++){
		if(t.paarungen.get(j)!=null){
			int ggNmbr=t.paarungen.get(j);
			KBegegnungen b = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(tnNmbr)).getComponent(ggNmbr));
			if(b.runde==hf.rundenZaehler){
				hf.alleBegegnungenVector.add(b);
				if(hf.begegnungsVector.contains(b)){
					hf.begegnungsVector.remove(b);
				}
				b.setEnabled(false);
				b.setUnpairedColor();
				b.runde=0;
				b.tisch=0;
				b.setText("");
				b.p1pri=0;
				b.p1sek=0;
				b.p2pri=0;
				b.p2sek=0;
				t.tische.remove(j);
				t.paarungen.remove(j);
			}
		}
	}	
}

public void begegnungErstellen(KBegegnungen b){
	b.setEnabled(true);
	b.setBackground(Color.orange);
	b.runde=hf.rundenZaehler;
	b.setText(""+hf.rundenZaehler);
	hf.begegnungsVector.add(b);
	b.t2.paarungen.put(hf.rundenZaehler,b.xPos);
	b.t1.paarungen.put(hf.rundenZaehler,b.yPos);
	for(int j=0;j<hf.alleBegegnungenVector.size();j++){
		KBegegnungen b2 = hf.alleBegegnungenVector.get(j);
		if(b.xPos==b2.yPos && b.yPos==b2.xPos){
			b2.setEnabled(true);
			b2.setBackground(Color.orange);
			b2.runde=hf.rundenZaehler;
			b2.setText(""+hf.rundenZaehler);
			hf.alleBegegnungenVector.remove(b2);
			if(hf.begegnungsVector.contains(b2)){
				hf.begegnungsVector.remove(b2);
			}
			j--;
		}
	}
	hf.alleBegegnungenVector.remove(b);
}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		if(arg0.getStateChange()==1){
			if(arg0.getSource()==comboPaarung1){
				((DefaultComboBoxModel<String>)comboPaarung2.getModel()).removeAllElements();
				fillCombo2(arg0.getItem().toString());
				fillComboNeu();
				fillLabel();
			} else if(arg0.getSource()==comboPaarung2){
				fillComboNeu();
				fillLabel();
			} else if(arg0.getSource()==comboPaarungNeu){
				fillLabel();
			}
		}
	}

	
}
