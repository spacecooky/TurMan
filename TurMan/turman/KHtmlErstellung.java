package turman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

/**
 * Dient dem Drucken der Punkte/Begegnungen. 
 * @author jk
 *
 */
public class KHtmlErstellung{

	public KHtmlErstellung(KHauptFenster hf, Vector<KTeilnehmer> tV, KOptionenFeld of){

		File f = new File("urkunden.html");
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
			
			OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(f),"CP1252");
			//fw = new FileWriter(f);
			////////////////////////////Überschrift ////////////////////////
			fw.write("<TD><FONT  SIZE=+4>"+hf.turniername+"</FONT></TD>");
			fw.write("<TD>Platz</TD>");
			fw.write("<TD>Name</TD>");
			fw.write("<TD>"+kopfPrim+"</TD>");
			fw.write(of.sKeine.isSelected()?"":("<TD>"+kopfSek+"</TD>"));
			fw.write(of.tKeine.isSelected()?"":("<TD>"+kopfTer+"</TD>"));

			for (int i=tV.size()-1;i>=0;i--){
				KTeilnehmer t=tV.get(i);
				fw.write("<TD>"+t.platz+".</TD>");
				fw.write("<TD>"+t.vorname+" "+t.nachname+"</TD>");
				fw.write("<TD>"+t.erstwertung+"</TD>");
				fw.write(of.sKeine.isSelected()?"":("<TD>"+t.zweitwertung+"</TD>"));
				fw.write(of.tKeine.isSelected()?"":("<TD>"+t.drittwertung+"</TD>"));	
			}
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
