package turman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
		begegnungsTabButton.addActionListener(this);
		begegnungsFensterButton.setBackground(Color.white);
		begegnungsTabButton.setBackground(Color.white);
		normalButtonColor=begegnungsFensterButton.getBackground();
		khf.siegP1.addActionListener(this);
		khf.siegP2.addActionListener(this);
		khf.unentschieden.addActionListener(this);
		SUN.add(khf.siegP1);
		SUN.add(khf.siegP2);
		SUN.add(khf.unentschieden);
		SUN.add(khf.unsichtbar);
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
	int p1pri=0;
	int p1sek=0;
	int p1ter=0;
	KTeilnehmer t2;
	int p2pri=0;
	int p2sek=0;
	int p2ter=0;
	KHauptFenster khf;

	int xPos=0;
	int yPos=0;

	int tisch=0;
	int runde=0;


	ButtonGroup SUN = new ButtonGroup();

	JButton bestaetigung = new JButton("Bestätigung");
	JButton begegnungsFensterButton = new JButton("");
	JButton begegnungsTabButton = new JButton("");
	Color normalButtonColor;

	/**
	 * @return true, wenn Teilnehmer 1 oder  2 entfernt wurde
	 */
	public boolean deleted(){
		return (t1.deleted || t2.deleted);
	}

	/**
	 * @return true, wenn Teilnehmer 1 und 2 im selben Team sind
	 */
	public boolean team(){
		return !t1.team.equals("") && !t2.team.equals("") && t1.team.equals(t2.team);
	}

	/**
	 * @return true, wenn Teilnehmer 1 und 2 aus dem selben Ort sind
	 */
	public boolean ort(){
		return !t1.ort.equals("") && !t2.ort.equals("") && t1.ort.equals(t2.ort);
	}

	/**
	 * @return true, wenn t1 oder t2 bereits gegen die selbe Armee gespielt haben
	 */
	public boolean armee(){
		if(!t2.armee.equals("")){
			//for(int i=0;i<t1.paarungen.size();i++){
			for(int i=1;i<=khf.rundenZaehler;i++){
				if(t1.paarungen.get(i)!=null){
					if(khf.teilnehmerVector.get(t1.paarungen.get(i)).armee.equals(t2.armee)){
						return true;
					}
				}
			}
		}
		if(!t1.armee.equals("")){
			//for(int i=0;i<t2.paarungen.size();i++){
			for(int i=1;i<=khf.rundenZaehler;i++){
				if(t2.paarungen.get(i)!=null){
					if(khf.teilnehmerVector.get(t2.paarungen.get(i)).armee.equals(t1.armee)){
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * @return true, wenn Teilnehmer 1 und 2 die gleiche Armee spielen
	 */
	public boolean mirror(){
		return !t1.armee.equals("") && !t2.armee.equals("") && t1.armee.equals(t2.armee);
	}

	/**
	 * Berechnung, ob mit dem angegebenen Tisch i ein Tischfehler möglich ist
	 * @param i 
	 * @return
	 */
	public boolean tischfehler(int i){
		int tn1Cnt=0;
		int tn2Cnt=0;
		//for(int j=0;j<t1.tische.size()-1;j++){
		for(int j=1;j<=khf.rundenZaehler;j++){
			if(t1.tische.get(j)!=null){
				if(t1.tische.get(j)==i){
					tn1Cnt++;
				}
			}
		}
		//for(int j=0;j<t2.tische.size()-1;j++){
		for(int j=1;j<=khf.rundenZaehler;j++){
			if(t2.tische.get(j)!=null){
				if(t2.tische.get(j)==i){
					tn2Cnt++;
				}
			}
		}
		return (tn1Cnt>0 || tn2Cnt>0);
	}

	public void setUnpairedColor(){
		setBackground(Color.darkGray);
		//begegnungsFensterButton.setBackground(normalButtonColor);
		//begegnungsTabButton.setBackground(normalButtonColor);
		begegnungsFensterButton.setBackground(Color.white);
		begegnungsTabButton.setBackground(Color.white);
	}

	public void actionPerformed(ActionEvent arg0) {
		Object quelle = arg0.getSource();

		if(quelle==bestaetigung){

			try{
				p1pri=Integer.parseInt(khf.p1priField.getText());
				p2pri=Integer.parseInt(khf.p2priField.getText());
				if(khf.optionenFeldVar.sPunkte.isSelected()){
					p1sek=Integer.parseInt(khf.p1sekField.getText());
					p2sek=Integer.parseInt(khf.p2sekField.getText());
				}
				if(khf.optionenFeldVar.tPunkte.isSelected()){
					p1ter=Integer.parseInt(khf.p1terField.getText());
					p2ter=Integer.parseInt(khf.p2terField.getText());
				}
				setBackground(Color.green);
				begegnungsFensterButton.setBackground(Color.gray);
				begegnungsTabButton.setBackground(Color.gray);
			}catch(NumberFormatException e){}

			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).p1pri=p2pri;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).p2pri=p1pri;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).p1sek=p2sek;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).p2sek=p1sek;
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).setBackground(Color.green);
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).begegnungsFensterButton.setBackground(Color.gray);
			((KBegegnungen)((JPanel)khf.HauptPanel.getComponent(yPos)).getComponent(xPos)).begegnungsTabButton.setBackground(Color.gray);
			//khf.sortieren(khf.punkteFenster.ab.isSelected(),khf.punkteFenster.bm.isSelected(),khf.rundenZaehler);
			khf.sortierenVar(khf.punkteFenster.ab.isSelected(),khf.punkteFenster.bm.isSelected(),khf.rundenZaehler);
			khf.begegnungsFrame.removeAll();
			khf.begegnungsPanel.removeAll();
			khf.begegnungsFrame.dispose();
			khf.updatePanels();
		}else if(quelle== this || quelle==begegnungsFensterButton || quelle==begegnungsTabButton){
			try{
				khf.begegnungsFrame.removeAll();
				khf.begegnungsPanel.removeAll();
				khf.begegnungsFrame.dispose();
			}catch(Exception e){}

			khf.begegnungsFrame = new JFrame();
			khf.begegnungsFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("tm.jpg"));
			khf.begegnungsFrame.setTitle("Ergebnis");
			khf.begegnungsFrame.setContentPane(khf.begegnungsPanel);
			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsPanel.setLayout(new GridLayout(5,3+(khf.optionenFeldVar.sPunkte.isSelected()?1:0)+(khf.optionenFeldVar.tPunkte.isSelected()?1:0)));
			}else{
				khf.begegnungsPanel.setLayout(new GridLayout(4,2+(khf.optionenFeldVar.sPunkte.isSelected()?1:0)+(khf.optionenFeldVar.tPunkte.isSelected()?1:0)));
			}

			khf.begegnungsPanel.add(new JLabel(""));
			
			khf.begegnungsPanel.add(new JLabel("Primär"));
			if(khf.optionenFeldVar.sPunkte.isSelected()){
				khf.begegnungsPanel.add(new JLabel("Sekundär"));
			}
			if(khf.optionenFeldVar.tPunkte.isSelected()){
				khf.begegnungsPanel.add(new JLabel("Tertiär"));
			}
			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsPanel.add(new JLabel("Sieger"));
			}

			String vn1 = t1.vornameAlter.equals("")?t1.vorname:t1.vornameAlter;
			String nn1 = t1.nachnameAlter.equals("")?t1.nachname:t1.nachnameAlter;
			khf.begegnungsPanel.add(khf.t1Label);
			khf.t1Label.setText(vn1+" "+nn1);
			khf.begegnungsPanel.add(khf.p1priField);
			khf.p1priField.setText(Integer.toString(p1pri));
			if(khf.optionenFeldVar.sPunkte.isSelected()){
				khf.begegnungsPanel.add(khf.p1sekField);
				khf.p1sekField.setText(Integer.toString(p1sek));
			}
			if(khf.optionenFeldVar.tPunkte.isSelected()){
				khf.begegnungsPanel.add(khf.p1terField);
				khf.p1terField.setText(Integer.toString(p1sek));
			}
			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsPanel.add(khf.siegP1);
			}
			
			String vn2 = t2.vornameAlter.equals("")?t2.vorname:t2.vornameAlter;
			String nn2 = t2.nachnameAlter.equals("")?t2.nachname:t2.nachnameAlter;
			khf.begegnungsPanel.add(khf.t2Label);
			khf.t2Label.setText(vn2+" "+nn2);
			khf.begegnungsPanel.add(khf.p2priField);
			khf.p2priField.setText(Integer.toString(p2pri));
			if(khf.optionenFeldVar.sPunkte.isSelected()){
				khf.begegnungsPanel.add(khf.p2sekField);
				khf.p2sekField.setText(Integer.toString(p1sek));
			}
			if(khf.optionenFeldVar.tPunkte.isSelected()){
				khf.begegnungsPanel.add(khf.p2terField);
				khf.p2terField.setText(Integer.toString(p1sek));
			}
			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsPanel.add(khf.siegP2);
			}

			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsPanel.add(new JLabel(""));
				khf.begegnungsPanel.add(new JLabel(""));
				if(khf.optionenFeldVar.sPunkte.isSelected()){
					khf.begegnungsPanel.add(new JLabel(""));
				}
				if(khf.optionenFeldVar.tPunkte.isSelected()){
					khf.begegnungsPanel.add(new JLabel(""));
				}
				khf.begegnungsPanel.add(khf.unentschieden);
			}

			khf.begegnungsPanel.add(new JLabel(""));
			if(khf.optionenFeldVar.sPunkte.isSelected()){
				khf.begegnungsPanel.add(new JLabel(""));
			}
			if(khf.optionenFeldVar.tPunkte.isSelected()){
				khf.begegnungsPanel.add(new JLabel(""));
			}
			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsPanel.add(new JLabel(""));
			}
			khf.begegnungsPanel.add(bestaetigung);
			bestaetigung.addActionListener(this);
			if(khf.optionenFeldVar.SUN.isSelected()){
				khf.begegnungsFrame.setSize(600,175);
				khf.p1priField.setEditable(false);
				khf.p2priField.setEditable(false);
			}else{
				khf.begegnungsFrame.setSize(500,150);
				khf.p1priField.setEditable(true);
				khf.p2priField.setEditable(true);
			}
			khf.unsichtbar.setSelected(true);
			khf.begegnungsFrame.setVisible(true);
		}else if(quelle==khf.siegP1){
			if(khf.optionenFeldVar.SUN20_10_1.isSelected()){
				khf.p1priField.setText("20");
				khf.p2priField.setText("1");
			} else if(khf.optionenFeldVar.SUN3_1_0.isSelected()){
				khf.p1priField.setText("3");
				khf.p2priField.setText("0");
			} else if(khf.optionenFeldVar.SUN2_1_0.isSelected()){
				khf.p1priField.setText("2");
				khf.p2priField.setText("0");
			} else if(khf.optionenFeldVar.SUN_frei.isSelected()){
				khf.p1priField.setText(khf.optionenFeldVar.SUN_S.getText());
				khf.p2priField.setText(khf.optionenFeldVar.SUN_N.getText());
			}
		}else if(quelle==khf.siegP2){
			if(khf.optionenFeldVar.SUN20_10_1.isSelected()){
				khf.p2priField.setText("20");
				khf.p1priField.setText("1");
			} else if(khf.optionenFeldVar.SUN3_1_0.isSelected()){
				khf.p2priField.setText("3");
				khf.p1priField.setText("0");
			} else if(khf.optionenFeldVar.SUN2_1_0.isSelected()){
				khf.p2priField.setText("2");
				khf.p1priField.setText("0");
			} else if(khf.optionenFeldVar.SUN_frei.isSelected()){
				khf.p2priField.setText(khf.optionenFeldVar.SUN_S.getText());
				khf.p1priField.setText(khf.optionenFeldVar.SUN_N.getText());
			}
		}else if(quelle==khf.unentschieden){
			if(khf.optionenFeldVar.SUN20_10_1.isSelected()){
				khf.p2priField.setText("10");
				khf.p1priField.setText("10");
			} else if(khf.optionenFeldVar.SUN3_1_0.isSelected() || khf.optionenFeldVar.SUN2_1_0.isSelected()){
				khf.p2priField.setText("1");
				khf.p1priField.setText("1");
			} else if(khf.optionenFeldVar.SUN_frei.isSelected()){
				khf.p2priField.setText(khf.optionenFeldVar.SUN_U.getText());
				khf.p1priField.setText(khf.optionenFeldVar.SUN_U.getText());
			}
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
			colY=((JLabel)((JPanel)khf.sp.getColumnHeader().getComponent(0)).getComponent(yPos)).getBackground();
			((JLabel)((JPanel)khf.sp.getColumnHeader().getComponent(0)).getComponent(yPos)).setOpaque(true);
			((JLabel)((JPanel)khf.sp.getColumnHeader().getComponent(0)).getComponent(yPos)).setBackground(Color.green);
			//((JLabel)((JPanel)khf.sp.getColumnHeader().getComponent(0)).getComponent(yPos)).setForeground(Color.green);
			colX=((JButton)((KTeilnehmerPanel)khf.HauptPanel.getComponent(xPos)).nameLabel).getBackground();
			((JButton)((KTeilnehmerPanel)khf.HauptPanel.getComponent(xPos)).nameLabel).setBackground(Color.green);
		}

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		if(getBackground().equals(Color.green) || getBackground().equals(Color.orange)){
			((JLabel)((JPanel)khf.sp.getColumnHeader().getComponent(0)).getComponent(yPos)).setBackground(colY);
			//((JLabel)((JPanel)khf.sp.getColumnHeader().getComponent(0)).getComponent(yPos)).setForeground(Color.black);
			((JButton)((KTeilnehmerPanel)khf.HauptPanel.getComponent(xPos)).nameLabel).setBackground(colX);
		}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}