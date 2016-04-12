package turman;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
		super("Spieler entfernen");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
		rok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JComboBox combo = new JComboBox();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Löschen");
	JButton rok= new JButton("Wiederherstellen");
	
	public void init(){
		p.removeAll();
		setSize(400,100);
		p.setLayout(new GridLayout(2,2));
		combo=new JComboBox();
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			//if(hf.teilnehmerVector.get(i).deleted==false){
				combo.addItem(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
			//}
		}
		p.add(new JLabel("Spieler"));
		p.add(combo);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}
	
	public void initRestore(){
		p.removeAll();
		setSize(400,100);
		p.setLayout(new GridLayout(2,2));
		combo=new JComboBox();
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			if(hf.teilnehmerVector.get(i).deleted==true){
				combo.addItem(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
			} 
		}
		p.add(new JLabel("Spieler"));
		p.add(combo);
		p.add(cancel);
		p.add(rok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
//			if(hf.rundenZaehler==0){
//				hf.teilnehmerVector.remove(combo.getSelectedIndex());
//				hf.HauptPanel.removeAll();
//				hf.fillPanels();
//				hf.fillTeamPanels();
//			} else {
				entfernen(combo.getSelectedIndex());
//			}
			hf.updatePanels();
			setVisible(false);
		} else if(arg0.getSource()==rok){
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				if((hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname).equals(combo.getSelectedItem())){
					wiederherstellen(i);
				}
			}
			hf.updatePanels();
			setVisible(false);
		}
	}
	
	public void entfernen(int index){
		if(hf.optionenFeldVar.geloeschteVerstecken.isSelected()){
			((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setVisible(false);
			((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).setVisible(false);
			((JLabel)((JPanel)hf.sp.getColumnHeader().getComponent(0)).getComponent(index)).setVisible(false);
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				if(((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index) instanceof KBegegnungen){
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).setVisible(false);
				}
			}
		}else{
			((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setForeground(Color.gray);
			((JLabel)((JPanel)hf.sp.getColumnHeader().getComponent(0)).getComponent(index)).setForeground(Color.gray);
			((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setEnabled(false);
			((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setVisible(true);
			((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).setVisible(true);
			((JLabel)((JPanel)hf.sp.getColumnHeader().getComponent(0)).getComponent(index)).setVisible(true);
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				if(((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i) instanceof KBegegnungen){
					if(!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).getBackground().equals(Color.green) &&
							!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).getBackground().equals(Color.orange)){
						((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).setBackground(Color.black);
					}
				}
				if(((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index) instanceof KBegegnungen){
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).setVisible(true);
					if(!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).getBackground().equals(Color.green) &&
							!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).getBackground().equals(Color.orange)){
						((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).setBackground(Color.black);
					}
				}
			}
		}
		hf.teilnehmerVector.get(index).deleted=true;
		hf.gelöschteTeilnehmer++;
	}
	
	public void wiederherstellen(int index){
		((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setForeground(Color.black);
		((JLabel)((JPanel)hf.sp.getColumnHeader().getComponent(0)).getComponent(index)).setForeground(Color.black);
		((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setEnabled(true);
		((JButton)((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).nameLabel).setVisible(true);
		((KTeilnehmerPanel)hf.HauptPanel.getComponent(index)).setVisible(true);
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			if(((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i) instanceof KBegegnungen){
				if(!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).getBackground().equals(Color.green) &&
						!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).getBackground().equals(Color.orange) &&
						!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).t2.deleted){
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).setBackground(Color.darkGray);
				}
			}
			if(((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index) instanceof KBegegnungen){
				((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).setVisible(true);
				if(!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).getBackground().equals(Color.green) &&
						!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).getBackground().equals(Color.orange) &&
						!((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(index)).getComponent(i)).t2.deleted){
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(i)).getComponent(index)).setBackground(Color.darkGray);
				}
			}
		}
		hf.teilnehmerVector.get(index).deleted=false;
		hf.gelöschteTeilnehmer--;
	}
	
}
