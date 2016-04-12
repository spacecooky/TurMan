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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

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


public class KAnmeldeFenster extends JFrame implements ActionListener,ComponentListener, KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2924742194067324526L;

	public KAnmeldeFenster(KHauptFenster hf) {
		super("Anmeldeverwaltung");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		abbrechenButton.addActionListener(this);
		druckenButton.addActionListener(this);
		endeButton.addActionListener(this);
		alle.addActionListener(this);
		geloeschteVerstecken.addActionListener(this);
		addComponentListener(this);
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
	}

	KHauptFenster hf=null;
	boolean anmeldesicht =true;
	JPanel anmeldePanel=new JPanel();
	JPanel header= new JPanel();
	JPanel body = new JPanel();
	JPanel foot = new JPanel();
	JButton abbrechenButton=new JButton("Abbrechen");
	JButton druckenButton=new JButton("Drucken");
	JButton endeButton= new JButton("Anmeldeverwaltung abschließen");
	JCheckBox alle = new JCheckBox("Alle anmelden");
	boolean color=false;
	JCheckBox geloeschteVerstecken = new JCheckBox("Gelöschte verstecken");
	
	JPanel anwesend;
	JPanel vorname;
	JPanel nickname;
	JPanel nachname;
	JPanel armee;
	JPanel ort;
	JPanel team;

	public void init(Dimension d){

		
		setContentPane(anmeldePanel);
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			setSize(d);
		}
		setVisible(true);
		
		init();
	}
	
public void initWrap(Dimension d){

		
		setContentPane(anmeldePanel);
		if(d==null){
			setSize(Toolkit.getDefaultToolkit().getScreenSize());
		} else {
			setSize(d);
		}
		
		//init();
		initList();
	}
	
	public void initList(){
		System.out.println("initList");
		Font f = new Font("Dialog", Font.BOLD, 16);
		anmeldePanel.removeAll();
		anmeldePanel.setLayout(new BorderLayout());
		anmeldePanel.setBackground(Color.white);

		header= new JPanel();
		body = new JPanel();
		foot = new JPanel();
		
		body.setBackground(Color.white);
		foot.setBackground(Color.white);

		body.setLayout(new BoxLayout(body,BoxLayout.X_AXIS));
		JScrollPane sp = new JScrollPane(body);

		foot.setLayout(new GridLayout(1, 8));
		if(anmeldesicht){
			foot.add(alle);
			foot.add(new JLabel(""));
			foot.add(druckenButton);
			for(int i=0;i<3;i++){
				foot.add(new JLabel(""));
			}
			foot.add(abbrechenButton);
			foot.add(endeButton);
		}else{
			foot.add(geloeschteVerstecken);
			foot.add(new JLabel(""));
			foot.add(druckenButton);
			for(int i=0;i<5;i++){
				foot.add(new JLabel(""));
			}
		}
			

		anmeldePanel.add(sp,BorderLayout.CENTER);
		anmeldePanel.add(foot,BorderLayout.SOUTH);

		if(anmeldesicht){
			anwesend = createPanel(body,true);
		}else{
			anwesend = createPanel(body,false);
		}
		vorname = createPanel(body,true);
		nickname = createPanel(body,true);
		nachname = createPanel(body,true);
		armee = createPanel(body,true);
		ort = createPanel(body,true);
		team = createPanel(body,true);

		for(int i=sortLocal.size()-1;i>=0;i--){
			if(sortLocal.get(i).deleted==false || !geloeschteVerstecken.isSelected()){
				KTeilnehmer tn=sortLocal.get(i);
				anwesend.add(tn.anwesend);
				vorname.add(createField(tn.vorname, f));
				nickname.add(createField(tn.nickname, f));
				nachname.add(createField(tn.nachname, f));
				armee.add(createField(tn.armee, f));
				ort.add(createField(tn.ort, f));
				team.add(createField(tn.team, f));
			} 
		}

		//Falls noch keine Teilnehmer eingetragen sind, wird die Anzeige aufgefüllt.
		if(hf.teilnehmerVector.size()==0){

			anwesend.setLayout(new GridLayout(33,1));
			vorname.setLayout(new GridLayout(33,1));
			nickname.setLayout(new GridLayout(33,1));
			nachname.setLayout(new GridLayout(33,1));
			armee.setLayout(new GridLayout(33,1));
			ort.setLayout(new GridLayout(33,1));
			team.setLayout(new GridLayout(33,1));

			for(int i=30;i>0;i--){
				anwesend.add(createLabel("", f));
				vorname.add(createLabel("", f));
				nickname.add(createLabel("", f));
				nachname.add(createLabel("", f));
				armee.add(createLabel("", f));
				ort.add(createLabel("", f));
				team.add(createLabel("", f));
			}
		}

		anmeldePanel.validate();
		
		header.setLayout(new BoxLayout(header,BoxLayout.X_AXIS));

		System.out.println("vorname.getWidth(): "+vorname.getWidth());
		if(anmeldesicht){
			header.add(createHeader("Anwesend",f,anwesend));
		}
		header.add(createHeader("Vorname",f,vorname));
		header.add(createHeader("Nickname",f,nickname));
		header.add(createHeader("Nachname",f,nachname));
		header.add(createHeader("Armee",f,armee));
		header.add(createHeader("Ort",f,ort));
		header.add(createHeader("Team",f,team));
		
		header.setBackground(Color.white);
		sp.setColumnHeaderView(header);

		anmeldePanel.validate();
	}
	
	
	public void init(){
		anmeldePanel.validate();
		resizeHeaders();
		anmeldePanel.validate();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==abbrechenButton){
			setVisible(false);
		} else if(e.getSource()==druckenButton){
			PrinterJob pj = PrinterJob.getPrinterJob();
			KTextPrintable tp = new KTextPrintable();
			tp.hf=hf;
			tp.sicht=KTextPrintable.ANMELDELISTE;
			pj.setPrintable(tp);
			//PageFormat pf = pj.pageDialog(pj.defaultPage());
			if (pj.printDialog()) {
				try {pj.print();}
				catch (PrinterException exc) {
					System.out.println(exc);
				}
			}
		} else if(e.getSource()==endeButton ){
			for(int i=hf.teilnehmerVector.size()-1;i>=0;i--){
				KTeilnehmer tn = hf.teilnehmerVector.get(i);
				if(tn.deleted==tn.anwesend.isSelected()){
					if(tn.anwesend.isSelected()){
						hf.entfernenFenster.wiederherstellen(i);
					}else{
						hf.entfernenFenster.entfernen(i);
					}
				}
			}
			hf.updatePanels();
			setVisible(false);
		} else if(e.getSource() instanceof JButton){
			System.out.println(((JButton)e.getSource()).getText());
			if(((JButton)e.getSource()).getText().equals("Vorname")){
				sortieren("Vorname");
			} else if(((JButton)e.getSource()).getText().equals("Nachname")){
				sortieren("Nachname");
			} else if(((JButton)e.getSource()).getText().equals("Nickname")){
				sortieren("Nickname");
			} else if(((JButton)e.getSource()).getText().equals("Armee")){
				sortieren("Armee");
			} else if(((JButton)e.getSource()).getText().equals("Ort")){
				sortieren("Ort");
			} else if(((JButton)e.getSource()).getText().equals("Team")){
				sortieren("Team");
			}
		} else if(e.getSource()==alle){
			if(alle.isSelected()){
				for(int i=sortLocal.size()-1;i>=0;i--){
//					if(sortLocal.get(i).deleted==false){
						KTeilnehmer tn=sortLocal.get(i);
						tn.anwesend.setSelected(true);
//					} 
				}
			}else{
				for(int i=sortLocal.size()-1;i>=0;i--){
//					if(sortLocal.get(i).deleted==false){
						KTeilnehmer tn=sortLocal.get(i);
						tn.anwesend.setSelected(false);
//					} 
				}
			}
		}else if(e.getSource()==geloeschteVerstecken){
			initList();
		}
	}
	
	public void resizeHeaders(){
		for(int i=0;i<header.getComponentCount();i++){
			if(body.getComponent(i) instanceof JPanel){
				JPanel p =(JPanel)body.getComponent(i);
				if(header.getComponent(i) instanceof JButton){
					JButton b=(JButton)header.getComponent(i);
					b.setMaximumSize(new Dimension(p.getWidth(),35));
					b.setMinimumSize(new Dimension(p.getWidth(),35));
					b.setPreferredSize(new Dimension(p.getWidth(),35));
				}
			}
		}
	}
	
	public JButton createHeader(String s, Font f, JPanel p){
		JButton l = new JButton(s);
		//JLabel l = new JLabel(s);
		l.setMaximumSize(new Dimension(p.getWidth(),35));
		l.setMinimumSize(new Dimension(p.getWidth(),35));
		l.setPreferredSize(new Dimension(p.getWidth(),35));
		l.setBorder(BorderFactory.createRaisedBevelBorder());
		l.addActionListener(this);
		l.setFont(f);
		return l;
	}

	public JButton createLabel(String s, Font f){
		JButton l = new JButton(s);
		l.setBorder(BorderFactory.createEtchedBorder());
		l.setFont(f);
		return l;
	}
	
	public JCheckBox createBox(){
		JCheckBox l = new JCheckBox();
		l.setBorder(BorderFactory.createEtchedBorder());
		return l;
	}
	
	public JTextField createField(String s, Font f){
		JTextField l = new JTextField(s);
		l.setBorder(BorderFactory.createEtchedBorder());
		l.setFont(f);
		l.addKeyListener(this);
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
//		p.setLayout(new GridLayout(hf.teilnehmerVector.size(),1));
		p.setLayout(new GridLayout(sortLocal.size(),1));
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
		//System.out.println("component moved");
		//initWrap(getSize());
	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("component resized");
		initWrap(getSize());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("component shown");
		initWrap(getSize());
	}
	
	Vector<KTeilnehmer> sortLocal = new Vector<KTeilnehmer>();
	String lastButton="";
	boolean dir=true;
	
	public void sortieren(String name){
		KTeilnehmer t;
		sortLocal.clear();
		if(lastButton.equals(name)){
			dir=!dir;
		}
		for(int i=0;i<hf.teilnehmerVector.size();i++){//for(int i=0;i<hf.sortierterVector.size();i++){
			t=hf.teilnehmerVector.get(i);//t=hf.sortierterVector.get(i);
//			if(t.deleted==false){
				int j=0;
				if(name=="Vorname"){
					while(j<sortLocal.size()&&(dir==false?t.vorname.compareTo(sortLocal.get(j).vorname)>0:t.vorname.compareTo(sortLocal.get(j).vorname)<0)){
						j++;
					}
				} else if(name=="Nachname"){
					while(j<sortLocal.size()&&(dir==false?t.nachname.compareTo(sortLocal.get(j).nachname)>0:t.nachname.compareTo(sortLocal.get(j).nachname)<0)){
						j++;
					}
				} else if(name=="Nickname"){
					while(j<sortLocal.size()&&(dir==false?t.nickname.compareTo(sortLocal.get(j).nickname)>0:t.nickname.compareTo(sortLocal.get(j).nickname)<0)){
						j++;
					}
				} else if(name=="Armee"){
					while(j<sortLocal.size()&&(dir==false?t.armee.compareTo(sortLocal.get(j).armee)>0:t.armee.compareTo(sortLocal.get(j).armee)<0)){
						j++;
					}
				} else if(name=="Ort"){
					while(j<sortLocal.size()&&(dir==false?t.ort.compareTo(sortLocal.get(j).ort)>0:t.ort.compareTo(sortLocal.get(j).ort)<0)){
						j++;
					}
				} else if(name=="Team"){
					while(j<sortLocal.size()&&(dir==false?t.team.compareTo(sortLocal.get(j).team)>0:t.team.compareTo(sortLocal.get(j).team)<0)){
						j++;
					}
				}
				sortLocal.insertElementAt(t,j);	
//			}
		}
		lastButton=name;
		initList();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyListener");
		JPanel p = (JPanel)((JTextField)e.getSource()).getParent();
		for(int i = 0; i < p.getComponentCount(); i++){
			if(p.getComponent(i).equals(e.getSource())){
				if(p.equals(vorname)){
					System.out.println("vorname");
					sortLocal.get(p.getComponentCount()-1-i).vorname = ((JTextField)e.getSource()).getText();
					System.out.println(sortLocal.get(p.getComponentCount()-1-i).vorname);
				}else if(p.equals(nickname)){
					System.out.println("nickname");
					sortLocal.get(p.getComponentCount()-1-i).nickname = ((JTextField)e.getSource()).getText();
					System.out.println(sortLocal.get(p.getComponentCount()-1-i).nickname);
				}else if(p.equals(nachname)){
					System.out.println("nachname");
					sortLocal.get(p.getComponentCount()-1-i).nachname = ((JTextField)e.getSource()).getText();
					System.out.println(sortLocal.get(p.getComponentCount()-1-i).nachname);
				}else if(p.equals(armee)){
					System.out.println("armee");
					sortLocal.get(p.getComponentCount()-1-i).armee = ((JTextField)e.getSource()).getText();
					System.out.println(sortLocal.get(p.getComponentCount()-1-i).armee);
				}else if(p.equals(ort)){
					System.out.println("ort");
					sortLocal.get(p.getComponentCount()-1-i).ort = ((JTextField)e.getSource()).getText();
					System.out.println(sortLocal.get(p.getComponentCount()-1-i).ort);
				}else if(p.equals(team)){
					System.out.println("team");
					sortLocal.get(p.getComponentCount()-1-i).team = ((JTextField)e.getSource()).getText();
					System.out.println(sortLocal.get(p.getComponentCount()-1-i).team);
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}