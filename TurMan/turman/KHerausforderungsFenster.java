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
		fillCombo2("");
		combo1.addItemListener(this);
		combo2.addItemListener(this);
		p.add(new JLabel("Spieler 1"));
		p.add(new JLabel("Spieler 2"));
		p.add(combo1);
		p.add(combo2);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	public void fillCombo1(String s){
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			String vn = hf.teilnehmerVector.get(i).vornameAlter.equals("")?hf.teilnehmerVector.get(i).vorname:hf.teilnehmerVector.get(i).vornameAlter;
			String nn = hf.teilnehmerVector.get(i).nachnameAlter.equals("")?hf.teilnehmerVector.get(i).nachname:hf.teilnehmerVector.get(i).nachnameAlter;
			if(!s.equals(vn+" "+nn)){
				combo1.addItem(vn+" "+nn);
			}
		}
	}
	
	public void fillCombo2(String s){
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			String vn = hf.teilnehmerVector.get(i).vornameAlter.equals("")?hf.teilnehmerVector.get(i).vorname:hf.teilnehmerVector.get(i).vornameAlter;
			String nn = hf.teilnehmerVector.get(i).nachnameAlter.equals("")?hf.teilnehmerVector.get(i).nachname:hf.teilnehmerVector.get(i).nachnameAlter;
			if(!s.equals(vn+" "+nn)){
				combo2.addItem(vn+" "+nn);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			if(combo1.getSelectedIndex()==combo2.getSelectedIndex()){
				hf.dialog.getErrorDialog(hf.dialog.errorHerausforderung1);
			}else if(hf.herausforderungsVector.contains(hf.teilnehmerVector.get(combo1.getSelectedIndex())) || hf.herausforderungsVector.contains(hf.teilnehmerVector.get(combo2.getSelectedIndex()))){
				hf.dialog.getErrorDialog(hf.dialog.errorHerausforderung2);
			}else{
				
				KBegegnungen b1=((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex()));
				KBegegnungen b2=((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex()));
				if(hf.alleBegegnungenVector.contains(b1)){
					hf.herausforderungsVector.add(hf.teilnehmerVector.get(combo1.getSelectedIndex()));
					hf.herausforderungsVector.add(hf.teilnehmerVector.get(combo2.getSelectedIndex()));
					b1.setEnabled(true);
					b2.setEnabled(true);
					b1.setBackground(Color.orange);
					b2.setBackground(Color.orange);
					b1.setText(""+(hf.rundenZaehler+1));
					b2.setText(""+(hf.rundenZaehler+1));
					//b1.t2.paarungen.add(b1.xPos);
					//b1.t1.paarungen.add(b1.yPos);
					
					b1.runde=hf.rundenZaehler+1;
					((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex())).runde=hf.rundenZaehler+1;
					
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
			System.out.println(arg0.getItem());
			//TODO gewähltes Element in der anderen Box abwählen
		}
		/*if(arg0.getSource()==combo1){
			System.out.println(combo1.getSelectedItem());
		}else if(arg0.getSource()==combo2){
			System.out.println(combo2.getSelectedItem());
		}*/
	}

	
}
