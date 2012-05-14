package turman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;


public class KPunkteFenster extends JFrame implements ActionListener,ComponentListener{

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
		anzeigenButton.addActionListener(this);
		anzeigenButtonTab.addActionListener(this);
		addComponentListener(this);
	}

	KHauptFenster hf=null;

	public void init(Dimension d){

		updatePanel(punktePanelTab);
		setContentPane(punktePanel);
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		setVisible(true);
		updatePanel(punktePanel);
	}

	public void updatePanel(JPanel punktePanel){
		//hf.sortieren(ab.isSelected(),bm.isSelected());
		Font f = new Font("Dialog", Font.BOLD, 16);
		hf.sortieren(ab.isSelected(),bm.isSelected(),hf.rundenAnzeige);
		color=false;
		punktePanel.removeAll();
		punktePanel.setLayout(new BorderLayout());
		punktePanel.setBackground(Color.white);
		ab.setBackground(Color.white);
		bm.setBackground(Color.white);
		abTab.setBackground(Color.white);
		bmTab.setBackground(Color.white);

		JPanel head = new JPanel();
		JPanel body = new JPanel();
		JPanel foot = new JPanel();
		head.setBackground(Color.white);
		body.setBackground(Color.white);
		foot.setBackground(Color.white);

		head.setLayout(new BoxLayout(head,BoxLayout.X_AXIS));
		head.setBorder(BorderFactory.createEtchedBorder());
		head.add(new JLabel("Runde: "));
		head.add(punktePanel==this.punktePanel?combo:comboTab);
		head.add(punktePanel==this.punktePanel?anzeigenButton:anzeigenButtonTab);
		head.add(new JLabel(" Spieler: "+hf.sortierterVector.size()));
		for(int i=0;i<14;i++){
			JPanel p = new JPanel();
			p.setBackground(Color.white);
			head.add(p);
		}

		body.setLayout(new BoxLayout(body,BoxLayout.X_AXIS));
		JScrollPane sp = new JScrollPane(body);

		foot.setLayout(new GridLayout(1, 8));
		foot.add(punktePanel==this.punktePanel?druckenButton:druckenButtonTab);
		foot.add(punktePanel==this.punktePanel?bm:bmTab);
		foot.add(punktePanel==this.punktePanel?ab:abTab);
		for(int i=0;i<4;i++){
			foot.add(new JLabel(""));
		}
		foot.add(punktePanel==this.punktePanel?punkteSchliessenButton:new JLabel(""));

		punktePanel.add(head,BorderLayout.NORTH);
		punktePanel.add(sp,BorderLayout.CENTER);
		punktePanel.add(foot,BorderLayout.SOUTH);

		combo.removeAllItems();
		comboTab.removeAllItems();

		for(int i=0;i<=hf.rundenZaehler;i++){
			combo.addItem(i);
			comboTab.addItem(i);
		}

		combo.setSelectedItem(hf.rundenAnzeige);
		comboTab.setSelectedItem(hf.rundenAnzeige);

		JPanel platz = createPanel(body,true);
		platz.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/15,20000));

		JPanel spieler = createPanel(body,true);
		JPanel prim�r = createPanel(body,true);
		JPanel prim�rEinzel = createPanel(body,(hf.optionenFeld.bemalPri.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeePri.isSelected()&& ab.isSelected()));
		JPanel bemalung= createPanel(body,false);
		JPanel armee= createPanel(body,false);
		if(hf.optionenFeld.bemalPri.isSelected() && bm.isSelected()){
			body.add(bemalung);
		}
		if(hf.optionenFeld.armeePri.isSelected() && ab.isSelected()){
			body.add(armee);
		}
		JPanel sekund�r = createPanel(body,true);
		JPanel sekund�rEinzel = createPanel(body,(hf.optionenFeld.bemalSek.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeeSek.isSelected()&& ab.isSelected()));
		if(hf.optionenFeld.bemalSek.isSelected() && bm.isSelected()){
			body.add(bemalung);
		}
		if(hf.optionenFeld.armeeSek.isSelected() && ab.isSelected()){
			body.add(armee);
		}

		JPanel sos = createPanel(body,true);
		if(!hf.optionenFeld.PSS.isSelected()){
			sos.setVisible(false);
		}

		if((hf.optionenFeld.bemalPri.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeePri.isSelected()&& ab.isSelected()) || (hf.optionenFeld.bemalSek.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeeSek.isSelected()&& ab.isSelected())){
			prim�r.setBackground(Color.lightGray);
			sekund�r.setBackground(Color.lightGray);
			sos.setBackground(Color.lightGray);
		}

		for(int i=hf.sortierterVector.size()-1;i>=0;i--){
			if(hf.sortierterVector.get(i).deleted==false){
				KTeilnehmer tn=hf.sortierterVector.get(i);
				platz.add(createLabel(laengeAnpassenVorne(""+hf.sortierterVector.get(i).platz+" ",10), f));
				spieler.add(createButton(punktePanel,tn, f));
				prim�r.add(createLabel(" "+tn.prim�r, f));
				prim�rEinzel.add(createLabel(" "+tn.prim�rEinzel, f));
				sekund�r.add(createLabel(" "+hf.sortierterVector.get(i).sekund�r, f));
				sekund�rEinzel.add(createLabel(""+tn.sekund�rEinzel, f));
				bemalung.add(createLabel(" "+tn.bemalwertung, f));
				armee.add(createLabel(" "+tn.armeeliste, f));
				sos.add(createLabel(" "+hf.sortierterVector.get(i).sos, f));
			} 
		}

		//Falls noch keine Teilnehmer eingetragen sind, wird die Anzeige aufgef�llt.
		if(hf.teilnehmerVector.size()==0){

			platz.setLayout(new GridLayout(33,1));
			spieler.setLayout(new GridLayout(33,1));
			prim�r.setLayout(new GridLayout(33,1));
			prim�rEinzel.setLayout(new GridLayout(33,1));
			sekund�r.setLayout(new GridLayout(33,1));
			sekund�rEinzel.setLayout(new GridLayout(33,1));
			bemalung.setLayout(new GridLayout(33,1));
			armee.setLayout(new GridLayout(33,1));
			sos.setLayout(new GridLayout(33,1));

			for(int i=30;i>0;i--){
				platz.add(createLabel("", f));
				spieler.add(createLabel("", f));
				prim�r.add(createLabel("", f));
				prim�rEinzel.add(createLabel("", f));
				sekund�r.add(createLabel("", f));
				sekund�rEinzel.add(createLabel("", f));
				bemalung.add(createLabel("", f));
				armee.add(createLabel("", f));
				sos.add(createLabel("", f));
			}
		}

		punktePanel.validate();
		
		JPanel header= new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));

		header.add(createHeader("Platz",f,platz));
		header.add(createHeader("Spieler",f,spieler));

		if(hf.optionenFeld.PSS.isSelected()){
			header.add(createHeader("Prim�r(komplett)",f,prim�r));
		} else if(hf.optionenFeld.TS.isSelected()){
			header.add(createHeader("Turnierpunkte(komplett)",f,prim�r));
		}

		if((hf.optionenFeld.bemalPri.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeePri.isSelected()&& ab.isSelected())){
			header.add(createHeader("Prim�r(einzel)",f,prim�rEinzel));
		}

		if(hf.optionenFeld.bemalPri.isSelected() && bm.isSelected()){
			header.add(createHeader("Bemalwertung",f,bemalung));
		}
		if(hf.optionenFeld.armeePri.isSelected() && ab.isSelected()){
			header.add(createHeader("Armeewertung",f,armee));
		}

		if(hf.optionenFeld.PSS.isSelected()){
			header.add(createHeader("Sekund�r(komplett)",f,sekund�r));
		} else if(hf.optionenFeld.TS.isSelected()){
			header.add(createHeader("Siegespunktedifferenz",f,sekund�r));
		}

		if((hf.optionenFeld.bemalSek.isSelected()&& bm.isSelected()) || (hf.optionenFeld.armeeSek.isSelected()&& ab.isSelected())){
			header.add(createHeader("Sekund�r(einzel)",f,sekund�rEinzel));
		}

		if(hf.optionenFeld.bemalSek.isSelected() && bm.isSelected()){
			header.add(createHeader("Bemalwertung",f,bemalung));
		}
		if(hf.optionenFeld.armeeSek.isSelected() && ab.isSelected()){
			header.add(createHeader("Armeewertung",f,armee));
		}

		if(hf.optionenFeld.PSS.isSelected()){
			header.add(createHeader("SOS",f,sos));
		}

		header.setBackground(Color.white);
		sp.setColumnHeaderView(header);

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
	JComboBox combo = new JComboBox();
	JComboBox comboTab = new JComboBox();
	JButton anzeigenButton= new JButton("Anzeigen");
	JButton anzeigenButtonTab= new JButton("Anzeigen");
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
		} else if(e.getSource()==anzeigenButton ){
			hf.rundenAnzeige= combo.getSelectedIndex();
			hf.updatePanels();
		} else if(e.getSource()==anzeigenButtonTab){
			hf.rundenAnzeige= comboTab.getSelectedIndex();
			hf.updatePanels();
		}
	}
	
	public JLabel createHeader(String s, Font f, JPanel p){
		JLabel l = new JLabel(s);
		l.setMaximumSize(new Dimension(p.getWidth(),35));
		l.setMinimumSize(new Dimension(p.getWidth(),35));
		l.setPreferredSize(new Dimension(p.getWidth(),35));
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

		b.setText(" "+tn.vorname+" "+tn.nachname);
		b.setBorder(BorderFactory.createEtchedBorder());
		b.setHorizontalAlignment(SwingConstants.LEFT);
		b.setFont(f);
		b.setBackground(Color.white);
		return b;
	}

	public JPanel createPanel(JPanel punktePanel, boolean b){
		JPanel p = new JPanel() ;
		p.setLayout(new GridLayout(hf.teilnehmerVector.size(),1));
		if(b){
			punktePanel.add(p);
		}
		p.setBackground(Color.white);
		return p;
	}
	
	public String laengeAnpassenVorne(String s, int i){
		while(s.length()<i){
			s= " "+s;
		}
		return s;
	}
	public String laengeAnpassenHinten(String s, int i){
		while(s.length()<i){
			s+= " ";
		}
		return s;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		init(getSize());
	}

	@Override
	public void componentResized(ComponentEvent e) {
		init(getSize());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		init(getSize());
	}
	
}