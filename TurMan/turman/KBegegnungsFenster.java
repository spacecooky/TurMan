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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class KBegegnungsFenster extends JFrame implements ActionListener,ComponentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KBegegnungsFenster(KHauptFenster hf) {
		super("Begegnung");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		punkteSchliessenButton.addActionListener(this);
		druckenButton.addActionListener(this);
		druckenButtonTab.addActionListener(this);
		pdfButton.addActionListener(this);
		pdfButtonTab.addActionListener(this);
		txtButton.addActionListener(this);
		txtButtonTab.addActionListener(this);
		anzeigenButton.addActionListener(this);
		anzeigenButtonTab.addActionListener(this);
		addComponentListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(Dimension d){
		
		updatePanel(begegnungsPanelTab);
		setContentPane(begegnungsPanel);
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		setVisible(true);
		updatePanel(begegnungsPanel);
		
	}

	public void updatePanel(JPanel begegnungsPanel){
		Font f = new Font("Dialog", Font.BOLD, 16);
		
		begegnungsPanel.removeAll();
		begegnungsPanel.setLayout(new BorderLayout());
		begegnungsPanel.setBackground(Color.white);
		
		JPanel head = new JPanel();
		JPanel body = new JPanel();
		JPanel foot = new JPanel();
		head.setBackground(Color.white);
		body.setBackground(Color.white);
		foot.setBackground(Color.white);
		
		head.setLayout(new BoxLayout(head,BoxLayout.X_AXIS));
		head.setBorder(BorderFactory.createEtchedBorder());
		head.add(new JLabel("Runde: "));
		head.add(begegnungsPanel==this.begegnungsPanel?combo:comboTab);
		head.add(begegnungsPanel==this.begegnungsPanel?anzeigenButton:anzeigenButtonTab);
		head.add(new JLabel(" Spieler: "+hf.sortierterVector.size()));
		for(int i=0;i<14;i++){
			JPanel p = new JPanel();
			p.setBackground(Color.white);
			head.add(p);
		}
		
		body.setLayout(new BoxLayout(body,BoxLayout.X_AXIS));
		JScrollPane sp = new JScrollPane(body);
		
		foot.setLayout(new GridLayout(1, 8));
		foot.add(begegnungsPanel==this.begegnungsPanel?druckenButton:druckenButtonTab);
		foot.add(begegnungsPanel==this.begegnungsPanel?pdfButton:pdfButtonTab);
		foot.add(begegnungsPanel==this.begegnungsPanel?txtButton:txtButtonTab);
		for(int i=0;i<4;i++){
			foot.add(new JLabel(""));
		}
		foot.add(begegnungsPanel==this.begegnungsPanel?punkteSchliessenButton:new JLabel(""));
		
		begegnungsPanel.add(head,BorderLayout.NORTH);
		begegnungsPanel.add(sp,BorderLayout.CENTER);
		begegnungsPanel.add(foot,BorderLayout.SOUTH);
		
		combo.removeAllItems();
		comboTab.removeAllItems();
		
		for(int i=0;i<=hf.rundenZaehler;i++){
			combo.addItem(i);
			comboTab.addItem(i);
		}
		
		combo.setSelectedItem(hf.rundenAnzeige);
		comboTab.setSelectedItem(hf.rundenAnzeige);
		
		JPanel tische = createPanel(body,true);
		tische.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/15,20000));
		
		JPanel begegnung = createPanel(body,true);
		JPanel primaer= createPanel(body,true);
		JPanel sekundaer = createPanel(body,hf.optionenFeldVar.sPunkte.isSelected());
		JPanel tertiaer = createPanel(body,hf.optionenFeldVar.tPunkte.isSelected());
		
		for(int i=0;i<hf.begegnungsVector.size();i++){
			KBegegnungen bg = hf.begegnungsVector.get(i);
			if(bg.runde==hf.rundenAnzeige){
				tische.add(createLabel(laengeAnpassenVorne(""+(bg.tisch+1)+" ",8), f));
				
				KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
				KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
				
				// Durch begegnungsFensterButton ersetzen.
				JButton b1 = new JButton();
				if(begegnungsPanel.equals(this.begegnungsPanel)){
					b1=((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(bg.xPos)).getComponent(bg.yPos)).begegnungsFensterButton;
				} else {
					b1=((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(bg.xPos)).getComponent(bg.yPos)).begegnungsTabButton;
				}
				
				String vn1 = tn1.vornameAlter.equals("")?tn1.vorname:tn1.vornameAlter;
				String nn1 = tn1.nachnameAlter.equals("")?tn1.nachname:tn1.nachnameAlter;
				String vn2 = tn2.vornameAlter.equals("")?tn2.vorname:tn2.vornameAlter;
				String nn2 = tn2.nachnameAlter.equals("")?tn2.nachname:tn2.nachnameAlter;
				
				b1.setText(vn1+" "+nn1 +" : "+vn2+" "+nn2);
				begegnung.add(b1);
				b1.setBorder(BorderFactory.createEtchedBorder());
				b1.setFont(f);
				
				primaer.add(createLabel(bg.p1pri+" : "+bg.p2pri,f));
				sekundaer.add(createLabel(bg.p1sek+" : "+bg.p2sek,f));
				tertiaer.add(createLabel(bg.p1ter+" : "+bg.p2ter,f));
			}
		}
		
		//Falls noch keine Teilnehmer eingetragen sind, wird die Anzeige aufgefüllt.
				if(hf.begegnungsVector.size()==0){
					
					tische.setLayout(new GridLayout(16,1));
					begegnung.setLayout(new GridLayout(16,1));
					primaer.setLayout(new GridLayout(16,1));
					sekundaer.setLayout(new GridLayout(16,1));
					tertiaer.setLayout(new GridLayout(16,1));
					
					for(int i=15;i>0;i--){
						JLabel label6 = new JLabel("");
						tische.add(label6);
						label6.setBorder(BorderFactory.createEtchedBorder());
						
						JLabel label1 = new JLabel("");
						begegnung.add(label1);
						label1.setBorder(BorderFactory.createEtchedBorder());
						
						JLabel label2 = new JLabel("");
						primaer.add(label2);
						label2.setBorder(BorderFactory.createEtchedBorder());
						
						JLabel label3 = new JLabel("");
						sekundaer.add(label3);
						label3.setBorder(BorderFactory.createEtchedBorder());
					}
				}
		begegnungsPanel.validate();
		
		JPanel header= new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));

		
		header.add(createHeader("Tisch",f,tische));
		header.add(createHeader("Begegnung",f,begegnung));
		
		header.add(createHeader("Primärpunkte",f,primaer));	
		
		if(hf.optionenFeldVar.sPunkte.isSelected()){
			header.add(createHeader("Sekundärpunkte",f,sekundaer));
		} 
		
		if(hf.optionenFeldVar.tPunkte.isSelected()){
			header.add(createHeader("Tertiärpunkte",f,tertiaer));
		} 

		header.setBackground(Color.white);
		sp.setColumnHeaderView(header);

		begegnungsPanel.validate();
	}
	
	JPanel begegnungsPanel=new JPanel();
	JPanel begegnungsPanelTab=new JPanel();
	JButton punkteSchliessenButton=new JButton("OK");
	JButton druckenButton=new JButton("Drucken");
	JButton druckenButtonTab=new JButton("Drucken");
	JButton pdfButton=new JButton("PDF");
	JButton pdfButtonTab=new JButton("PDF");
	JButton txtButton=new JButton("TXT");
	JButton txtButtonTab=new JButton("TXT");
	JComboBox<Integer> combo = new JComboBox<Integer>();
	JComboBox<Integer> comboTab = new JComboBox<Integer>();
	JButton anzeigenButton= new JButton("Anzeigen");
	JButton anzeigenButtonTab= new JButton("Anzeigen");
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==punkteSchliessenButton){
			setVisible(false);
		} else if(e.getSource()==druckenButton || e.getSource()==druckenButtonTab){
			PrinterJob pj = PrinterJob.getPrinterJob();
			KTextPrintable tp = new KTextPrintable();
			tp.hf=hf;
			tp.sicht=KTextPrintable.BEGEGNUNG;
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
		} else if(e.getSource()==pdfButton || e.getSource()==pdfButtonTab){
			hf.pdf.begegnungenAnzeigen(hf.begegnungsVector, hf.rundenAnzeige);
		} else if(e.getSource()==txtButton || e.getSource()==txtButtonTab){
			hf.txt.begegnungenAnzeigen(hf.begegnungsVector, hf.rundenAnzeige);
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
	
	public JPanel createPanel(JPanel punktePanel, boolean b){
		JPanel p = new JPanel() ;
		p.setLayout(new GridLayout(hf.teilnehmerVector.size()/2,1));
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
