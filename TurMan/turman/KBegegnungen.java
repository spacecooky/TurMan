package turman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class KBegegnungen extends JButton implements ActionListener, MouseListener{
	public KBegegnungen(KHauptFenster khf,KTeilnehmer t1,KTeilnehmer t2,int xPos, int yPos){
		this.t1=t1;
		this.t2=t2;
		this.khf=khf;
		addActionListener(this);
		begegnungsFensterButton.addActionListener(this);
		addMouseListener(this);
		setBorder(BorderFactory.createRaisedBevelBorder());
		setMaximumSize(new Dimension(20,20));
		setMinimumSize(new Dimension(20,20));
		setPreferredSize(new Dimension(20,20));
		setEnabled(false);
		setBackground(Color.darkGray);
		
		this.xPos=xPos;
		this.yPos=yPos;
	}
	
	KTeilnehmer t1;
	int p1=0;
	int p12=0;
	KTeilnehmer t2;
	int p2=0;
	int p22=0;
	KHauptFenster khf;
	
	int xPos=0;
	int yPos=0;
	
	int tisch=0;
	int runde=0;
	
	int position=0;
	int primär=0;
	int sekundär=0;
	int sos=0;
	
	JButton bestaetigung = new JButton("Bestätigung");
	JButton begegnungsFensterButton = new JButton("");
	
	/**
	 * @return true, wenn Teilnehmer 1 und 2 im selben Team sind
	 */
	public boolean team(){
		return t1.team.equals(t2.team);
	}
	
	/**
	 * @return true, wenn Teilnehmer 1 und 2 aus dem selben Ort sind
	 */
	public boolean ort(){
		return t1.ort.equals(t2.ort);
	}
	
	/**
	 * @return true, wenn t1 oder t2 bereits gegen die selbe Armee gespielt haben
	 */
	public boolean armee(){
		//TODO nicht mehrmals gegen die selbe Armee spielen.
		//runde ermitteln
		//spiele vor der runde bei beiden spielern ermitteln
		//vergleich
		return false;
	}
	
	/**
	 * @return true, wenn Teilnehmer 1 und 2 die gleiche Armee spielen
	 */
	public boolean mirror(){
		return t1.armee.equals(t2.armee);
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		Object quelle = arg0.getSource();
		
		if(quelle==bestaetigung){
			
			try{
			p1=Integer.parseInt(khf.p1Field.getText());
			p2=Integer.parseInt(khf.p2Field.getText());
			p12=Integer.parseInt(khf.p12Field.getText());
			p22=Integer.parseInt(khf.p22Field.getText());
			setBackground(Color.green);
			}catch(NumberFormatException e){}
			
			
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos+1)).getComponent(xPos+1)).p1=p2;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos+1)).getComponent(xPos+1)).p2=p1;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos+1)).getComponent(xPos+1)).p12=p22;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos+1)).getComponent(xPos+1)).p22=p12;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos+1)).getComponent(xPos+1)).setBackground(Color.green);
			khf.sortieren(khf.punkteFenster.ab.isSelected(),khf.punkteFenster.bm.isSelected());
			khf.begegnungsFrame.removeAll();
			khf.begegnungsPanel.removeAll();
			khf.begegnungsFrame.dispose();
			if(khf.begegnungsFenster.isVisible()){
				khf.begegnungsFenster.init();
			}
		}else if(quelle== this || quelle==begegnungsFensterButton){
			try{
				khf.begegnungsFrame.removeAll();
				khf.begegnungsPanel.removeAll();
				khf.begegnungsFrame.dispose();
			}catch(Exception e){}
			
				khf.begegnungsFrame = new JFrame();
				khf.begegnungsFrame.setContentPane(khf.begegnungsPanel);
				khf.begegnungsPanel.setLayout(new GridLayout(4,3));
				
				khf.begegnungsPanel.add(new JLabel(""));
				khf.begegnungsPanel.add(new JLabel("Primär"));
				khf.begegnungsPanel.add(new JLabel("Sekundär"));
				
				khf.begegnungsPanel.add(khf.t1Label);
				khf.t1Label.setText(t1.vorname+" "+t1.nachname);
				khf.begegnungsPanel.add(khf.p1Field);
				khf.p1Field.setText(Integer.toString(p1));
				khf.begegnungsPanel.add(khf.p12Field);
				khf.p12Field.setText(Integer.toString(p12));
				
				
				khf.begegnungsPanel.add(khf.t2Label);
				khf.t2Label.setText(t2.vorname+" "+t2.nachname);
				khf.begegnungsPanel.add(khf.p2Field);
				khf.p2Field.setText(Integer.toString(p2));
				khf.begegnungsPanel.add(khf.p22Field);
				khf.p22Field.setText(Integer.toString(p22));
				
				khf.begegnungsPanel.add(new JLabel(""));
				khf.begegnungsPanel.add(new JLabel(""));
				khf.begegnungsPanel.add(bestaetigung);
				bestaetigung.addActionListener(this);
				khf.begegnungsFrame.setSize(400,100);
				khf.begegnungsFrame.setVisible(true);	
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	
	Color colX=null;
	Color colY=null;
	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(getBackground().equals(Color.green) || getBackground().equals(Color.orange)){
			colY=((JLabel)((JPanel)khf.HauptPanel.getComponent(0)).getComponent(yPos+1)).getBackground();
			((JLabel)((JPanel)khf.HauptPanel.getComponent(0)).getComponent(yPos+1)).setBackground(Color.green);
			((JLabel)((JPanel)khf.HauptPanel.getComponent(0)).getComponent(yPos+1)).setForeground(Color.green);
			colY=((JButton)((JPanel)khf.HauptPanel.getComponent(xPos+1)).getComponent(0)).getBackground();
			((JButton)((JPanel)khf.HauptPanel.getComponent(xPos+1)).getComponent(0)).setBackground(Color.green);
		}
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if(getBackground().equals(Color.green) || getBackground().equals(Color.orange)){
			((JLabel)((JPanel)khf.HauptPanel.getComponent(0)).getComponent(yPos+1)).setBackground(colY);
			((JLabel)((JPanel)khf.HauptPanel.getComponent(0)).getComponent(yPos+1)).setForeground(Color.black);
			((JButton)((JPanel)khf.HauptPanel.getComponent(xPos+1)).getComponent(0)).setBackground(colX);
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}