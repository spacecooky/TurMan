package turman;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Dient dem Drucken der Punkte/Begegnungen. 
 * @author jk
 *
 */
public class KTextPrintable implements Printable{
	static Font begegnungsFont = new Font("Courier",Font.PLAIN,12);
	static Font begegnungsFont2 = new Font("Courier",Font.BOLD,12);
	static Font punkteFont = new Font("Courier",Font.PLAIN,10);
	static Font punkteFont2 = new Font("Courier",Font.BOLD,10);
	static Font font = new Font("Courier",Font.PLAIN,8);
	static Font font2 = new Font("Courier",Font.BOLD,8);
	//static Font font = new Font("Bitstream Vera Sans Mono",Font.PLAIN,8);
	//static Font font2 = new Font("Bitstream Vera Sans Mono",Font.BOLD,8);

	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {

		if(sicht==PUNKTE){
			if(pageIndex > (hf.sortierterVector.size())/50){
				return Printable.NO_SUCH_PAGE;
			}

			g.setFont(punkteFont2);

			//////////////////////////// Überschrift ////////////////////////

			KOptionenFeld of = hf.optionenFeldVar;

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
			FontMetrics fontMetrics = g.getFontMetrics();
			int nameWidth = fontMetrics.stringWidth("Name");
			int primWidth = fontMetrics.stringWidth(kopfPrim);
			int sekWidth = fontMetrics.stringWidth(kopfSek);
			int terWidth = fontMetrics.stringWidth(kopfTer);
			for (int i=0;i<hf.sortierterVector.size();i++){
				KTeilnehmer t=hf.sortierterVector.get(i);
				nameWidth=(fontMetrics.stringWidth(t.vorname+" "+t.nachname)>nameWidth)?(fontMetrics.stringWidth(t.vorname+" "+t.nachname)):nameWidth;
				primWidth=(fontMetrics.stringWidth(""+t.erstwertung)>primWidth)?(fontMetrics.stringWidth(""+t.erstwertung)):primWidth;
				sekWidth=(fontMetrics.stringWidth(""+t.zweitwertung)>sekWidth)?(fontMetrics.stringWidth(""+t.zweitwertung)):sekWidth;
				terWidth=(fontMetrics.stringWidth(""+t.drittwertung)>terWidth)?(fontMetrics.stringWidth(""+t.drittwertung)):terWidth;
			}

			int offsetTisch = 65;
			int offsetBeg = offsetTisch + 35;
			int offsetPri = offsetBeg + 30;
			int offsetSek = offsetPri + 30;
			int offsetTer = offsetSek + 30;

			//Kopfzeile
			g.drawString("Platz",offsetTisch,20);
			g.drawString("Name",offsetBeg,20);
			g.drawString(kopfPrim,offsetPri+nameWidth,20); 
			//Kopfzeile Sekundär
			if(!hf.optionenFeldVar.sKeine.isSelected()){
				g.drawString(kopfSek,offsetSek+nameWidth+primWidth,20);
			} 
			//Kopfzeile Teriär
			if(!hf.optionenFeldVar.tKeine.isSelected()){
				g.drawString(kopfTer,offsetTer+nameWidth+primWidth+sekWidth,20);
			} 

			g.setFont(punkteFont);

			for (int i=0;i<50;i++){
				if((hf.sortierterVector.size()-(i+1)-(pageIndex*50))>=0){
					KTeilnehmer t=hf.sortierterVector.get(i);
					g.drawString(laengeAnpassenVorne(Integer.toString(t.platz), 6),offsetTisch,(g.getFontMetrics(punkteFont).getHeight()+1)*i+50);
					g.drawString(t.vorname+" "+t.nachname,offsetBeg,(g.getFontMetrics(punkteFont).getHeight()+1)*i+50);
					g.drawString(""+t.erstwertung,offsetPri+nameWidth,(g.getFontMetrics(punkteFont).getHeight()+1)*i+50);
					if(!hf.optionenFeldVar.sKeine.isSelected()){
						g.drawString(""+t.zweitwertung,offsetSek+nameWidth+primWidth,(g.getFontMetrics(punkteFont).getHeight()+1)*i+50);
					}
					if(!hf.optionenFeldVar.tKeine.isSelected()){
						g.drawString(""+t.drittwertung,offsetTer+nameWidth+primWidth+sekWidth,(g.getFontMetrics(punkteFont).getHeight()+1)*i+50);
					} 
				}else{
					//g.drawString(laengeAnpassenVorne(Integer.toString(i+1), 6),offsetTisch,(g.getFontMetrics(punkteFont).getHeight()+1)*i+50);
				}
			}
		} else if(sicht==BEGEGNUNG){
			if(pageIndex > (hf.sortierterVector.size())/50){
				return Printable.NO_SUCH_PAGE;
			}

			g.setFont(begegnungsFont2);
			int offsetTisch = 25;
			int offsetBeg = offsetTisch + 35;
			int offsetPri = offsetBeg + 20;
			int offsetSek = offsetPri + 20;
			int offsetTer = offsetSek + 20;
			Graphics2D g2 = (Graphics2D)g; 


			//Max Längenberechnungen
			FontMetrics fontMetrics = g.getFontMetrics();
			int nameWidth = fontMetrics.stringWidth("Begegnung");
			int primWidth = fontMetrics.stringWidth("Primär");
			int sekWidth = fontMetrics.stringWidth("Sekundär");
			int terWidth = fontMetrics.stringWidth("Tertiär");
			for (int i=0;i<hf.begegnungsVector.size();i++){
				KBegegnungen bg = hf.begegnungsVector.get(i);
				if(bg.runde==hf.rundenZaehler){
					KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
					KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
					nameWidth=(fontMetrics.stringWidth(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname)>nameWidth)?(fontMetrics.stringWidth(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname)):nameWidth;
					primWidth=(fontMetrics.stringWidth(bg.p1pri+" : "+bg.p2pri)>primWidth)?(fontMetrics.stringWidth(bg.p1pri+" : "+bg.p2pri)):primWidth;
					sekWidth=(fontMetrics.stringWidth(bg.p1sek+" : "+bg.p2sek)>sekWidth)?(fontMetrics.stringWidth(bg.p1sek+" : "+bg.p2sek)):sekWidth;
					terWidth=(fontMetrics.stringWidth(bg.p1ter+" : "+bg.p2ter)>terWidth)?(fontMetrics.stringWidth(bg.p1ter+" : "+bg.p2sek)):terWidth;
				}
			}

			//////////////////////////// Überschrift ////////////////////////

			g.drawString("Tisch",offsetTisch,20);
			g.drawString("Begegnung",offsetBeg,20);
			g.drawString("Primär",offsetPri+nameWidth,20);
			if(hf.optionenFeldVar.sPunkte.isSelected()){
				g.drawString("Sekundär",offsetSek+nameWidth+primWidth,20);
			}
			if(hf.optionenFeldVar.tPunkte.isSelected()){
				g.drawString("Tertär",offsetTer+nameWidth+primWidth+sekWidth,20);
			}

			g.setFont(begegnungsFont);

			for (int i=0;i<50;i++){
				if(i+pageIndex*50<hf.begegnungsVector.size()){
					KBegegnungen bg = hf.begegnungsVector.get(i+pageIndex*50);
					if(bg.runde==hf.rundenZaehler){
						KTeilnehmer tn1 = hf.teilnehmerVector.get(bg.xPos);
						KTeilnehmer tn2 = hf.teilnehmerVector.get(bg.yPos);
						g.drawString(laengeAnpassenVorne(Integer.toString(bg.tisch+1), 6),offsetTisch,(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(tn1.vorname+" "+tn1.nachname +" : "+tn2.vorname+" "+tn2.nachname,offsetBeg,(g.getFontMetrics(font).getHeight()+5)*i+50);
						g.drawString(bg.p1pri+" : "+bg.p2pri,offsetPri+nameWidth,(g.getFontMetrics(font).getHeight()+5)*i+50);
						if(hf.optionenFeldVar.sPunkte.isSelected()){
							g.drawString(bg.p1sek+" : "+bg.p2sek,offsetSek+nameWidth+primWidth,(g.getFontMetrics(font).getHeight()+5)*i+50);
						}
						if(hf.optionenFeldVar.tPunkte.isSelected()){
							g.drawString(bg.p1ter+" : "+bg.p2ter,offsetTer+nameWidth+primWidth+sekWidth,(g.getFontMetrics(font).getHeight()+5)*i+50);
						}

						//Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
						//g2.setStroke(dashed);
						g2.drawLine(offsetTisch+16, (g.getFontMetrics(font).getHeight()+5)*i+53, offsetTer+nameWidth+primWidth+sekWidth+24, (g.getFontMetrics(font).getHeight()+5)*i+53);
					}
				}
				//Zum Test, ob 50 Zeilen auf ein Blatt passen
				//g.drawString(laengeAnpassenVorne(Integer.toString(i+1), 6),offsetTisch,(g.getFontMetrics(font).getHeight()+5)*i+50);
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
		}else if(sicht==ANMELDELISTE){
			if(pageIndex > (hf.sortierterVector.size())/50){
				return Printable.NO_SUCH_PAGE;
			}

			g.setFont(punkteFont2);

			String anwesend = "";
			String name = "Name"; //Vorname //Nickname //Nachname
			String armee = "Armee";
			String team = "Team";

			//////////////////////////// Überschrift ////////////////////////

			KOptionenFeld of = hf.optionenFeldVar;

			//Max Längenberechnungen
			FontMetrics fontMetrics = g.getFontMetrics();
			int nameWidth = fontMetrics.stringWidth(name);
			int armyWidth = fontMetrics.stringWidth(armee);
			int teamWidth = fontMetrics.stringWidth(team);
			for (int i=0;i<hf.sortierterVector.size();i++){
				KTeilnehmer t=hf.sortierterVector.get(i);
				String n =t.vorname+" \""+t.nickname+"\" "+t.nachname;
				nameWidth=(fontMetrics.stringWidth(n)>nameWidth)?(fontMetrics.stringWidth(n)):nameWidth;
				armyWidth=(fontMetrics.stringWidth(""+t.armee)>armyWidth)?(fontMetrics.stringWidth(""+t.armee)):armyWidth;
				teamWidth=(fontMetrics.stringWidth(""+t.team)>teamWidth)?(fontMetrics.stringWidth(""+t.team)):teamWidth;
			}

			int offsetAnwesend = 45;
			int offsetName = offsetAnwesend + 10;
			int offsetArmee = offsetName + 30;
			int offsetTeam = offsetArmee + 30;

			//Kopfzeile
			g.setFont(font2);
			g.drawString(anwesend,offsetAnwesend,20);
			g.drawString(name,offsetName,20);
			g.drawString(armee,offsetArmee+nameWidth,20); 
			g.drawString(team,offsetTeam+nameWidth+armyWidth,20); 
			

			g.setFont(font);

			for (int i=0;i<50;i++){
				if((hf.sortierterVector.size()-(i+1)-(pageIndex*50))>=0){
					KTeilnehmer t=hf.sortierterVector.get(i);
					String n =t.vorname+" \""+t.nickname+"\" "+t.nachname;
					g.drawRect(offsetAnwesend, (g.getFontMetrics(font).getHeight()+1)*i+50, (g.getFontMetrics(font).getHeight()-4), -(g.getFontMetrics(font).getHeight()-4));
					g.drawString(n,offsetName,(g.getFontMetrics(font).getHeight()+1)*i+50);
					g.drawString(""+t.armee,offsetArmee+nameWidth,(g.getFontMetrics(font).getHeight()+1)*i+50);
					g.drawString(""+t.team,offsetTeam+nameWidth+armyWidth,(g.getFontMetrics(font).getHeight()+1)*i+50);
					
				}else{
					//g.drawString(laengeAnpassenVorne(Integer.toString(i+1), 6),offsetAnwesend,(g.getFontMetrics(font).getHeight()+1)*i+50);
				}
			}
		}
		return Printable.PAGE_EXISTS;
	}

	int sicht=0;
	static int PUNKTE=0;
	static int BEGEGNUNG=1;
	static int ZUSPUNKTE=2;
	static int ANMELDELISTE=3;
	KHauptFenster hf;

	public void setHauptfenster(KHauptFenster hf){
		this.hf = hf;
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
