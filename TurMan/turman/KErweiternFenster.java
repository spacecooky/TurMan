package turman;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KErweiternFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4005838746171262L;

	public KErweiternFenster(KHauptFenster hf){
		this.hf=hf;
		add(p);
		cancel.addActionListener(this);
		ok.addActionListener(this);
	}
	
	KHauptFenster hf;
	JPanel p = new JPanel();
	JTextField vn = new JTextField();
	JTextField nn = new JTextField();
	JButton cancel= new JButton("Abbrechen");
	JButton ok= new JButton("Hinzufügen");
	
	public void init(){
		p.removeAll();
		setSize(500,100);
		p.setLayout(new GridLayout(3,2));
		p.add(new JLabel("Vorname"));
		p.add(new JLabel("Nachname"));
		p.add(vn);
		p.add(nn);
		p.add(cancel);
		p.add(ok);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==cancel){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			hf.teilnehmerVector.add(new KTeilnehmer(vn.getText(),nn.getText()));
			hf.HauptPanel.removeAll();
			hf.fillPanels();
			hf.fillTeamPanels();
			hf.updatePanels();
			hf.refillPanels();
			setVisible(false);
		}
		
	}
}
