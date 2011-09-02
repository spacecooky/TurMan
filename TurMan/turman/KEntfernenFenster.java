package turman;

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
			combo.addItem(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
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
			hf.teilnehmerVector.remove(combo.getSelectedIndex());
			hf.HauptPanel.removeAll();
			hf.fillPanels();
			hf.repaint();
			hf.setVisible(true);
			setVisible(false);
			
		}
		
	}
}
