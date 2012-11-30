package turman;

import java.awt.Color;
import java.awt.Font;
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

	public TDynamischerTimer(KHauptFenster hf, int zeit, int takt, int typ){
		this.hf=hf;
		this.zeit=zeit;
		this.takt=takt;
		this.typ=typ;
		frame.add(hauptPanel);
		hauptPanel.setLayout(new BoxLayout(hauptPanel, BoxLayout.Y_AXIS));
		hauptPanel.add(logoPanel);
		hauptPanel.add(zeitPanel);
		zeitPanel.add(zeitLabel);
		hauptPanel.add(typPanel);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		start();
	}
	
	public void setVars(int zeit, int takt, int typ){
		this.zeit=zeit;
		this.takt=takt;
		this.typ=typ;
	}

	JFrame frame= new JFrame();
	JPanel hauptPanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JPanel zeitPanel = new JPanel();
	JLabel zeitLabel = new JLabel("",JLabel.CENTER);
	JPanel typPanel = new JPanel();
	
	KHauptFenster hf;
	long zeit=0;
	long takt=1000;
	long typ=0;
	boolean run=true;

	public void run(){
		if(zeit>0){
			long starttime = System.currentTimeMillis();
			
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
			
			zeitPanel.setBackground(Color.black);
			zeitLabel.setForeground(Color.white);
			int size1 = Toolkit.getDefaultToolkit().getScreenSize().width/5;
			zeitLabel.setFont(new Font("Comic Sans Serif",1,size1));
			zeitLabel.setFocusable(false);
			logoPanel.add(new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("tm.jpg"))));
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
				try {
					sleep(takt);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
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
		int size1 = frame.getWidth()/5;
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
