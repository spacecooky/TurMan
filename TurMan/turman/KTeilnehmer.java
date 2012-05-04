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
	
	int prim�r=0;
	int prim�rEinzel=0;
	int sekund�r=0;
	int sekund�rEinzel=0;
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
	
	public Vector<KBegegnungen> getPossiblePairings(Vector<KBegegnungen> b){
		Vector<KBegegnungen> re = new Vector<KBegegnungen>();
		for(int i=0;i<b.size();i++){
			if(b.get(i).t1.equals(this)){
				int j=0;
				for(;j<re.size();j++){
					if(b.get(i).t2.platz<=re.get(j).t2.platz){
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
