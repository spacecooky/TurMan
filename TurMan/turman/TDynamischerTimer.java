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

public class TDynamischerTimer extends Thread implements MouseListener,ComponentListener{

	public TDynamischerTimer(KHauptFenster hf, int zeit, int takt, int timerTyp,int zusTyp,boolean zusTypAnzeigen,String logo){
		System.out.println("Constructor");
		this.hf=hf;
		this.zeit=zeit*60*1000;
		this.takt=takt*10;
		this.timerTyp=timerTyp;
		this.zusTyp=zusTyp;
		this.zusTypAnzeigen=zusTypAnzeigen;
		this.logo=logo;
		timerFaktor=zusTypAnzeigen?10:5;
		start();
	}

	public void setVars(int zeit, int takt, int timerTyp,int zusTyp,boolean zusTypAnzeigen,String logo){
		this.zeit=zeit*60*1000;
		this.takt=takt*10;
		this.timerTyp=timerTyp;
		this.zusTyp=zusTyp;
		this.zusTypAnzeigen=zusTypAnzeigen;
		this.logo=logo;
		timerFaktor=zusTypAnzeigen?10:5;
	}

	JFrame frame= new JFrame();
	JPanel hauptPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JPanel zeitPanel = new JPanel();
	JLabel zeitLabel = new JLabel("",JLabel.CENTER);
	int zeitFontHeight=0;
	JPanel typPanel = new JPanel(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			super.paintComponent(g);

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
					} else if(hf.optionenFeldVar.sSOOS.isSelected()){
						kopfSek="Sekundär (SOOS)";
					} 
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
						g.drawString(laengeAnpassenVorne(Integer.toString(t.platz), 6),75,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(t.vorname+" "+t.nachname,200,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(""+t.erstwertung,240+nameWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						if(!hf.optionenFeldVar.sKeine.isSelected()){
							g.drawString(""+t.zweitwertung,280+nameWidth+primWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
						if(!hf.optionenFeldVar.tKeine.isSelected()){
							g.drawString(""+t.drittwertung,320+nameWidth+primWidth+sekWidth,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						} 
					}

					int newPosVerschiebung=posVerschiebung+startVal2;
					//for (int i=hf.sortierterVector.size()-1;i>=0;i--){
					for (int i=0;i<hf.sortierterVector.size();i++){
						KTeilnehmer t=hf.sortierterVector.get(i);
						g.drawString(laengeAnpassenVorne(Integer.toString(t.platz), 6),75,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(t.vorname+" "+t.nachname,200,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(""+t.erstwertung,240+nameWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						if(!hf.optionenFeldVar.sKeine.isSelected()){
							g.drawString(""+t.zweitwertung,280+nameWidth+primWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
						if(!hf.optionenFeldVar.tKeine.isSelected()){
							g.drawString(""+t.drittwertung,320+nameWidth+primWidth+sekWidth,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						} 
					}

					//Kopfzeile
					g.setColor(Color.black);
					g.fillRect(0, 0, typPanel.getWidth(), 30);
					g.setColor(Color.white);
					g.drawString("Platz",75,20);
					g.drawString("Name",200,20);
					g.drawString(kopfPrim,240+nameWidth,20); 
					//Kopfzeile Sekundär
					if(!hf.optionenFeldVar.sKeine.isSelected()){
						g.drawString(kopfSek,280+nameWidth+primWidth,20);
					} 
					//Kopfzeile Teriär
					if(!hf.optionenFeldVar.tKeine.isSelected()){
						g.drawString(kopfTer,320+nameWidth+primWidth+sekWidth,20);
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

					for (int i=0;i<hf.begegnungsVector.size();i++){
						KBegegnungen bg = hf.begegnungsVector.get(i);
						if(bg.runde==hf.rundenZaehler){
							KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
							KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
							g.drawString(laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6),75,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
							g.drawString(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname,200,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
							g.drawString(bg.p1pri+" : "+bg.p2pri,900,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
							g.drawString(bg.p1sek+" : "+bg.p2sek,1050,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
					}



					int newPosVerschiebung=posVerschiebung+startVal2;
					for (int i=0;i<hf.begegnungsVector.size();i++){
						KBegegnungen bg = hf.begegnungsVector.get(i);
						if(bg.runde==hf.rundenZaehler){
							KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
							KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
							g.drawString(laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6),75,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
							g.drawString(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname,200,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
							g.drawString(bg.p1pri+" : "+bg.p2pri,900,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
							g.drawString(bg.p1sek+" : "+bg.p2sek,1050,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
					}

					g.setColor(Color.black);
					g.fillRect(0, 0, typPanel.getWidth(), 30);
					g.setColor(Color.white);
					g.drawString("Tisch",75,20);
					g.drawString("Begegnung",200,20);
					g.drawString("Primär",900,20);
					g.drawString("Sekundär",1050,20);

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




			zeitPanel.setBackground(Color.black);
			zeitLabel.setForeground(Color.white);
			int size1 = Toolkit.getDefaultToolkit().getScreenSize().width/timerFaktor;
			zeitLabel.setFont(new Font("Comic Sans Serif",1,size1));
			zeitLabel.setFocusable(false);
			try{
				zeitFontHeight=hauptPanel.getGraphics().getFontMetrics(zeitLabel.getFont()).getHeight()+10;
			}catch(NullPointerException e){

			}

			ImageIcon i =new ImageIcon(Toolkit.getDefaultToolkit().getImage(logo));
			float trans = Toolkit.getDefaultToolkit().getScreenSize().width/i.getIconWidth();
			i.setImage(i.getImage().getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width, ((int)(i.getIconHeight()*trans)), Image.SCALE_DEFAULT));
			logoPanel.add(new JLabel(i));
			logoPanel.setPreferredSize(new Dimension(i.getIconWidth(),i.getIconHeight()));
			logoPanel.setMaximumSize(new Dimension(i.getIconWidth(),i.getIconHeight()));
			logoPanel.setMinimumSize(new Dimension(i.getIconWidth(),i.getIconHeight()));

			typPanel.setBackground(Color.black);
			typPanel.setForeground(Color.white);

			hauptPanel.add(logoPanel);
			hauptPanel.add(zeitPanel);
			zeitPanel.add(zeitLabel);
			hauptPanel.add(typPanel);

			typPanel.repaint();
			frame.setVisible(true);
			while(run){
				long acttime = zeit-(System.currentTimeMillis()-starttime);

				if(acttime<0){
					frame.setVisible(false);
					break;
				} else {

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					sdf.setTimeZone(TimeZone.getTimeZone("GMT+00"));
					Date actDate = new Date(acttime);

					zeitLabel.setText(sdf.format(actDate));
				}
				if(zusTypAnzeigen){
					zeitPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
					zeitPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
					zeitPanel.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitFontHeight));
				}

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
		adaptPanel();
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
