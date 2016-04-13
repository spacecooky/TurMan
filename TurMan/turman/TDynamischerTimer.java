package turman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class TDynamischerTimer extends Thread implements MouseListener,ComponentListener{

	public TDynamischerTimer(KHauptFenster hf, int zeit, int takt, int timerTyp,int zusTyp,boolean zusTypAnzeigen,String logo,int bgColor){
		System.out.println("Constructor");
		this.hf=hf;
		this.zeit=zeit*60*1000;
		this.takt=takt*10;
		this.timerTyp=timerTyp;
		this.zusTyp=zusTyp;
		this.zusTypAnzeigen=zusTypAnzeigen;
		this.logo=logo;
		this.bgColor=bgColor;
		setColors();
		if(timerTyp==0){
			timerFaktor=zusTypAnzeigen?10:5;
		}else{
			timerFaktor=20;
		}
		initAgendaVals();
		start();
	}

	public void setVars(int zeit, int takt, int timerTyp,int zusTyp,boolean zusTypAnzeigen,String logo,int bgColor){
		this.zeit=zeit*60*1000;
		this.takt=takt*10;
		this.timerTyp=timerTyp;
		this.zusTyp=zusTyp;
		this.zusTypAnzeigen=zusTypAnzeigen;
		this.logo=logo;
		this.bgColor=bgColor;
		setColors();
		logoPanel.setBackground(stdBg);
		zeitPanel.setBackground(stdBg);
		aktionPanel.setBackground(stdBg);
		zeitLabel.setForeground(stdFg);
		aktionLabel.setForeground(stdFg);
		typPanel.setBackground(stdBg);
		typPanel.setForeground(stdFg);
		setImage();
		if(timerTyp==0){
			timerFaktor=zusTypAnzeigen?10:5;
		}else{
			timerFaktor=20;
		}
		initAgendaVals();
		adaptPanel();
	}

	public void setColors(){
		if(bgColor==0){
			stdBg = Color.black;
			stdFg = Color.white;
			spFg = Color.gray;
		}else if(bgColor==1){
			stdBg = Color.yellow;
			stdFg = Color.black;
			spFg = Color.gray;
		}else if(bgColor==2){
			stdBg = Color.gray;
			stdFg = Color.black;
			spFg = Color.lightGray;
		}else if(bgColor==3){
			stdBg = Color.red;
			stdFg = Color.black;
			spFg = Color.white;
		}else if(bgColor==4){
			stdBg = Color.green;
			stdFg = Color.black;
			spFg = Color.white;
		}
	}
	
	public void initAgendaVals(){
		JTable table = hf.dynamischerTimerFeld.table;
		hf.agendaVector.clear();
		for(int i=0; i<table.getRowCount(); i++){
			String name = (String)table.getModel().getValueAt(i, 0);
			String day = (String)table.getModel().getValueAt(i, 1);
			String month = (String)table.getModel().getValueAt(i, 2);
			String year = (String)table.getModel().getValueAt(i, 3);
			String hour = (String)table.getModel().getValueAt(i, 4);
			String minute = (String)table.getModel().getValueAt(i, 5);

			try{
				hf.agendaVector.add(new KAgendaEintrag(name, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute)));
			}catch(NumberFormatException e){
				//TODO Fehlerdialog
				System.err.println("err");
			}
		}
	}

	JFrame frame= new JFrame();
	JPanel hauptPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JPanel zeitPanel = new JPanel();
	JPanel aktionPanel = new JPanel();
	JLabel zeitLabel = new JLabel("",JLabel.CENTER);
	JLabel aktionLabel = new JLabel("Zeit bis zum Start:",JLabel.CENTER);
	int zeitFontHeight=0;
	int xOffset=0;
	int aktAgendaPunkt=-1;
	Color stdBg = Color.black;
	Color stdFg = Color.white;
	Color spFg = Color.gray;
	JPanel typPanel = new JPanel(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unused")
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			
			if(timerTyp==0){
				xOffset=0;
			}else{
				//AgendaPunkte
				xOffset=400;
				JTable table = hf.dynamischerTimerFeld.table;
				g.setFont(font2);
				g.setColor(stdFg);
				g.drawLine(xOffset+50, 0, xOffset+50, typPanel.getHeight());
				g.drawLine(xOffset+52, 0, xOffset+52, typPanel.getHeight());

				g.drawString("Agenda:",10,50);
				for(int i=0; i<table.getRowCount(); i++){
					String name = (String)table.getModel().getValueAt(i, 0);
					String day = (String)table.getModel().getValueAt(i, 1);
					String month = (String)table.getModel().getValueAt(i, 2);
					String year = (String)table.getModel().getValueAt(i, 3);
					String hour = (String)table.getModel().getValueAt(i, 4);
					String minute = (String)table.getModel().getValueAt(i, 5);
					if(aktAgendaPunkt==i){
						g.setColor(stdFg);
						g.setFont(font2);
					}else{
						g.setColor(spFg);
						g.setFont(font);
					}
					g.drawString(hour+":"+minute+": "+name,10,(g.getFontMetrics(font).getHeight())*(i+1)+50);
					//g.drawString(day+"."+month+"."+year+" "+hour+":"+minute+":",10,(g.getFontMetrics(font).getHeight())*i*2+50);
					//g.drawString(name,10,(g.getFontMetrics(font).getHeight())*i*2+(g.getFontMetrics(font).getHeight())+50);

				}
				g.setColor(stdFg);

			}

			if(zusTypAnzeigen){
				if(zusTyp==0){
					g.setFont(font2);

					//////////////////////////// Platzierungen ////////////////////////
					g.setFont(font);
					int startVal=0;
					int begegnungslaenge=(g.getFontMetrics(font).getHeight()+5)*(hf.sortierterVector.size()+1);
					int startVal2=(begegnungslaenge>typPanel.getHeight())?begegnungslaenge:(typPanel.getHeight());
					System.out.println("StartVal "+startVal);
					System.out.println("Begegnungslaenge "+begegnungslaenge);
					System.out.println("StartVal2 "+startVal2);

					if(((g.getFontMetrics(font).getHeight()+5)*hf.sortierterVector.size()+50)<typPanel.getHeight()){
						posVerschiebung=0;
					}

					String kopfPrim="";
					//Kopfzeile Primär
					if(hf.optionenFeldVar.pPunkte.isSelected()){
						kopfPrim="Primär";
					} else if(hf.optionenFeldVar.pRPI.isSelected()){
						kopfPrim="Primär (RPI)";
					} else if(hf.optionenFeldVar.pStrength.isSelected()){
						kopfPrim="Primär (Strenght of Schedule)";
					}
					String kopfSek="";
					//Kopfzeile Sekundär
					if(hf.optionenFeldVar.sPunkte.isSelected()){
						kopfSek="Sekundär";
					} else if(hf.optionenFeldVar.sRPI.isSelected()){
						kopfSek="Sekundär (RPI)";
					} else if(hf.optionenFeldVar.sStrength.isSelected()){
						kopfSek="Sekundär (Strenght of Schedule)";
					} else if(hf.optionenFeldVar.sSOS.isSelected()){
						kopfSek="Sekundär (SOS)";
					} /*else if(hf.optionenFeldVar.sSOOS.isSelected()){
						kopfSek="Sekundär (SOOS)";
					} */
					String kopfTer="";
					//Kopfzeile Teriär
					if(hf.optionenFeldVar.tPunkte.isSelected()){
						kopfTer="Tertiär";
					} else if(hf.optionenFeldVar.tRPI.isSelected()){
						kopfTer="Tertiär (RPI)";
					} else if(hf.optionenFeldVar.tStrength.isSelected()){
						kopfTer="Tertiär (Strenght of Schedule)";
					} else if(hf.optionenFeldVar.tSOS.isSelected()){
						kopfTer="Tertiär (SOS)";
					} else if(hf.optionenFeldVar.tSOOS.isSelected()){
						kopfTer="Tertiär (SOOS)";
					} 

					//Max Längenberechnungen
					FontMetrics fontMetrics = g.getFontMetrics();
					int nameWidth = fontMetrics.stringWidth("Name");
					int primWidth = fontMetrics.stringWidth(kopfPrim);
					int sekWidth = fontMetrics.stringWidth(kopfSek);
					int terWidth = fontMetrics.stringWidth(kopfTer);
					for (int i=0;i<hf.sortierterVector.size();i++){
						KTeilnehmer t=hf.sortierterVector.get(i);
						nameWidth=(fontMetrics.stringWidth(t.vorname+" "+t.nachname)>nameWidth)?(fontMetrics.stringWidth(t.vorname+" "+t.nachname)):nameWidth;
						primWidth=(fontMetrics.stringWidth(""+t.erstwertung)>primWidth)?(fontMetrics.stringWidth(""+t.erstwertung)):primWidth;
						sekWidth=(fontMetrics.stringWidth(""+t.zweitwertung)>sekWidth)?(fontMetrics.stringWidth(""+t.zweitwertung)):sekWidth;
						terWidth=(fontMetrics.stringWidth(""+t.drittwertung)>terWidth)?(fontMetrics.stringWidth(""+t.drittwertung)):terWidth;
					}


					//for (int i=hf.sortierterVector.size()-1;i>=0;i--){
					for (int i=0;i<hf.sortierterVector.size();i++){
						KTeilnehmer t=hf.sortierterVector.get(i);
						g.drawString(laengeAnpassenVorne(Integer.toString(t.platz), 6),xOffset+75,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(t.vorname+" "+t.nachname,xOffset+200,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(""+t.erstwertung,xOffset+240+nameWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						if(!hf.optionenFeldVar.sKeine.isSelected()){
							g.drawString(""+t.zweitwertung,xOffset+280+nameWidth+primWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
						if(!hf.optionenFeldVar.tKeine.isSelected()){
							g.drawString(""+t.drittwertung,xOffset+320+nameWidth+primWidth+sekWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						} 
					}

					int newPosVerschiebung=posVerschiebung+startVal2;
					//for (int i=hf.sortierterVector.size()-1;i>=0;i--){
					for (int i=0;i<hf.sortierterVector.size();i++){
						KTeilnehmer t=hf.sortierterVector.get(i);
						g.drawString(laengeAnpassenVorne(Integer.toString(t.platz), 6),xOffset+75,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(t.vorname+" "+t.nachname,xOffset+200,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(""+t.erstwertung,xOffset+240+nameWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						if(!hf.optionenFeldVar.sKeine.isSelected()){
							g.drawString(""+t.zweitwertung,xOffset+280+nameWidth+primWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
						if(!hf.optionenFeldVar.tKeine.isSelected()){
							g.drawString(""+t.drittwertung,xOffset+320+nameWidth+primWidth+sekWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						} 
					}

					//Kopfzeile
					g.setColor(stdBg);
					g.fillRect(0, 0, typPanel.getWidth(), 30);
					g.setColor(stdFg);
					g.drawString("Platz",xOffset+75,20);
					g.drawString("Name",xOffset+200,20);
					g.drawString(kopfPrim,xOffset+240+nameWidth,20); 
					//Kopfzeile Sekundär
					if(!hf.optionenFeldVar.sKeine.isSelected()){
						g.drawString(kopfSek,xOffset+280+nameWidth+primWidth,20);
					} 
					//Kopfzeile Teriär
					if(!hf.optionenFeldVar.tKeine.isSelected()){
						g.drawString(kopfTer,xOffset+320+nameWidth+primWidth+sekWidth,20);
					} 

					if((newPosVerschiebung-startVal)<5 && (newPosVerschiebung-startVal)>-5){
						posVerschiebung=(newPosVerschiebung-startVal);
					}

				} else if(zusTyp==1){
					g.setFont(font2);

					//////////////////////////// Begegnungen ////////////////////////
					g.setFont(font);
					int startVal=0;
					int begegnungslaenge=(g.getFontMetrics(font).getHeight()+5)*(hf.begegnungsVector.size()+1);
					int startVal2=(begegnungslaenge>typPanel.getHeight())?begegnungslaenge:(typPanel.getHeight());
					System.out.println("StartVal "+startVal);
					System.out.println("Begegnungslaenge "+begegnungslaenge);
					System.out.println("StartVal2 "+startVal2);

					if(((g.getFontMetrics(font).getHeight()+5)*hf.begegnungsVector.size()+50)<typPanel.getHeight()){
						posVerschiebung=0;
					}


					//Max Längenberechnungen
					FontMetrics fontMetrics = g.getFontMetrics();
					int nameWidth = fontMetrics.stringWidth("Begegnung");
					int primWidth = fontMetrics.stringWidth("Primär");
					int sekWidth = fontMetrics.stringWidth("Sekundär");
					int terWidth = fontMetrics.stringWidth("Tertiär");
					for (int i=0;i<hf.begegnungsVector.size();i++){
						KBegegnungen bg = hf.begegnungsVector.get(i);
						if(bg.runde==hf.rundenZaehler){
							KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
							KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
							nameWidth=(fontMetrics.stringWidth(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname)>nameWidth)?(fontMetrics.stringWidth(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname)):nameWidth;
							primWidth=(fontMetrics.stringWidth(bg.p1pri+" : "+bg.p2pri)>primWidth)?(fontMetrics.stringWidth(bg.p1pri+" : "+bg.p2pri)):primWidth;
							sekWidth=(fontMetrics.stringWidth(bg.p1sek+" : "+bg.p2sek)>sekWidth)?(fontMetrics.stringWidth(bg.p1sek+" : "+bg.p2sek)):sekWidth;
							terWidth=(fontMetrics.stringWidth(bg.p1ter+" : "+bg.p2ter)>terWidth)?(fontMetrics.stringWidth(bg.p1ter+" : "+bg.p2sek)):terWidth;
						}
					}

					int showCnt=0;
					for (int i=0;i<hf.begegnungsVector.size();i++){
						KBegegnungen bg = hf.begegnungsVector.get(i);
						if(bg.runde==hf.rundenZaehler){
							KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
							KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
							g.drawString(laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6),xOffset+75,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							g.drawString(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname,xOffset+200,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							g.drawString(bg.p1pri+" : "+bg.p2pri,xOffset+230+nameWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							if(hf.optionenFeldVar.sPunkte.isSelected()){
								g.drawString(bg.p1sek+" : "+bg.p2sek,xOffset+260+nameWidth+primWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							}
							if(hf.optionenFeldVar.tPunkte.isSelected()){
								g.drawString(bg.p1ter+" : "+bg.p2ter,xOffset+290+nameWidth+primWidth+sekWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							}
							showCnt++;
						}
					}


					showCnt=0;
					int newPosVerschiebung=posVerschiebung+startVal2;
					for (int i=0;i<hf.begegnungsVector.size();i++){
						KBegegnungen bg = hf.begegnungsVector.get(i);
						if(bg.runde==hf.rundenZaehler){
							KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
							KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
							g.drawString(laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6),xOffset+75,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							g.drawString(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname,xOffset+200,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							g.drawString(bg.p1pri+" : "+bg.p2pri,xOffset+230+nameWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							if(hf.optionenFeldVar.sPunkte.isSelected()){
								g.drawString(bg.p1sek+" : "+bg.p2sek,xOffset+260+nameWidth+primWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							}
							if(hf.optionenFeldVar.tPunkte.isSelected()){
								g.drawString(bg.p1ter+" : "+bg.p2ter,xOffset+290+nameWidth+primWidth+sekWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*showCnt+50);
							}
							showCnt++;
						}
					}

					g.setColor(stdBg);
					g.fillRect(0, 0, typPanel.getWidth(), 30);
					g.setColor(stdFg);
					g.drawString("Tisch",xOffset+75,20);
					g.drawString("Begegnung",xOffset+200,20);
					g.drawString("Primär",xOffset+230+nameWidth,20);
					if(hf.optionenFeldVar.sPunkte.isSelected()){
						g.drawString("Sekundär",xOffset+260+nameWidth+primWidth,20);
					}
					if(hf.optionenFeldVar.tPunkte.isSelected()){
						g.drawString("Tertär",xOffset+290+nameWidth+primWidth+sekWidth,20);
					}

					if((newPosVerschiebung-startVal)<5 && (newPosVerschiebung-startVal)>-5){
						posVerschiebung=(newPosVerschiebung-startVal);
					}

				}

			}

		}
	};

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

	KHauptFenster hf;
	long zeit=0;
	long takt=1000;
	long timerTyp=0;
	long zusTyp=0;
	String logo="";
	int bgColor=0;
	boolean run=true;
	static Font font = new Font("Courier",Font.PLAIN,24);
	static Font font2 = new Font("Courier",Font.BOLD,24);
	int posVerschiebung=0;
	boolean zusTypAnzeigen=false;
	int timerFaktor=5;

	public void run(){
		if(zeit>0){
			System.out.println("Run");
			long starttime = System.currentTimeMillis();

			frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			frame.setResizable(false);
			frame.setUndecorated(true);
			frame.addMouseListener(this);
			frame.addComponentListener(this);
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));

			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			WindowListener meinListener=new WindowAdapter(){
				public void windowClosing(WindowEvent ereignis){
					run=false;
				}
			};
			frame.addWindowListener(meinListener);

			frame.add(hauptPanel);
			hauptPanel.setLayout(new BoxLayout(hauptPanel, BoxLayout.Y_AXIS));

			logoPanel.setBackground(stdBg);
			zeitPanel.setBackground(stdBg);
			aktionPanel.setBackground(stdBg);
			zeitLabel.setForeground(stdFg);
			aktionLabel.setForeground(stdFg);
			int size1 = Toolkit.getDefaultToolkit().getScreenSize().width/timerFaktor;
			zeitLabel.setFont(new Font("Comic Sans Serif",1,size1));
			zeitLabel.setFocusable(false);
			aktionLabel.setFont(new Font("Comic Sans Serif",1,size1));
			aktionLabel.setFocusable(false);
			try{
				zeitFontHeight=hauptPanel.getGraphics().getFontMetrics(zeitLabel.getFont()).getHeight();
			}catch(NullPointerException e){

			}

			setImage();

			typPanel.setBackground(stdBg);
			typPanel.setForeground(stdFg);

			hauptPanel.add(logoPanel);
			hauptPanel.add(aktionPanel);
			hauptPanel.add(zeitPanel);
			aktionPanel.add(aktionLabel);
			zeitPanel.add(zeitLabel);
			hauptPanel.add(typPanel);

			typPanel.repaint();
			frame.setVisible(true);
			while(run){
				long acttime = 0;
				if(timerTyp==0){
					acttime = zeit-(System.currentTimeMillis()-starttime);

					if(acttime<0){
						frame.setVisible(false);
						break;
					} else {

						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
						sdf.setTimeZone(TimeZone.getTimeZone("GMT+00"));
						Date actDate = new Date(acttime);

						aktionPanel.setVisible(false);
						zeitLabel.setText(sdf.format(actDate));
					}
				}else{
					int agendaCounter=-1;
					for(int j=0;j<hf.agendaVector.size();j++){
						acttime=hf.agendaVector.get(j).zeitBis();
						//System.out.println(acttime);
						if(acttime>0){
							agendaCounter=j-1;
							break;
						} 
					}

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					sdf.setTimeZone(TimeZone.getTimeZone("GMT+00"));
					Date actDate = new Date(acttime);
					zeitLabel.setText(sdf.format(actDate));

					if(agendaCounter>-1){
						aktionLabel.setText((String)hf.dynamischerTimerFeld.table.getModel().getValueAt(agendaCounter, 0));
						aktAgendaPunkt=agendaCounter;
					}else{
						aktAgendaPunkt=-1;
					}

					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					aktionPanel.setVisible(true);
					//zeitLabel.setText(sdf.format(actDate));
					//aktionLabel.setText("Aktion");
				}
				
				zeitPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
				zeitPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
				zeitPanel.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
				aktionPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
				aktionPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
				aktionPanel.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));

				typPanel.repaint();
				posVerschiebung-=2;
				try {
					sleep(takt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
			System.out.println("close");
			frame.dispose();
		}
	}

	public void setImage(){
		ImageIcon i =new ImageIcon(Toolkit.getDefaultToolkit().getImage(logo));
		float trans = Toolkit.getDefaultToolkit().getScreenSize().width/i.getIconWidth();
		i.setImage(i.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, ((int)(i.getIconHeight()*trans)), Image.SCALE_DEFAULT));
		logoPanel.add(new JLabel(i));
		logoPanel.setPreferredSize(new Dimension(i.getIconWidth(),i.getIconHeight()));
		logoPanel.setMaximumSize(new Dimension(i.getIconWidth(),i.getIconHeight()));
		logoPanel.setMinimumSize(new Dimension(i.getIconWidth(),i.getIconHeight()));
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
			frame.setVisible(true);


		}else{
			frame.dispose(); 
			frame.setResizable(false);
			frame.setUndecorated(true); 
			frame.setBounds(mXPos, mYPos, mWidth, mHeight);
			frame.setVisible(true);
		}
	}

	public void adaptPanel(){
		int size1 = frame.getWidth()/timerFaktor;
		zeitLabel.setFont(new Font("Comic Sans Serif",1,size1));
		aktionLabel.setFont(new Font("Comic Sans Serif",1,size1));
		zeitFontHeight=hauptPanel.getGraphics().getFontMetrics(zeitLabel.getFont()).getHeight()+10;
		frame.repaint();
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

	@Override
	public void componentHidden(ComponentEvent e) {
		adaptPanel();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		//adaptPanel();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		adaptPanel();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		adaptPanel();
	}

}
