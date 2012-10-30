package turman;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Erstellt die V-Con-Urkunden
 * Vorgefertigtes PDF wird genommen, pro Teilnehmer eingef�gt und mit Name und Armee versehen. Und dann wieder abgespeichert. 
 * @author Jan Koch
 *
 */
public class KUrkunde {

	public KUrkunde(){
	}

	
	int turnierX1=0;
	int turnierY1=0;
	int turnierX2=0;
	int turnierY2=0;
	String turnier1="";
	String turnier2="";
	
	int armyPosX=0;
	int armyPosY=0;
	int namePosX=0;
	int namePosY=0;
		
	/**
	 * Erstellt das Dokument anhand der Benutzereingaben, der gespeicherten Informationen und dem Erfolg der einzelnen Pr�fungen.
	 */
	public void urkundeErstellen(Vector<KTeilnehmer> tV,String zeile1,String zeile2){

		
		//System.out.println(tV.size());
			//Fonts
			Font name = FontFactory.getFont("Colonna MT", 32);
			Font nameKlein = FontFactory.getFont("Colonna MT", 28);
			Font turnier = FontFactory.getFont("Colonna MT", 36);
			Font oger = FontFactory.getFont("Colonna MT", 18);
			
			
			PdfReader reader;
			Document document = new Document();
			document.setMargins(0, 0, 0, 0);
			document.setMarginMirroring(true);
			try {
				reader = new PdfReader("Urkunde_Einzel.pdf");
				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("Urkunden.pdf")));
				document.open();
				// In das Urkunden-PDF werden so viele Kopien der einzelnen Urkunde eingef�gt, wie es Teilnehmer gibt.
				// Wurde ein Teilnehmer gel�scht, wird f�r jeden gel�schten eine Seite weniger eingef�gt.
				PdfImportedPage page;
				for (int i = 0; i < tV.size(); i++) {
						page = writer.getImportedPage(reader, 1);
						document.add(Image.getInstance(page));
				}
				
				document.close();
				
				//Auf jede Urkundenseite werden die Daten f�r einen Spieler eingetragen.
				//Gel�schte Spieler werden �bersprungen und der Z�hler del inkrementiert und bei sp�teren Seiten eingerechnet.
				reader = new PdfReader("Urkunden.pdf");
				PdfStamper stamper;
				stamper = new PdfStamper(reader, new FileOutputStream(new File("UrkundenFertig.pdf")));
				int del=0;
				for (int i = 0; i < tV.size(); i++) {
						PdfContentByte canvas = stamper.getOverContent(i+1-del);
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("V-Con 12.1",turnier), reader.getPageSize(i+1-del).width()/2, 525, 0);
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("WH40K-Turnier",turnier), reader.getPageSize(i+1-del).width()/2, 485, 0);
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("Kreuzender Brennzug",turnier), reader.getPageSize(i+1).width()/2, 525, 0);
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("FanDex Special",turnier), reader.getPageSize(i+1).width()/2, 485, 0);
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("Mit dem Brennzug",turnier), reader.getPageSize(i+1).width()/2, 525, 0);
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("nach Sibirien 2",turnier), reader.getPageSize(i+1).width()/2, 485, 0);
						ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase(zeile1,turnier), reader.getPageSize(i+1).width()/2, 525, 0);
						ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase(zeile2,turnier), reader.getPageSize(i+1).width()/2, 485, 0);
						
						//ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase((tV.size()-i-delPlaces)+".Platz",turnier), reader.getPageSize(i+1-del).width()/2, 400, 0);
						ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase(tV.get(i).platz+".Platz",turnier), reader.getPageSize(i+1-del).width()/2, 400, 0);
						
						String vn = tV.get(i).vornameAlter.equals("")?tV.get(i).vorname:tV.get(i).vornameAlter;
						String nn = tV.get(i).nachnameAlter.equals("")?tV.get(i).nachname:tV.get(i).nachnameAlter;
						String insertName = vn+" \""+tV.get(i).nickname+"\" "+nn;
						
						insertName = insertName.replace("\"\" ", "");
						ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase(insertName,insertName.length()<40?name:nameKlein), reader.getPageSize(i+1-del).width()/2, 310, 0);
						
						ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase("mit",name), reader.getPageSize(i+1-del).width()/2, 270, 0);
						
						ColumnText.showTextAligned(canvas,Element.ALIGN_CENTER, new Phrase(tV.get(i).armee,name), reader.getPageSize(i+1-del).width()/2, 230, 0);
						
						ColumnText.showTextAligned(canvas,Element.ALIGN_RIGHT, new Phrase("Turnier-Oger",oger), 185, 70, 0);
				}
				stamper.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
		try{
			 
			Desktop d = java.awt.Desktop.getDesktop();
			 d.open(new java.io.File("UrkundenFertig.pdf")); 
		 
		     }catch(Exception ex){
			  ex.printStackTrace();
		     }
	}

	/**
	 * Erstellt eine Tabellenzelle mit 2 Zeilen.
	 * @param ger Text f�r die obere Zeile.
	 * @param fger Schrifttyp der oberern Zeile.
	 * @param eng Text f�r die untere Zeile.
	 * @param feng Schrifttyp f�r die untere Zeile.
	 * @param noBorder true=kein Rahmen. false=Rahmen mit Dicke 1.
	 * @return Die erstellte Celle
	 */
	public PdfPCell createCell(String ger, Font fger, String eng, Font feng, boolean noBorder){
		PdfPCell cell = new PdfPCell();
		Phrase p = new Phrase(ger,fger);
		p.add(new Chunk(eng,feng));
		p.setLeading(8.0f);
		cell.addElement(p);
		if(noBorder){
			cell.setBorderWidth(0);
		}
		return cell;
	}

	
}