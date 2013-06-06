package turman;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KHerausforderungsFenster extends JFrame implements ActionListener, ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KHerausforderungsFenster(KHauptFenster hf){
		super("Herausforderung erstellen");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JComboBox<String> combo1 = new JComboBox<String>();
	JComboBox<String> combo2 = new JComboBox<String>();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Herausfordern");
	
	public void init(){
		p.removeAll();
		setSize(400,100);
		p.setLayout(new GridLayout(3,2));
		combo1=new JComboBox<String>();
		fillCombo1("");
		combo2=new JComboBox<String>();
		fillCombo2(combo1.getSelectedItem().toString());
		combo1.addItemListener(this);
		p.add(new JLabel("Spieler 1"));
		p.add(new JLabel("Spieler 2"));
		p.add(combo1);
		p.add(combo2);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	public void fillCombo1(String s){
		if(!s.equals("")){
			String p1=s.split(" ")[2];
			p1=p1.split(":")[1];
			int p1ID=Integer.parseInt(p1);
		}
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			String vn = hf.teilnehmerVector.get(i).vornameAlter.equals("")?hf.teilnehmerVector.get(i).vorname:hf.teilnehmerVector.get(i).vornameAlter;
			String nn = hf.teilnehmerVector.get(i).nachnameAlter.equals("")?hf.teilnehmerVector.get(i).nachname:hf.teilnehmerVector.get(i).nachnameAlter;
			String ID = "SID:"+i;
			if(hf.herausforderungsVector.contains(hf.teilnehmerVector.get(i))){
				System.out.println(vn+" "+nn+" ist bereits in einer Herausforderung");
			}else if(!s.equals(vn+" "+nn+" "+ID)){
				combo1.addItem(vn+" "+nn+" "+ID);
			}
		}
	}
	
	public void fillCombo2(String s){
		int p1ID=-1;
		if(!s.equals("")){
			String p1=s.split(" ")[2];
			p1=p1.split(":")[1];
			p1ID=Integer.parseInt(p1);
		}
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			boolean ignore=false;
			String vn = hf.teilnehmerVector.get(i).vornameAlter.equals("")?hf.teilnehmerVector.get(i).vorname:hf.teilnehmerVector.get(i).vornameAlter;
			String nn = hf.teilnehmerVector.get(i).nachnameAlter.equals("")?hf.teilnehmerVector.get(i).nachname:hf.teilnehmerVector.get(i).nachnameAlter;
			String ID = "SID:"+i;
			if(p1ID!=-1){
				for(int j=0;j<hf.teilnehmerVector.get(p1ID).paarungen.size();j++){
					if(i==hf.teilnehmerVector.get(p1ID).paarungen.get(j)){
						ignore=true;
						break;
					}
				}
			}
			//Herausforderung bereits vorhanden:
			if(ignore){
				System.out.println("Gegen "+ vn+" "+nn+" wurde bereits gespielt");
			}else if(hf.herausforderungsVector.contains(hf.teilnehmerVector.get(i))){
				System.out.println(vn+" "+nn+" ist bereits in einer Herausforderung");
			}else if(!s.equals(vn+" "+nn+" "+ID)){
				combo2.addItem(vn+" "+nn+" "+ID);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			
			String p1=combo1.getSelectedItem().toString().split(" ")[2];
			p1=p1.split(":")[1];
			int p1ID=Integer.parseInt(p1);
			
			String p2=combo2.getSelectedItem().toString().split(" ")[2];
			p2=p2.split(":")[1];
			int p2ID=Integer.parseInt(p2);
			
			if(p1ID==p2ID){
				hf.dialog.getErrorDialog(hf.dialog.errorHerausforderung1);
			}else if(hf.herausforderungsVector.contains(hf.teilnehmerVector.get(p1ID)) || hf.herausforderungsVector.contains(hf.teilnehmerVector.get(p2ID))){
				hf.dialog.getErrorDialog(hf.dialog.errorHerausforderung2);
			}else{
				KBegegnungen b1=((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(p1ID)).getComponent(p2ID));
				KBegegnungen b2=((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(p2ID)).getComponent(p1ID));
				if(hf.alleBegegnungenVector.contains(b1)){
					hf.herausforderungsVector.add(hf.teilnehmerVector.get(p1ID));
					hf.herausforderungsVector.add(hf.teilnehmerVector.get(p2ID));
					b1.setEnabled(true);
					b2.setEnabled(true);
					b1.setBackground(Color.orange);
					b2.setBackground(Color.orange);
					b1.setText(""+(hf.rundenZaehler+1));
					b2.setText(""+(hf.rundenZaehler+1));
					//b1.t2.paarungen.add(b1.xPos);
					//b1.t1.paarungen.add(b1.yPos);
					
					b1.runde=hf.rundenZaehler+1;
					((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(p2ID)).getComponent(p1ID)).runde=hf.rundenZaehler+1;
					
					hf.begegnungsVector.add(b1);
					hf.alleBegegnungenVector.remove(b1);
					hf.alleBegegnungenVector.remove(b2);
					
					hf.repaint();
					hf.setVisible(true);
					setVisible(false);
				}else{
					hf.dialog.getErrorDialog(hf.dialog.errorHerausforderung3);
				}
			}
		}	
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(arg0.getStateChange()==1){
			System.out.println(arg0.getItem().toString());
			//TODO gewähltes Element in der anderen Box abwählen
			if(arg0.getSource()==combo1){
				((DefaultComboBoxModel<String>)combo2.getModel()).removeAllElements();
				fillCombo2(arg0.getItem().toString());
			}
		}
	}

	
}
