package turman;

import java.awt.Desktop;
import java.awt.FontMetrics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * Dient dem Drucken der Punkte/Begegnungen. 
 * @author jk
 *
 */
public class KTextErstellung{

	public void tabelleAnzeigen(Vector<KTeilnehmer> tV, int runde, KOptionenFeld of){

		File f = new File("tabelle.txt");
		FileWriter fw;
		try {
			
			String kopfPrim="";
			//Kopfzeile Primär
			if(of.pPunkte.isSelected()){
				kopfPrim="Primär";
			} else if(of.pRPI.isSelected()){
				kopfPrim="Primär (RPI)";
			} else if(of.pStrength.isSelected()){
				kopfPrim="Primär (Strenght of Schedule)";
			}
			String kopfSek="";
			//Kopfzeile Sekundär
			if(of.sPunkte.isSelected()){
				kopfSek="Sekundär";
			} else if(of.sRPI.isSelected()){
				kopfSek="Sekundär (RPI)";
			} else if(of.sStrength.isSelected()){
				kopfSek="Sekundär (Strenght of Schedule)";
			} else if(of.sSOS.isSelected()){
				kopfSek="Sekundär (SOS)";
			} else if(of.sSOOS.isSelected()){
				kopfSek="Sekundär (SOOS)";
			} 
			String kopfTer="";
			//Kopfzeile Teriär
			if(of.tPunkte.isSelected()){
				kopfTer="Tertiär";
			} else if(of.tRPI.isSelected()){
				kopfTer="Tertiär (RPI)";
			} else if(of.tStrength.isSelected()){
				kopfTer="Tertiär (Strenght of Schedule)";
			} else if(of.tSOS.isSelected()){
				kopfTer="Tertiär (SOS)";
			} else if(of.tSOOS.isSelected()){
				kopfTer="Tertiär (SOOS)";
			} 
			
			//Max Längenberechnungen
			int nameWidth = 4;
			int primWidth = kopfPrim.length();
			int sekWidth = kopfSek.length();
			int terWidth = kopfTer.length();
			for (int i=0;i<tV.size();i++){
				KTeilnehmer t=tV.get(i);
				nameWidth=((t.vorname+" "+t.nachname).length()>nameWidth)?(t.vorname+" "+t.nachname).length():nameWidth;
				primWidth=((""+t.erstwertung).length()>primWidth)?((""+t.erstwertung)).length():primWidth;
				sekWidth=((""+t.zweitwertung).length()>sekWidth)?((""+t.zweitwertung)).length():sekWidth;
				terWidth=((""+t.drittwertung).length()>terWidth)?((""+t.drittwertung)).length():terWidth;
			}
			
			fw = new FileWriter(f);
			////////////////////////////Überschrift ////////////////////////
			fw.write("Tabelle Runde "+runde+"\r\n");
			fw.write("\r\n");


			String platz="Platz";
			String name="Name";

			platz=laengeAnpassenVorne(platz, 6);
			platz+="   ";
			name = laengeAnpassenHinten(name, nameWidth+5);
			kopfPrim  = laengeAnpassenHinten(kopfPrim, primWidth +5);
			kopfSek  = laengeAnpassenHinten(kopfSek, sekWidth +5);
			kopfTer = laengeAnpassenHinten(kopfTer, terWidth+5);

			String nachricht=platz+name+kopfPrim+(of.sKeine.isSelected()?"":kopfSek)+(of.tKeine.isSelected()?"":kopfTer);
			fw.write(nachricht+"\r\n");

			for (int i=tV.size()-1;i>=0;i--){
				KTeilnehmer t=tV.get(i);
				nachricht =laengeAnpassenVorne(Integer.toString(t.platz), 6);
				nachricht+="   ";
				nachricht +=laengeAnpassenHinten(""+t.vorname+" "+t.nachname, nameWidth +5);
				nachricht +=laengeAnpassenHinten(""+t.erstwertung,primWidth +5);
				nachricht +=(of.sKeine.isSelected()?"":laengeAnpassenHinten(""+t.zweitwertung,sekWidth +5));
				nachricht +=(of.tKeine.isSelected()?"":laengeAnpassenHinten(""+t.drittwertung,terWidth +5));
				fw.write(nachricht+"\r\n");	
			}
			fw.close();

			Desktop d = java.awt.Desktop.getDesktop();
			d.open(new java.io.File("tabelle.txt")); 
		} catch (IOException e) {
			e.printStackTrace();
		}


	}






	public void begegnungenAnzeigen(Vector<KBegegnungen> bV, int runde){

		File f = new File("begegnung.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			////////////////////////////Überschrift ////////////////////////
			fw.write("Begegnungen Runde "+runde+"\r\n");
			fw.write("\r\n");
			String platz="Tisch";
			String name="Begegnung";
			String pri="Primär";
			String sek="Sekundär";

			platz=laengeAnpassenVorne(platz, 6);
			platz+="   ";
			name = laengeAnpassenHinten(name, 70);
			pri  = laengeAnpassenHinten(pri, 13);
			sek  = laengeAnpassenHinten(sek, 13);

			String nachricht=platz+name+pri+sek;
			fw.write(nachricht+"\r\n");

			for (int i=0;i<bV.size();i++){

				KBegegnungen bg = bV.get(i);
				if(bg.runde==runde){
					KTeilnehmer tn1 = bg.t1;
					KTeilnehmer tn2 = bg.t2;
					nachricht =laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6);
					nachricht +=laengeAnpassenHinten("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname, 73);
					nachricht +=laengeAnpassenHinten(""+bg.p1pri+" : "+bg.p2pri,13);
					nachricht +=laengeAnpassenHinten(""+bg.p1sek+" : "+bg.p2sek,13);
					fw.write(nachricht+"\r\n");
				}
			}
			fw.close();
			Desktop d = java.awt.Desktop.getDesktop();
			d.open(new java.io.File("begegnung.txt")); 
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public String laengeAnpassenVorne(String s, int i){
		while(s.length()<i){
			s= " "+s;
		}
		return s;
	}
	public String laengeAnpassenHinten(String s, int i){
		while(s.length()<i){
			s+= " ";
		}
		return s;
	}

}
