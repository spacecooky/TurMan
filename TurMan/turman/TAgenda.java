package turman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TAgenda extends Thread implements ActionListener, MouseListener{

	public TAgenda(KHauptFenster hf){
		this.hf=hf;
		
		frame.add(panel);
		
		panel.setLayout(new BorderLayout());
		panel.add(left,BorderLayout.WEST);
		panel.add(center,BorderLayout.CENTER);
		
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		table=new JTable(new DefaultTableModel(eintraege,headers));
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		left.add(aheader);
		left.add(scrollPane);
		left.add(ablauf);
		ablauf.setLayout(new BoxLayout(ablauf, BoxLayout.Y_AXIS));
		ablauf.setBackground(Color.black);
		//left.add(Box.createRigidArea(new Dimension(0,100)));
		left.add(add);
		add.addActionListener(this);
		left.add(remove);
		remove.addActionListener(this);
		left.add(ok);
		ok.addActionListener(this);
		left.add(cancel);
		cancel.addActionListener(this);
		left.add(plus5);
		plus5.addActionListener(this);
		left.add(minus5);
		minus5.addActionListener(this);
		left.add(startNextPhase);
		startNextPhase.addActionListener(this);
		
		center.add(agendaPunkt);
		center.add(timeLabel);
		frame.setTitle("Agenda");
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		//frame.setSize(new Dimension(1024,300));
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.addMouseListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		WindowListener meinListener=new WindowAdapter(){
			public void windowClosing(WindowEvent ereignis){
				speichern();
				run=false;
				frame.dispose();
			}
		};
		frame.addWindowListener(meinListener);
		left.setBackground(Color.black);
		left.setBorder(BorderFactory.createLineBorder(Color.white));
		table.setBackground(Color.black);
		table.setForeground(Color.white);
		aheader.setForeground(Color.white);
		aheader.setFont(new Font("Comic Sans Serif",1,18));
		aheader.setHorizontalAlignment(JLabel.LEFT);
		table.setFont(new Font("Comic Sans Serif",1,18));
		center.setBackground(Color.black);
		center.setBorder(BorderFactory.createLineBorder(Color.white));
		agendaPunkt.setForeground(Color.white);
		agendaPunkt.setBackground(Color.black);
		//agendaPunkt.setLineWrap(true);
		timeLabel.setForeground(Color.white);
		add.setBackground(Color.black);
		add.setForeground(Color.white);
		remove.setBackground(Color.black);
		remove.setForeground(Color.white);
		ok.setBackground(Color.black);
		ok.setForeground(Color.white);
		cancel.setBackground(Color.black);
		cancel.setForeground(Color.white);
		plus5.setBackground(Color.black);
		plus5.setForeground(Color.white);
		minus5.setBackground(Color.black);
		minus5.setForeground(Color.white);
		startNextPhase.setBackground(Color.black);
		startNextPhase.setForeground(Color.white);
			
		int size1 = Toolkit.getDefaultToolkit().getScreenSize().width/6;
		int size2 = size1/2;
		timeLabel.setFont(new Font("Comic Sans Serif",1,size1));
		agendaPunkt.setFont(new Font("Comic Sans Serif",1,size2));
		
		agendaPunkt.setFocusable(false);
		timeLabel.setFocusable(false);
		plus5.setVisible(false);
		minus5.setVisible(false);
		startNextPhase.setVisible(false);
		laden();
		frame.setVisible(true);
	}

	
	JFrame frame= new JFrame();
	JPanel panel= new JPanel();
	JPanel left= new JPanel();
	JPanel center = new JPanel();
	
	JTable table;
	JScrollPane scrollPane;
	JPanel ablauf= new JPanel();
	String [] headers={"Ereignis","TT","MM","JJJJ","HH","MM"};
	String [][] eintraege={{"Anmeldung","13","07","2012","09","00"},
						   {"Hinweise auf Turniersonderregeln und Bekanntgabe der ersten Paarungen","13","07","2012","09","45"},
						   {"Turnierrunde 1","13","07","2012","10","00"},
						   {"Mittagspause + Bemalwertung","13","07","2012","12","00"}};
	Vector<KAgendaEintrag> agendaEintraege=new Vector<KAgendaEintrag>();
	JLabel aheader = new JLabel("Turnierablauf");
	JTextArea agendaPunkt= new JTextArea("Zeit bis zum Start:");
	JLabel timeLabel= new JLabel("00:00:00");
	JTextField field=new JTextField("");
	
	JButton add = new JButton("Neue Zeile anhängen");
	JButton remove = new JButton("Aktuelle Zeile löschen");
	JButton ok = new JButton("Agenda Starten");
	JButton cancel = new JButton("Abbrechen");
	JButton plus5 = new JButton("+5 Minuten");
	JButton minus5 = new JButton("-5 Minuten");
	JButton startNextPhase = new JButton("Nächsten Agendapunkt starten");
	
	KHauptFenster hf;
	long time;
	boolean run=true;

	public void run(){
		left.setPreferredSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
		left.setMinimumSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
		left.setMaximumSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
		for(int i=0;i<left.getComponentCount();i++){
			if(left.getComponent(i) instanceof JButton){
				((JButton)left.getComponent(i)).setAlignmentX(Component.LEFT_ALIGNMENT);
				((JButton)left.getComponent(i)).setPreferredSize(new Dimension(left.getWidth(),30));
				((JButton)left.getComponent(i)).setMaximumSize(new Dimension(left.getWidth(),30));
				((JButton)left.getComponent(i)).setMinimumSize(new Dimension(left.getWidth(),30));
			}else if(left.getComponent(i) instanceof JLabel){
				((JLabel)left.getComponent(i)).setAlignmentX(Component.LEFT_ALIGNMENT);
			}else if(left.getComponent(i) instanceof JPanel){
				((JPanel)left.getComponent(i)).setAlignmentX(Component.LEFT_ALIGNMENT);
			}
		}
			scrollPane.setVisible(false);
			ok.setVisible(false);
			cancel.setVisible(false);
			add.setVisible(false);
			remove.setVisible(false);
			agendaEintraege.clear();
			ablauf.removeAll();
			for(int i=0; i<table.getRowCount(); i++){
				String name = (String)table.getModel().getValueAt(i, 0);
				String day = (String)table.getModel().getValueAt(i, 1);
				String month = (String)table.getModel().getValueAt(i, 2);
				String year = (String)table.getModel().getValueAt(i, 3);
				String hour = (String)table.getModel().getValueAt(i, 4);
				String minute = (String)table.getModel().getValueAt(i, 5);
				
				try{
					agendaEintraege.add(new KAgendaEintrag(name, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute)));
					JTextArea ta =new JTextArea(day+"."+month+"."+year+" "+hour+":"+minute+":\n"+name);
					ta.setFont(new Font("Comic Sans Serif",Font.PLAIN,18));
					ta.setLineWrap(true);
					ta.setBackground(Color.black);
					ta.setForeground(Color.lightGray);
					ablauf.add(ta);
				}catch(NumberFormatException e){
					//TODO Fehlerdialog
					System.err.println("err");
				}
			}
			
			plus5.setVisible(true);
			minus5.setVisible(true);
			startNextPhase.setVisible(true);
			long acttime=0;
			while(run){
				int agendaCounter=-1;
				for(int i=0;i<agendaEintraege.size();i++){
					acttime=agendaEintraege.get(i).zeitBis();
					//System.out.println(acttime);
					if(acttime>0){
						agendaCounter=i-1;
						break;
					} else{
						((JTextArea)ablauf.getComponent(i)).setFont(new Font("Comic Sans Serif",Font.PLAIN,18));
						((JTextArea)ablauf.getComponent(i)).setForeground(Color.lightGray);
					}
				}
			
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				sdf.setTimeZone(TimeZone.getTimeZone("GMT+00"));
				Date actDate = new Date(acttime);
				timeLabel.setText(sdf.format(actDate));
				
				if(agendaCounter>-1){
					agendaPunkt.setText((String)table.getModel().getValueAt(agendaCounter, 0));
					((JTextArea)ablauf.getComponent(agendaCounter)).setFont(new Font("Comic Sans Serif",Font.BOLD,18));
					((JTextArea)ablauf.getComponent(agendaCounter)).setForeground(Color.white);
				}
				
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src=arg0.getSource();
		if(src==ok){
			speichern();
			start();
		} else if(src==cancel){
			frame.setVisible(false);
		} else if(src==add){
			((DefaultTableModel)table.getModel()).addRow(new Object[]{"","","","","",""});
		} else if(src==remove){
			if(table.getSelectedRow()>-1){
				((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
			}
		} else if(src==plus5){
			add5minutes();
		} else if(src==minus5){
			subtract5minutes();
		}else if(src==startNextPhase){
			startNext();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		int mXPos = 0;
		int mYPos = 0;
		int mWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int mHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		if(frame.isUndecorated()){
			
			frame.dispose(); 
			frame.setResizable(true);
			frame.setUndecorated(false); 
			frame.setBounds(mXPos, mYPos, mWidth, mHeight);
			left.setPreferredSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
			left.setMinimumSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
			left.setMaximumSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
			frame.setVisible(true);
			
			
		}else{
			frame.dispose(); 
			frame.setResizable(false);
			frame.setUndecorated(true); 
			frame.setBounds(mXPos, mYPos, mWidth, mHeight);
			left.setPreferredSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
			left.setMinimumSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
			left.setMaximumSize(new Dimension(panel.getWidth()/4,panel.getHeight()));
			frame.setVisible(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}

	public void speichern(){
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
	
	public void laden(){
		
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

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void adaptPanel(){
		int restHeight=panel.getHeight()/25-table.getRowCount()-1;
		if(restHeight<0){
			restHeight=0;
		}
		left.setLayout(new GridLayout(table.getRowCount()+1+restHeight, 1,0,0));
	}
	
	public void add5minutes(){
		for(int i=0;i<agendaEintraege.size();i++){
			long acttime=agendaEintraege.get(i).zeitBis();
			if(acttime>0){
				agendaEintraege.get(i).add5Minutes();
				String sArr[] = agendaEintraege.get(i).getTimeArray();
				String s = agendaEintraege.get(i).getTimeString();
				((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
				((JTextArea)ablauf.getComponent(i)).setText(s);	
			}
		}
	}
	
	public void subtract5minutes(){
		for(int i=0;i<agendaEintraege.size();i++){
			long acttime=agendaEintraege.get(i).zeitBis();
			if(acttime>0){
				agendaEintraege.get(i).subtract5Minutes();
				String sArr[] = agendaEintraege.get(i).getTimeArray();
				String s = agendaEintraege.get(i).getTimeString();
				((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
				((JTextArea)ablauf.getComponent(i)).setText(s);	
			}
		}
	}
	
	public void startNext(){
		for(int i=0;i<agendaEintraege.size();i++){
			long acttime=agendaEintraege.get(i).zeitBis();
			if(acttime>0){
				agendaEintraege.get(i).setCalendarToNow();
				String sArr[] = agendaEintraege.get(i).getTimeArray();
				String s = agendaEintraege.get(i).getTimeString();
				((DefaultTableModel)table.getModel()).setValueAt(sArr[0], i, 0);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[1], i, 1);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[2], i, 2);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[3], i, 3);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[4], i, 4);
				((DefaultTableModel)table.getModel()).setValueAt(sArr[5], i, 5);
				((JTextArea)ablauf.getComponent(i)).setText(s);	
				break;
			}
		}
	}
}
