package turman;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class KDialog extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4366211042490612732L;


	public KDialog(KHauptFenster s){
		this.s = s;
		init();
		setModal(true);
		setUndecorated(true);
		setAlwaysOnTop(true);
	}
	
	KHauptFenster s;
	
	public JOptionPane yesno = new JOptionPane(
			"Das Programm wird beendet\n" +
			"Wollen Sie speichern?",
			JOptionPane.QUESTION_MESSAGE,
			JOptionPane.YES_NO_OPTION);

	public JOptionPane info = new JOptionPane(
			"Die Gateway Adresse wurde geaendert",
			JOptionPane.INFORMATION_MESSAGE);
	
	public JOptionPane errorUngerade = new JOptionPane(
			"Die Spielerzahl ist ungerade",
		    JOptionPane.ERROR_MESSAGE);
	
	public JOptionPane errorDateiFehler = new JOptionPane(
			"Fehler beim Öffnen der Datei",
		    JOptionPane.ERROR_MESSAGE);
	
	public JOptionPane errorNummerEingabe = new JOptionPane(
			"Es können nur natürliche Zahlen eingegeben werden.",
		    JOptionPane.ERROR_MESSAGE);
	
	public JOptionPane errorSwiss = new JOptionPane(
			"Es konnten keine Paarungen generiert werden.\n" +
			"Die Paarungsoptionen sollten gelockert werden.",
		    JOptionPane.ERROR_MESSAGE);
	
	public JOptionPane infoTische = new JOptionPane(
			"Es müssen Spieler erneut am selben Tisch spielen.",
		    JOptionPane.INFORMATION_MESSAGE);
	
	
	public void getErrorDialog(JOptionPane jop){
		setContentPane(jop);
		((JButton)((JPanel)jop.getComponent(1)).getComponent(0)).removeActionListener(this);
		((JButton)((JPanel)jop.getComponent(1)).getComponent(0)).addActionListener(this);
		jop.setBorder(BorderFactory.createRaisedBevelBorder());
		setVisible(true);
	}
	
	public void getInfoDialog(JOptionPane jop){
		setContentPane(jop);
		((JButton)((JPanel)jop.getComponent(1)).getComponent(0)).removeActionListener(this);
		((JButton)((JPanel)jop.getComponent(1)).getComponent(0)).addActionListener(this);
		jop.setBorder(BorderFactory.createRaisedBevelBorder());
		setVisible(true);
	}
	
	public void getQuestionDialog(JOptionPane jop){
		setContentPane(jop);
		setVisible(true);
	}
	
public void init(){
	((JButton)((JPanel)yesno.getComponent(1)).getComponent(0)).addActionListener(this);
	((JButton)((JPanel)yesno.getComponent(1)).getComponent(1)).addActionListener(this);
	yesno.setBorder(BorderFactory.createRaisedBevelBorder());
	((JButton)((JPanel)yesno.getComponent(1)).getComponent(0)).addActionListener(this);
	((JButton)((JPanel)yesno.getComponent(1)).getComponent(1)).addActionListener(this);
	yesno.setBorder(BorderFactory.createRaisedBevelBorder());
	
	setVisible(false);
	setSize(400,140);
	setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-400)/2,(Toolkit.getDefaultToolkit().getScreenSize().height-140)/2);
	}

public void actionPerformed(ActionEvent arg0) {
	Object quelle= arg0.getSource();
	
	if(quelle==((JButton)((JPanel)yesno.getComponent(1)).getComponent(0))){
		s.beenden();
	}
	else if(quelle==((JButton)((JPanel)yesno.getComponent(1)).getComponent(1))){
	}
	
	setVisible(false);
	
}
}
