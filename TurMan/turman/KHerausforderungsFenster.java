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

public class KHerausforderungsFenster extends JFrame implements ActionListener{

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
			String vn = hf.teilnehmerVector.get(i).vornameAlter.equals("")?hf.teilnehmerVector.get(i).vorname:hf.teilnehmerVector.get(i).vornameAlter;
			String nn = hf.teilnehmerVector.get(i).nachnameAlter.equals("")?hf.teilnehmerVector.get(i).nachname:hf.teilnehmerVector.get(i).nachnameAlter;
			combo1.addItem(vn+" "+nn);
		}
		combo2=new JComboBox();
		for(int i=0;i<hf.teilnehmerVector.size();i++){
			String vn = hf.teilnehmerVector.get(i).vornameAlter.equals("")?hf.teilnehmerVector.get(i).vorname:hf.teilnehmerVector.get(i).vornameAlter;
			String nn = hf.teilnehmerVector.get(i).nachnameAlter.equals("")?hf.teilnehmerVector.get(i).nachname:hf.teilnehmerVector.get(i).nachnameAlter;
			combo2.addItem(vn+" "+nn);
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
}
