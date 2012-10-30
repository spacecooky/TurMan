package turman;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KSiegpunkteMatrix extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KSiegpunkteMatrix(KHauptFenster hf){
		super("Siegpunkte-Matrix");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
		anpassen.addActionListener(this);
		
		felder=11;
		max=felder-1*100;
		felderFeld.setText(""+felder);
		maxFeld.setText(""+max);
		for(int i=0;i<felder;i++){
			spv.add(""+(i*100));
			tpa.add(""+((felder-1+i)));
			tpb.add(""+((felder-1-i)));
		}
		
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	int felder;
	int max;
	JTextField felderFeld = new JTextField();
	JTextField maxFeld = new JTextField();
	JButton anpassen = new JButton("Anpassen");
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Speichern");
	Vector<String> spv = new Vector<String>();
	Vector<String> tpa = new Vector<String>();
	Vector<String> tpb = new Vector<String>();
	
	public void init(){
		p.removeAll();
		setSize(400, 60+(felder)*18);
		
		p.setLayout(new GridLayout(felder+3,3));
		
		p.add(new JLabel("Felder"));
		p.add(felderFeld);
		p.add(anpassen);
		
		p.add(new JLabel("Min. Siegpunktediff."));
		p.add(new JLabel("Turnierpunkte A"));
		p.add(new JLabel("Turnierpunkte B"));
		
		for(int i=0;i<felder;i++){
			p.add(new JTextField());
			p.add(new JTextField());
			p.add(new JTextField());	
		}
		
		for(int i=0;i<spv.size();i++){
			((JTextField)p.getComponent(6+i*3)).setText(spv.get(i));
			((JTextField)p.getComponent(7+i*3)).setText(tpa.get(i));
			((JTextField)p.getComponent(8+i*3)).setText(tpb.get(i));
		}
		
		p.add(new JLabel(""));
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			spv.clear();
			tpa.clear();
			tpb.clear();
			for(int i=0;i<felder;i++){
				spv.add(((JTextField)p.getComponent(6+i*3)).getText());
				tpa.add(((JTextField)p.getComponent(7+i*3)).getText());
				tpb.add(((JTextField)p.getComponent(8+i*3)).getText());
			}
			setVisible(false);
		} else if(arg0.getSource()==anpassen){
			try{
				felder=Integer.parseInt(felderFeld.getText());
			}catch(NumberFormatException e){
				
			}
			init();
		}
		
	}
	
	public int getTP(int diff){
		try{
			if(diff<0){
				diff*=-1;
				for(int i=spv.size()-1;i>=0;i--){
					if(diff>Integer.parseInt(spv.get(i))){
						return Integer.parseInt(tpb.get(i));
					}
				}
			}else{
				for(int i=spv.size()-1;i>=0;i--){
					if(diff>Integer.parseInt(spv.get(i))){
						return Integer.parseInt(tpa.get(i));
					}
				}
			}
		}catch(NumberFormatException e){
			
		}
		return 0;
	}
}
