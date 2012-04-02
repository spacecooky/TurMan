package turman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;

public class KTeilnehmer implements ActionListener {
	
	public KTeilnehmer(String vorname,String nachname,KHauptFenster hf){
		this.vorname=vorname;
		this.nachname=nachname;
		matrixButton.addActionListener(this);
		punkteFensterButton.addActionListener(this);
		punkteTabButton.addActionListener(this);
		this.hf=hf;
	}
	
	public KTeilnehmer(int id,String vorname,String nachname,String nickname, String armee,
			String ort, String team, int unknown, int armeeliste, int bezahlt, int ntr,KHauptFenster hf){
		
		this.id=id;
		this.vorname=vorname;
		this.nachname=nachname;
		this.nickname=nickname;
		this.armee=armee;
		this.ort=ort;
		this.team=team;
		this.unknown=unknown;
		this.armeeliste=armeeliste;
		this.bezahlt=bezahlt;
		this.ntr=ntr;
		matrixButton.addActionListener(this);
		punkteFensterButton.addActionListener(this);
		punkteTabButton.addActionListener(this);
		this.hf=hf;
	}
	
	int id=0;
	String vorname="";
	String nachname="";
	String nickname="";
	String armee="";
	String ort="";
	String team="";
	int unknown;
	int armeeliste;
	int bezahlt;
	int ntr;
	
	int primär=0;
	int primärEinzel=0;
	int sekundär=0;
	int sekundärEinzel=0;
	int sos=0;
	int bemalwertung=0;
	
	int platzGruppe=-1;
	int platz=0;
	
	Vector<Integer> paarungen= new Vector<Integer>();
	Vector<Integer> tische= new Vector<Integer>();
	
	int paired=-1;
	
	boolean deleted = false;
	
	KHauptFenster hf;
	JButton matrixButton = new JButton();
	JButton punkteFensterButton = new JButton();
	JButton punkteTabButton = new JButton();
	@Override
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		hf.spielerFenster.anzeigen(this);
	}
	
}
