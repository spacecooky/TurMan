package turman;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

public class KPairings {

	static boolean debug =false;

	static int RANDOM=0;
	static int SWISS=1;
	static boolean team=true;
	
	static boolean createPairings(KHauptFenster hf, int mode){
		System.out.println("NEW ROUND. MODE="+(mode==0?"RANDOM":"SWISS"));
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
			//for(int i=0;i<b.length;i++){
			for(int i=v.size()-1;i>=0;i--){
				if (v.get(i).paired==-1){
					v.get(i).paired=1;
					int fails=0;
					int gegner=i;//v.size();
					while(true){
						gegner = randomGenerator.nextInt(v.size());
						if(v.get(gegner).paired==-1){
							if(team && !v.get(i).team.equals("") && v.get(i).team.equals(v.get(gegner).team)){
								if(fails==v.size()-1){
									return false;
								} else{
									fails++;
								}
							}else{
								int gegnerNmbr=hf.teilnehmerVector.indexOf(v.get(gegner));
								if(v.get(i).paarungen.contains(gegnerNmbr)){
									if(fails==v.size()-1){
										return false;
									} else{
										fails++;
									}
								}else{
									int tnNmbr=hf.teilnehmerVector.indexOf(v.get(i));
									int ggNmbr=hf.teilnehmerVector.indexOf(v.get(gegner));
									buttons.add(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(tnNmbr+1)).getComponent(ggNmbr+1)));
									buttons.add(((KBegegnungen)((KTeilnehmerPanel)hf.HauptPanel.getComponent(ggNmbr+1)).getComponent(tnNmbr+1)));
									v.get(gegner).paired=tnNmbr;
									v.get(i).paired=ggNmbr;
									break;
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
			Vector<KTeilnehmer> tVector = new Vector<KTeilnehmer>();
			for(int i=hf.sortierterVector.size()-1;i>=0;i--){
				System.out.println(hf.sortierterVector.get(i).vorname+" "+hf.sortierterVector.get(i).nachname);
				tVector.add(hf.sortierterVector.get(i));
			}
			Vector<KBegegnungen> bVector= swiss(tVector,new Vector<KBegegnungen>(),hf);
			System.out.println(bVector.size());


			for(int i=0;i<bVector.size();i++){

				KBegegnungen b = bVector.get(i);
				KTeilnehmer tn1 = b.t1;
				KTeilnehmer tn2 = b.t2;
				System.out.println(tn1.vorname+" "+tn1.nachname+" : "+tn2.vorname+" "+tn2.nachname+" : "+b.primär+" : "+b.sekundär+" : "+b.sos);
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
			//System.out.println(sortiert.size());
			System.out.println(hf.begegnungsVector.size());
		} 

		return true;
	}


	static void runde(KHauptFenster hf){
		if(hf.teilnehmerVector.size()%2==1){
			hf.dialog.getDialog(hf.dialog.errorUngerade);
		}else{
			hf.sortieren(hf.punkteFenster.ab.isSelected());
			hf.rundenZaehler++;
			while(!KPairings.createPairings(hf,hf.mode)){};
			if(hf.mode==KPairings.RANDOM){
				hf.mode=KPairings.SWISS;
				KPairings.team=false;
			}
		}
		hf.herausforderungsVector.clear();
		tische(hf);	

	}

	static void tische(KHauptFenster hf){
		int tische[] = new int[hf.teilnehmerVector.size()/2];
		int bgCount=hf.teilnehmerVector.size()/2;
		Vector<KBegegnungen> tischlos= new Vector<KBegegnungen>();
		for(int i=(hf.rundenZaehler-1)*((bgCount));i<hf.begegnungsVector.size();i++){
			KTeilnehmer tn1 = hf.teilnehmerVector.get(hf.begegnungsVector.get(i).xPos);
			KTeilnehmer tn2 = hf.teilnehmerVector.get(hf.begegnungsVector.get(i).yPos);
			for(int j=0;j<tische.length;j++){
				if(tische[j]!=1 && !tn1.tische.contains(j) && !tn2.tische.contains(j)){
					//System.out.println("Tisch "+(j+1)+": "+tn1.vorname+" "+tn1.nachname+" : "+tn2.vorname+" "+tn2.nachname);
					tn1.tische.add(j);
					tn2.tische.add(j);
					hf.begegnungsVector.get(i).tisch=j;
					((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.begegnungsVector.get(i).yPos+1)).getComponent(hf.begegnungsVector.get(i).xPos+1)).tisch=j;
					tische[j]=1;
					break;
				}
			}
			if(tn1.tische.size()<hf.rundenZaehler){
				tischlos.add(hf.begegnungsVector.get(i));
			}
		}
		//System.out.println(tischlos.size());
		if(tischlos.size()>0){
			for(int i=0;i<tische.length && tischlos.size()>0;i++){
				if(tische[i]!=1){
					for(int j=hf.begegnungsVector.size()-1;j>hf.begegnungsVector.size()-bgCount;j--){
						KTeilnehmer tn1 = hf.teilnehmerVector.get(tischlos.get(0).xPos);
						KTeilnehmer tn2 = hf.teilnehmerVector.get(tischlos.get(0).yPos);
						KTeilnehmer tn3 = hf.teilnehmerVector.get(hf.begegnungsVector.get(j).xPos);
						KTeilnehmer tn4 = hf.teilnehmerVector.get(hf.begegnungsVector.get(j).yPos);
						if(tn1.tische.size()==hf.rundenZaehler){
							tn1.tische.remove(tn1.tische.lastElement());
							tn2.tische.remove(tn2.tische.lastElement());
						}
						if(!tn1.equals(tn3)){
							if(!tn1.tische.contains(hf.begegnungsVector.get(j).tisch) && 
									!tn2.tische.contains(hf.begegnungsVector.get(j).tisch) &&
									!tn3.tische.contains(i) &&
									!tn4.tische.contains(i)){
								tn3.tische.remove(tn3.tische.lastElement());
								tn4.tische.remove(tn4.tische.lastElement());
								tn1.tische.add(hf.begegnungsVector.get(j).tisch);
								tn2.tische.add(hf.begegnungsVector.get(j).tisch);
								tn3.tische.add(i);
								tn4.tische.add(i);
								tischlos.get(0).tisch=hf.begegnungsVector.get(j).tisch;
								hf.begegnungsVector.get(j).tisch=i;
								((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.begegnungsVector.get(j).yPos+1)).getComponent(hf.begegnungsVector.get(j).xPos+1)).tisch=i;
								
								tischlos.remove(0);
								break;
							}
						}
					}
				} 
			}
		}

	}

	@SuppressWarnings("unchecked")
	static Vector<KBegegnungen> swiss(Vector<KTeilnehmer> tV,Vector<KBegegnungen> bV,KHauptFenster hf){
		System.out.println(bV.size());
		Vector<KTeilnehmer> tVector=(Vector<KTeilnehmer>)tV.clone();
		for(int i=0;i < tVector.size();i++){
			System.out.println(""+i+"<"+tVector.size());
			Vector<KBegegnungen> bVector=(Vector<KBegegnungen>)bV.clone();
			if((hf.teilnehmerVector.indexOf(tVector.get(0))+1)!=hf.teilnehmerVector.indexOf(tVector.get(i))+1){
				Vector<KTeilnehmer> tneu=(Vector<KTeilnehmer>)tV.clone();
				if(hf.alleBegegnungenVector.contains((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tneu.get(0))+1)).getComponent(hf.teilnehmerVector.indexOf(tneu.get(i))+1))){
					System.out.println((hf.teilnehmerVector.indexOf(tneu.get(0))+1)+","+(hf.teilnehmerVector.indexOf(tneu.get(i))+1));
					bVector.add((KBegegnungen)((JPanel)hf.HauptPanel.getComponent(hf.teilnehmerVector.indexOf(tneu.get(0))+1)).getComponent(hf.teilnehmerVector.indexOf(tneu.get(i))+1));
					tneu.remove(i);
					tneu.remove(0);
					if(tneu.size()==0){
						return bVector;
					}
					bVector=swiss(tneu,bVector,hf);
					if(bVector!=null){
						return bVector;
					}
				}
			}
		}
		return null;
	}

}
