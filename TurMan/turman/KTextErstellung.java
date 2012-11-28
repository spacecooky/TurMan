package turman;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * Dient dem Drucken des Meldungsspeichers. 
 * @author jk
 *
 */
public class KTextErstellung{

	public void tabelleAnzeigen(Vector<KTeilnehmer> tV, int runde){

		File f = new File("tabelle.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(f);
			////////////////////////////Überschrift ////////////////////////
			fw.write("Tabelle Runde "+runde+"\r\n");
			fw.write("\r\n");


			String platz="Platz";
			String name="Name";
			String pri="Primär";
			String sek="Sekundär";
			String sos="SOS";

			platz=laengeAnpassenVorne(platz, 6);
			platz+="   ";
			name = laengeAnpassenHinten(name, 50);
			pri  = laengeAnpassenHinten(pri, 9);
			sek  = laengeAnpassenHinten(sek, 9);
			sos = laengeAnpassenHinten(sos, 9);

			String nachricht=platz+name+pri+sek+sos;
			fw.write(nachricht+"\r\n");

			for (int i=0;i<tV.size();i++){
				KTeilnehmer t=tV.get(i);
				nachricht =laengeAnpassenVorne(Integer.toString(t.platz), 6);
				nachricht+="   ";
				nachricht +=laengeAnpassenHinten(""+t.vorname+" "+t.nachname, 50);
				nachricht +=laengeAnpassenHinten(""+t.primär,9);
				nachricht +=laengeAnpassenHinten(""+t.sekundär,9);
				nachricht +=laengeAnpassenHinten(""+t.sos,9);
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
					nachricht +=laengeAnpassenHinten(""+bg.p1+" : "+bg.p2,13);
					nachricht +=laengeAnpassenHinten(""+bg.p12+" : "+bg.p22,13);
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
