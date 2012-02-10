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
		bm.addActionListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(Dimension d){
		Font f = new Font("Dialog", Font.BOLD, 16);
		hf.sortieren(ab.isSelected(),bm.isSelected());
		
		punktePanel.removeAll();
		setContentPane(new JScrollPane(punktePanel));
		punktePanel.setLayout(new BoxLayout(punktePanel,BoxLayout.X_AXIS));
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		
		JPanel platz = new JPanel();
		platz.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		platz.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/15,20000));
		punktePanel.add(platz);
		
		JPanel spieler = new JPanel();
		spieler.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(spieler);
		
		JPanel primär = new JPanel();
		primär.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(primär);
		
		JPanel sekundär = new JPanel();
		sekundär.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(sekundär);
		
		JPanel sos = new JPanel();
		sos.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		punktePanel.add(sos);
		
		
		platz.add(new JLabel("Platz"));
		platz.getComponent(0).setFont(f);
		((JLabel)platz.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		spieler.add(new JLabel("Spieler"));
		spieler.getComponent(0).setFont(f);
		((JLabel)spieler.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		primär.add(new JLabel("Primär(B)"));
		primär.getComponent(0).setFont(f);
		((JLabel)primär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		sekundär.add(new JLabel("Sekundär(AL)"));
		sekundär.getComponent(0).setFont(f);
		((JLabel)sekundär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		sos.add(new JLabel("SOS"));
		sos.getComponent(0).setFont(f);
		((JLabel)sos.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		int deleted=0;
		for(int i=hf.teilnehmerVector.size()-1;i>=0;i--){
			if(hf.sortierterVector.get(i).deleted==false){
				JLabel label6 = new JLabel(""+(hf.teilnehmerVector.size()-i-deleted));
				platz.add(label6);
				label6.setBorder(BorderFactory.createEtchedBorder());
				label6.setFont(f);
				
				JLabel label1 = new JLabel(hf.sortierterVector.get(i).vorname+" "+hf.sortierterVector.get(i).nachname);
				spieler.add(label1);
				label1.setBorder(BorderFactory.createEtchedBorder());
				label1.setFont(f);
				
				JLabel label2 = new JLabel(Integer.toString(hf.sortierterVector.get(i).primär)+(bm.isSelected()?"("+hf.sortierterVector.get(i).bemalwertung+")":"(0)"));
				primär.add(label2);
				label2.setBorder(BorderFactory.createEtchedBorder());
				label2.setFont(f);
				
				JLabel label3 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sekundär)+(ab.isSelected()?"("+hf.sortierterVector.get(i).armeeliste+")":"(0)"));
				sekundär.add(label3);
				label3.setBorder(BorderFactory.createEtchedBorder());
				label3.setFont(f);
				
				JLabel label4 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sos));
				sos.add(label4);
				label4.setBorder(BorderFactory.createEtchedBorder());
				label4.setFont(f);
			} else {
				deleted++;
			}
		}
		platz.add(new JLabel(""));
		spieler.add(new JLabel(""));
		primär.add(bm);
		sekundär.add(ab);
		sos.add(punkteSchliessenButton);
		setVisible(true);
	}
	
	JPanel punktePanel=new JPanel();
	JCheckBox ab = new JCheckBox("Armeeliste");
	JCheckBox bm = new JCheckBox("Bemalung");
	JButton punkteSchliessenButton=new JButton("OK");
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==punkteSchliessenButton){
			setVisible(false);
		} else if(e.getSource()==ab){
			init(getSize());
		} else if(e.getSource()==bm){
			init(getSize());
		}
	}
}