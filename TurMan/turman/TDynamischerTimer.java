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
import java.awt.print.Printable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TDynamischerTimer extends Thread implements MouseListener,ComponentListener{

	public TDynamischerTimer(KHauptFenster hf, int zeit, int takt, int typ,String logo){
		System.out.println("Constructor");
		this.hf=hf;
		this.zeit=zeit*60*1000;
		this.takt=takt*10;
		this.typ=typ;
		this.logo=logo;
		start();
	}
	
	public void setVars(int zeit, int takt, int typ,String logo){
		this.zeit=zeit*60*1000;
		this.takt=takt*100;
		this.typ=typ;
		this.logo=logo;
	}

	JFrame frame= new JFrame();
	JPanel hauptPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JPanel zeitPanel = new JPanel();
	JLabel zeitLabel = new JLabel("",JLabel.CENTER);
	JPanel typPanel = new JPanel(){
        public void paintComponent(Graphics g){
            super.paintComponent(g);
 
            if(typ==0){
    			g.setFont(font2);
    	
    			//////////////////////////// Überschrift ////////////////////////
    			String platz="Platz";
    			String name="Name";
    			String pri="Primär";
    			String sek="Sekundär";
    			String sos="SOS";
    			
    			platz=laengeAnpassenVorne(platz, 6);
    			platz+="   ";
    			name = laengeAnpassenHinten(name, 50);
    			pri  = laengeAnpassenHinten(pri, 9);
    			sek  = laengeAnpassenHinten(sek, 9);
    			sos = laengeAnpassenHinten(sos, 9);
    			
    			String nachricht=platz+name+pri+sek+sos;
    			g.drawString(nachricht,75,84+14);
    	
    			g.setFont(font);
    			int startVal=(g.getFontMetrics(font).getHeight()+5)*1;
    			int begegnungslaenge=(g.getFontMetrics(font).getHeight()+5)*(hf.sortierterVector.size()+1);
    			int startVal2=begegnungslaenge>typPanel.getHeight()?begegnungslaenge:(typPanel.getHeight()-begegnungslaenge);
    	
    			for (int i=0;i<hf.sortierterVector.size();i++){
    					KTeilnehmer t=hf.sortierterVector.get(i);
    					nachricht =laengeAnpassenVorne(Integer.toString(t.platz), 6);
    					nachricht+="   ";
    					nachricht +=laengeAnpassenHinten(""+t.vorname+" "+t.nachname, 50);
    					nachricht +=laengeAnpassenHinten(""+t.primär,9);
    					nachricht +=laengeAnpassenHinten(""+t.sekundär,9);
    					nachricht +=laengeAnpassenHinten(""+t.sos,9);
    					g.drawString(nachricht,75,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i);
    			}
    			
    			
    			/*int newPosVerschiebung=posVerschiebung+startVal2;
    			for (int i=0;i<hf.sortierterVector.size();i++){
					KTeilnehmer t=hf.sortierterVector.get(i);
					nachricht =laengeAnpassenVorne(Integer.toString(t.platz), 6);
					nachricht+="   ";
					nachricht +=laengeAnpassenHinten(""+t.vorname+" "+t.nachname, 50);
					nachricht +=laengeAnpassenHinten(""+t.primär,9);
					nachricht +=laengeAnpassenHinten(""+t.sekundär,9);
					nachricht +=laengeAnpassenHinten(""+t.sos,9);
					g.drawString(nachricht,75,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i);
			}
				if(newPosVerschiebung-startVal<1 || newPosVerschiebung-startVal>-1){
					posVerschiebung=0;
				}*/
				
    		} else if(typ==1){
    			g.setFont(font2);
    	
    			//////////////////////////// Überschrift ////////////////////////
    			String platz="Tisch";
    			String name="Begegnung";
    			String pri="Primär";
    			String sek="Sekundär";
    			platz=laengeAnpassenVorne(platz, 6);
    			platz+="   ";
    			name = laengeAnpassenHinten(name, 70);
    			pri  = laengeAnpassenHinten(pri, 13);
    			sek  = laengeAnpassenHinten(sek, 13);
    			
    			String nachricht=platz+name+pri+sek;
    			//g.drawString(nachricht,55,84+14);
    	
    			g.setFont(font);
    			int startVal=(g.getFontMetrics(font).getHeight()+5)*1;
    			int begegnungslaenge=(g.getFontMetrics(font).getHeight()+5)*(hf.begegnungsVector.size()+1);
    			int startVal2=(begegnungslaenge>typPanel.getHeight())?begegnungslaenge:(typPanel.getHeight()+startVal);
    			System.out.println("StartVal "+startVal);
    			System.out.println("Begegnungslaenge "+begegnungslaenge);
    			System.out.println("StartVal2 "+startVal2);
    			
    			
    			for (int i=0;i<hf.begegnungsVector.size();i++){
					KBegegnungen bg = hf.begegnungsVector.get(i);
					if(bg.runde==hf.rundenZaehler){
						KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
						KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
						nachricht =laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6);
						nachricht +=laengeAnpassenHinten("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname, 73);
						nachricht +=laengeAnpassenHinten(""+bg.p1+" : "+bg.p2,13);
						nachricht +=laengeAnpassenHinten(""+bg.p12+" : "+bg.p22,13);
						g.drawString(nachricht,55,posVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i+1);
					}
    			}
    			
    			
    			
				int newPosVerschiebung=posVerschiebung+startVal2;
				for (int i=0;i<hf.begegnungsVector.size();i++){
					KBegegnungen bg = hf.begegnungsVector.get(i);
					if(bg.runde==hf.rundenZaehler){
						KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
						KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
						nachricht =laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6);
						nachricht +=laengeAnpassenHinten("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname, 73);
						nachricht +=laengeAnpassenHinten(""+bg.p1+" : "+bg.p2,13);
						nachricht +=laengeAnpassenHinten(""+bg.p12+" : "+bg.p22,13);
						g.drawString(nachricht,55,newPosVerschiebung+(g.getFontMetrics(font).getHeight()+5)*i);
					}
    			}
				if((newPosVerschiebung-startVal)<5 && (newPosVerschiebung-startVal)>-5){
					posVerschiebung=0;
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
	long typ=0;
	String logo="";
	boolean run=true;
	static Font font = new Font("Courier",Font.PLAIN,24);
	static Font font2 = new Font("Courier",Font.BOLD,24);
	int posVerschiebung=0;

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
			int size1 = Toolkit.getDefaultToolkit().getScreenSize().width/10;
			zeitLabel.setFont(new Font("Comic Sans Serif",1,size1));
			zeitLabel.setFocusable(false);
			
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
				
				zeitPanel.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,hauptPanel.getGraphics().getFontMetrics(zeitLabel.getFont()).getHeight()+10));
				zeitPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitPanel.getGraphics().getFontMetrics(zeitLabel.getFont()).getHeight()+10));
				zeitPanel.setMinimumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,zeitPanel.getGraphics().getFontMetrics(zeitLabel.getFont()).getHeight()+10));
				
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
		int size1 = frame.getWidth()/10;
		zeitLabel.setFont(new Font("Comic Sans Serif",1,size1));
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
