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
		color=false;
		punktePanel.removeAll();
		
		punktePanel.setLayout(new BoxLayout(punktePanel,BoxLayout.X_AXIS));
		punktePanel.setBackground(Color.white);
		ab.setBackground(Color.white);
		bm.setBackground(Color.white);
		abTab.setBackground(Color.white);
		bmTab.setBackground(Color.white);
		
		JPanel platz = createPanel(punktePanel,true);
		platz.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/15,20000));
		
		JPanel spieler = createPanel(punktePanel,true);
		JPanel primär = createPanel(punktePanel,true);
		JPanel primärEinzel = createPanel(punktePanel,(hf.optionenFeld.bemalPri.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeePri.isSelected()&& ab.isSelected()));
		JPanel bemalung= createPanel(punktePanel,false);
		JPanel armee= createPanel(punktePanel,false);
		if(hf.optionenFeld.bemalPri.isSelected() && bm.isSelected()){
			punktePanel.add(bemalung);
		}
		if(hf.optionenFeld.armeePri.isSelected() && ab.isSelected()){
			punktePanel.add(armee);
		}
		JPanel sekundär = createPanel(punktePanel,true);
		JPanel sekundärEinzel = createPanel(punktePanel,(hf.optionenFeld.bemalSek.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeeSek.isSelected()&& ab.isSelected()));
		if(hf.optionenFeld.bemalSek.isSelected() && bm.isSelected()){
			punktePanel.add(bemalung);
		}
		if(hf.optionenFeld.armeeSek.isSelected() && ab.isSelected()){
			punktePanel.add(armee);
		}
		
		JPanel sos = createPanel(punktePanel,true);
		if(!hf.optionenFeld.PSS.isSelected()){
			sos.setVisible(false);
		}
		
		if((hf.optionenFeld.bemalPri.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeePri.isSelected()&& ab.isSelected()) || (hf.optionenFeld.bemalSek.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeeSek.isSelected()&& ab.isSelected())){
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
		
		for(int i=hf.sortierterVector.size()-1;i>=0;i--){
			if(hf.sortierterVector.get(i).deleted==false){
				KTeilnehmer tn=hf.sortierterVector.get(i);
				platz.add(createLabel(""+hf.sortierterVector.get(i).platz, f));
				spieler.add(createButton(punktePanel,tn, f));
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
			
			platz.setLayout(new GridLayout(33,1));
			spieler.setLayout(new GridLayout(33,1));
			primär.setLayout(new GridLayout(33,1));
			primärEinzel.setLayout(new GridLayout(33,1));
			sekundär.setLayout(new GridLayout(33,1));
			sekundärEinzel.setLayout(new GridLayout(33,1));
			bemalung.setLayout(new GridLayout(33,1));
			armee.setLayout(new GridLayout(33,1));
			sos.setLayout(new GridLayout(33,1));
			
			for(int i=30;i>0;i--){
					platz.add(createLabel("", f));
					spieler.add(createLabel("", f));
					primär.add(createLabel("", f));
					primärEinzel.add(createLabel("", f));
					sekundär.add(createLabel("", f));
					sekundärEinzel.add(createLabel("", f));
					bemalung.add(createLabel("", f));
					armee.add(createLabel("", f));
					sos.add(createLabel("", f));
			}
		}
		if(punktePanel.equals(this.punktePanel)){
			platz.add(druckenButton);
			platz.add(createEmptyPanel());
		} else{
			platz.add(druckenButtonTab);
			platz.add(createEmptyPanel());
		}
		primär.add(createEmptyPanel());
		primär.add(createEmptyPanel());
		sekundär.add(createEmptyPanel());
		sekundär.add(createEmptyPanel());
		primärEinzel.add(createEmptyPanel());
		primärEinzel.add(createEmptyPanel());
		sekundärEinzel.add(createEmptyPanel());
		sekundärEinzel.add(createEmptyPanel());
		bemalung.add(createEmptyPanel());
		bemalung.add(createEmptyPanel());
		armee.add(createEmptyPanel());
		armee.add(createEmptyPanel());
		
		if(punktePanel.equals(this.punktePanel)){
			spieler.add(bm);
			spieler.add(ab);
			sos.add(punkteSchliessenButton);
			sos.add(createEmptyPanel());
		}else{
			spieler.add(bmTab);
			spieler.add(abTab);
			sos.add(createEmptyPanel());
			sos.add(createEmptyPanel());
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
	boolean color=false;
	
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
	
	public JButton createButton(JPanel punktePanel,KTeilnehmer tn, Font f){
		JButton b;
		if(punktePanel.equals(this.punktePanel)){
			b=tn.punkteFensterButton;
		} else{
			b=tn.punkteTabButton;
		}
		
		b.setText(tn.vorname+" "+tn.nachname);
		b.setBorder(BorderFactory.createEtchedBorder());
		b.setFont(f);
		b.setBackground(Color.white);
		return b;
	}
	
	public JPanel createPanel(JPanel punktePanel, boolean b){
		JPanel p = new JPanel() ;
		p.setLayout(new GridLayout(hf.teilnehmerVector.size()+3,1));
		if(b){
			punktePanel.add(p);
		}
		p.setBackground(Color.white);
		return p;
	}

	public JPanel createEmptyPanel(){
		JPanel p = new JPanel() ;
		p.setBackground(Color.white);
		return p;
	}
	
}