package turman;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

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
		abTab.addActionListener(this);
		bmTab.addActionListener(this);
		druckenButton.addActionListener(this);
		druckenButtonTab.addActionListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(Dimension d){
		
		updatePanel(punktePanel);
		updatePanel(punktePanelTab);
		setContentPane(new JScrollPane(punktePanel));
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		setVisible(true);
	}
	
	public void updatePanel(JPanel punktePanel){
		hf.sortieren(ab.isSelected(),bm.isSelected());
		Font f = new Font("Dialog", Font.BOLD, 16);
		hf.sortieren(ab.isSelected(),bm.isSelected());
		
		punktePanel.removeAll();
		
		punktePanel.setLayout(new BoxLayout(punktePanel,BoxLayout.X_AXIS));
		
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
		if(hf.optionenFeld.PSS.isSelected()){
			punktePanel.add(sos);
		}
		
		
		platz.add(new JLabel("Platz"));
		platz.getComponent(0).setFont(f);
		((JLabel)platz.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		spieler.add(new JLabel("Spieler"));
		spieler.getComponent(0).setFont(f);
		((JLabel)spieler.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(hf.optionenFeld.PSS.isSelected()){
			primär.add(new JLabel("Primär(B)"));
		} else if(hf.optionenFeld.TS.isSelected()){
			primär.add(new JLabel("Turnierpunkte(B)(AL)"));
		}
		primär.getComponent(0).setFont(f);
		((JLabel)primär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(hf.optionenFeld.PSS.isSelected()){
			sekundär.add(new JLabel("Sekundär(AL)"));
		} else if(hf.optionenFeld.TS.isSelected()){
			sekundär.add(new JLabel("Siegespunktedifferenz"));
		}
		sekundär.getComponent(0).setFont(f);
		((JLabel)sekundär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		sos.add(new JLabel("SOS"));
		sos.getComponent(0).setFont(f);
		((JLabel)sos.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		for(int i=hf.teilnehmerVector.size()-1;i>=0;i--){
			if(hf.sortierterVector.get(i).deleted==false){
				JLabel label6 = new JLabel(""+hf.sortierterVector.get(i).platz);
				platz.add(label6);
				label6.setBorder(BorderFactory.createEtchedBorder());
				label6.setFont(f);
				
				JLabel label1 = new JLabel(hf.sortierterVector.get(i).vorname+" "+hf.sortierterVector.get(i).nachname);
				spieler.add(label1);
				label1.setBorder(BorderFactory.createEtchedBorder());
				label1.setFont(f);
				
				JLabel label2 = new JLabel("");
				if(hf.optionenFeld.PSS.isSelected()){
					label2 = new JLabel(Integer.toString(hf.sortierterVector.get(i).primär)+(bm.isSelected()?"("+hf.sortierterVector.get(i).bemalwertung+")":"(0)"));
				} else if(hf.optionenFeld.TS.isSelected()){
					label2 = new JLabel(Integer.toString(hf.sortierterVector.get(i).primär)+(bm.isSelected()?"("+hf.sortierterVector.get(i).bemalwertung+")":"(0)")+(ab.isSelected()?"("+hf.sortierterVector.get(i).armeeliste+")":"(0)"));
				}
				primär.add(label2);
				label2.setBorder(BorderFactory.createEtchedBorder());
				label2.setFont(f);
				
				JLabel label3 = new JLabel("");
				if(hf.optionenFeld.PSS.isSelected()){
					label3 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sekundär)+(ab.isSelected()?"("+hf.sortierterVector.get(i).armeeliste+")":"(0)"));
				} else if(hf.optionenFeld.TS.isSelected()){
					label3 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sekundär));	
				}
				sekundär.add(label3);
				label3.setBorder(BorderFactory.createEtchedBorder());
				label3.setFont(f);
				
				JLabel label4 = new JLabel(Integer.toString(hf.sortierterVector.get(i).sos));
				sos.add(label4);
				label4.setBorder(BorderFactory.createEtchedBorder());
				label4.setFont(f);
			} 
		}
		
		//Falls noch keine Teilnehmer eingetragen sind, wird die Anzeige aufgefüllt.
		if(hf.teilnehmerVector.size()==0){
			
			
			platz.setLayout(new GridLayout(32,1));
			spieler.setLayout(new GridLayout(32,1));
			primär.setLayout(new GridLayout(32,1));
			sekundär.setLayout(new GridLayout(32,1));
			sos.setLayout(new GridLayout(32,1));
			
			for(int i=30;i>0;i--){
					JLabel label6 = new JLabel(" ");
					platz.add(label6);
					label6.setBorder(BorderFactory.createEtchedBorder());
					
					JLabel label1 = new JLabel(" ");
					spieler.add(label1);
					label1.setBorder(BorderFactory.createEtchedBorder());
					
					JLabel label2 = new JLabel(" ");
					primär.add(label2);
					label2.setBorder(BorderFactory.createEtchedBorder());
					
					JLabel label3 = new JLabel(" ");
					sekundär.add(label3);
					label3.setBorder(BorderFactory.createEtchedBorder());
					
					JLabel label4 = new JLabel(" ");
					sos.add(label4);
					label4.setBorder(BorderFactory.createEtchedBorder());
			}
		}
		if(punktePanel.equals(this.punktePanel)){
			platz.add(druckenButton);
		} else{
			platz.add(druckenButtonTab);
		}
		spieler.add(new JLabel(""));
		
		if(punktePanel.equals(this.punktePanel)){
			primär.add(bm);
			sekundär.add(ab);
			sos.add(punkteSchliessenButton);
		}else{
			primär.add(bmTab);
			sekundär.add(abTab);
		}
		punktePanel.validate();
	}
	
	JPanel punktePanel=new JPanel();
	JPanel punktePanelTab=new JPanel();
	JCheckBox ab = new JCheckBox("Armeeliste");
	JCheckBox bm = new JCheckBox("Bemalung");
	JCheckBox abTab = new JCheckBox("Armeeliste");
	JCheckBox bmTab = new JCheckBox("Bemalung");
	JButton punkteSchliessenButton=new JButton("OK");
	JButton druckenButton=new JButton("Drucken");
	JButton druckenButtonTab=new JButton("Drucken");
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==punkteSchliessenButton){
			setVisible(false);
		} else if(e.getSource()==ab){
			abTab.setSelected(ab.isSelected());
			init(getSize());
		} else if(e.getSource()==bm){
			bmTab.setSelected(bm.isSelected());
			init(getSize());
		} else if(e.getSource()==abTab){
			ab.setSelected(abTab.isSelected());
			updatePanel(punktePanelTab);
		} else if(e.getSource()==bmTab){
			bm.setSelected(bmTab.isSelected());
			updatePanel(punktePanelTab);
		} else if(e.getSource()==druckenButton){
			PrinterJob pj = PrinterJob.getPrinterJob();
			KTextPrintable tp = new KTextPrintable();
			tp.hf=hf;
			tp.sicht=KTextPrintable.PUNKTE;
			pj.setPrintable(tp);
			//PageFormat pf = pj.pageDialog(pj.defaultPage());
		    if (pj.printDialog()) {
		        try {pj.print();}
		        catch (PrinterException exc) {
		            System.out.println(exc);
		         }
		     }
		} else if(e.getSource()==druckenButtonTab){
			PrinterJob pj = PrinterJob.getPrinterJob();
			KTextPrintable tp = new KTextPrintable();
			tp.hf=hf;
			tp.sicht=KTextPrintable.PUNKTE;
			pj.setPrintable(tp);
			//PageFormat pf = pj.pageDialog(pj.defaultPage());
		    if (pj.printDialog()) {
		        try {pj.print();}
		        catch (PrinterException exc) {
		            System.out.println(exc);
		         }
		     }
		}
	}
}