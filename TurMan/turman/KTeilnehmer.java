package turman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;

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
	String vornameAlter="";
	String nachnameAlter="";
	String nickname="";
	String armee="";
	String ort="";
	String team="";
	int unknown;
	int armeeliste;
	int bezahlt;
	int ntr;
	
	int primaer=0;
	int primaerEinzel=0;
	int sekundaer=0;
	int sekundaerDiff=0;
	int sekundaerEinzel=0;
	int tertiaer=0;
	int tertiaerDiff=0;
	int tertiaerEinzel=0;
	int sos=0;
	int sosos=0;
	int sos_sog=0;
	int sosos_sog=0;
	double rpi;
	double strengthOfSchedulde;
	int bemalwertung=0;
	double erstwertung=0.0;
	double erstwertungEinzel=0.0;
	double zweitwertung=0.0;
	double zweitwertungEinzel=0.0;
	double drittwertung=0.0;
	double drittwertungEinzel=0.0;
	
	int platzGruppe=-1;
	int platz=0;
	int tabellenPosition=0;
	
	//Vector<Integer> paarungen= new Vector<Integer>();
	//Vector<Integer> tische= new Vector<Integer>();
	HashMap<Integer,Integer> paarungen= new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> tische= new HashMap<Integer,Integer>();
	
	int paired=-1;
	
	boolean deleted = false;
	
	KHauptFenster hf;
	JButton matrixButton = new JButton();
	JButton punkteFensterButton = new JButton();
	JButton punkteTabButton = new JButton();
	JCheckBox anwesend = new JCheckBox();
	@Override
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		hf.spielerFenster.anzeigen(this);
	}
	
	public Vector<KBegegnungen> getPossiblePairings(Vector<KBegegnungen> b){
		Vector<KBegegnungen> re = new Vector<KBegegnungen>();
		for(int i=0;i<b.size();i++){
			if(b.get(i).t1.equals(this)){
				int j=0;
				for(;j<re.size();j++){
					/*if(b.get(i).t2.platz<=re.get(j).t2.platz){
						break;
					}*/
					if(b.get(i).t2.tabellenPosition<=re.get(j).t2.tabellenPosition){
						break;
					}
				}
				re.add(j,b.get(i));
			}
		}
		/*for(int i=0;i<re.size();i++){
			System.out.println("Platz:"+re.get(i).t2.platz);
		}*/
		return re;
		
	}
	public Vector<KBegegnungen> get2ndMemberIn(Vector<KBegegnungen> b){
		Vector<KBegegnungen> re = new Vector<KBegegnungen>();
		for(int i=0;i<b.size();i++){
			if(b.get(i).t2.equals(this)){
				re.add(b.get(i));
			}
		}
		return re;
	}
	
}
