package turman;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class KPunkteFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KPunkteFenster(KHauptFenster hf) {
		this.hf=hf;
		punkteSchliessenButton.addActionListener(this);
		ab.addActionListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(){
		
		hf.sortieren(ab.isSelected());
		
		punktePanel.removeAll();
		setContentPane(new JScrollPane(punktePanel));
		punktePanel.setLayout(new BoxLayout(punktePanel,BoxLayout.X_AXIS));
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		JPanel platz = new JPanel();
		platz.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		platz.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/20,Toolkit.getDefaultToolkit().getScreenSize().height));
		punktePanel.add(platz);
		
		JPanel spieler = new JPanel();
		spieler.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(spieler);
		
		JPanel prim�r = new JPanel();
		prim�r.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(prim�r);
		
		JPanel sekund�r = new JPanel();
		sekund�r.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(sekund�r);
		
		JPanel sos = new JPanel();
		sos.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(sos);
		
		
		platz.add(new JLabel("Platz"));
		spieler.add(new JLabel("Spieler"));
		prim�r.add(new JLabel("Prim�r(AB)"));
		sekund�r.add(new JLabel("Sekund�r"));
		sos.add(new JLabel("SOS"));
		
		for(int i=hf.teilnehmerVector.size()-1;i>=0;i--){
			
			JLabel label6 = new JLabel(""+(hf.teilnehmerVector.size()-i));
			platz.add(label6);
			label6.setBorder(BorderFactory.createEtchedBorder());
			
			JLabel label1 = new JLabel(hf.sortierterVector.get(i).vorname+" "+hf.sortierterVector.get(i).nachname);
			spieler.add(label1);
			label1.setBorder(BorderFactory.createEtchedBorder());
			
			JLabel label2 = new JLabel(Integer.toString(hf.sortierterVector.get(i).prim�r)+(ab.isSelected()?"("+hf.sortierterVector.get(i).armeeliste+")":"(0)"));
			prim�r.add(label2);
			label2.setBorder(BorderFactory.createEtchedBorder());
			
			JLabel label3 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sekund�r));
			sekund�r.add(label3);
			label3.setBorder(BorderFactory.createEtchedBorder());
			
			JLabel label4 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sos));
			sos.add(label4);
			label4.setBorder(BorderFactory.createEtchedBorder());
			
		}
		platz.add(new JLabel(""));
		spieler.add(new JLabel(""));
		prim�r.add(ab);
		sekund�r.add(punkteSchliessenButton);
		sos.add(new JLabel(""));
		setVisible(true);
		
	}
	
	JPanel punktePanel=new JPanel();
	JCheckBox ab = new JCheckBox("AB/�berweisung");
	JButton punkteSchliessenButton=new JButton("OK");
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==punkteSchliessenButton){
			setVisible(false);
		} else if(e.getSource()==ab){
			init();
		}
		
	}
	
	
}
