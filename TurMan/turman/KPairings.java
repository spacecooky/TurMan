package turman;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JPanel;

public class KPairings {

	static boolean debug =false;

	/**
	 * Berechnet die Paarungen je nach Turniertyp
	 * @param hf
	 * @param mode
	 * @return
	 */
	static boolean createPairings(KHauptFenster hf){
			//Platzgruppen mischen
			hf.platzGruppenMischen(hf.sortierterVector);
			
			Vector<KTeilnehmer> tVector = new Vector<KTeilnehmer>();
			for(int i=hf.sortierterVector.size()-1;i>=0;i--){
				tVector.add(hf.sortierterVector.get(i));
			}
			
			//Begegnungspool vorbereiten
			@SuppressWarnings("unchecked")
			Vector<KBegegnungen> begegnungsPool = (Vector<KBegegnungen>)hf.alleBegegnungenVector.clone();
				//Herausforderungen aus tVector und pool entfernen, und Begegnung in den Begegnungsvektor eintragen
			for(int i=0;i<hf.herausforderungsVector.size();i+=2){
				KTeilnehmer t1 = hf.herausforderungsVector.get(i);
				KTeilnehmer t2 = hf.herausforderungsVector.get(i+1);
				
				int ggNmbr=hf.teilnehmerVector.indexOf(hf.herausforderungsVector.get(i+1));
				int tnNmbr=hf.teilnehmerVector.indexOf(hf.herausforderungsVector.get(i));
				
				//t2.paarungen.add(tnNmbr);
				//t1.paarungen.add(ggNmbr);
				t2.paarungen.put(hf.rundenZaehler,tnNmbr);
				t1.paarungen.put(hf.rundenZaehler,ggNmbr);
			}
			for(int i=0;i<hf.herausforderungsVector.size();i++){
				KTeilnehmer t1 = hf.herausforderungsVector.get(i);
				tVector.remove(t1);
				//System.out.println("Herausforderungsspieler "+t1.vorname+" "+t1.nachname+" wird entfernt.");
				
				Vector<KBegegnungen> bt1= t1.getPossiblePairings(begegnungsPool);
				Vector<KBegegnungen> bt12nd= t1.get2ndMemberIn(begegnungsPool);
				
				for(int j=0;j<bt1.size();j++){
					begegnungsPool.remove(bt1.get(j));
				}
				for(int j=0;j<bt12nd.size();j++){
					begegnungsPool.remove(bt12nd.get(j));
				}
			}
			
			//Entfernen aller unerlaubten Paarungen //alt
			/*for(int i=0; i<begegnungsPool.size();i++){
				KBegegnungen b = begegnungsPool.get(i);
				if(b.deleted()){
					begegnungsPool.remove(b);
					//i=0;
					i-=2;
				}
				System.out.println("Rundenzähler: "+hf.rundenZaehler+" Paarungs-ID:"+i);
				if(hf.optionenFeldVar.armeenBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.armee()){
						begegnungsPool.remove(b);
						i=0;
					}
				}
				if(hf.optionenFeldVar.mirrorBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.mirror()){
						begegnungsPool.remove(b);
						i=0;
					}
				}
				if(hf.optionenFeldVar.orteBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.ort()){
						begegnungsPool.remove(b);
						i=0;
					}
				}
				if(hf.optionenFeldVar.teamsBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.team()){
						begegnungsPool.remove(b);
						i=0;
					}
				}
			}*/
			//Entfernen aller unerlaubten Paarungen
			for(int i=begegnungsPool.size()-1; i>=0;i--){
				KBegegnungen b = begegnungsPool.get(i);
				if(b.deleted()){
					begegnungsPool.remove(b);
					//System.out.println("Paarung entfernt, da Teilnehmer gelöscht");
					//i=0;
				}
				//System.out.println("Rundenzähler: "+hf.rundenZaehler+" Paarungs-ID:"+i);
				if(hf.optionenFeldVar.armeenBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.armee()){
						begegnungsPool.remove(b);
						i=0;
					}
				}
				if(hf.optionenFeldVar.mirrorBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.mirror()){
						begegnungsPool.remove(b);
						//System.out.println("Paarung entfernt, da Mirrormatch");
						//i=0;
					}
				}
				if(hf.optionenFeldVar.orteBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.ort()){
						begegnungsPool.remove(b);
						//System.out.println("Paarung entfernt, da gleicher Ort");
						//i=0;
					}
				}
				if(hf.optionenFeldVar.teamsBoxes.get(hf.rundenZaehler-1).isSelected()){
					if(b.team()){
						begegnungsPool.remove(b);
						//System.out.println("Paarung entfernt, da gleiches Team");
						//i=0;
					}
				}
			}
			
			//Test, ob ein Spieler keine möglichen Paarungen hat
			for(int i=0;i < tVector.size();i++){
				Vector<KBegegnungen> pB =tVector.get(i).getPossiblePairings(begegnungsPool);
				//System.out.println(tVector.get(i).vorname+" "+tVector.get(i).nachname+", mögliche Paarungen: "+pB.size());
				if(pB.size()==0){
					return false;
				}
			}
			
			//Paaren
			Vector<KBegegnungen> bVector= swiss3(tVector,new Vector<KBegegnungen>(),begegnungsPool);
			if(bVector==null){
				return false;
			}
			System.out.println("BegegnungenEnde: "+bVector.size());
			for(int i=0;i<bVector.size();i++){

				KBegegnungen b = bVector.get(i);
				//System.out.println(b.t1.vorname+" "+b.t1.nachname+" : "+b.t2.vorname+" "+b.t2.nachname);
				b.setEnabled(true);
				b.setBackground(Color.orange);
				b.runde=hf.rundenZaehler;
				b.setText(""+hf.rundenZaehler);
				hf.begegnungsVector.add(b);
				b.t2.paarungen.put(hf.rundenZaehler,b.xPos);
				b.t1.paarungen.put(hf.rundenZaehler,b.yPos);
				for(int j=0;j<hf.alleBegegnungenVector.size();j++){
					KBegegnungen b2 = hf.alleBegegnungenVector.get(j);
					if(b.xPos==b2.yPos && b.yPos==b2.xPos){
						b2.setEnabled(true);
						b2.setBackground(Color.orange);
						b2.runde=hf.rundenZaehler;
						b2.setText(""+hf.rundenZaehler);
						hf.alleBegegnungenVector.remove(b2);
						if(hf.begegnungsVector.contains(b2)){
							hf.begegnungsVector.remove(b2);
						}
						//sortiert.remove(b2);
						j--;
					}
				}
				hf.alleBegegnungenVector.remove(b);
			}
			System.out.println("Begegnungsvektor size: "+hf.begegnungsVector.size());
		

		return true;
	}


	/**
	 * Berechnet die Paarungen einer Runde je nach Turniertyp
	 * @param hf
	 */
	static void runde(KHauptFenster hf){
		boolean swissErr=false;
		if((hf.teilnehmerVector.size()-hf.gelöschteTeilnehmer)%2==1){
			hf.dialog.getErrorDialog(hf.dialog.errorUngerade);
		}else{
			//hf.sortieren(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
			hf.sortierenVar(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
			hf.rundenZaehler++;
			while(!KPairings.createPairings(hf)){
				System.out.println("SWISS-ERR");
				hf.rundenZaehler--;
				swissErr=true;
				hf.dialog.getErrorDialog(hf.dialog.errorSwiss);
				break;
			};
		}
		
		if((hf.teilnehmerVector.size()-hf.gelöschteTeilnehmer)%2==0 && swissErr==false){
			hf.herausforderungsVector.clear();
			tische(hf);
			if(hf.rundenZaehler>1){
				differenz(hf);
			}
		}
		
		//Freilos anzeigen
		for(int i=0; i<hf.begegnungsVector.size();i++){
			KBegegnungen b=hf.begegnungsVector.get(i);
			if(b.runde==hf.rundenZaehler){
				KTeilnehmer t =b.t1;
				if(t.vorname.equals("Freilos") && t.nachname.equals("Freilos") && t.nickname.equals("Freilos") && t.armee.equals("Freilos") && t.ort.equals("Freilos")){
					b.p2pri=hf.freilosPrim;
					b.p2sek=hf.freilosSek;
					b.setBackground(Color.green);
					b.begegnungsFensterButton.setBackground(Color.gray);
					b.begegnungsTabButton.setBackground(Color.gray);
					
					KBegegnungen b2 = ((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(b.yPos)).getComponent(b.xPos));
					b2.p1pri=hf.freilosPrim;
					b2.p1sek=hf.freilosPrim;
					b2.setBackground(Color.green);
					b2.begegnungsFensterButton.setBackground(Color.gray);
					b2.begegnungsTabButton.setBackground(Color.gray);
					//hf.sortieren(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
					hf.sortierenVar(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
					break;
				}
				
				t =b.t2;
				if(t.vorname.equals("Freilos") && t.nachname.equals("Freilos") && t.nickname.equals("Freilos") && t.armee.equals("Freilos") && t.ort.equals("Freilos")){
					b.p1pri=hf.freilosPrim;
					b.p1sek=hf.freilosSek;
					b.setBackground(Color.green);
					b.begegnungsFensterButton.setBackground(Color.gray);
					b.begegnungsTabButton.setBackground(Color.gray);
					
					KBegegnungen b2 = ((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(b.yPos)).getComponent(b.xPos));
					b2.p2pri=hf.freilosPrim;
					b2.p2sek=hf.freilosPrim;
					b2.setBackground(Color.green);
					b2.begegnungsFensterButton.setBackground(Color.gray);
					b2.begegnungsTabButton.setBackground(Color.gray);
					hf.sortierenVar(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
					//hf.sortieren(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
					break;
				}
			}
		}
		
		hf.rundenAnzeige=hf.rundenZaehler;
		if(hf.rundenZaehler>0){
			hf.anmeldung.setEnabled(false);
		}
		hf.updatePanels();
	}

	
	/**
	 * Verteilt die Spieler möglichst so an den Tischen, dass sie nicht mehrmals am selben Tisch spielen müssen
	 * @param hf
	 */
	static void tische(KHauptFenster hf){
		int bgCount=hf.teilnehmerVector.size()/2;
		for(int i=(hf.rundenZaehler-1)*((bgCount));i<hf.begegnungsVector.size();i++){
			int tisch=i-(hf.rundenZaehler-1)*((bgCount));
			KTeilnehmer tn1 = hf.teilnehmerVector.get(hf.begegnungsVector.get(i).xPos);
			KTeilnehmer tn2 = hf.teilnehmerVector.get(hf.begegnungsVector.get(i).yPos);
			//tn1.tische.add(tisch);
			//tn2.tische.add(tisch);
			tn1.tische.put(hf.rundenZaehler,tisch);
			tn2.tische.put(hf.rundenZaehler,tisch);
			hf.begegnungsVector.get(i).tisch=tisch;
			((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.begegnungsVector.get(i).yPos)).getComponent(hf.begegnungsVector.get(i).xPos)).tisch=tisch;
		}
		
		//Prüfung, falls gleiche Tische beachtet werden sollen
		if(hf.optionenFeldVar.tischBoxes.get(hf.rundenZaehler-1).isSelected()){
				
			for(int i=0;i<((bgCount+1)*bgCount/2);i++ ){
				//System.out.println("Tischfehler vorhanden(Start): "+getTischFehlerSize(hf));
				repairTischFehler(hf);
			
			}
			/*if(getTischFehlerSize(hf)>Integer.parseInt(hf.optionenFeldVar.tischField.getText())){
				hf.dialog.getInfoDialog(hf.dialog.infoTische);
			}*/
			//System.out.println("Tischfehler vorhanden(Ende): "+getTischFehlerSize(hf));
			for(int i=0;i<hf.teilnehmerVector.size();i++ ){
				String s="";
				KTeilnehmer t = hf.teilnehmerVector.get(i);
				s+=t.vorname+" "+t.nachname+": ";
				for(int j=0;j<t.tische.size();j++){
					s+=t.tische.get(j)+" ";
				}
				int rest=(56-s.length())/8;
				rest+=(40-s.length())%8==0?0:1;
				for(int j=0;j<rest;j++){
					s+="\t";
				}
				System.out.print(s);
			}
			System.out.println();
			
		}
	}
	
	/**
	 * Überprüft, wie weit bestimmte Paarungen auseinander liegen
	 * @param hf
	 */
	static void differenz(KHauptFenster hf){
		hf.dialog.differenz.clear();
		int bgCount=hf.teilnehmerVector.size()/2;
		for(int i=(hf.rundenZaehler-1)*((bgCount));i<hf.begegnungsVector.size();i++){
			KTeilnehmer tn1 = hf.teilnehmerVector.get(hf.begegnungsVector.get(i).xPos);
			KTeilnehmer tn2 = hf.teilnehmerVector.get(hf.begegnungsVector.get(i).yPos);
			
			int t1pos = hf.sortierterVector.indexOf(tn1)+1;
			int t2pos = hf.sortierterVector.indexOf(tn2)+1;
			
			if(t1pos-t2pos >1){
				hf.dialog.differenz.add("Position "+t1pos+" gegen Position "+t2pos);
			}
				
			if(t2pos-t1pos >1){
				hf.dialog.differenz.add("Position "+t2pos+" gegen Position "+t1pos);
			}
		}
		if(hf.dialog.differenz.size()>0){
			hf.dialog.setPaarungsDialog();
			hf.dialog.getInfoDialog(hf.dialog.infoDifferenz);
		}
	}
	
	static int getTischFehlerSize(KHauptFenster hf){
		int tischfehler=0;
		for(int i=(hf.rundenZaehler-1)*((hf.teilnehmerVector.size()/2));i<hf.begegnungsVector.size();i++){
			KBegegnungen b =hf.begegnungsVector.get(i);
			if(b.tischfehler(b.tisch)){
				tischfehler++;
			}
		}
		return tischfehler;
	}
	
	static void repairTischFehler(KHauptFenster hf){
		for(int i=(hf.rundenZaehler-1)*((hf.teilnehmerVector.size()/2));i<hf.begegnungsVector.size();i++){
			KBegegnungen b =hf.begegnungsVector.get(i);
			if(b.tischfehler(b.tisch)){
				for(int j=(hf.rundenZaehler-1)*((hf.teilnehmerVector.size()/2));j<hf.begegnungsVector.size();j++){
					KBegegnungen b2 =hf.begegnungsVector.get(j);
					if(!b2.tischfehler(b.tisch) && !b.tischfehler(b2.tisch)){
//						b.t1.tische.remove(b.t1.tische.lastIndexOf(b.tisch));
//						b.t2.tische.remove(b.t2.tische.lastIndexOf(b.tisch));
//						b2.t1.tische.remove(b2.t1.tische.lastIndexOf(b2.tisch));
//						b2.t2.tische.remove(b2.t2.tische.lastIndexOf(b2.tisch));
						b.t1.tische.remove(hf.rundenZaehler);
						b.t2.tische.remove(hf.rundenZaehler);
						b2.t1.tische.remove(hf.rundenZaehler);
						b2.t2.tische.remove(hf.rundenZaehler);
						
						int b1tempTisch=b.tisch;
						b.tisch=b2.tisch;
						b2.tisch=b1tempTisch;
//						b.t1.tische.add(b.tisch);
//						b.t2.tische.add(b.tisch);
//						b2.t1.tische.add(b2.tisch);
//						b2.t2.tische.add(b2.tisch);
						b.t1.tische.put(hf.rundenZaehler,b.tisch);
						b.t2.tische.put(hf.rundenZaehler,b.tisch);
						b2.t1.tische.put(hf.rundenZaehler,b2.tisch);
						b2.t2.tische.put(hf.rundenZaehler,b2.tisch);
						((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(b.yPos)).getComponent(b.xPos)).tisch=b.tisch;
						((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(b2.yPos)).getComponent(b2.xPos)).tisch=b2.tisch;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Rekursive Berechnung der Paarungen
	 * 
	 * @param tV
	 * @param bV
	 * @param hf
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static Vector<KBegegnungen> swiss3(Vector<KTeilnehmer> tV,Vector<KBegegnungen> bV,Vector<KBegegnungen> Expool){
		//System.out.println("Übrige Teilnehmer: "+tV.size());
		if(tV.size()==0){
			return bV;
		}
		
		Vector<KBegegnungen> pB =tV.get(0).getPossiblePairings(Expool);
		//System.out.println(tV.get(0).vorname+" "+tV.get(0).nachname+", mögliche Paarungen: "+pB.size());
//		for(int i=0;i < pB.size();i++){
//			System.out.println(pB.get(i).t1.vorname+ " "+pB.get(i).t1.nachname+" : "+pB.get(i).t2.vorname+ " "+pB.get(i).t2.nachname);
//		}
		//System.out.println("Berechnete Begegnungen: "+bV.size());
		//System.out.println("Pool: "+Expool.size());
		//System.out.println();
		
		for(int i=0;i < pB.size();i++){
			Vector<KBegegnungen> retVec= (Vector<KBegegnungen>)bV.clone();
			Vector<KTeilnehmer> tVector=(Vector<KTeilnehmer>)tV.clone();
			Vector<KBegegnungen> pool=(Vector<KBegegnungen>)Expool.clone();
			KBegegnungen b = pB.get(i);
			
			KTeilnehmer t1 = b.t1;
			KTeilnehmer t2 = b.t2;
			Vector<KBegegnungen> bt1= t1.getPossiblePairings(pool);
			Vector<KBegegnungen> bt2= t2.getPossiblePairings(pool);
			Vector<KBegegnungen> bt12nd= t1.get2ndMemberIn(pool);
			Vector<KBegegnungen> bt22nd= t2.get2ndMemberIn(pool);
			
			retVec.add(b);
			tVector.remove(t1);
			tVector.remove(t2);
			//System.out.println("tvector.size(): "+tVector.size());
			for(int j=0;j<bt1.size();j++){
				pool.remove(bt1.get(j));
			}
			for(int j=0;j<bt2.size();j++){
				pool.remove(bt2.get(j));
			}
			for(int j=0;j<bt12nd.size();j++){
				pool.remove(bt12nd.get(j));
			}
			for(int j=0;j<bt22nd.size();j++){
				pool.remove(bt22nd.get(j));
			}
			
			
			retVec = swiss3(tVector, retVec, pool);
			if(retVec!=null){
				return retVec;
			}
		}
		return null;
	}

	static void rundeReset(KHauptFenster hf){
		//System.out.println("Begegnungen vor Reset: "+hf.begegnungsVector.size());
		System.out.println(hf.rundenZaehler);
		if(hf.rundenZaehler>0){
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				KTeilnehmer t=hf.teilnehmerVector.get(i);
				int tnNmbr=i;
				//for(int j=0;j<t.paarungen.size();j++){
				for(int j=1;j<=hf.rundenZaehler;j++){
					if(t.paarungen.get(j)!=null){//Bei Hash. Bei Vector entfernen
						int ggNmbr=t.paarungen.get(j);
						KBegegnungen b = ((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(tnNmbr)).getComponent(ggNmbr));
						if(b.runde==hf.rundenZaehler){
							hf.alleBegegnungenVector.add(b);
							if(hf.begegnungsVector.contains(b)){
								hf.begegnungsVector.remove(b);
							}
							b.setEnabled(false);
							b.setUnpairedColor();
							b.runde=0;
							b.tisch=0;
							b.setText("");
							b.p1pri=0;
							b.p1sek=0;
							b.p2pri=0;
							b.p2sek=0;
							t.tische.remove(j);
							t.paarungen.remove(j);
						}
					}
				}
			}
			hf.rundenZaehler--;
			hf.updatePanels();
			
			if(hf.rundenZaehler==0){
				hf.anmeldung.setEnabled(true);
			}
		}
		//System.out.println("Begegnungen nach Reset: "+hf.begegnungsVector.size());
	}
}
