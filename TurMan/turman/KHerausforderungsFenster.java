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

public class KHerausforderungsFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KHerausforderungsFenster(KHauptFenster hf){
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JComboBox combo1 = new JComboBox();
	JComboBox combo2 = new JComboBox();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Herausfordern");
	
	public void init(){
		p.removeAll();
		setSize(400,100);
		p.setLayout(new GridLayout(3,2));
		combo1=new JComboBox();
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			combo1.addItem(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
		}
		combo2=new JComboBox();
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			combo2.addItem(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
		}
		p.add(new JLabel("Spieler 1"));
		p.add(new JLabel("Spieler 2"));
		p.add(combo1);
		p.add(combo2);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			hf.herausforderungsVector.add(hf.teilnehmerVector.get(combo1.getSelectedIndex()));
			hf.herausforderungsVector.add(hf.teilnehmerVector.get(combo2.getSelectedIndex()));
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex())).setEnabled(true);
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex())).setEnabled(true);
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex())).setBackground(Color.orange);
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex())).setBackground(Color.orange);
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex())).setText(""+(hf.rundenZaehler+1));
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex())).setText(""+(hf.rundenZaehler+1));
			
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex())).runde=hf.rundenZaehler+1;
			((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex())).runde=hf.rundenZaehler+1;
			
			hf.begegnungsVector.add(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex())));
			hf.alleBegegnungenVector.remove(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo1.getSelectedIndex())).getComponent(combo2.getSelectedIndex())));
			hf.alleBegegnungenVector.remove(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(combo2.getSelectedIndex())).getComponent(combo1.getSelectedIndex())));
			
			hf.repaint();
			hf.setVisible(true);
			setVisible(false);
		}
		
	}
}
