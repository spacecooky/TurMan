package turman;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Dient dem Drucken des Meldungsspeichers. 
 * @author jk
 *
 */
public class KTextPrintable implements Printable{
	static Font font = new Font("Courier",Font.PLAIN,8);
	static Font font2 = new Font("Courier",Font.BOLD,8);
	//static Font font = new Font("Bitstream Vera Sans Mono",Font.PLAIN,8);
	//static Font font2 = new Font("Bitstream Vera Sans Mono",Font.BOLD,8);

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
		if(sicht==PUNKTE){
			if(pageIndex > (hf.sortierterVector.size())/50){
				return Printable.NO_SUCH_PAGE;
			}
	
			g.setFont(font2);
	
			//////////////////////////// Überschrift ////////////////////////
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
			g.drawString(nachricht,75,84+14);
	
			g.setFont(font);
	
			for (int i=0;i<50;i++){
				if((hf.sortierterVector.size()-(i+1)-(pageIndex*50))>=0){
					KTeilnehmer t=hf.sortierterVector.get(hf.sortierterVector.size()-(i+1)-(pageIndex*50));
					nachricht =laengeAnpassenVorne(Integer.toString(t.platz), 6);
					nachricht+="   ";
					nachricht +=laengeAnpassenHinten(""+t.vorname+" "+t.nachname, 50);
					nachricht +=laengeAnpassenHinten(""+t.primaer,9);
					nachricht +=laengeAnpassenHinten(""+t.sekundaer,9);
					nachricht +=laengeAnpassenHinten(""+t.sos,9);
					g.drawString(nachricht,75,84+14*(i+2));
				}
			}
		} else if(sicht==BEGEGNUNG){
			if(pageIndex > (hf.sortierterVector.size())/50){
				return Printable.NO_SUCH_PAGE;
			}
	
			g.setFont(font2);
	
			//////////////////////////// Überschrift ////////////////////////
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
			g.drawString(nachricht,55,84+14);
	
			g.setFont(font);
			
			for (int i=0;i<50;i++){
				if(i+pageIndex*50<hf.begegnungsVector.size()){
					KBegegnungen bg = hf.begegnungsVector.get(i+pageIndex*50);
					if(bg.runde==hf.rundenZaehler){
						KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
						KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
						nachricht =laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6);
						nachricht +=laengeAnpassenHinten("   "+tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname, 73);
						nachricht +=laengeAnpassenHinten(""+bg.p1pri+" : "+bg.p2pri,13);
						nachricht +=laengeAnpassenHinten(""+bg.p1sek+" : "+bg.p2sek,13);
						g.drawString(nachricht,55,84+14*(i+2));
					}
				}
			}
		} else if(sicht==ZUSPUNKTE){
			if(pageIndex > (hf.teilnehmerVector.size())/50){
				return Printable.NO_SUCH_PAGE;
			}
	
			g.setFont(font2);
	
			//////////////////////////// Überschrift ////////////////////////
			
			String name="Name";
			String pri="Bemalpunkte";
			String sek="Armeeliste";
			
			name = laengeAnpassenHinten(name, 50);
			pri  = laengeAnpassenHinten(pri, 13);
			sek  = laengeAnpassenHinten(sek, 13);
			
			String nachricht=name+pri+sek;
			g.drawString(nachricht,75,84+14);
	
			g.setFont(font);
	
			for (int i=0;i<50;i++){
				if(i+pageIndex*50<hf.teilnehmerVector.size()){
					KTeilnehmer t=hf.teilnehmerVector.get(i+pageIndex*50);
					nachricht =laengeAnpassenHinten(t.vorname+" "+t.nachname, 50);
					nachricht +=laengeAnpassenHinten(""+t.bemalwertung,13);
					nachricht +=laengeAnpassenHinten(""+t.armeeliste,13);
					g.drawString(nachricht,75,84+14*(i+2));
				}
			}
		}
		return Printable.PAGE_EXISTS;
	}

	int sicht=0;
	static int PUNKTE=0;
	static int BEGEGNUNG=1;
	static int ZUSPUNKTE=2;
	KHauptFenster hf;

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
