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
	
	public void init(){
		Font f = new Font("Dialog", Font.BOLD, 16);
		
		begegnungsPanel.removeAll();
		setContentPane(new JScrollPane(begegnungsPanel));
		begegnungsPanel.setLayout(new BoxLayout(begegnungsPanel,BoxLayout.X_AXIS));
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		JPanel tische = new JPanel();
		tische.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		tische.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/20,Toolkit.getDefaultToolkit().getScreenSize().height));
		begegnungsPanel.add(tische);
		
		JPanel begegnung = new JPanel();
		begegnung.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		begegnungsPanel.add(begegnung);
		
		JPanel primär = new JPanel();
		primär.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		begegnungsPanel.add(primär);
		
		JPanel sekundär = new JPanel();
		sekundär.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+2,1));
		begegnungsPanel.add(sekundär);
		
		tische.add(new JLabel("Tisch"));
		tische.getComponent(0).setFont(f);
		((JLabel)tische.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		begegnung.add(new JLabel("Begegnung"));
		begegnung.getComponent(0).setFont(f);
		((JLabel)begegnung.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		primär.add(new JLabel("Primär"));
		primär.getComponent(0).setFont(f);
		((JLabel)primär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		sekundär.add(new JLabel("Sekundär"));
		sekundär.getComponent(0).setFont(f);
		((JLabel)sekundär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		for(int i=0;i<hf.begegnungsVector.size();i++){
		//for(int i=(hf.rundenZaehler-1)*(hf.teilnehmerVector.size()/2);i<hf.begegnungsVector.size();i++){
			KBegegnungen bg = hf.begegnungsVector.get(i);
			if(bg.runde==hf.rundenZaehler){
				JLabel label6 = new JLabel(""+(bg.tisch+1));
				tische.add(label6);
				label6.setBorder(BorderFactory.createEtchedBorder());
				label6.setFont(f);
				
				KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
				KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
				
				// Durch begegnungsFensterButton ersetzen.
				// ((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(bg.yPos+1)).getComponent(bg.xPos+1)).
				JButton b1=((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(bg.xPos+1)).getComponent(bg.yPos+1)).begegnungsFensterButton;
				b1.setText(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname);
				begegnung.add(b1);
				b1.setBorder(BorderFactory.createEtchedBorder());
				b1.setFont(f);
				//JLabel label1 = new JLabel(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname);
				//begegnung.add(label1);
				//label1.setBorder(BorderFactory.createEtchedBorder());
				//label1.setFont(f);
				
				JLabel label2 = new JLabel(bg.p1+" : "+bg.p2);
				primär.add(label2);
				label2.setBorder(BorderFactory.createEtchedBorder());
				label2.setFont(f);
				
				JLabel label3 = new JLabel(bg.p12+" : "+bg.p22);
				sekundär.add(label3);
				label3.setBorder(BorderFactory.createEtchedBorder());
				label3.setFont(f);
			}
		}
		tische.add(new JLabel(""));
		begegnung.add(new JLabel(""));
		primär.add(new JLabel(""));
		sekundär.add(punkteSchliessenButton);
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
