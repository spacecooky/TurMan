package turman;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

public class KSpeicherverwaltung {

	static void speichernWrap(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		if(fileChooser.showSaveDialog(hf)==JFileChooser.APPROVE_OPTION){
			File f =new File(fileChooser.getSelectedFile().toString());
			if(f !=null){
				//System.out.println(f.getName());
				speichern(hf,f);	
			}
		}
	}

	static void ladenWrap(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		if(fileChooser.showOpenDialog(hf)==JFileChooser.APPROVE_OPTION){
			File f =new File(fileChooser.getSelectedFile().toString());
			if(f!=null){
				laden(hf,f);
			}
		}
	}

	static void laden(KHauptFenster hf, File f){
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
			hf.fillTeamPanels();

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
							bg.begegnungsFensterButton.setBackground(Color.gray);
							bg.begegnungsTabButton.setBackground(Color.gray);
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
			}

			for(int i=0;i<hf.teilnehmerVector.size();i++){
				if(hf.teilnehmerVector.get(i).deleted==true){
					hf.entfernenFenster.entfernen(i);
				}
			}

			hf.updatePanels();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void speichern(KHauptFenster hf, File f){
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
						//System.out.println(bg.tisch);
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
				if(i!=hf.HauptPanel.getComponentCount()-1){
					fw.write("\r\n");
				}
			}
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void speichernKonfig(KHauptFenster hf, File f){
		try {
			FileWriter fw = new FileWriter(f);

			if(hf.optionenFeld.einzel.isSelected()){
				fw.write("turniertyp=einzel\r\n");
			} else if(hf.optionenFeld.team.isSelected()){
				fw.write("turniertyp=team\r\n");
			}

			if(hf.optionenFeld.schweizer.isSelected()){
				fw.write("modus=schweizer\r\n");
			} else if(hf.optionenFeld.zufall.isSelected()){
				fw.write("modus=zufall\r\n");
			} else if(hf.optionenFeld.ko.isSelected()){
				fw.write("modus=ko\r\n");
			}

			if(hf.optionenFeld.teams.isSelected()){
				fw.write("teams="+hf.optionenFeld.teamsField.getText()+"\r\n");
			}
			if(hf.optionenFeld.orte.isSelected()){
				fw.write("orte="+hf.optionenFeld.orteField.getText()+"\r\n");
			}
			if(hf.optionenFeld.armeen.isSelected()){
				fw.write("armeen="+hf.optionenFeld.armeenField.getText()+"\r\n");
			}
			if(hf.optionenFeld.mirror.isSelected()){
				fw.write("mirror="+hf.optionenFeld.mirrorField.getText()+"\r\n");
			}
			if(hf.optionenFeld.tisch.isSelected()){
				fw.write("tisch="+hf.optionenFeld.tischField.getText()+"\r\n");
			}

			if(hf.optionenFeld.PSS.isSelected()){
				fw.write("punktetyp=pss\r\n");
			} else if(hf.optionenFeld.TS.isSelected()){
				fw.write("punktetyp=ts\r\n");
			}

			if(hf.optionenFeld.bemalNo.isSelected()){
				fw.write("bemalwertung=keine\r\n");
			} else if(hf.optionenFeld.bemalPri.isSelected()){
				fw.write("bemalwertung=pri\r\n");
			} else if(hf.optionenFeld.bemalSek.isSelected()){
				fw.write("bemalwertung=sek\r\n");
			}

			if(hf.optionenFeld.armeeNo.isSelected()){
				fw.write("armeewertung=keine\r\n");
			} else if(hf.optionenFeld.armeePri.isSelected()){
				fw.write("armeewertung=pri\r\n");
			} else if(hf.optionenFeld.armeeSek.isSelected()){
				fw.write("armeewertung=sek\r\n");
			}

			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void ladenKonfig(KHauptFenster hf, File f){

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

			//Splitten in Optionen
			String[] optionen=s.split("\r\n");

			for(int i=0;i<optionen.length;i++){
				if(!optionen[i].equals("")){
					String optname= optionen[i].split("=")[0];
					String optval= optionen[i].split("=")[1];

					if(optname.equals("turniertyp")){
						if(optval.equals("einzel")){
							hf.optionenFeld.einzel.setSelected(true);
						} else if(optval.equals("team")){
							hf.optionenFeld.team.setSelected(true);
						}
					} else if(optname.equals("modus")){
						if(optval.equals("schweizer")){
							hf.optionenFeld.schweizer.setSelected(true);
						} else if(optval.equals("zufall")){
							hf.optionenFeld.zufall.setSelected(true);
						} else if(optval.equals("ko")){
							hf.optionenFeld.ko.setSelected(true);
						}
					} else if(optname.equals("punktetyp")){
						if(optval.equals("pss")){
							hf.optionenFeld.PSS.setSelected(true);
						} else if(optval.equals("TS")){
							hf.optionenFeld.TS.setSelected(true);
						} 
					} else if(optname.equals("bemalwertung")){
						if(optval.equals("keine")){
							hf.optionenFeld.bemalNo.setSelected(true);
						} else if(optval.equals("pri")){
							hf.optionenFeld.bemalPri.setSelected(true);
						} else if(optval.equals("sek")){
							hf.optionenFeld.bemalSek.setSelected(true);
						} 
					} else if(optname.equals("armeewerung")){
						if(optval.equals("keine")){
							hf.optionenFeld.armeeNo.setSelected(true);
						} else if(optval.equals("pri")){
							hf.optionenFeld.armeePri.setSelected(true);
						} else if(optval.equals("sek")){
							hf.optionenFeld.armeeSek.setSelected(true);
						} 
					}else if(optname.equals("teams")){
						hf.optionenFeld.teamsField.setText(optval);
					} else if(optname.equals("orte")){
						hf.optionenFeld.orteField.setText(optval);
					} else if(optname.equals("armeen")){
						hf.optionenFeld.armeenField.setText(optval);
					} else if(optname.equals("mirror")){
						hf.optionenFeld.mirrorField.setText(optval);
					} else if(optname.equals("tisch")){
						hf.optionenFeld.tischField.setText(optval);
					}

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
