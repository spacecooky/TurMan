package turman;

import java.awt.Desktop;
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






	public void begegnungenAnzeigen(Vector<KBegegnungen> bV, int runde, KOptionenFeld of){

		File f = new File("begegnung.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			
			int nameWidth = ("Begegnung").length();
			int primWidth = ("Primär").length();
			int sekWidth = ("Sekundär").length();
			int terWidth = ("Tertiär").length();
				for (int i=0;i<bV.size();i++){
					KBegegnungen bg = bV.get(i);
					if(bg.runde==runde){
					KTeilnehmer tn1 = bg.t1;
					KTeilnehmer tn2 = bg.t2;
					nameWidth=(("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname).length()>nameWidth)?(("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname).length()):nameWidth;
					primWidth=((bg.p1pri+" : "+bg.p2pri).length()>primWidth)?((bg.p1pri+" : "+bg.p2pri).length()):primWidth;
					sekWidth=((bg.p1sek+" : "+bg.p2sek).length()>sekWidth)?((bg.p1sek+" : "+bg.p2sek).length()):sekWidth;
					terWidth=((bg.p1ter+" : "+bg.p2ter).length()>terWidth)?((bg.p1ter+" : "+bg.p2ter).length()):terWidth;
				}
			}
			
			////////////////////////////Überschrift ////////////////////////
			fw.write("Begegnungen Runde "+runde+"\r\n");
			fw.write("\r\n");
			String platz="Tisch";
			String name="   "+"Begegnung";
			String pri="Primär";
			String sek="Sekundär";
			String ter="Tertiär";

			platz=laengeAnpassenVorne(platz, 6);
			name = laengeAnpassenHinten(name, nameWidth+5);
			pri  = laengeAnpassenHinten(pri, primWidth+5);
			sek  = laengeAnpassenHinten(sek, sekWidth+5);
			ter  = laengeAnpassenHinten(ter, terWidth+5);

			String nachricht=platz+name+pri+(of.sPunkte.isSelected()?sek:"")+(of.tPunkte.isSelected()?ter:"");
			fw.write(nachricht+"\r\n");

			for (int i=0;i<bV.size();i++){
				KBegegnungen bg = bV.get(i);
				if(bg.runde==runde){
					KTeilnehmer tn1 = bg.t1;
					KTeilnehmer tn2 = bg.t2;
					nachricht =laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6);
					nachricht +=laengeAnpassenHinten("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname, nameWidth+5);
					nachricht +=laengeAnpassenHinten(""+bg.p1pri+" : "+bg.p2pri,primWidth+5);
					nachricht +=of.sPunkte.isSelected()?laengeAnpassenHinten(""+bg.p1sek+" : "+bg.p2sek,sekWidth+5):"";
					nachricht +=of.tPunkte.isSelected()?laengeAnpassenHinten(""+bg.p1ter+" : "+bg.p2ter,terWidth+5):"";
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
