package turman;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class KBegegnungsFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KBegegnungsFenster(KHauptFenster hf) {
		this.hf=hf;
		punkteSchliessenButton.addActionListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(Dimension d){
		Font f = new Font("Dialog", Font.BOLD, 16);
		
		begegnungsPanel.removeAll();
		setContentPane(new JScrollPane(begegnungsPanel));
		begegnungsPanel.setLayout(new BoxLayout(begegnungsPanel,BoxLayout.X_AXIS));
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		
		JPanel tische = new JPanel();
		tische.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		tische.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/20,Toolkit.getDefaultToolkit().getScreenSize().height));
		begegnungsPanel.add(tische);
		
		JPanel begegnung = new JPanel();
		begegnung.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		begegnungsPanel.add(begegnung);
		
		JPanel prim�r = new JPanel();
		prim�r.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		begegnungsPanel.add(prim�r);
		
		JPanel sekund�r = new JPanel();
		sekund�r.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		begegnungsPanel.add(sekund�r);
		
		tische.add(new JLabel("Tisch"));
		tische.getComponent(0).setFont(f);
		((JLabel)tische.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		begegnung.add(new JLabel("Begegnung"));
		begegnung.getComponent(0).setFont(f);
		((JLabel)begegnung.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(hf.optionenFeld.PSS.isSelected()){	
			prim�r.add(new JLabel("Prim�r"));
		} else if(hf.optionenFeld.TS.isSelected()){	
			prim�r.add(new JLabel("Turnierpunkte"));
		}
		prim�r.getComponent(0).setFont(f);
		((JLabel)prim�r.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(hf.optionenFeld.PSS.isSelected()){
			sekund�r.add(new JLabel("Sekund�r"));
		} else if(hf.optionenFeld.TS.isSelected()){
			sekund�r.add(new JLabel("Siegespunkte"));
		}
		sekund�r.getComponent(0).setFont(f);
		((JLabel)sekund�r.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		for(int i=0;i<hf.begegnungsVector.size();i++){
			KBegegnungen bg = hf.begegnungsVector.get(i);
			if(bg.runde==hf.rundenZaehler){
				JLabel label6 = new JLabel(""+(bg.tisch+1));
				tische.add(label6);
				label6.setBorder(BorderFactory.createEtchedBorder());
				label6.setFont(f);
				
				KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
				KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
				
				// Durch begegnungsFensterButton ersetzen.
				JButton b1=((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(bg.xPos+1)).getComponent(bg.yPos+1)).begegnungsFensterButton;
				b1.setText(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname);
				begegnung.add(b1);
				b1.setBorder(BorderFactory.createEtchedBorder());
				b1.setFont(f);
				
				JLabel label2 = new JLabel(bg.p1+" : "+bg.p2);
				prim�r.add(label2);
				label2.setBorder(BorderFactory.createEtchedBorder());
				label2.setFont(f);
				
				JLabel label3 = new JLabel(bg.p12+" : "+bg.p22);
				sekund�r.add(label3);
				label3.setBorder(BorderFactory.createEtchedBorder());
				label3.setFont(f);
			}
		}
		tische.add(new JLabel(""));
		begegnung.add(new JLabel(""));
		prim�r.add(new JLabel(""));
		sekund�r.add(punkteSchliessenButton);
		setVisible(true);
		
	}
	
	JPanel begegnungsPanel=new JPanel();
	JButton punkteSchliessenButton=new JButton("OK");
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==punkteSchliessenButton){
			setVisible(false);
		} 
	}
}
