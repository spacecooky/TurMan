package turman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFileChooser;

public class KPort {

	static void gImport(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		@SuppressWarnings("unused")
		int returnVal = fileChooser.showOpenDialog(hf);
		File f =new File(fileChooser.getSelectedFile().toString());
		KSpeicherverwaltung.leeren(hf);
		
		hf.mode=KPairings.RANDOM;
		KPairings.team=true;
		hf.rundenZaehler=0;
		hf.alleBegegnungenVector.clear();
		
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
			String[] sAr=s.split("\r\n");
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
					
					int ntr = 0;
					try{
						ntr = Integer.parseInt(sBr[10]);
					}catch(NumberFormatException e){
					}
					
					hf.teilnehmerVector.add(new KTeilnehmer(id,vorname,nachname,nickname,armee,ort,team,unknown,armeeliste,bezahlt,ntr));
				}
			}
			hf.fillPanels();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	static void gExport(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
		@SuppressWarnings("unused")
		int returnVal = fileChooser.showSaveDialog(hf);
		File f =new File(fileChooser.getSelectedFile().toString());
		try {
			FileWriter fw = new FileWriter(f);
			String date = datum();
			
			fw.write("#GoePP-Exportdatei, v1.3.3 Export vom "+date+"||x\r\n");
			fw.write("#TID-"+hf.TID+"||x\r\n");
			hf.sortieren(hf.punkteFenster.ab.isSelected());
			
			for(int i=hf.sortierterVector.size()-1;i>=0;i--){
				KTeilnehmer tn = hf.sortierterVector.get(i);
				fw.write(tn.id+"||"+tn.vorname+"||"+tn.nachname+"||"+tn.nickname+"||"+tn.armee+"||"+tn.ort+"||"+tn.team+"||"+(hf.sortierterVector.size()-i)+"||"+tn.prim�r+"||"+(tn.prim�r-tn.armeeliste)+"||"+tn.sekund�r+tn.sos+"||"+tn.armeeliste+"||0||0||0||0||x\r\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
