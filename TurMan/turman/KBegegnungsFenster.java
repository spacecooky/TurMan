package turman;

import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
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
		druckenButton.addActionListener(this);
		druckenButtonTab.addActionListener(this);
		anzeigenButton.addActionListener(this);
		anzeigenButtonTab.addActionListener(this);
	}
	
	KHauptFenster hf=null;
	
	public void init(Dimension d){
		updatePanel(begegnungsPanel);
		updatePanel(begegnungsPanelTab);
		setContentPane(begegnungsPanel);
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		
		
		setVisible(true);
		
	}

	public void updatePanel(JPanel begegnungsPanel){
		Font f = new Font("Dialog", Font.BOLD, 16);
		
		begegnungsPanel.removeAll();
		begegnungsPanel.setLayout(new BorderLayout());

		JPanel head = new JPanel();
		JPanel body = new JPanel();
		JPanel foot = new JPanel();
		
		head.setLayout(new BoxLayout(head,BoxLayout.X_AXIS));
		head.add(new JLabel("Runde: "));
		head.add(begegnungsPanel==this.begegnungsPanel?combo:comboTab);
		head.add(begegnungsPanel==this.begegnungsPanel?anzeigenButton:anzeigenButtonTab);
		for(int i=0;i<15;i++){
			head.add(new JPanel());
		}
		
		body.setLayout(new BoxLayout(body,BoxLayout.X_AXIS));
		begegnungsPanel.add(new JScrollPane(body));
		
		foot.setLayout(new GridLayout(1, 8));
		foot.add(begegnungsPanel==this.begegnungsPanel?druckenButton:druckenButtonTab);
		for(int i=0;i<6;i++){
			foot.add(new JLabel(""));
		}
		foot.add(begegnungsPanel==this.begegnungsPanel?punkteSchliessenButton:new JLabel(""));
		
		begegnungsPanel.add(head,BorderLayout.NORTH);
		begegnungsPanel.add(new JScrollPane(body),BorderLayout.CENTER);
		begegnungsPanel.add(foot,BorderLayout.SOUTH);
		
		combo.removeAllItems();
		comboTab.removeAllItems();
		
		for(int i=0;i<=hf.rundenZaehler;i++){
			combo.addItem(i);
			comboTab.addItem(i);
		}
		
		combo.setSelectedItem(hf.rundenAnzeige);
		comboTab.setSelectedItem(hf.rundenAnzeige);
		
		JPanel tische = new JPanel();
		tische.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+1,1));
		tische.setMaximumSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width)/20,20000));
		body.add(tische);
		
		JPanel begegnung = new JPanel();
		begegnung.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+1,1));
		body.add(begegnung);
		
		JPanel primär = new JPanel();
		primär.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+1,1));
		body.add(primär);
		
		JPanel sekundär = new JPanel();
		sekundär.setLayout(new GridLayout(hf.teilnehmerVector.size()/2+1,1));
		body.add(sekundär);
		
		tische.add(new JLabel("Tisch"));
		tische.getComponent(0).setFont(f);
		((JLabel)tische.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		begegnung.add(new JLabel("Begegnung"));
		begegnung.getComponent(0).setFont(f);
		((JLabel)begegnung.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(hf.optionenFeld.PSS.isSelected()){	
			primär.add(new JLabel("Primär"));
		} else if(hf.optionenFeld.TS.isSelected()){	
			primär.add(new JLabel("Turnierpunkte"));
		}
		primär.getComponent(0).setFont(f);
		((JLabel)primär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		if(hf.optionenFeld.PSS.isSelected()){
			sekundär.add(new JLabel("Sekundär"));
		} else if(hf.optionenFeld.TS.isSelected()){
			sekundär.add(new JLabel("Siegespunkte"));
		}
		sekundär.getComponent(0).setFont(f);
		((JLabel)sekundär.getComponent(0)).setBorder(BorderFactory.createRaisedBevelBorder());
		
		for(int i=0;i<hf.begegnungsVector.size();i++){
			KBegegnungen bg = hf.begegnungsVector.get(i);
			if(bg.runde==hf.rundenAnzeige){
				JLabel label6 = new JLabel(""+(bg.tisch+1));
				tische.add(label6);
				label6.setBorder(BorderFactory.createEtchedBorder());
				label6.setFont(f);
				
				KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
				KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
				
				// Durch begegnungsFensterButton ersetzen.
				JButton b1 = new JButton();
				if(begegnungsPanel.equals(this.begegnungsPanel)){
					b1=((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(bg.xPos)).getComponent(bg.yPos)).begegnungsFensterButton;
				} else {
					b1=((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(bg.xPos)).getComponent(bg.yPos)).begegnungsTabButton;
				}
				b1.setText(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname);
				begegnung.add(b1);
				b1.setBorder(BorderFactory.createEtchedBorder());
				b1.setFont(f);
				
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
		
		//Falls noch keine Teilnehmer eingetragen sind, wird die Anzeige aufgefüllt.
				if(hf.begegnungsVector.size()==0){
					
					tische.setLayout(new GridLayout(16,1));
					begegnung.setLayout(new GridLayout(16,1));
					primär.setLayout(new GridLayout(16,1));
					sekundär.setLayout(new GridLayout(16,1));
					
					for(int i=15;i>0;i--){
						JLabel label6 = new JLabel("");
						tische.add(label6);
						label6.setBorder(BorderFactory.createEtchedBorder());
						
						JLabel label1 = new JLabel("");
						begegnung.add(label1);
						label1.setBorder(BorderFactory.createEtchedBorder());
						
						JLabel label2 = new JLabel("");
						primär.add(label2);
						label2.setBorder(BorderFactory.createEtchedBorder());
						
						JLabel label3 = new JLabel("");
						sekundär.add(label3);
						label3.setBorder(BorderFactory.createEtchedBorder());
					}
				}
		begegnungsPanel.validate();
	}
	
	JPanel begegnungsPanel=new JPanel();
	JPanel begegnungsPanelTab=new JPanel();
	JButton punkteSchliessenButton=new JButton("OK");
	JButton druckenButton=new JButton("Drucken");
	JButton druckenButtonTab=new JButton("Drucken");
	JComboBox combo = new JComboBox();
	JComboBox comboTab = new JComboBox();
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
		}
	}

	
}
