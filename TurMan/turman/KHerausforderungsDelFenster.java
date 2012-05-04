package turman;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class KHerausforderungsDelFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KHerausforderungsDelFenster(KHauptFenster hf){
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JComboBox combo1 = new JComboBox();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Herausforderung entfernen");
	
	public void init(){
		p.removeAll();
		setSize(600,80);
		p.setLayout(new GridLayout(2,2));
		combo1=new JComboBox();
		for(int i=0;i<hf.herausforderungsVector.size();i+=2){
			combo1.addItem(hf.herausforderungsVector.get(i).vorname+" "+hf.herausforderungsVector.get(i).nachname+" : "+hf.herausforderungsVector.get(i+1).vorname+" "+hf.herausforderungsVector.get(i+1).nachname);
		}
		
		p.add(new JLabel("Herausforderung"));
		p.add(combo1);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			int index =combo1.getSelectedIndex();
			KTeilnehmer t1 = hf.herausforderungsVector.get(index);
			KTeilnehmer t2 = hf.herausforderungsVector.get(index+1);
			hf.herausforderungsVector.remove(t1);
			hf.herausforderungsVector.remove(t2);
			KBegegnungen b1=((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(t1))).getComponent(hf.teilnehmerVector.indexOf(t2)));
			KBegegnungen b2=((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(t2))).getComponent(hf.teilnehmerVector.indexOf(t1)));
			b1.setEnabled(false);
			b2.setEnabled(false);
			b1.setUnpairedColor();
			b2.setUnpairedColor();
			b1.setText("");
			b2.setText("");
			for(int i=0;i<t1.paarungen.size();i++){
				if(t1.paarungen.get(i)==hf.teilnehmerVector.indexOf(t2)){
					t1.paarungen.remove(i);
					break;
				}
			}
			for(int i=0;i<t2.paarungen.size();i++){
				if(t2.paarungen.get(i)==hf.teilnehmerVector.indexOf(t1)){
					t2.paarungen.remove(i);
					break;
				}
			}
			
			b1.runde=0;
			b2.runde=0;
			b1.tisch=0;
			b2.tisch=0;
			b1.p1=0;
			b1.p12=0;
			b1.p2=0;
			b1.p22=0;
			b2.p1=0;
			b2.p12=0;
			b2.p2=0;
			b2.p22=0;
			
			hf.begegnungsVector.remove(b1);
			hf.begegnungsVector.remove(b2);
			hf.alleBegegnungenVector.add(b1);
			hf.alleBegegnungenVector.add(b2);
			
			hf.repaint();
			hf.setVisible(true);
			setVisible(false);
		}
		
	}
}
