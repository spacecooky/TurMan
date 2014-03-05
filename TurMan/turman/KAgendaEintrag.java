package turman;

import java.util.Calendar;


public class KAgendaEintrag {

	public KAgendaEintrag(String name, int year,int month,int date,int hour,int minute){
		this.name=name;
		this.startzeit=Calendar.getInstance();
		int second=0;
		startzeit.set(year, month-1, date, hour, minute, second);

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
	
	protected void add5Minutes(){
		startzeit.add(Calendar.MINUTE,+5);
	}
	
	protected void subtract5Minutes(){
		startzeit.add(Calendar.MINUTE,-5);
	}
	
	protected void changeTime(int minutes){
		startzeit.add(Calendar.MINUTE,minutes);
	}
	
	protected void setCalendarToNow(){
		startzeit=Calendar.getInstance();
	}
	
	protected String[] getTimeArray(){
		String time[] = new String[6];
		time[0]=name;
		time[1]=startzeit.get(Calendar.DAY_OF_MONTH)<10?"0"+startzeit.get(Calendar.DAY_OF_MONTH):""+startzeit.get(Calendar.DAY_OF_MONTH);
		time[2]=(startzeit.get(Calendar.MONTH)+1)<10?"0"+(startzeit.get(Calendar.MONTH)+1):""+(startzeit.get(Calendar.MONTH)+1);
		time[3]=startzeit.get(Calendar.YEAR)<10?"0"+startzeit.get(Calendar.YEAR):""+startzeit.get(Calendar.YEAR);
		time[4]=(startzeit.get(Calendar.HOUR_OF_DAY))<10?"0"+(startzeit.get(Calendar.HOUR_OF_DAY)):""+(startzeit.get(Calendar.HOUR_OF_DAY));
		time[5]=startzeit.get(Calendar.MINUTE)<10?"0"+startzeit.get(Calendar.MINUTE):""+startzeit.get(Calendar.MINUTE);
		
		return time;
	}
	
	protected String getTimeString(){
		String time = "";
		time+=startzeit.get(Calendar.DAY_OF_MONTH)<10?"0"+startzeit.get(Calendar.DAY_OF_MONTH):""+startzeit.get(Calendar.DAY_OF_MONTH);
		time+=".";
		time+=(startzeit.get(Calendar.MONTH)+1)<10?"0"+(startzeit.get(Calendar.MONTH)+1):""+(startzeit.get(Calendar.MONTH)+1);
		time+=".";
		time+=startzeit.get(Calendar.YEAR)<10?"0"+startzeit.get(Calendar.YEAR):""+startzeit.get(Calendar.YEAR);
		time+=" ";
		time+=(startzeit.get(Calendar.HOUR_OF_DAY))<10?"0"+(startzeit.get(Calendar.HOUR_OF_DAY)):""+(startzeit.get(Calendar.HOUR_OF_DAY));
		time+=":";
		time+=startzeit.get(Calendar.MINUTE)<10?"0"+startzeit.get(Calendar.MINUTE):""+startzeit.get(Calendar.MINUTE);
		time+=":\n"+name;
		
		return time;
	}
	
}
