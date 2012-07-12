package turman;

import java.util.Calendar;


public class KAgendaEintrag {

	public KAgendaEintrag(String name, int year,int month,int date,int hour,int minute){
		this.name=name;
		this.startzeit=Calendar.getInstance();
		startzeit.set(year, month-1, date, hour, minute);
		
	}
	
	String name;
	private Calendar startzeit;
	
	protected long zeitBis(){
		Calendar aktuell=Calendar.getInstance();
		if(aktuell.before(startzeit)){
			return startzeit.getTimeInMillis()-aktuell.getTimeInMillis();
		}else{
			return 0;
		}
	}
	
}
