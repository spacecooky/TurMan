package turman;

import java.util.Vector;

public class KTeam {
	
	public KTeam(String name){
		this.name=name;
	}
	
	String name="";
	
	int prim�r=0;
	int sekund�r=0;
	int sos=0;
	
	Vector<KTeilnehmer> teilnehmer=new Vector<KTeilnehmer>();
	Vector<Integer> paarungen= new Vector<Integer>();
	Vector<Integer> tische= new Vector<Integer>();
	
	int paired=-1;
	
	boolean deleted = false;
	
}
