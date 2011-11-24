package turman;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class KKonfigFenster {

	public KKonfigFenster(KHauptFenster hf){
		this.hf=hf;
	}
	
	KHauptFenster hf;
	
	int bezahlung=0;
	int armeeliste=0;
	int bemalung=0;
	int quiz=0;
	int fairplay=0;
	int sonstige1=0;
	int sonstige2=0;
	int sonstige3=0;
	int sonstige4=0;
	int sonstige5=0;
	
	JLabel bezahlungLabel = new JLabel("Bezahlung");
	JTextField feld1 = new JTextField("");
	JTextField feld2 = new JTextField("");
	JTextField feld3 = new JTextField("");
	JTextField feld4 = new JTextField("");
	JTextField feld5 = new JTextField("");
	
	
}
