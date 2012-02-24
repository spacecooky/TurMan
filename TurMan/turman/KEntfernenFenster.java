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

public class KEntfernenFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KEntfernenFenster(KHauptFenster hf){
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JComboBox combo = new JComboBox();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Löschen");
	
	public void init(){
		p.removeAll();
		setSize(400,100);
		p.setLayout(new GridLayout(2,2));
		combo=new JComboBox();
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			if(hf.teilnehmerVector.get(i).deleted==false){
				combo.addItem(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
			}
		}
		p.add(new JLabel("Spieler"));
		p.add(combo);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			if(hf.rundenZaehler==0){
				hf.teilnehmerVector.remove(combo.getSelectedIndex());
				hf.HauptPanel.removeAll();
				hf.fillPanels();
				hf.fillTeamPanels();
			} else {
				
				entfernen(combo.getSelectedIndex());
				
				
			}
			hf.updatePanels();
			setVisible(false);
		}
	}
	
	public void entfernen(int index){
		((JButton)((JPanel)hf.HauptPanel.getComponent(index+1)).getComponent(0)).setForeground(Color.gray);
		((JLabel)((JPanel)hf.HauptPanel.getComponent(0)).getComponent(index+1)).setForeground(Color.gray);
		((JButton)((JPanel)hf.HauptPanel.getComponent(index+1)).getComponent(0)).setEnabled(false);
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			if(((JPanel)hf.HauptPanel.getComponent(index+1)).getComponent(i+1) instanceof KBegegnungen){
				if(!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index+1)).getComponent(i+1)).getBackground().equals(Color.green) &&
						!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index+1)).getComponent(i+1)).getBackground().equals(Color.orange)){
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index+1)).getComponent(i+1)).setBackground(Color.black);
				}
			}
			if(((JPanel)hf.HauptPanel.getComponent(i+1)).getComponent(index+1) instanceof KBegegnungen){
				if(!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i+1)).getComponent(index+1)).getBackground().equals(Color.green) &&
						!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i+1)).getComponent(index+1)).getBackground().equals(Color.orange)){
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i+1)).getComponent(index+1)).setBackground(Color.black);
				}
			}
		}
		hf.teilnehmerVector.get(index).deleted=true;
		hf.gelöschteTeilnehmer++;
	}
	
}
