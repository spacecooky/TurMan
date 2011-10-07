package turman;

import java.util.Vector;

public class KTeilnehmer {
	
	public KTeilnehmer(String vorname,String nachname){
		this.vorname=vorname;
		this.nachname=nachname;
	}
	
	public KTeilnehmer(int id,String vorname,String nachname,String nickname, String armee,
			String ort, String team, int unknown, int armeeliste, int bezahlt, int ntr){
		
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
	int sekundär=0;
	int sos=0;
	int bemalwertung=0;
	
	Vector<Integer> paarungen= new Vector<Integer>();
	Vector<Integer> tische= new Vector<Integer>();
	Vector<KBegegnungen> sortiert= new Vector<KBegegnungen>();
	
	int paired=-1;
	
}
