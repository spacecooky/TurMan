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
			String sArr[]=s.split("\\|\\|");
			System.out.println("sArr.size(): "+sArr.length);
			String rundenzähler=s.split("\\|\\|")[0];
			hf.TID=s.split("\\|\\|")[1];
			String teilnehmerDaten=s.split("\\|\\|")[2];
			String begegnungsDaten=s.split("\\|\\|")[3];

			//Teilnehmerdaten
			String[] sAr=teilnehmerDaten.split("\r\n");
			for(int i=0;i<sAr.length;i++){
				String[] sBr = sAr[i].split(";");

				int id=Integer.parseInt(sBr[0]);
				String vorname =sBr[1];
				String nachname =sBr[2];
				String nickname =sBr[3];
				String vornameAlter =sBr[4];
				String nachnameAlter =sBr[5];
				String armee =sBr[6];
				String ort =sBr[7];
				String team =sBr[8];

				int unknown=0;
				try{
					unknown = Integer.parseInt(sBr[9]);
				}catch(NumberFormatException e){

				}

				int armeeliste = 0;
				try{
					armeeliste = Integer.parseInt(sBr[10]);
				}catch(NumberFormatException e){

				}

				int bezahlt = 0;
				try{
					bezahlt = Integer.parseInt(sBr[11]);
				}catch(NumberFormatException e){

				}

				int bemalwertung = 0;
				try{
					bemalwertung = Integer.parseInt(sBr[12]);
				}catch(NumberFormatException e){

				}

				int ntr = 0;
				try{
					ntr = Integer.parseInt(sBr[13]);
				}catch(NumberFormatException e){

				}

				hf.teilnehmerVector.add(new KTeilnehmer(id,vorname,nachname,nickname,armee,ort,team,unknown,armeeliste,bezahlt,ntr,hf));

				hf.teilnehmerVector.lastElement().bemalwertung=bemalwertung;
				hf.teilnehmerVector.lastElement().vornameAlter=vornameAlter;
				hf.teilnehmerVector.lastElement().nachnameAlter=nachnameAlter;

				if(sBr[12].contains("true")){
					hf.teilnehmerVector.lastElement().deleted=true;
				}

			}
			hf.fillPanels();
			hf.fillTeamPanels();

			//Begegnungsdaten
			String[] sCr=begegnungsDaten.split("\r\n");
			for(int i=0;i<sCr.length;i++){
				String[] sDr = sCr[i].split(";");
				KTeilnehmerPanel tnp=(KTeilnehmerPanel)hf.HauptPanel.getComponent(i);
				for(int j=0;j<sDr.length;j++){
					if(sDr[j].contains(",")){


						KBegegnungen bg = (KBegegnungen) tnp.getComponent(j);
						String[] sEr = sDr[j].split(",");
						bg.p1pri=Integer.parseInt(sEr[1]);
						bg.p1sek=Integer.parseInt(sEr[2]);
						bg.p2pri=Integer.parseInt(sEr[3]);
						bg.p2sek=Integer.parseInt(sEr[4]);
						//hf.teilnehmerVector.get(i).paarungen.add(j);//welcher Spieler
						hf.teilnehmerVector.get(i).paarungen.put(Integer.parseInt(sEr[5]),j);//welcher Spieler
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
							//bg.t1.tische.add(Integer.parseInt(sEr[6]));
							//bg.t2.tische.add(Integer.parseInt(sEr[6]));
							bg.t1.tische.put(bg.runde,Integer.parseInt(sEr[6]));
							bg.t2.tische.put(bg.runde,Integer.parseInt(sEr[6]));
						}
					}
				}
			}

			try{
				hf.rundenZaehler=Integer.parseInt(rundenzähler);
			}catch (NumberFormatException e) {
				// TODO Fehlerdialog
				System.err.println("rundenzähler nicht auslesbar");
			}

			for(int i=0;i<hf.teilnehmerVector.size();i++){
				if(hf.teilnehmerVector.get(i).deleted==true){
					hf.entfernenFenster.entfernen(i);
				}
			}

			hf.rundenAnzeige=hf.rundenZaehler;

			//Herausforderungen in den Herausforderungsvektor
			for(int i=0;i<hf.begegnungsVector.size();i++){
				KBegegnungen b = hf.begegnungsVector.get(i);
				if(b.runde>hf.rundenZaehler){
					KTeilnehmer t1= b.t1;
					KTeilnehmer t2= b.t2;
					if(!hf.herausforderungsVector.contains(t1)){
						hf.herausforderungsVector.add(t1);
						hf.herausforderungsVector.add(t2);
					}
				}
			}

			hf.updatePanels();

			if(hf.rundenZaehler>0){
				hf.anmeldung.setEnabled(false);
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void speichern(KHauptFenster hf, File f){
		try {
			FileWriter fw = new FileWriter(f);

			fw.write(hf.rundenZaehler+"");
			fw.write("||");
			fw.write(hf.TID+"");
			fw.write("||");
			//Teilnehmer
			for(int i=0;i<hf.teilnehmerVector.size();i++){
				KTeilnehmer tn = hf.teilnehmerVector.get(i);
				fw.write(tn.id+";");
				fw.write(tn.vorname+";");
				fw.write(tn.nachname+";");
				fw.write(tn.nickname+";");
				fw.write(tn.vornameAlter+";");
				fw.write(tn.nachnameAlter+";");
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
			for(int i=0;i<hf.HauptPanel.getComponentCount();i++){
				KTeilnehmerPanel tp = (KTeilnehmerPanel)hf.HauptPanel.getComponent(i);
				for(int j=0;j<tp.getComponentCount();j++){
					try{
						KBegegnungen bg =(KBegegnungen)tp.getComponent(j);
						//System.out.println(bg.tisch);
						if(bg.getBackground().equals(Color.darkGray) || bg.getBackground().equals(Color.black)){
							fw.write("1;");
						} else if(bg.getBackground().equals(Color.orange)){
							fw.write("2,"+bg.p1pri+","+bg.p1sek+","+bg.p2pri+","+bg.p2sek+","+bg.getText()+","+bg.tisch+";");
						} else if(bg.getBackground().equals(Color.green)){
							fw.write("3,"+bg.p1pri+","+bg.p1sek+","+bg.p2pri+","+bg.p2sek+","+bg.getText()+","+bg.tisch+";");
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

	static void speichernKonfigWrap(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		if(fileChooser.showSaveDialog(hf)==JFileChooser.APPROVE_OPTION){
			File f =new File(fileChooser.getSelectedFile().toString());
			if(f !=null){
				//System.out.println(f.getName());
				speichernKonfig(hf,f);	
			}
		}
	}

	static void ladenKonfigWrap(KHauptFenster hf){
		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		if(fileChooser.showOpenDialog(hf)==JFileChooser.APPROVE_OPTION){
			File f =new File(fileChooser.getSelectedFile().toString());
			if(f!=null){
				ladenKonfig(hf,f);
			}
		}
	}

	static void speichernKonfig(KHauptFenster hf, File f){
		try {
			FileWriter fw = new FileWriter(f);

			if(hf.optionenFeldVar.einzel.isSelected()){
				fw.write("turniertyp=einzel\r\n");
			} else if(hf.optionenFeldVar.team.isSelected()){
				fw.write("turniertyp=team\r\n");
			}

			if(hf.optionenFeldVar.schweizer.isSelected()){
				fw.write("modus=schweizer\r\n");
			} else if(hf.optionenFeldVar.zufall.isSelected()){
				fw.write("modus=zufall\r\n");
			} else if(hf.optionenFeldVar.ko.isSelected()){
				fw.write("modus=ko\r\n");
			}

			if(hf.optionenFeldVar.r1ntr.isSelected()){
				fw.write("runde1=ntr\r\n");
			} else if(hf.optionenFeldVar.r1zufall.isSelected()){
				fw.write("runde1=zufall\r\n");
			}
			
			if(hf.optionenFeldVar.teams.isSelected()){
				fw.write("teams="+hf.optionenFeldVar.teamsField.getText()+"\r\n");
			}
			if(hf.optionenFeldVar.orte.isSelected()){
				fw.write("orte="+hf.optionenFeldVar.orteField.getText()+"\r\n");
			}
			if(hf.optionenFeldVar.armeen.isSelected()){
				fw.write("armeen="+hf.optionenFeldVar.armeenField.getText()+"\r\n");
			}
			if(hf.optionenFeldVar.mirror.isSelected()){
				fw.write("mirror="+hf.optionenFeldVar.mirrorField.getText()+"\r\n");
			}
			if(hf.optionenFeldVar.tisch.isSelected()){
				fw.write("tisch="+hf.optionenFeldVar.tischField.getText()+"\r\n");
			}

			if(hf.optionenFeldVar.pPunkte.isSelected()){
				fw.write("erstwertung=Punkte\r\n");
			} else if(hf.optionenFeldVar.pStrength.isSelected()){
				fw.write("erstwertung=Strength\r\n");
			} else if(hf.optionenFeldVar.pRPI.isSelected()){
				fw.write("erstwertung=RPI\r\n");
			}

			if(hf.optionenFeldVar.sPunkte.isSelected()){
				fw.write("zweitwertung=Punkte\r\n");
			} else if(hf.optionenFeldVar.sStrength.isSelected()){
				fw.write("zweitwertung=Strength\r\n");
			} else if(hf.optionenFeldVar.sRPI.isSelected()){
				fw.write("zweitwertung=RPI\r\n");
			} else if(hf.optionenFeldVar.sSOS.isSelected()){
				fw.write("zweitwertung=SOS\r\n");
			} else if(hf.optionenFeldVar.sSOOS.isSelected()){
				fw.write("zweitwertung=SOOS\r\n");
			} else if(hf.optionenFeldVar.sKeine.isSelected()){
				fw.write("zweitwertung=Keine\r\n");
			}

			if(hf.optionenFeldVar.tPunkte.isSelected()){
				fw.write("drittwertung=Punkte\r\n");
			} else if(hf.optionenFeldVar.tStrength.isSelected()){
				fw.write("drittwertung=Strength\r\n");
			} else if(hf.optionenFeldVar.tRPI.isSelected()){
				fw.write("drittwertung=RPI\r\n");
			} else if(hf.optionenFeldVar.tSOS.isSelected()){
				fw.write("drittwertung=SOS\r\n");
			} else if(hf.optionenFeldVar.tSOOS.isSelected()){
				fw.write("drittwertung=SOOS\r\n");
			} else if(hf.optionenFeldVar.tKeine.isSelected()){
				fw.write("drittwertung=Keine\r\n");
			}

			if(hf.optionenFeldVar.matrixBenutzen.isSelected()){
				fw.write("matrix=ja\r\n");
			} else{
				fw.write("matrix=nein\r\n");
			}

			if(hf.optionenFeldVar.SUN.isSelected()){
				fw.write("sun=ja\r\n");
				if(hf.optionenFeldVar.SUN2_1_0.isSelected()){
					fw.write("sunmode=0\r\n");
				} else if(hf.optionenFeldVar.SUN3_1_0.isSelected()){
					fw.write("sunmode=1\r\n");
				} else if(hf.optionenFeldVar.SUN20_10_1.isSelected()){
					fw.write("sunmode=2\r\n");
				} else if(hf.optionenFeldVar.SUN_frei.isSelected()){
					fw.write("sunmode=3,"+hf.optionenFeldVar.SUN_S.getText()+","+hf.optionenFeldVar.SUN_U.getText()+","+hf.optionenFeldVar.SUN_N.getText()+""+"\r\n");
				}
			} else{
				fw.write("sun=nein\r\n");
			}

			if(hf.optionenFeldVar.bemalNo.isSelected()){
				fw.write("bemalwertung=keine\r\n");
			} else if(hf.optionenFeldVar.bemalPri.isSelected()){
				fw.write("bemalwertung=pri\r\n");
			} else if(hf.optionenFeldVar.bemalSek.isSelected()){
				fw.write("bemalwertung=sek\r\n");
			} else if(hf.optionenFeldVar.bemalTer.isSelected()){
				fw.write("bemalwertung=ter\r\n");
			}

			if(hf.optionenFeldVar.armeeNo.isSelected()){
				fw.write("armeewertung=keine\r\n");
			} else if(hf.optionenFeldVar.armeePri.isSelected()){
				fw.write("armeewertung=pri\r\n");
			} else if(hf.optionenFeldVar.armeeSek.isSelected()){
				fw.write("armeewertung=sek\r\n");
			} else if(hf.optionenFeldVar.armeeTer.isSelected()){
				fw.write("armeewertung=ter\r\n");
			}

			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		speichernMatrix(hf);
	}

	static void ladenKonfig(KHauptFenster hf, File f){

		hf.optionenFeldVar.clear();

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
							hf.optionenFeldVar.einzel.setSelected(true);
						} else if(optval.equals("team")){
							hf.optionenFeldVar.team.setSelected(true);
						}
					} else if(optname.equals("modus")){
						if(optval.equals("schweizer")){
							hf.optionenFeldVar.schweizer.setSelected(true);
						} else if(optval.equals("zufall")){
							hf.optionenFeldVar.zufall.setSelected(true);
						} else if(optval.equals("ko")){
							hf.optionenFeldVar.ko.setSelected(true);
						}
					} else if(optname.equals("erstwertung")){
						if(optval.equals("Punkte")){
							hf.optionenFeldVar.pPunkte.setSelected(true);
						} else if(optval.equals("Strength")){
							hf.optionenFeldVar.pStrength.setSelected(true);
						} else if(optval.equals("RPI")){
							hf.optionenFeldVar.pRPI.setSelected(true);
						}
					} else if(optname.equals("zweitwertung")){
						if(optval.equals("Punkte")){
							hf.optionenFeldVar.sPunkte.setSelected(true);
							hf.optionenFeldVar.matrixBenutzen.setEnabled(true);
							hf.optionenFeldVar.matrix.setEnabled(true);
						} else if(optval.equals("Strength")){
							hf.optionenFeldVar.sStrength.setSelected(true);
						} else if(optval.equals("RPI")){
							hf.optionenFeldVar.sRPI.setSelected(true);
						} else if(optval.equals("SOS")){
							hf.optionenFeldVar.sSOS.setSelected(true);
						} else if(optval.equals("SOOS")){
							hf.optionenFeldVar.sSOOS.setSelected(true);
						} else if(optval.equals("Keine")){
							hf.optionenFeldVar.sKeine.setSelected(true);
						}
					} else if(optname.equals("drittwertung")){
						if(optval.equals("Punkte")){
							hf.optionenFeldVar.tPunkte.setSelected(true);
						} else if(optval.equals("Strength")){
							hf.optionenFeldVar.tStrength.setSelected(true);
						} else if(optval.equals("RPI")){
							hf.optionenFeldVar.tRPI.setSelected(true);
						} else if(optval.equals("SOS")){
							hf.optionenFeldVar.tSOS.setSelected(true);
						} else if(optval.equals("SOOS")){
							hf.optionenFeldVar.tSOOS.setSelected(true);
						} else if(optval.equals("Keine")){
							hf.optionenFeldVar.tKeine.setSelected(true);
						}
					}else if(optname.equals("matrix")){
						if(optval.equals("ja")){
							hf.optionenFeldVar.matrixBenutzen.setSelected(true);
						} else if(optval.equals("nein")){
							hf.optionenFeldVar.matrixBenutzen.setSelected(false);
						} 
					}else if(optname.equals("sun")){
						if(optval.equals("ja")){
							hf.optionenFeldVar.SUN.setSelected(true);
						} else if(optval.equals("nein")){
							hf.optionenFeldVar.SUN.setSelected(false);
						}
					}else if(optname.equals("sunmode")){
						if(optval.startsWith("0")){
							hf.optionenFeldVar.SUN2_1_0.setSelected(true);
						} else if(optval.startsWith("1")){
							hf.optionenFeldVar.SUN3_1_0.setSelected(true);
						} else if(optval.startsWith("2")){
							hf.optionenFeldVar.SUN20_10_1.setSelected(true);
						} else if(optval.startsWith("3")){
							hf.optionenFeldVar.SUN_frei.setSelected(true);
							String[] free=optval.split(",");
							hf.optionenFeldVar.SUN_S.setText(free[1]);
							hf.optionenFeldVar.SUN_U.setText(free[2]);
							hf.optionenFeldVar.SUN_N.setText(free[3]);
						}
					}else if(optname.equals("bemalwertung")){
						if(optval.equals("keine")){
							hf.optionenFeldVar.bemalNo.setSelected(true);
						} else if(optval.equals("pri")){
							hf.optionenFeldVar.bemalPri.setSelected(true);
						} else if(optval.equals("sek")){
							hf.optionenFeldVar.bemalSek.setSelected(true);
						} else if(optval.equals("ter")){
							hf.optionenFeldVar.bemalTer.setSelected(true);
						} 
					} else if(optname.equals("armeewertung")){
						if(optval.equals("keine")){
							hf.optionenFeldVar.armeeNo.setSelected(true);
						} else if(optval.equals("pri")){
							hf.optionenFeldVar.armeePri.setSelected(true);
						} else if(optval.equals("sek")){
							hf.optionenFeldVar.armeeSek.setSelected(true);
						} else if(optval.equals("ter")){
							hf.optionenFeldVar.armeeTer.setSelected(true);
						} 
					} else if(optname.equals("teams")){
						hf.optionenFeldVar.teams.setSelected(true);
						hf.optionenFeldVar.teamsField.setText(optval);
					} else if(optname.equals("orte")){
						hf.optionenFeldVar.orte.setSelected(true);
						hf.optionenFeldVar.orteField.setText(optval);
					} else if(optname.equals("armeen")){
						hf.optionenFeldVar.armeen.setSelected(true);
						hf.optionenFeldVar.armeenField.setText(optval);
					} else if(optname.equals("mirror")){
						hf.optionenFeldVar.mirror.setSelected(true);
						hf.optionenFeldVar.mirrorField.setText(optval);
					} else if(optname.equals("tisch")){
						hf.optionenFeldVar.tisch.setSelected(true);
						hf.optionenFeldVar.tischField.setText(optval);
					} else if(optname.equals("runde1")){
						if(optval.equals("ntr")){
							hf.optionenFeldVar.r1ntr.setSelected(true);
						} else if(optval.equals("zufall")){
							hf.optionenFeldVar.r1zufall.setSelected(true);
						} 
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ladenMatrix(hf);
	}

	static void speichernMatrix(KHauptFenster hf){
		try {
			File f = new File("matrix");
			FileWriter fw = new FileWriter(f);

			for(int i=0;i<hf.matrix.spv.size();i++){
				fw.write(hf.matrix.spv.get(i)+";");
				fw.write(hf.matrix.tpa.get(i)+";");
				fw.write(hf.matrix.tpb.get(i)+"\r\n");
			}

			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void ladenMatrix(KHauptFenster hf){

		String s ="";
		int read;
		File f = new File("matrix");
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

			//Splitten in Matrixzeilen
			String[] matrix=s.split("\r\n");
			hf.matrix.felder=matrix.length;
			hf.matrix.spv.clear();
			hf.matrix.tpa.clear();
			hf.matrix.tpb.clear();
			for(int i=0;i<matrix.length;i++){
				String zeile[]=matrix[i].split(";");
				hf.matrix.spv.add(zeile[0]);
				hf.matrix.tpa.add(zeile[1]);
				hf.matrix.tpb.add(zeile[2]);
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