package turman;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KTeilnehmerFenster extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6451174956602020757L;
	public KTeilnehmerFenster(){
		init();
	}
	
	JPanel mainPanel = new JPanel();
	JLabel vornameLabel = new JLabel("Vorname");
	JLabel nicknameLabel = new JLabel("Nickname");
	JLabel nachnameLabel = new JLabel("Nachname");
	JLabel armeeLabel = new JLabel("Armee");
	JLabel ortLabel = new JLabel("Ort");
	JLabel teamLabel = new JLabel("Team");
	JLabel armeelisteLabel = new JLabel("Armeepunkte");
	JLabel bezahltLabel = new JLabel("Bezahlpunkte");
	
	JTextField vornameArea = new JTextField("");
	JTextField nicknameArea = new JTextField("");
	JTextField nachnameArea = new JTextField("");
	JTextField armeeArea = new JTextField("");
	JTextField ortArea = new JTextField("");
	JTextField teamArea = new JTextField("");
	JTextField armeelisteArea = new JTextField("");
	JTextField bezahltArea = new JTextField("");
	
	JButton cancel = new JButton("Abbrechen");
	JButton ok = new JButton("Ändern");
	
	KTeilnehmer teilnehmer = null;
	
	public void init(){
		setSize(300, 300);
		mainPanel.setLayout(new GridLayout(8,2));
		
		mainPanel.add(vornameLabel);
		mainPanel.add(vornameArea);
		
		mainPanel.add(nicknameLabel);
		mainPanel.add(nicknameArea);
		
		mainPanel.add(nachnameLabel);
		mainPanel.add(nachnameArea);
		
		mainPanel.add(armeeLabel);
		mainPanel.add(armeeArea);
		
		mainPanel.add(ortLabel);
		mainPanel.add(ortArea);
		
		mainPanel.add(teamLabel);
		mainPanel.add(teamArea);
		
		mainPanel.add(armeelisteLabel);
		mainPanel.add(armeelisteArea);
		
		//mainPanel.add(bezahltLabel);
		//mainPanel.add(bezahltArea);
		
		mainPanel.add(cancel);
		mainPanel.add(ok);
		
		cancel.addActionListener(this);
		ok.addActionListener(this);
		
		add(mainPanel);
		setVisible(false);
	}
	
	
	public void anzeigen(KTeilnehmer teilnehmer){
		this.teilnehmer=teilnehmer;
		vornameArea.setText(teilnehmer.vorname);
		nicknameArea.setText(teilnehmer.nickname);
		nachnameArea.setText(teilnehmer.nachname);
		armeeArea.setText(teilnehmer.armee);
		ortArea.setText(teilnehmer.ort);
		teamArea.setText(teilnehmer.team);
		armeelisteArea.setText(Integer.toString(teilnehmer.armeeliste));
		bezahltArea.setText(teilnehmer.bezahlt==1?"2":"0");
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(cancel)){
			setVisible(false);
		} else if(arg0.getSource()==ok){
			teilnehmer.vorname=vornameArea.getText();
			teilnehmer.nickname=nicknameArea.getText();
			teilnehmer.nachname=nachnameArea.getText();
			teilnehmer.armee=armeeArea.getText();
			teilnehmer.ort=ortArea.getText();
			teilnehmer.team=teamArea.getText();
			try{
			teilnehmer.armeeliste=Integer.parseInt(armeelisteArea.getText());
			} catch(Exception e){
				
			}
			teilnehmer.bezahlt=bezahltArea.getText().equals("2")?1:0;
			setVisible(false);
		}
		
	}
}
