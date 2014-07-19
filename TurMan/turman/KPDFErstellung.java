package turman;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class KPDFErstellung {

	public KPDFErstellung(){
	}

	Font normal = FontFactory.getFont("Courier", 8,Font.NORMAL);
	Font bold = FontFactory.getFont("Courier", 8,Font.BOLD);
	Font title = FontFactory.getFont("Courier", 14,Font.BOLD);

	public void tabelleAnzeigen(Vector<KTeilnehmer> tV, int runde, KOptionenFeld of){

		Document document = new Document();
		document.setMargins(55, 55, 20, 40);
		document.setMarginMirroring(true);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("tabelle.pdf")));
			writer.setLinearPageMode();
			document.open();
			Chapter tabelle= new Chapter(new Paragraph(new Chunk("Tabelle Runde "+runde,title).setLocalDestination("")),runde);
			tabelle.setNumberDepth(0);
			tabelle.add(Chunk.NEWLINE);

			
			float[] widthsP = {0.1f, 0.6f, 0.1f};
			float[]	widthsPS = {0.1f, 0.6f, 0.1f, 0.1f};
			float[]	widthsPST = {0.1f, 0.6f, 0.1f, 0.1f, 0.1f};
			
			PdfPTable table2 = new PdfPTable(widthsPST);
			
			if(of.sKeine.isSelected()){
				table2 = new PdfPTable(widthsP);
			} else if(of.tKeine.isSelected()){
				table2 = new PdfPTable(widthsPS);
			}
			
			tabelle.add(table2);
			table2.setTotalWidth(document.getPageSize().width()-110);
			table2.setLockedWidth(true);
			table2.setHorizontalAlignment(0);

			PdfPCell cellHeader1 = new PdfPCell(new Phrase("Platz",bold));
			PdfPCell cellHeader2 = new PdfPCell(new Phrase("Spieler",bold));
			table2.addCell(cellHeader1);
			table2.addCell(cellHeader2);
			
			//Kopfzeile Primär
			if(of.pPunkte.isSelected()){
				PdfPCell cellHeader4 = new PdfPCell(new Phrase("Primär",bold));
				table2.addCell(cellHeader4);
			} else if(of.pRPI.isSelected()){
				PdfPCell cellHeader4 = new PdfPCell(new Phrase("Primär (RPI)",bold));
				table2.addCell(cellHeader4);;
			} else if(of.pStrength.isSelected()){
				PdfPCell cellHeader4 = new PdfPCell(new Phrase("Primär (Strenght of Schedule)",bold));
				table2.addCell(cellHeader4);
			}
			
			
			//Kopfzeile Sekundär
			if(of.sPunkte.isSelected()){
				PdfPCell cellHeader5 = new PdfPCell(new Phrase("Sekundär",bold));
				table2.addCell(cellHeader5);
			} else if(of.sRPI.isSelected()){
				PdfPCell cellHeader5 = new PdfPCell(new Phrase("Sekundär (RPI)",bold));
				table2.addCell(cellHeader5);
			} else if(of.sStrength.isSelected()){
				PdfPCell cellHeader5 = new PdfPCell(new Phrase("Sekundär (Strenght of Schedule)",bold));
				table2.addCell(cellHeader5);
			} else if(of.sSOS.isSelected()){
				PdfPCell cellHeader5 = new PdfPCell(new Phrase("Sekundär (SOS)",bold));
				table2.addCell(cellHeader5);
			} else if(of.sSOOS.isSelected()){
				PdfPCell cellHeader5 = new PdfPCell(new Phrase("Sekundär (SOOS)",bold));
				table2.addCell(cellHeader5);
			} 
				
			//Kopfzeile Teriär
			if(of.tPunkte.isSelected()){
				PdfPCell cellHeader6 = new PdfPCell(new Phrase("SOS",bold));
				table2.addCell(cellHeader6);
			} else if(of.tRPI.isSelected()){
				PdfPCell cellHeader6 = new PdfPCell(new Phrase("Tertiär (RPI)",bold));
				table2.addCell(cellHeader6);
			} else if(of.tStrength.isSelected()){
				PdfPCell cellHeader6 = new PdfPCell(new Phrase("Tertiär (Strenght of Schedule)",bold));
				table2.addCell(cellHeader6);
			} else if(of.tSOS.isSelected()){
				PdfPCell cellHeader6 = new PdfPCell(new Phrase("Tertiär (SOS)",bold));
				table2.addCell(cellHeader6);
			} else if(of.tSOOS.isSelected()){
				PdfPCell cellHeader6 = new PdfPCell(new Phrase("Tertiär (SOOS)",bold));
				table2.addCell(cellHeader6);
			}
			

			table2.setHeaderRows(1);

			for(int i=tV.size()-1; i>=0;i--){
				table2.addCell(new PdfPCell(new Phrase(""+tV.get(i).platz,normal)));
				table2.addCell(new PdfPCell(new Phrase(""+tV.get(i).vorname+" "+tV.get(i).nachname,normal)));
				table2.addCell(new PdfPCell(new Phrase(""+tV.get(i).erstwertung,normal)));
				if(!of.sKeine.isSelected()){
					table2.addCell(new PdfPCell(new Phrase(""+tV.get(i).zweitwertung,normal)));
				}
				if(!of.tKeine.isSelected()){
					table2.addCell(new PdfPCell(new Phrase(""+tV.get(i).drittwertung,normal)));
				}
			}


			document.newPage();
			document.add(tabelle);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		try{

			Desktop d = java.awt.Desktop.getDesktop();
			d.open(new java.io.File("tabelle.pdf")); 

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void begegnungenAnzeigen(Vector<KBegegnungen> bV,int runde, KOptionenFeld of){

		Document document = new Document();
		document.setMargins(55, 55, 20, 40);
		document.setMarginMirroring(true);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File("begegnung.pdf")));
			writer.setLinearPageMode();
			document.open();
			Chapter tabelle= new Chapter(new Paragraph(new Chunk("Begegnungen Runde "+runde,title).setLocalDestination("")),runde);
			tabelle.setNumberDepth(0);
			tabelle.add(Chunk.NEWLINE);

			float[] widthsPST = {0.06f, 0.64f, 0.1f, 0.1f, 0.1f};
			float[] widthsPS = {0.06f, 0.64f, 0.1f, 0.1f};
			float[] widthsP = {0.06f, 0.64f, 0.1f};
			PdfPTable table2 = new PdfPTable(widthsP);
			if(of.tPunkte.isSelected()){
				table2 = new PdfPTable(widthsPST);
			}else if(of.sPunkte.isSelected()){
				table2 = new PdfPTable(widthsPS);
			}
			tabelle.add(table2);
			table2.setTotalWidth(document.getPageSize().width()-110);
			table2.setLockedWidth(true);
			table2.setHorizontalAlignment(0);

			PdfPCell cellHeader1 = new PdfPCell(new Phrase("Tisch",bold));
			PdfPCell cellHeader2 = new PdfPCell(new Phrase("Begegnung",bold));
			PdfPCell cellHeader4 = new PdfPCell(new Phrase("Primär",bold));
			table2.addCell(cellHeader1);
			table2.addCell(cellHeader2);
			table2.addCell(cellHeader4);
			
			if(of.sPunkte.isSelected()){
				PdfPCell cellHeader5 = new PdfPCell(new Phrase("Sekundär",bold));
				table2.addCell(cellHeader5);
			}
			if(of.tPunkte.isSelected()){
				PdfPCell cellHeader6 = new PdfPCell(new Phrase("Tertiär",bold));
				table2.addCell(cellHeader6);
			}

			

			table2.setHeaderRows(1);

			for(int i=0; i<bV.size();i++){
				if(bV.get(i).runde==runde){
					table2.addCell(new PdfPCell(new Phrase(""+(bV.get(i).tisch+1),normal)));
					table2.addCell(new PdfPCell(new Phrase(""+bV.get(i).t1.vorname+" "+bV.get(i).t1.nachname+" : "+bV.get(i).t2.vorname+" "+bV.get(i).t2.nachname,normal)));
					table2.addCell(new PdfPCell(new Phrase(""+bV.get(i).p1pri+" : "+bV.get(i).p2pri,normal)));
					if(of.sPunkte.isSelected()){
						table2.addCell(new PdfPCell(new Phrase(""+bV.get(i).p1sek+" : "+bV.get(i).p2sek,normal)));
					}
					if(of.tPunkte.isSelected()){
						table2.addCell(new PdfPCell(new Phrase(""+bV.get(i).p1ter+" : "+bV.get(i).p2ter,normal)));
					}
				}
			}


			document.newPage();
			document.add(tabelle);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		try{
			Desktop d = java.awt.Desktop.getDesktop();
			d.open(new java.io.File("begegnung.pdf")); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}