﻿package turman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class KPort {

	static void gImport(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		FileFilter ff = new FileNameExtensionFilter("GöPP Importformat von T³ (.gip)", "gip");
		fileChooser.addChoosableFileFilter(ff);
		fileChooser.setFileFilter(ff);
		if(fileChooser.showOpenDialog(hf)==JFileChooser.APPROVE_OPTION){
			File f =new File(fileChooser.getSelectedFile().toString());
			if(f!=null){
				KSpeicherverwaltung.leeren(hf);
				hf.gelöschteTeilnehmer=0;
				//KPairings.team=true; //Haken muss manuell entfernt werden
				hf.rundenZaehler=0;
				hf.alleBegegnungenVector.clear();

				String s ="";
				int read;
				//FileReader fr;
				InputStreamReader isr;
				try {
					isr = new InputStreamReader(new FileInputStream(f),"CP1252");
					//fr = new FileReader(f);
					
					while(true){
						//read=fr.read();
						read=isr.read();
						if(read==-1){
							break;
						}else{
							s+=(char)read;
						}
					}
					//fr.close();
					isr.close();
					String[] sAr=s.split("\r\n");
					hf.turniername=sAr[1];
					hf.TID=sAr[2];
					for(int i=0;i<sAr.length;i++){
						if(sAr[i].endsWith("x")){
							String[] sBr = sAr[i].split("\\|\\|");
							int id=Integer.parseInt(sBr[0]);
							String vorname =sBr[1];
							String nachname =sBr[2];
							String nickname =sBr[3];
							String armee =sBr[4];
							String ort =sBr[5];
							String team =sBr[6];

							int unknown=0;
							try{
								unknown = Integer.parseInt(sBr[7]);
							}catch(NumberFormatException e){
							}

							int armeeliste = 0;
							try{
								armeeliste = Integer.parseInt(sBr[8]);
							}catch(NumberFormatException e){
							}

							int bezahlt = 0;
							try{
								bezahlt = Integer.parseInt(sBr[9]);
							}catch(NumberFormatException e){
							}

							int ntr = 999999;
							try{
								ntr = Integer.parseInt(sBr[10]);
							}catch(NumberFormatException e){
							}

							hf.teilnehmerVector.add(new KTeilnehmer(id,vorname,nachname,nickname,armee,ort,team,unknown,armeeliste,bezahlt,ntr,hf));
							
							if(!hf.teamVector.contains(team)){
								hf.teamVector.add(team);
							}
						}
					}
					hf.fillPanels();
					hf.fillTeamPanels();
					hf.updatePanels();
					hf.anmeldung.setEnabled(true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	}

	static void gExport(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		FileFilter ff = new FileNameExtensionFilter("GöPP Exportformat für T³ (.gep)", "gep");
		fileChooser.addChoosableFileFilter(ff);
		fileChooser.setFileFilter(ff);
		if(fileChooser.showSaveDialog(hf)==JFileChooser.APPROVE_OPTION){
			String s=fileChooser.getSelectedFile().toString();
			if(!s.endsWith(".gep")){
				s+=".gep";
			}
			File f =new File(s);
			if(f!=null){
				try {
					//FileWriter fw = new FileWriter(f);
					OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f),"CP1252");
					String date = datum();

					fw.write("#GoePP-Exportdatei, v1.3.3 Export vom "+date+"||x\r\n");
					fw.write("#TID-"+hf.TID+"||x\r\n");
					hf.sortierenVar(hf.punkteFenster.ab.isSelected(),hf.punkteFenster.bm.isSelected(),hf.rundenZaehler);
					for(int i=hf.sortierterVector.size()-1;i>=0;i--){
						KTeilnehmer tn = hf.sortierterVector.get(i);
							int primeinzel=(int)tn.erstwertungEinzel;
							int armee=(hf.optionenFeldVar.armeePri.isSelected()?tn.armeeliste:0);
							int bemalung=(hf.optionenFeldVar.bemalPri.isSelected()?tn.bemalwertung:0);
							int primkomp=primeinzel+armee+bemalung;
							int platz=tn.platz;
							//ID||Vorname||Nachname||Nickname||Armee||Ort||Team||Platziereung||TPGesamt||TP||??||AL||Bemalung||Quiz||Fairplay||Sonstige||
							fw.write(tn.id+"||"+tn.vorname+"||"+tn.nachname+"||"+tn.nickname+"||"+tn.armee+"||"+tn.ort+"||"+tn.team+"||"+platz+"||"+primkomp+"||"+primeinzel+"||0||"+armee+"||"+bemalung+"||0||0||0||x\r\n");
					}
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static void eloExport(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		if(fileChooser.showSaveDialog(hf)==JFileChooser.APPROVE_OPTION){
			File f =new File(fileChooser.getSelectedFile().toString());
			if(f!=null){
				try {
					//FileWriter fw = new FileWriter(f);
					OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f),"CP1252");
					@SuppressWarnings("unused")
					String date = datum();

					
					// Kommentarzeilen
					fw.write("c Turniername-Platzhalter\r\n");
					fw.write("c Datums-Platzhalter DD-MM-YYYY\r\n");
					fw.write("c Teilnehmer: "+hf.teilnehmer+"\r\n");
					fw.write("c Sieg ab Punkte-Ungleichheit\r\n");
					fw.write("c \r\n");
					//Spielerdaten
					for(int i=hf.sortierterVector.size()-1;i>=0;i--){
						KTeilnehmer tn = hf.sortierterVector.get(i);
						//Hans Pete Fortmann : Orks und Goblins
						fw.write(tn.vorname+" "+tn.nachname+" : "+tn.armee+"\r\n");
					}
					
					//Ergebnisse
					for(int runde=1;runde<=hf.rundenZaehler;runde++){
						fw.write("#\r\n");
						for(int i=hf.begegnungsVector.size()-1;i>=0;i--){
							KBegegnungen b = hf.begegnungsVector.get(i);
							if(b.runde==runde){
								KTeilnehmer tn1=b.t1;
								KTeilnehmer tn2=b.t2;
								int sun = 0;
								if(b.p1pri>b.p2pri){
									sun = 1;
								}else if(b.p1pri<b.p2pri){
									sun = 2;
								}
								//Benjamin Breitbach : Stephan Heiser	 1
								fw.write(tn1.vorname+" "+tn1.nachname+" : "+tn2.vorname+" "+tn2.nachname+"\t"+sun+"\r\n");
							}
						}
					}
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Gibt das aktuelle Datum im Format DD.MM.YYYY wieder. 
	 * @return Aktuelles Datum
	 */
	static String datum(){
		DateFormat time = DateFormat.getDateInstance();
		return time.format(new Date());


	}

}
