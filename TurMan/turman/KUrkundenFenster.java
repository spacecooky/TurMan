package turman;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KUrkundenFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6451174956602020757L;
	public KUrkundenFenster(KHauptFenster hf){
		super("Urkunden-Einstellungen");
		setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
		this.hf=hf;
		init();
	}
	
	KHauptFenster hf;
	
	JPanel mainPanel = new JPanel();
	JLabel zeile1 = new JLabel("Zeile 1");
	JLabel zeile2 = new JLabel("Zeile 2");
	
	JTextField zeile1Area = new JTextField("");
	JTextField zeile2Area = new JTextField("");
	
	JButton cancel = new JButton("Abbrechen");
	JButton ok = new JButton("Ändern");
	
	
	public void init(){
		setSize(300, 100);
		mainPanel.setLayout(new GridLayout(3,2));
		
		mainPanel.add(zeile1);
		mainPanel.add(zeile1Area);
		
		mainPanel.add(zeile2);
		mainPanel.add(zeile2Area);
		
		mainPanel.add(cancel);
		mainPanel.add(ok);
		
		cancel.addActionListener(this);
		ok.addActionListener(this);
		
		add(mainPanel);
		setVisible(false);
	}
	
	
	public void anzeigen(){
		laden();
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(cancel)){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			speichern();
			setVisible(false);
		}
		
	}
	
	public void speichern(){
		File f = new File("urkundenInfo");
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			fw.write(zeile1Area.getText()+"\r\n");
			fw.write(zeile2Area.getText()+"\r\n");
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void laden(){
		
		File f = new File("urkundenInfo");
		if(f.exists()){
			int read=-1;
			FileReader fr;
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
	
				zeile1Area.setText(optionen[0]);
				zeile2Area.setText(optionen[1]);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			zeile1Area.setText("Zeile1");
			zeile2Area.setText("Zeile2");
		}
	}
}
