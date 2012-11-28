package turman;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TTimer extends Thread implements ActionListener, MouseListener,ComponentListener{

	public TTimer(KHauptFenster hf){
		this.hf=hf;
		frame.add(panel);
		panel.setLayout(new GridLayout(2,2));
		panel.add(label);
		panel.add(field);
		panel.add(cancel);
		panel.add(ok);		
		cancel.addActionListener(this);
		ok.addActionListener(this);
		frame.setSize(300,100);
		frame.setVisible(true);
	}

	JFrame frame= new JFrame();
	JPanel panel= new JPanel();
	JLabel label= new JLabel("Spielzeit(Minuten)");
	JTextField field=new JTextField("");
	JButton ok = new JButton("Starten");
	JButton cancel = new JButton("Abbrechen");
	KHauptFenster hf;
	long time;
	boolean run=true;

	public void run(){
		if(time>0){
			long starttime = System.currentTimeMillis();
			frame=new JFrame();
			frame.setTitle("Countdown");
			panel=new JPanel();
			frame.add(panel);
			panel.setLayout(new GridLayout(1,1));
			label=new JLabel("",JLabel.CENTER);
			panel.add(label);
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
			
			panel.setBackground(Color.black);
			label.setForeground(Color.white);
			int size1 = Toolkit.getDefaultToolkit().getScreenSize().width/5;
			label.setFont(new Font("Comic Sans Serif",1,size1));
			/*if(Toolkit.getDefaultToolkit().getScreenSize().width<=1024){
				label.setFont(new Font("Comic Sans Serif",1,200));
			}else{
				label.setFont(new Font("Comic Sans Serif",1,300));
			}*/
			label.setFocusable(false);
			//panel.setAlignmentX(0.5f);
			frame.setVisible(true);
			while(run){
				long acttime = time-(System.currentTimeMillis()-starttime);

				if(acttime<0){
					frame.setVisible(false);
					break;
				} else {

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					sdf.setTimeZone(TimeZone.getTimeZone("GMT+00"));
					Date actDate = new Date(acttime);

					label.setText(sdf.format(actDate));
					//System.out.println(sdf.format(actDate));
				}
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
			frame.dispose();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		time=0;
		if(arg0.getSource()==ok){
			try{
				time=Integer.parseInt(field.getText());
				time=time*60*1000;
				
			}catch(NumberFormatException e){}
		}
		frame.setVisible(false);
		start();
		

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
		label.setFont(new Font("Comic Sans Serif",1,size1));
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
