package turman;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class KDynamischerTimerFeld extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KDynamischerTimerFeld(KHauptFenster hf){
		this.hf=hf;
		init();
	}

	KHauptFenster hf;

	//dyn
	TDynamischerTimer dynTimer=null;
	int timerTyp=0;
	int zusTyp=0;
	int takt=0;
	int zeit=0;
	int bgColor=0;

	//Logo
	JCheckBox logoAnzeigen= new JCheckBox("Logo anzeigen");
	JButton logoButton= new JButton("LogoAuswählen");
	String imageName="";
	JLabel logoLabel=new JLabel();

	//Hintergrund
	JRadioButton bgSchwarz = new JRadioButton("Schwarz");
	JRadioButton bgGelb = new JRadioButton("Gelb");
	JRadioButton bgGrau = new JRadioButton("Grau");
	JRadioButton bgRot = new JRadioButton("Rot");
	JRadioButton bgGruen = new JRadioButton("Grün");
	ButtonGroup bgGruppe = new ButtonGroup();
	
	//Agenda/Timer
	JRadioButton agenda = new JRadioButton("Agenda");
	JRadioButton timer = new JRadioButton("Timer   ");
	JLabel timerLabel = new JLabel("Spielzeit(Minuten)");
	JTextField timerFeld = new JTextField("10");
	ButtonGroup timerAgendaGruppe = new ButtonGroup();
	JButton aktualisieren= new JButton("Starten/Aktualisieren");
	JButton plus5= new JButton("+5 Minuten");
	JButton minus5= new JButton("-5 Minuten");
	JButton startNextPhase = new JButton("Nächster Agendapunkt");

	//Pukte/Begegnungen
	JRadioButton punkte = new JRadioButton("Punkte");
	JRadioButton begegnungen = new JRadioButton("Begegnungen");
	ButtonGroup punkteBegegnungenGruppe = new ButtonGroup();
	JCheckBox punkteBegegnungenAnzeigen= new JCheckBox("Punkte/Begegnungen anzeigen");
	JLabel taktLabel = new JLabel("Aktualisierungszeit (*10ms)");
	JTextField taktFeld = new JTextField("5");

	//Agendaberechnung
	JButton add = new JButton("Neue Zeile unterhalb");
	JButton add2 = new JButton("Neue Zeile oberhalb");
	JButton remove = new JButton("Aktuelle Zeile löschen");
	JButton copy= new JButton("Zeile Kopieren");
	JButton paste = new JButton("Zeile Einfügen");
	JButton save = new JButton("Agenda speichern");
	JTable table;
	JScrollPane scrollPane;
	JPanel ablauf= new JPanel();
	Object saveObject[]=null;


	JLabel aheader = new JLabel("Turnierablauf");

	public void init(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//Dateiauswahl für das Logo
		JPanel p4 = new JPanel();
		p4.setBorder(BorderFactory.createTitledBorder("Logo"));
		p4.setLayout(new GridLayout(1,1));
		JPanel p44 = new JPanel();
		p4.add(p44);
		p44.setLayout(new BoxLayout(p44,BoxLayout.X_AXIS));
		p44.add(logoAnzeigen);
		p44.add(logoButton);
		p44.add(new JLabel(" "));
		p44.add(logoLabel);
		//		p4.add(new JLabel());
		//		p4.add(new JLabel());
		logoAnzeigen.addActionListener(this);
		logoButton.setEnabled(false);
		logoButton.addActionListener(this);

		//Hintergrund
		JPanel p3 = new JPanel();
		p3.setBorder(BorderFactory.createTitledBorder("Hintergrundfarbe"));
		p3.setLayout(new GridLayout(1,1));
		JPanel p33 = new JPanel();
		p3.add(p33);
		p33.setLayout(new BoxLayout(p33,BoxLayout.X_AXIS));
		p33.add(bgSchwarz);
		p33.add(bgGelb);
		p33.add(bgGrau);
		p33.add(bgRot);
		p33.add(bgGruen);
		bgGruppe.add(bgSchwarz);
		bgGruppe.add(bgGelb);
		bgGruppe.add(bgGrau);
		bgGruppe.add(bgRot);
		bgGruppe.add(bgGruen);
		bgSchwarz.setSelected(true);
		
		//Timer/Agenda
		JPanel p5 = new JPanel();
		p5.setBorder(BorderFactory.createTitledBorder("Timer/Agenda"));
		p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));

		//Agenda
		JPanel p51=new JPanel();
		JPanel p511=new JPanel();
		p51.setLayout(new BoxLayout(p51, BoxLayout.X_AXIS));
		p51.setBorder(BorderFactory.createEtchedBorder());
		p51.add(agenda);

		p511.setLayout(new BoxLayout(p511, BoxLayout.Y_AXIS));
		String [] agendaHeaders={"Ereignis","TT","MM","JJJJ","HH","MM"};
		String [][] agendaEintraege={{"Anmeldung","13","07","2012","09","00"},
				{"Infos","13","07","2012","09","45"},
				{"Turnierrunde 1","13","07","2012","10","00"},
				{"Mittagspause","13","07","2012","12","00"}};
		table=new JTable(new DefaultTableModel(agendaEintraege,agendaHeaders));
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		JPanel hPanel= new JPanel();
		hPanel.add(aheader);
		hPanel.setLayout(new GridLayout(1,1));
		p511.add(hPanel);
		p511.add(scrollPane);
		/*p511.add(ablauf);
		ablauf.setLayout(new BoxLayout(ablauf, BoxLayout.Y_AXIS));*/
		aheader.setFont(new Font("Comic Sans Serif",1,18));
		aheader.setHorizontalAlignment(JLabel.LEFT);
		table.setFont(new Font("Comic Sans Serif",1,18));
		JPanel agendaActionPanel = new JPanel();
		agendaActionPanel.setLayout(new GridLayout(2,5));
		
		add.addActionListener(this);
		add2.addActionListener(this);
		remove.addActionListener(this);
		save.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		//Zeile 1
		agendaActionPanel.add(add);
		agendaActionPanel.add(remove);
		agendaActionPanel.add(copy);
		agendaActionPanel.add(new JLabel());
		agendaActionPanel.add(new JLabel());
		//Zeile 2
		agendaActionPanel.add(add2);
		agendaActionPanel.add(new JLabel());
		agendaActionPanel.add(paste);
		agendaActionPanel.add(new JLabel());
		agendaActionPanel.add(save);
		p511.add(agendaActionPanel);
		p51.add(p511);
		agendaLaden();

		//Timer
		JPanel p52=new JPanel();
		p52.setLayout(new BoxLayout(p52, BoxLayout.X_AXIS));
		p52.setBorder(BorderFactory.createEtchedBorder());
		p52.add(timer);
		p52.add(timerLabel);
		p52.add(timerFeld);
		p52.add(Box.createVerticalStrut(p52.getHeight()-timer.getHeight()-timerLabel.getHeight()-timerFeld.getHeight()));

		timerAgendaGruppe.add(agenda);
		timerAgendaGruppe.add(timer);
		timer.setSelected(true);
		agenda.addActionListener(this);
		timer.addActionListener(this);
		//agenda.setEnabled(false);
		//add.setEnabled(false);
		//remove.setEnabled(false);
		//aheader.setEnabled(false);

		p5.add(p52);
		p5.add(p51);


		JPanel p6 = new JPanel();
		p6.setBorder(BorderFactory.createTitledBorder("Zusatzanzeige"));
		p6.setLayout(new GridLayout(1,1));
		JPanel p66 = new JPanel();
		p66.setLayout(new BoxLayout(p66,BoxLayout.X_AXIS));
		p6.add(p66);
		p66.add(punkteBegegnungenAnzeigen);
		p66.add(new JLabel("   "));
		p66.add(punkte);
		p66.add(begegnungen);
		p66.add(new JLabel("   "));
		p66.add(taktLabel);
		p66.add(taktFeld);

		punkteBegegnungenGruppe.add(punkte);
		punkteBegegnungenGruppe.add(begegnungen);

		punkte.addActionListener(this);
		punkteBegegnungenAnzeigen.addActionListener(this);
		begegnungen.addActionListener(this);

		punkte.setSelected(true);
		punkte.setEnabled(false);
		begegnungen.setEnabled(false);

		JPanel p7 = new JPanel();
		p7.setLayout(new GridLayout(1,6));
		p7.add(aktualisieren);
		aktualisieren.addActionListener(this);
		p7.add(new JLabel());
		p7.add(plus5);
		plus5.addActionListener(this);
		p7.add(minus5);
		minus5.addActionListener(this);
		p7.add(new JLabel());
		p7.add(startNextPhase);
		startNextPhase.addActionListener(this);
		plus5.setEnabled(false);
		minus5.setEnabled(false);
		startNextPhase.setEnabled(false);

		JPanel p8 = new JPanel();
		p8.setLayout(new GridLayout(40,1));
		p8.add(new JLabel("a"));
		p8.getComponent(0).setForeground(p8.getBackground());

		add(p4);
		add(p3);
		add(p5);
		add(p6);
		add(p7);
		add(p8);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src=arg0.getSource();
		if(src==agenda || src==timer){
			//TODO
		}else if(src==aktualisieren){
			agendaSpeichern();
			if(dynTimer==null || (dynTimer!=null && !dynTimer.frame.isVisible())){
				timerTyp=agenda.isSelected()?0:1;
				zusTyp=begegnungen.isSelected()?1:0;
				zeit=Integer.parseInt(timerFeld.getText());
				takt=Integer.parseInt(taktFeld.getText());
				if(timer.isSelected()){
					timerTyp=0;
				}else{
					timerTyp=1;
				}
				if(bgSchwarz.isSelected()){
					bgColor=0;
				}else if(bgGelb.isSelected()){
					bgColor=1;
				}else if(bgGrau.isSelected()){
					bgColor=2;
				}else if(bgRot.isSelected()){
					bgColor=3;
				}else if(bgGruen.isSelected()){
					bgColor=4;
				}
				
				dynTimer=new TDynamischerTimer(hf, zeit, takt, timerTyp,zusTyp,punkteBegegnungenAnzeigen.isSelected(),logoAnzeigen.isSelected()?imageName:"",bgColor);
			}else if(dynTimer.frame.isVisible()){
				timerTyp=agenda.isSelected()?0:1;
				zusTyp=begegnungen.isSelected()?1:0;
				zeit=Integer.parseInt(timerFeld.getText());
				takt=Integer.parseInt(taktFeld.getText());
				if(timer.isSelected()){
					timerTyp=0;
				}else{
					timerTyp=1;
				}
				if(bgSchwarz.isSelected()){
					bgColor=0;
				}else if(bgGelb.isSelected()){
					bgColor=1;
				}else if(bgGrau.isSelected()){
					bgColor=2;
				}else if(bgRot.isSelected()){
					bgColor=3;
				}else if(bgGruen.isSelected()){
					bgColor=4;
				}
				dynTimer.setVars(zeit, takt, timerTyp,zusTyp,punkteBegegnungenAnzeigen.isSelected(),logoAnzeigen.isSelected()?imageName:"",bgColor);
				dynTimer.frame.setVisible(true);
			}
			
				plus5.setEnabled(timerTyp==1);
				minus5.setEnabled(timerTyp==1);
				startNextPhase.setEnabled(timerTyp==1);
			
		}else if(src==punkte || src==begegnungen){
			//TODO
		}else if(src==punkteBegegnungenAnzeigen){
			punkte.setEnabled(punkteBegegnungenAnzeigen.isSelected());
			begegnungen.setEnabled(punkteBegegnungenAnzeigen.isSelected());
			taktFeld.setEditable(punkteBegegnungenAnzeigen.isSelected());
		} else if(src==add){
			if(table.getSelectedRow()>-1){
				((DefaultTableModel)table.getModel()).insertRow(table.getSelectedRow()+1,new Object[]{"","","","","",""});
			}
		} else if(src==add2){
			if(table.getSelectedRow()>-1){
				((DefaultTableModel)table.getModel()).insertRow(table.getSelectedRow(),new Object[]{"","","","","",""});
			}
		}else if(src==remove){
			if(table.getSelectedRow()>-1){
				((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
			}
		}else if(src==copy){
			if(table.getSelectedRow()>-1){
				saveObject=new Object[]{"","","","","",""};
				saveObject[0]=((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 0);
				saveObject[1]=((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 1);
				saveObject[2]=((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 2);
				saveObject[3]=((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 3);
				saveObject[4]=((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 4);
				saveObject[5]=((DefaultTableModel)table.getModel()).getValueAt(table.getSelectedRow(), 5);
			}
		}else if(src==paste){
			if(table.getSelectedRow()>-1 && saveObject!=null){
				((DefaultTableModel)table.getModel()).insertRow(table.getSelectedRow()+1,saveObject);
			}
		} else if(src==save){
			agendaSpeichern();
		}else if(src==logoAnzeigen){
			logoButton.setEnabled(logoAnzeigen.isSelected());
		} else if(src==logoButton){
			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
			FileFilter ff = new FileNameExtensionFilter("JPEG (.jpg;.jpeg;.jpe;.jtif)",new String[]{"jpg","jpeg","jpe","jtif"});
			FileFilter ff2 = new FileNameExtensionFilter("PNG (.png)",new String[]{"png"});
			fileChooser.addChoosableFileFilter(ff);
			fileChooser.addChoosableFileFilter(ff2);
			fileChooser.setFileFilter(ff2);

			if(fileChooser.showOpenDialog(hf)==JFileChooser.APPROVE_OPTION){
				File f =new File(fileChooser.getSelectedFile().toString());
				if(f !=null){
					imageName=f.getAbsolutePath();
					logoLabel.setText(imageName);
				}
			}
		}else if(src==plus5){
			add5minutes();
		}else if(src==minus5){
			subtract5minutes();
		}else if(src==startNextPhase){
			startNext();
		}

	}	

	public void agendaLaden(){

		while(table.getModel().getRowCount()>0){
			((DefaultTableModel)table.getModel()).removeRow(0);
		}

		File f = new File("agenda");
		FileReader fr;
		int read=-1;
		String s="";
		try {
			fr = new FileReader(f);

			while(true){
				read=fr.read();
				if(read==-1){
					break;
				}else{
					s+=(char)read;
				}
			}
			fr.close();

			//Splitten in Optionen
			String[] optionen=s.split("\r\n");

			for(int i=0;i<optionen.length;i++){
				if(!optionen[i].equals("")){
					String dates[]= optionen[i].split(";");
					((DefaultTableModel)table.getModel()).addRow(dates);
				}
			}
			//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//table.getColumnModel().getColumn(0).setMinWidth(getWidth()/2);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agendaSpeichern(){
		File f = new File("agenda");
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			for(int i=0; i<table.getRowCount(); i++){

				fw.write((String)table.getModel().getValueAt(i, 0)+";");
				fw.write((String)table.getModel().getValueAt(i, 1)+";");
				fw.write((String)table.getModel().getValueAt(i, 2)+";");
				fw.write((String)table.getModel().getValueAt(i, 3)+";");
				fw.write((String)table.getModel().getValueAt(i, 4)+";");
				fw.write((String)table.getModel().getValueAt(i, 5)+"\r\n");
			}
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void add5minutes(){
		for(int i=0;i<hf.agendaVector.size();i++){
			long acttime=hf.agendaVector.get(i).zeitBis();
			if(acttime>0){
				hf.agendaVector.get(i).add5Minutes();
				String sArr[] = hf.agendaVector.get(i).getTimeArray();
				((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
			}
		}
		aktualisieren.doClick();	
	}

	public void subtract5minutes(){
		for(int i=0;i<hf.agendaVector.size();i++){
			long acttime=hf.agendaVector.get(i).zeitBis();
			if(acttime>0){
				hf.agendaVector.get(i).subtract5Minutes();
				String sArr[] = hf.agendaVector.get(i).getTimeArray();
				((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
			}
		}
		aktualisieren.doClick();	
	}

	public void startNext(){
		int i;
		long acttime=0;
		for(i=0;i<hf.agendaVector.size();i++){
			acttime=hf.agendaVector.get(i).zeitBis();
			if(acttime>0){
				hf.agendaVector.get(i).setCalendarToNow();
				String sArr[] = hf.agendaVector.get(i).getTimeArray();
				((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
				i++;
				break;
			}
		}
		int minutes = (int)(acttime/60000);

		for(;i<hf.agendaVector.size();i++){
			hf.agendaVector.get(i).changeTime(-minutes);
			String sArr[] = hf.agendaVector.get(i).getTimeArray();
			((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
			((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
			((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
			((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
			((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
			((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
		}

		aktualisieren.doClick();

	}

}
