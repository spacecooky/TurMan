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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class KAnmeldeFenster extends JFrame implements ActionListener,ComponentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KAnmeldeFenster(KHauptFenster hf) {
		this.hf=hf;
		abbrechenButton.addActionListener(this);
		druckenButton.addActionListener(this);
		endeButton.addActionListener(this);
		addComponentListener(this);
	}

	KHauptFenster hf=null;

	public void init(Dimension d){

		
		setContentPane(anmeldePanel);
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else{
			setSize(d);
		}
		setVisible(true);
		
		init();
	}

	public void init(){
		Font f = new Font("Dialog", Font.BOLD, 16);
		anmeldePanel.removeAll();
		anmeldePanel.setLayout(new BorderLayout());
		anmeldePanel.setBackground(Color.white);

		JPanel body = new JPanel();
		JPanel foot = new JPanel();
		body.setBackground(Color.white);
		foot.setBackground(Color.white);

		body.setLayout(new BoxLayout(body,BoxLayout.X_AXIS));
		JScrollPane sp = new JScrollPane(body);

		foot.setLayout(new GridLayout(1, 8));
		foot.add(druckenButton);
		for(int i=0;i<5;i++){
			foot.add(new JLabel(""));
		}
		foot.add(abbrechenButton);
		foot.add(endeButton);

		anmeldePanel.add(sp,BorderLayout.CENTER);
		anmeldePanel.add(foot,BorderLayout.SOUTH);

		JPanel vorname = createPanel(body,true);
		JPanel nickname = createPanel(body,true);
		JPanel nachname = createPanel(body,true);
		JPanel anwesend = createPanel(body,true);

		for(int i=hf.teilnehmerVector.size()-1;i>=0;i--){
			if(hf.sortierterVector.get(i).deleted==false){
				KTeilnehmer tn=hf.teilnehmerVector.get(i);
				vorname.add(createField(tn.vorname, f));
				nickname.add(createField(tn.nickname, f));
				nachname.add(createField(tn.nachname, f));
				anwesend.add(new JCheckBox());
			} 
		}

		//Falls noch keine Teilnehmer eingetragen sind, wird die Anzeige aufgef¸llt.
		if(hf.teilnehmerVector.size()==0){

			vorname.setLayout(new GridLayout(33,1));
			nickname.setLayout(new GridLayout(33,1));
			nachname.setLayout(new GridLayout(33,1));
			anwesend.setLayout(new GridLayout(33,1));

			for(int i=30;i>0;i--){
				vorname.add(createLabel("", f));
				nickname.add(createLabel("", f));
				nachname.add(createLabel("", f));
				anwesend.add(createLabel("", f));
			}
		}

		anmeldePanel.validate();
		
		JPanel header= new JPanel();
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));

		System.out.println(vorname.getWidth());
		header.add(createHeader("Spieler",f,vorname));
		header.add(createHeader("Spieler",f,nickname));
		header.add(createHeader("Spieler",f,nachname));
		header.add(createHeader("Anwesend",f,nachname));
		

		header.setBackground(Color.white);
		sp.setColumnHeaderView(header);

		anmeldePanel.validate();
	}

	JPanel anmeldePanel=new JPanel();
	JButton abbrechenButton=new JButton("Abbrechen");
	JButton druckenButton=new JButton("Drucken");
	JButton endeButton= new JButton("Anmeldung abschlieﬂen");
	boolean color=false;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==abbrechenButton){
			setVisible(false);
		} else if(e.getSource()==druckenButton){
			/*PrinterJob pj = PrinterJob.getPrinterJob();
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
			}*/
		} else if(e.getSource()==endeButton ){
			//TODO
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
	
	public JTextField createField(String s, Font f){
		JTextField l = new JTextField(s);
		l.setBorder(BorderFactory.createEtchedBorder());
		l.setFont(f);
		return l;
	}

	public JButton createButton(KTeilnehmer tn, Font f){
		JButton b=tn.punkteFensterButton;
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