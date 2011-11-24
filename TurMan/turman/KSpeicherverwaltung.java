package turman;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class KSpeicherverwaltung {

	static void speichern(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		@SuppressWarnings("unused")
		int returnVal = fileChooser.showSaveDialog(hf);
		File f =new File(fileChooser.getSelectedFile().toString());
		System.out.println(f.getName());
		try {
			FileWriter fw = new FileWriter(f);
			
			//Teilnehmer
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				KTeilnehmer tn = hf.teilnehmerVector.get(i);
				fw.write(tn.id+";");
				fw.write(tn.vorname+";");
				fw.write(tn.nachname+";");
				fw.write(tn.nickname+";");
				fw.write(tn.armee+";");
				fw.write(tn.ort+";");
				fw.write(tn.team+";");
				fw.write(tn.unknown+";");
				fw.write(tn.armeeliste+";");
				fw.write(tn.bezahlt+";");
				fw.write(tn.bemalwertung+";");
				fw.write(tn.ntr+";");
				fw.write(tn.deleted==true?"true"+";":"false"+";");
				fw.write("\r\n");
			}
			fw.write("||");
			//Begegnungen
			for(int i=1;i<hf.HauptPanel.getComponentCount();i++){
				KTeilnehmerPanel tp = (KTeilnehmerPanel)hf.HauptPanel.getComponent(i);
				for(int j=1;j<tp.getComponentCount();j++){
					try{
						KBegegnungen bg =(KBegegnungen)tp.getComponent(j);
						System.out.println(bg.tisch);
						if(bg.getBackground().equals(Color.darkGray) || bg.getBackground().equals(Color.black)){
							fw.write("1;");
						} else if(bg.getBackground().equals(Color.orange)){
							fw.write("2,"+bg.p1+","+bg.p12+","+bg.p2+","+bg.p22+","+bg.getText()+","+bg.tisch+";");
						} else if(bg.getBackground().equals(Color.green)){
							fw.write("3,"+bg.p1+","+bg.p12+","+bg.p2+","+bg.p22+","+bg.getText()+","+bg.tisch+";");
						}
					}catch(ClassCastException e){
						fw.write("0;");
					}
				}
				fw.write("\r\n");
			}
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	static void laden(KHauptFenster hf){
		
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		@SuppressWarnings("unused")
		int returnVal = fileChooser.showOpenDialog(hf);
		File f =new File(fileChooser.getSelectedFile().toString());
		
		leeren(hf);
		hf.gelöschteTeilnehmer=0;
		String s ="";
		int read;
		FileReader fr;
		
			try {
				fr = new FileReader(f);
			
			while(true){
				read=fr.read();
				if(read==-1){
					break;
				}else{
					s+=(char)read;
				}
			}
			fr.close();
			//Splitten in Teilnehmerdaten und Begegnungsdaten
			String teilnehmerDaten=s.split("\\|\\|")[0];
			String begegnungsDaten=s.split("\\|\\|")[1];
			
			//Teilnehmerdaten
			String[] sAr=teilnehmerDaten.split("\r\n");
			for(int i=0;i<sAr.length;i++){
					String[] sBr = sAr[i].split(";");
					
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
					
					int bemalwertung = 0;
					try{
						bemalwertung = Integer.parseInt(sBr[10]);
					}catch(NumberFormatException e){
						
					}
					
					int ntr = 0;
					try{
						ntr = Integer.parseInt(sBr[11]);
					}catch(NumberFormatException e){
						
					}
					
					hf.teilnehmerVector.add(new KTeilnehmer(id,vorname,nachname,nickname,armee,ort,team,unknown,armeeliste,bezahlt,ntr));
				
					hf.teilnehmerVector.lastElement().bemalwertung=bemalwertung;
					
					if(sBr[12].contains("true")){
						hf.teilnehmerVector.lastElement().deleted=true;
					}
			
			}
			hf.fillPanels();
			
			//Begegnungsdaten
			String[] sCr=begegnungsDaten.split("\r\n");
			for(int i=0;i<sCr.length;i++){
				hf.rundenZaehler=0;
					String[] sDr = sCr[i].split(";");
					KTeilnehmerPanel tnp=(KTeilnehmerPanel)hf.HauptPanel.getComponent(i+1);
					for(int j=0;j<sDr.length;j++){
						if(sDr[j].contains(",")){
							hf.rundenZaehler++;
							
							KBegegnungen bg = (KBegegnungen) tnp.getComponent(j+1);
							String[] sEr = sDr[j].split(",");
							bg.p1=Integer.parseInt(sEr[1]);
							bg.p12=Integer.parseInt(sEr[2]);
							bg.p2=Integer.parseInt(sEr[3]);
							bg.p22=Integer.parseInt(sEr[4]);
							hf.teilnehmerVector.get(i).paarungen.add(j);
							if(sEr[0].equals("2")){
								bg.setBackground(Color.orange);
							} else if(sEr[0].equals("3")){
								bg.setBackground(Color.green);
							}
							bg.setText(sEr[5]);
							bg.runde=Integer.parseInt(sEr[5]);
							bg.tisch=Integer.parseInt(sEr[6]);
							bg.setEnabled(true);
							hf.alleBegegnungenVector.remove(bg);
							boolean bgAdd=true;
							for(int k=0;k<hf.begegnungsVector.size();k++){
								if(bg.xPos==hf.begegnungsVector.get(k).yPos &&
										bg.yPos==hf.begegnungsVector.get(k).xPos){
									bgAdd=false;
								}
							}
							if(bgAdd){
								hf.begegnungsVector.add(bg);
								bg.t1.tische.add(Integer.parseInt(sEr[6]));
								bg.t2.tische.add(Integer.parseInt(sEr[6]));
							}
						}
					}
			}
			if(hf.rundenZaehler>0){
				hf.mode=KPairings.SWISS;
				KPairings.team=false;
			}
			
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				if(hf.teilnehmerVector.get(i).deleted==true){
					hf.entfernenFenster.entfernen(i);
				}
			}
			
			
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		
	}
	
	static void leeren(KHauptFenster hf){
		hf.teilnehmerVector.removeAllElements();
		hf.begegnungsVector.removeAllElements();
		hf.HauptPanel.removeAll();
	}
	
	
}
