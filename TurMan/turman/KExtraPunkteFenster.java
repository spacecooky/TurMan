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
import javax.swing.JTextField;

public class KExtraPunkteFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KExtraPunkteFenster(KHauptFenster hf) {
		this.hf=hf;
		SchliessenButton.addActionListener(this);
		AbbrechenButton.addActionListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(Dimension d){
		Font f = new Font("Dialog", Font.BOLD, 16);
		
		hauptPanel.removeAll();
		spieler.removeAll();
		ab.removeAll();
		bm.removeAll();
		setContentPane(new JScrollPane(hauptPanel));
		hauptPanel.setLayout(new BoxLayout(hauptPanel,BoxLayout.X_AXIS));
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		
		spieler.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		hauptPanel.add(spieler);
		
		bm.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		hauptPanel.add(bm);
		
		ab.setLayout(new GridLayout(hf.teilnehmerVector.size()+2,1));
		hauptPanel.add(ab);
		
		spieler.add(new JLabel("Spieler"));
		spieler.getComponent(0).setFont(f);
		((JLabel)spieler.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		bm.add(new JLabel("Bemalpunkte"));
		bm.getComponent(0).setFont(f);
		((JLabel)bm.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		ab.add(new JLabel("Armeeliste"));
		ab.getComponent(0).setFont(f);
		((JLabel)ab.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		for(int i=0; i<hf.teilnehmerVector.size();i++){
			
			JLabel label1 = new JLabel(hf.teilnehmerVector.get(i).vorname+" "+hf.teilnehmerVector.get(i).nachname);
			spieler.add(label1);
			label1.setBorder(BorderFactory.createEtchedBorder());
			label1.setFont(f);
			
			JTextField bmField = new JTextField(""+hf.teilnehmerVector.get(i).bemalwertung);
			bm.add(bmField);
			bm.setBorder(BorderFactory.createEtchedBorder());
			bm.setFont(f);
			
			JTextField abField = new JTextField(""+hf.teilnehmerVector.get(i).armeeliste);
			ab.add(abField);
			ab.setBorder(BorderFactory.createEtchedBorder());
			ab.setFont(f);
			
			if(hf.teilnehmerVector.get(i).deleted){
				label1.setVisible(false);
				ab.setVisible(false);
				bm.setVisible(false);
			} 
		}
		spieler.add(new JLabel(""));
		bm.add(AbbrechenButton);
		ab.add(SchliessenButton);
		setVisible(true);
	}
	
	JPanel hauptPanel=new JPanel();
	JPanel spieler = new JPanel();
	JPanel bm = new JPanel();
	JPanel ab = new JPanel();
	JButton AbbrechenButton=new JButton("Abbrechen");
	JButton SchliessenButton=new JButton("Speichern");
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==SchliessenButton){
			try{
				for(int i=0; i<hf.teilnehmerVector.size();i++){
						hf.teilnehmerVector.get(i).bemalwertung=Integer.parseInt(((JTextField)bm.getComponent(i+1)).getText());
						hf.teilnehmerVector.get(i).armeeliste=Integer.parseInt(((JTextField)ab.getComponent(i+1)).getText());
				}
				setVisible(false);
			} catch(NumberFormatException e1){
				hf.dialog.getErrorDialog(hf.dialog.errorNummerEingabe);
			}
		} else if(e.getSource()==AbbrechenButton){
			setVisible(false);
		}
	}
}