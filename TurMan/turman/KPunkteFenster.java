package turman;

import java.awt.Color;
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
		boolean color=false;
		punktePanel.removeAll();
		
		punktePanel.setLayout(new BoxLayout(punktePanel,BoxLayout.X_AXIS));
		
		JPanel platz = new JPanel();
		platz.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		platz.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/15,20000));
		punktePanel.add(platz);
		
		JPanel spieler = new JPanel();
		spieler.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		punktePanel.add(spieler);
		
		JPanel primär = new JPanel();
		primär.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		punktePanel.add(primär);
		
		JPanel primärEinzel = new JPanel();
		primärEinzel.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		if((hf.optionenFeld.bemalPri.isSelected()&& bm.isSelected()) || hf.optionenFeld.armeePri.isSelected()&& ab.isSelected()){
		punktePanel.add(primärEinzel);
		//primär.setBackground(Color.green);
		//primärEinzel.setBackground(Color.green);
		color=true;
		}
		
		JPanel bemalung = new JPanel();
		bemalung.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		if(hf.optionenFeld.bemalPri.isSelected() && bm.isSelected()){
		punktePanel.add(bemalung);
		//bemalung.setBackground(Color.green);
		}
		
		JPanel armee = new JPanel();
		armee.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		if(hf.optionenFeld.armeePri.isSelected() && ab.isSelected()){
		punktePanel.add(armee);
		//armee.setBackground(Color.green);
		}
		
		JPanel sekundär = new JPanel();
		sekundär.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		punktePanel.add(sekundär);
		
		JPanel sekundärEinzel = new JPanel();
		sekundärEinzel.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		if((hf.optionenFeld.bemalSek.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeeSek.isSelected()&& ab.isSelected())){
			punktePanel.add(sekundärEinzel);
			//sekundär.setBackground(Color.orange);
			//sekundärEinzel.setBackground(Color.orange);
			color=true;
		}
		
		if(hf.optionenFeld.bemalSek.isSelected() && bm.isSelected()){
			punktePanel.add(bemalung);
			//bemalung.setBackground(Color.orange);
			}
		
		if(hf.optionenFeld.armeeSek.isSelected() && ab.isSelected()){
			punktePanel.add(armee);
			//armee.setBackground(Color.orange);
			}
		
			JPanel sos = new JPanel();
			sos.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		if(hf.optionenFeld.PSS.isSelected()){
			punktePanel.add(sos);
		}
		
		if(color){
			//primär.setBackground(Color.green);
			//sekundär.setBackground(Color.orange);
			//sos.setBackground(Color.cyan);
			primär.setBackground(Color.lightGray);
			sekundär.setBackground(Color.lightGray);
			sos.setBackground(Color.lightGray);
		}
		
		platz.add(createHeader("Platz",f));
		
		spieler.add(createHeader("Spieler",f));
		
		if(hf.optionenFeld.PSS.isSelected()){
			primär.add(createHeader("Primär(komplett)",f));
		} else if(hf.optionenFeld.TS.isSelected()){
			primär.add(createHeader("Turnierpunkte(komplett)",f));
		}
		
		if(hf.optionenFeld.PSS.isSelected()){
			sekundär.add(createHeader("Sekundär(komplett)",f));
		} else if(hf.optionenFeld.TS.isSelected()){
			sekundär.add(createHeader("Siegespunktedifferenz",f));
		}
		
		sos.add(createHeader("SOS",f));
		primärEinzel.add(createHeader("Primär(einzel)",f));
		sekundärEinzel.add(createHeader("Sekundär(einzel)",f));
		bemalung.add(createHeader("Bemalwertung",f));
		armee.add(createHeader("Armeeliste",f));
		
		for(int i=hf.teilnehmerVector.size()-1;i>=0;i--){
			if(hf.sortierterVector.get(i).deleted==false){
				KTeilnehmer tn=hf.sortierterVector.get(i);
				
				
				platz.add(createLabel(""+hf.sortierterVector.get(i).platz, f));
				spieler.add(createLabel(tn.vorname+" "+tn.nachname, f));
				primär.add(createLabel(""+tn.primär, f));
				primärEinzel.add(createLabel(""+tn.primärEinzel, f));
				sekundär.add(createLabel(""+hf.sortierterVector.get(i).sekundär, f));
				sekundärEinzel.add(createLabel(""+tn.sekundärEinzel, f));
				bemalung.add(createLabel(""+tn.bemalwertung, f));
				armee.add(createLabel(""+tn.armeeliste, f));
				sos.add(createLabel(""+hf.sortierterVector.get(i).sos, f));
				
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
			platz.add(new JPanel());
		} else{
			platz.add(druckenButtonTab);
			platz.add(new JPanel());
		}
		primär.add(new JPanel());
		primär.add(new JPanel());
		sekundär.add(new JPanel());
		sekundär.add(new JPanel());
		primärEinzel.add(new JPanel());
		primärEinzel.add(new JPanel());
		sekundärEinzel.add(new JPanel());
		sekundärEinzel.add(new JPanel());
		bemalung.add(new JPanel());
		bemalung.add(new JPanel());
		armee.add(new JPanel());
		armee.add(new JPanel());
		
		if(punktePanel.equals(this.punktePanel)){
			spieler.add(bm);
			spieler.add(ab);
			sos.add(punkteSchliessenButton);
			sos.add(new JPanel());
		}else{
			spieler.add(bmTab);
			spieler.add(abTab);
			sos.add(new JPanel());
			sos.add(new JPanel());
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
	
	public JLabel createHeader(String s, Font f){
		JLabel l = new JLabel(s);
		l.setBorder(BorderFactory.createRaisedBevelBorder());
		l.setFont(f);
		return l;
	}
	
	public JLabel createLabel(String s, Font f){
		JLabel l = new JLabel(s);
		l.setBorder(BorderFactory.createEtchedBorder());
		l.setFont(f);
		return l;
	}
	
}