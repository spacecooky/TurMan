package turman;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

public class KPairings {

	static boolean debug =false;

	static int RANDOM=0;
	static int SWISS=1;

	//TODO Paarungsalgorithmus ersetzen

	/**
	 * Berechnet die Paarungen je nach Turniertyp
	 * @param hf
	 * @param mode
	 * @return
	 */
	static boolean createPairings(KHauptFenster hf, int mode){
		int teamFailures=0;
		boolean teamFailureBool=false;
		int mirrorFailures=0;
		boolean mirrorFailureBool=false;
		int ortFailures=0;
		boolean ortFailureBool=false;
		int armeeFailures=0;
		boolean armeeFailureBool=false;

		//System.out.println("NEW ROUND. MODE="+(mode==0?"RANDOM":"SWISS"));
		Vector<KTeilnehmer> v;
		
		if(mode == RANDOM){
			v=hf.teilnehmerVector;
			Vector<KBegegnungen> buttons = new Vector<KBegegnungen>();
			for(int i=0;i<v.size();i++){
				v.get(i).paired=-1;
			}
			//Herausforderungen
			for(int i=0;i<hf.herausforderungsVector.size();i+=2){
				int ggNmbr=v.indexOf(hf.herausforderungsVector.get(i+1));
				int tnNmbr=v.indexOf(hf.herausforderungsVector.get(i));	
				v.get(ggNmbr).paired=tnNmbr;
				v.get(tnNmbr).paired=ggNmbr;
			}
			Random randomGenerator = new Random();
			for(int i=v.size()-1;i>=0;i--){
				if (v.get(i).paired==-1){
					v.get(i).paired=1;
					int fails=0;
					int gegner=i;//v.size();
					while(true){
						gegner = randomGenerator.nextInt(v.size());
						if(v.get(gegner).paired==-1){ //Der ausgewählte Gegner hat noch keine Paarung

							//Prüfung, falls Teams beachtet werden sollen
							if(hf.optionenFeld.teams.isSelected() && !v.get(i).team.equals("") && v.get(i).team.equals(v.get(gegner).team)){
								teamFailureBool=true;
							}
							//Prüfung, falls Mirrormatches beachtet werden sollen
							if(hf.optionenFeld.mirror.isSelected() && v.get(i).armee.equals(v.get(gegner).armee)){
								mirrorFailureBool=true;
							}
							//Prüfung, falls Orte beachtet werden sollen
							if(hf.optionenFeld.orte.isSelected() && v.get(i).ort.equals(v.get(gegner).ort)){
								ortFailureBool=true;
							}
							//Prüfung, falls Armeen beachtet werden sollen
							if(hf.optionenFeld.armeen.isSelected()){
								int runde=hf.rundenZaehler;
								for(int j=0;j<runde-1;j++){
									if(hf.teilnehmerVector.get(v.get(i).paarungen.get(j)).armee.equals(v.get(gegner).armee) ||
											hf.teilnehmerVector.get(v.get(gegner).paarungen.get(j)).armee.equals(v.get(i).armee)){
										armeeFailureBool=true;
									}
								}
							}
							//Auswertung ob die Paarung legal ist
							int gegnerNmbr=hf.teilnehmerVector.indexOf(v.get(gegner));
							if(!v.get(i).paarungen.contains(gegnerNmbr)&& // Es wurde noch nicht gegen diesen Gegner gespielt
									!teamFailureBool || teamFailures<Integer.parseInt(hf.optionenFeld.teamsField.getText()) &&// Es gibt keinen Konflikt bei den Teams der Spieler oder es ist eine bestimmte Anzahl erlaubt
									!mirrorFailureBool || mirrorFailures<Integer.parseInt(hf.optionenFeld.mirrorField.getText()) && // Es gibt keinen Konflikt bei Mirrormatches oder es ist eine bestimmte Anzahl erlaubt
									!ortFailureBool || ortFailures<Integer.parseInt(hf.optionenFeld.orteField.getText()) && // Es gibt keinen Konflikt mit Orten oder es ist eine bestimmte Anzahl erlaubt
									!armeeFailureBool || armeeFailures<Integer.parseInt(hf.optionenFeld.armeenField.getText()) // Es gibt keinen Konflikt mit Armeen oder es ist eine bestimmte Anzahl erlaubt
									){ 
								int tnNmbr=hf.teilnehmerVector.indexOf(v.get(i));
								int ggNmbr=hf.teilnehmerVector.indexOf(v.get(gegner));
								buttons.add(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(tnNmbr)).getComponent(ggNmbr)));
								buttons.add(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(ggNmbr)).getComponent(tnNmbr)));
								v.get(gegner).paired=tnNmbr;
								v.get(i).paired=ggNmbr;

								if(teamFailureBool){
									System.out.println("TeamFailure");
									teamFailures++;
									teamFailureBool=false;
								}
								if(mirrorFailureBool){
									System.out.println("MirrorFailure");
									mirrorFailures++;
									mirrorFailureBool=false;
								}
								if(ortFailureBool){
									System.out.println("OrteFailure");
									ortFailures++;
									ortFailureBool=false;
								}
								if(armeeFailureBool){
									System.out.println("ArmeeFailure");
									armeeFailures++;
									armeeFailureBool=false;
								}
								break;
							}else{
								if(fails==v.size()-1){
									return false;
								} else{
									fails++;
									teamFailureBool=false;
									mirrorFailureBool=false;
									ortFailureBool=false;
									armeeFailureBool=false;
								}
							}
						}else{
							if(fails==v.size()-1){
								return false;
							} else{
								fails++;
							}
						}
					}
				}
			}
			for(int i=0;i<buttons.size();i++){	
				buttons.get(i).setEnabled(true);
				buttons.get(i).setBackground(Color.orange);
				buttons.get(i).runde=hf.rundenZaehler;
				buttons.get(i).setText(""+hf.rundenZaehler);
				hf.alleBegegnungenVector.remove(buttons.get(i));
				if(i%2==0){
					hf.begegnungsVector.add(buttons.get(i));
				}
			}
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				hf.teilnehmerVector.get(i).paarungen.add(hf.teilnehmerVector.get(i).paired);
			}
		} else if(mode== SWISS){
			//Platzgruppen mischen
			hf.platzGruppenMischen(hf.sortierterVector);
			
			Vector<KTeilnehmer> tVector = new Vector<KTeilnehmer>();
			for(int i=hf.sortierterVector.size()-1;i>=0;i--){
				tVector.add(hf.sortierterVector.get(i));
			}

			//Herausforderungen aus tVector entfernen, Herausforderung ist schon komplett als Begegnung eingetragen
			for(int i=0;i<hf.herausforderungsVector.size();i++){
				tVector.remove(hf.herausforderungsVector.get(i));
			}
			
			
			//Paaren
			Vector<KBegegnungen> bVector= swiss(tVector,new Vector<KBegegnungen>(),hf,0,0,0,0);
			if(bVector==null){
				return false;
			}
			
			for(int i=0;i<bVector.size();i++){

				KBegegnungen b = bVector.get(i);
				//KTeilnehmer tn1 = b.t1;
				//KTeilnehmer tn2 = b.t2;
				//System.out.println(tn1.vorname+" "+tn1.nachname+" : "+tn2.vorname+" "+tn2.nachname+" : "+b.primär+" : "+b.sekundär+" : "+b.sos);
				b.setEnabled(true);
				b.setBackground(Color.orange);
				b.runde=hf.rundenZaehler;
				b.setText(""+hf.rundenZaehler);
				hf.begegnungsVector.add(b);
				b.t2.paarungen.add(b.xPos);
				b.t1.paarungen.add(b.yPos);
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
			//System.out.println(hf.begegnungsVector.size());
		} 

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
			hf.sortieren(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected());
			hf.rundenZaehler++;
			while(!KPairings.createPairings(hf,hf.mode)){
				if(hf.mode==KPairings.SWISS){
					System.out.println("SWISS-ERR");
					hf.rundenZaehler--;
					swissErr=true;
					hf.dialog.getErrorDialog(hf.dialog.errorSwiss);
					break;
				}
			};
			if(hf.mode==KPairings.RANDOM){
				hf.mode=KPairings.SWISS;
			}
		}
		hf.herausforderungsVector.clear();
		if((hf.teilnehmerVector.size()-hf.gelöschteTeilnehmer)%2==0 && swissErr==false){
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
					b.p2=hf.freilosPrim;
					b.p22=hf.freilosSek;
					b.setBackground(Color.green);
					b.begegnungsFensterButton.setBackground(Color.gray);
					b.begegnungsTabButton.setBackground(Color.gray);
					
					KBegegnungen b2 = ((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(b.yPos)).getComponent(b.xPos));
					b2.p1=hf.freilosPrim;
					b2.p12=hf.freilosPrim;
					b2.setBackground(Color.green);
					b2.begegnungsFensterButton.setBackground(Color.gray);
					b2.begegnungsTabButton.setBackground(Color.gray);
					hf.sortieren(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected());
					break;
				}
				
				t =b.t2;
				if(t.vorname.equals("Freilos") && t.nachname.equals("Freilos") && t.nickname.equals("Freilos") && t.armee.equals("Freilos") && t.ort.equals("Freilos")){
					b.p1=hf.freilosPrim;
					b.p12=hf.freilosSek;
					b.setBackground(Color.green);
					b.begegnungsFensterButton.setBackground(Color.gray);
					b.begegnungsTabButton.setBackground(Color.gray);
					
					KBegegnungen b2 = ((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(b.yPos)).getComponent(b.xPos));
					b2.p2=hf.freilosPrim;
					b2.p22=hf.freilosPrim;
					b2.setBackground(Color.green);
					b2.begegnungsFensterButton.setBackground(Color.gray);
					b2.begegnungsTabButton.setBackground(Color.gray);
					hf.sortieren(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected());
					break;
				}
			}
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
			tn1.tische.add(tisch);
			tn2.tische.add(tisch);
			hf.begegnungsVector.get(i).tisch=tisch;
			((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.begegnungsVector.get(i).yPos)).getComponent(hf.begegnungsVector.get(i).xPos)).tisch=tisch;
		}
		
		//Prüfung, falls gleiche Tische beachtet werden sollen
		if(hf.optionenFeld.tisch.isSelected()){
			//if(getTischFehlerSize(hf)>Integer.parseInt(hf.optionenFeld.tischField.getText())){
				
			for(int i=0;i<((bgCount+1)*bgCount/2) && getTischFehlerSize(hf)>Integer.parseInt(hf.optionenFeld.tischField.getText());i++ ){
				System.out.println("Tischfehler vorhanden(Start): "+getTischFehlerSize(hf));
				repairTischFehler(hf);
			//}
			}
			if(getTischFehlerSize(hf)>Integer.parseInt(hf.optionenFeld.tischField.getText())){
				hf.dialog.getInfoDialog(hf.dialog.infoTische);
			}
			System.out.println("Tischfehler vorhanden(Ende): "+getTischFehlerSize(hf));
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
						b.t1.tische.remove(b.t1.tische.lastIndexOf(b.tisch));
						b.t2.tische.remove(b.t2.tische.lastIndexOf(b.tisch));
						b2.t1.tische.remove(b2.t1.tische.lastIndexOf(b2.tisch));
						b2.t2.tische.remove(b2.t2.tische.lastIndexOf(b2.tisch));
						
						int b1tempTisch=b.tisch;
						b.tisch=b2.tisch;
						b2.tisch=b1tempTisch;
						b.t1.tische.add(b.tisch);
						b.t2.tische.add(b.tisch);
						b2.t1.tische.add(b2.tisch);
						b2.t2.tische.add(b2.tisch);
/*						int b1tempTisch=b.tisch;
						b.tisch=b2.tisch;
						b2.tisch=b1tempTisch;
						b.t1.tische.remove(b.t1.tische.lastElement());
						b.t1.tische.add(b.tisch);
						b.t2.tische.remove(b.t2.tische.lastElement());
						b.t2.tische.add(b.tisch);
						b2.t1.tische.remove(b2.t1.tische.lastElement());
						b2.t1.tische.add(b2.tisch);
						b2.t2.tische.remove(b2.t2.tische.lastElement());
						b2.t2.tische.add(b2.tisch);*/
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
	static Vector<KBegegnungen> swiss(Vector<KTeilnehmer> tV,Vector<KBegegnungen> bV,KHauptFenster hf,int teamFailures, int mirrorFailures,int ortFailures, int armeeFailures){
		boolean teamFailureBool=false;
		boolean mirrorFailureBool=false;
		boolean ortFailureBool=false;
		boolean armeeFailureBool=false;

		//System.out.println(bV.size());
		Vector<KTeilnehmer> tVector=(Vector<KTeilnehmer>)tV.clone();
		for(int i=0;i < tVector.size();i++){
			//System.out.println(""+i+"<"+tVector.size());
			Vector<KBegegnungen> bVector=(Vector<KBegegnungen>)bV.clone();
			if((hf.teilnehmerVector.indexOf(tVector.get(0))+1)!=hf.teilnehmerVector.indexOf(tVector.get(i))+1){//Falls beide Teilnehmer nicht identisch sind 
				Vector<KTeilnehmer> tneu=(Vector<KTeilnehmer>)tV.clone();
				KBegegnungen b=(KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tneu.get(0)))).getComponent(hf.teilnehmerVector.indexOf(tneu.get(i)));
				
				//Prüfung, falls Teams beachtet werden sollen
				if(hf.optionenFeld.teams.isSelected() && !tVector.get(0).team.equals("") && b.team()){
					teamFailureBool=true;
				}
				//Prüfung, falls Mirrormatches beachtet werden sollen
				if(hf.optionenFeld.mirror.isSelected() && b.mirror()){
					mirrorFailureBool=true;
				}
				//Prüfung, falls Orte beachtet werden sollen
				if(hf.optionenFeld.orte.isSelected() && b.ort()){
					ortFailureBool=true;
				}
				//Prüfung, falls Armeen beachtet werden sollen
				if(hf.optionenFeld.armeen.isSelected() && b.armee()){
					armeeFailureBool=true;
				}
				if((!teamFailureBool || teamFailures<Integer.parseInt(hf.optionenFeld.teamsField.getText())) &&// Es gibt keinen Konflikt bei den Teams der Spieler oder es ist eine bestimmte Anzahl erlaubt
						(!mirrorFailureBool || mirrorFailures<Integer.parseInt(hf.optionenFeld.mirrorField.getText())) && // Es gibt keinen Konflikt bei Mirrormatches oder es ist eine bestimmte Anzahl erlaubt
						(!ortFailureBool || ortFailures<Integer.parseInt(hf.optionenFeld.orteField.getText())) && // Es gibt keinen Konflikt mit Orten oder es ist eine bestimmte Anzahl erlaubt
						(!armeeFailureBool || armeeFailures<Integer.parseInt(hf.optionenFeld.armeenField.getText())) && // Es gibt keinen Konflikt mit Armeen oder es ist eine bestimmte Anzahl erlaubt
						hf.alleBegegnungenVector.contains(b)){//Falls sich die Begegnung noch im Pool aller möglichen Begegnungen befindet
					if(tVector.get(0).deleted){
						tneu.remove(0);
					}else if(tVector.get(i).deleted){
						tneu.remove(i);
					}else{
							//System.out.println((hf.teilnehmerVector.indexOf(tneu.get(0))+1)+","+(hf.teilnehmerVector.indexOf(tneu.get(i))+1));
							bVector.add((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tneu.get(0)))).getComponent(hf.teilnehmerVector.indexOf(tneu.get(i))));
							tneu.remove(i);
							tneu.remove(0);

							if(teamFailureBool){
								System.out.println("TeamFailure");
								teamFailures++;
							}
							if(mirrorFailureBool){
								System.out.println("MirrorFailure");
								mirrorFailures++;
							}
							if(ortFailureBool){
								System.out.println("OrteFailure");
								ortFailures++;
							}
							if(armeeFailureBool){
								System.out.println("ArmeeFailure");
								armeeFailures++;
							}
						
					}
					if(tneu.size()==0){
						return bVector;
					}
					bVector=swiss(tneu,bVector,hf,teamFailures,mirrorFailures,ortFailures,armeeFailures);
					if(bVector!=null){
						return bVector;
					}
				}
			}
		}
		return null;
	}

	static void rundeReset(KHauptFenster hf){
		if(hf.rundenZaehler>0){
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				KTeilnehmer t=hf.teilnehmerVector.get(i);
				int tnNmbr=i;
				for(int j=0;j<t.paarungen.size();j++){
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
						b.p1=0;
						b.p12=0;
						b.p2=0;
						b.p22=0;
						t.tische.remove(j);
						t.paarungen.remove(j);
					}
				}
			}
			hf.rundenZaehler--;
			hf.updatePanels();
			
			if(hf.rundenZaehler==0){
				hf.mode=KPairings.RANDOM;
			}
		}
		
	}
}
