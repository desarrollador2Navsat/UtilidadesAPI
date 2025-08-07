package util;
/* Created on Jul 17, 2008 Created by: jpvillalobos
 * 
 * Copyright 2008 Avantica Technologies. All rights reserved. This product is
 * protected by copyright and distributed under licenses restricting its use,
 * copying, distribution, decompilation, or transmission in any form or by any
 * means without the prior written authorization of Avantica Technologies or
 * NavSat.
 * 
 */



import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * Clase utilitaria para la manejo de fechas
 * 
 * @author jpvillalobos.
 * 
 * @version 1.0, 17-Jul-08
 */
public class DateTimeUtil {
	public static SimpleDateFormat RFC822DATEFORMAT
   		= new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.ENGLISH);
	public static SimpleDateFormat RFC822DATEFORMATGMT
		= new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'z", Locale.ROOT);
	
	/**
	 * Obtiene la hora actual del sistema
	 * 
	 * @return la hora actual del sistema
	 */
	public static Date getNow() {
		Calendar today;
		today = new GregorianCalendar();
		return today.getTime();
	}
	public static Date getNowUTC(){
		TimeZone utc = TimeZone.getTimeZone("UTC");
		//Calendar today;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//sdf.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
		sdf.setTimeZone(utc);
		
		//today = new GregorianCalendar(utc);
		
		return stringToDate(sdf.format(new Date()));
	}
	
	/**
	 * Convierte del objeto Date a su representacion en String
	 * 
	 * @param date
	 *            fecha a convertir
	 * @return fecha en string
	 */
	public static String dateToString(final Date date) {
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return frmt.format(date);
	}
	//public static Date dateToStringDay(final Date date){
		//SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
		//String dayString = frmt.format(date);
		//eturn dayString;
	//}
	
	public static String getHourString(String duration) {

		String value[] = duration.split(":");
		String hour = value[0];
		
		return hour;
		
	}
	
	public static String getMinuteString(String duration) {
		
		String value[] = duration.split(":");
		String minute = value[1];
		
		return minute;
		
	}
	
	public static String dateToDayString(final Date date) {
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("yyyy-MM-dd");
		return frmt.format(date);
	}
	
	public static String dateToOnlyDayString(final Date date) {
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("dd");
		return frmt.format(date);
	}
	
	public static String dateToDayTimeString(final Date date) {
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("hh:mm:ss");
		return frmt.format(date);
	}
	
	
	public static String dateToShortString(final Date date){
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("dd-MM-yy");
		return frmt.format(date);
	}
	
	public static Date combineDateAndTime(Date date, Date time){
		Date newDate = null;
		
		String dt = dateToDayString(date);
		String tm = dateToTimeString(time);
		
		newDate = DateTimeUtil.stringToDate(dt + " " + tm);
		
		return newDate;
	}
	/**
	 * 
	 * @param date
	 * @return String in format dd-MM-yy hh:mm
	 */
	public static String dateToShortDateTimeString(final Date date){
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("dd-MM-yy hh:mm");
		return frmt.format(date);
	}
	public static String dateToLongString(final Date date){
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("yyyyMMddHHmmssSS");
		return frmt.format(date);
	}
	
	
	public static String dateToLong_LTM_String(final Date date){
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("yyMMddHHmm");
		return frmt.format(date);
	}
	
	
	
	public static String dateToStartMonthString(Date date){
		//Date today = new Date();

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        //calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        //Date firstDayOfMonth = calendar.getTime();
		Date firstDayOfMonth = dateToStartMonth(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(firstDayOfMonth);
	}
	public static String dateToEndMonthString(Date date){
		//Date today = new Date();
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        //calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        //Date lastDayOfMonth = calendar.getTime();
        Date lastDayOfMonth = dateToEndMonth(date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(lastDayOfMonth);
	}
	public static Date dateToStartMonth(Date date){
		//Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        Date firstDayOfMonth = calendar.getTime();
        return firstDayOfMonth;
	}
	public static Date dateToEndMonth(Date date){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
	}
	public static Date dateToStartOfDay(Date date){
		String day = dateToDayString(date);
		String time = "00:00:00";
		date = stringToDate(day + " " + time);
		
		return date;
	}
	public static Date dateToEndOfDay(Date date){
		String day = dateToDayString(date);
		String time = "23:59:00";
		date = stringToDate(day + " " + time);
		
		return date;
	}
	
	
	public static String dateToTimeString(final Date date){
		SimpleDateFormat frmt;
		frmt = new SimpleDateFormat("HH:mm:ss");
		return frmt.format(date);
	}
	public static String monthABRtoNumString(String month){
		String res = ""; 
		month = month.toLowerCase();
		if(month.compareToIgnoreCase("jan")==0 || month.compareToIgnoreCase("ene")==0)
			res = "01";
		else if(month.compareToIgnoreCase("feb")==0)
			res = "02";
		else if(month.compareToIgnoreCase("mar")==0)
			res = "03";
		else if(month.compareToIgnoreCase("apr")==0 || month.compareToIgnoreCase("abr")==0)
			res = "04";
		else if(month.compareToIgnoreCase("may")==0)
			res = "05";
		else if(month.compareToIgnoreCase("jun")==0)
			res = "06";
		else if(month.compareToIgnoreCase("jul")==0)
			res = "07";
		else if(month.compareToIgnoreCase("aug")==0 || month.compareToIgnoreCase("ago")==0)
			res = "08";
		else if(month.compareToIgnoreCase("sep")==0)
			res = "09";
		else if(month.compareToIgnoreCase("oct")==0)
			res = "10";
		else if(month.compareToIgnoreCase("nov")==0)
			res = "11";
		else if(month.compareToIgnoreCase("dec")==0 || month.compareToIgnoreCase("dic")==0)
			res = "12";
		
		return res;
	}
	public static char dateToDayLetter(Date date, char[] monToFri){
		char day = ' ';
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		switch(calendar.get(Calendar.DAY_OF_WEEK)){
		case Calendar.MONDAY:
			day = monToFri[0];
			break;
		case Calendar.TUESDAY:
			day = monToFri[1];
			break;
		case Calendar.WEDNESDAY:
			day = monToFri[2];
			break;
		case Calendar.THURSDAY:
			day = monToFri[3];
			break;
		case Calendar.FRIDAY:
			day = monToFri[4];
			break;
		case Calendar.SATURDAY:
			day = monToFri[5];
			break;
		case Calendar.SUNDAY:
			day = monToFri[6];
			break;
		}
		
		return day;
	}
	
	public static String dateToMonthNumString(Date date){
		//Date today = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        Date firstDayOfMonth = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(firstDayOfMonth);
	}

	
	/**
	
	 *  Convierte la fecha en String a un objeto Date.
	 * 
	 * @param dateString
	 *            Fecha en Hilera a convertir
	 * @return fecha en objeto Date
	 */
	public static Date stringToDate(final String dateString) {
		GregorianCalendar cal;
		String dtStr = dateString;
		
		if(!dateString.contains(" "))
			dtStr += " " + "00:00:00";
		
		String[] allDate = dtStr.split(" ");
		String[] date = allDate[0].split("-");
		String[] time;
		if(allDate[1].contains(":")){
			time = allDate[1].split(":");
			if(time.length==2)
				time = new String[] {time[0],time[1],"00" };
		}else{
			time = new String[] {"00", "00", "00" };
		}

		cal = new GregorianCalendar(
				Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1,
				Integer.parseInt(date[2]), Integer.parseInt(time[0]),
				Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		
		return cal.getTime();
	}
	public static Date addYearsToDate(Date date, int years){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, years);
		
		
		return cal.getTime();
	}
	public static Date addMonthsToDate(Date date, int months){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONDAY, months);
		
		
		return cal.getTime();
	}
	public static Date addDaysToDate(Date date, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		
		
		return cal.getTime();
	}
	public static Date addHoursToDate(Date date, double hours){
		date.setTime(date.getTime() + (long)(hours*60*60*1000));

		return date;
	}
	public static Date addMilliSecondsToDate(Date date, long milliseconds){
		date.setTime(date.getTime() + milliseconds);
		
		return date;
	}

	public static int getDiffBetweenDates(Date start, Date end){
		int days = 0;
		
		//Difference in milliseconds
		long timeDiff = Math.abs(start.getTime() - end.getTime());
		timeDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);
		if(timeDiff<=Integer.MAX_VALUE)
			days = (int)timeDiff;
		else
			days = -1;
		
		return days;
	}
	public static long getDiffMillisecondsBetweenDates(Date start, Date end){
		//Difference in milliseconds
		long timeDiff = Math.abs(start.getTime() - end.getTime());
		return timeDiff;
	}
	public static String getDateAndTime(int week,int dayofweek,int seconds)
	{
		
		GregorianCalendar calendario = new GregorianCalendar(1980,0,6,0,0,0);
		calendario.add(GregorianCalendar.WEEK_OF_YEAR, week);
		calendario.set(GregorianCalendar.DAY_OF_WEEK, dayofweek+1);
		calendario.add(GregorianCalendar.SECOND, seconds);
		
		Calendar cal = calendario;
		String datePattern = "HH:mm:ss dd-MM-yy";
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(datePattern);

		return dateTimeFormat.format(cal.getTime());
	}
	//Converts time in hh:mm:ss to just seconds s
 	public static int getTimeInSeconds(String hhmmss){
 		int secs = -1;
		if(!hhmmss.contains(":"))
			return secs;
		secs = 0;
		String[] tokens = hhmmss.split(":");
		
		secs += Integer.parseInt(tokens[0])*3600;
		secs += Integer.parseInt(tokens[1])*60;
		secs += Integer.parseInt(tokens[2]);
		
		return secs;
	}
 	
	//Metod dia
	public static int getDayOfTheWeek(Date d){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);		
	}
	//Metod dia
	
	
	//Metod HORA
	public static int getHour(Date d){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.HOUR_OF_DAY);		
	}
	//Metod HORA
	
	public static Date resetTime (Date d) {
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(d);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    return cal.getTime();
	}
	
	public static int getWeeksBetween (Date a, Date b) {

	    if (b.before(a)) {
	        return -getWeeksBetween(b, a);
	    }
	    a = resetTime(a);
	    b = resetTime(b);

	    Calendar cal = new GregorianCalendar();
	    cal.setTime(a);
	    int weeks = 0;
	    while (cal.getTime().before(b)) {
	        // add another week
	        cal.add(Calendar.WEEK_OF_YEAR, 1);
	        weeks++;
	    }
	    return weeks;
	}
 	
	
 	public static int convertTimeStringToSecs(String hhmmss){
		int secs = 0;
		
		if(!hhmmss.contains(":"))
			return -1;
		String[] timeTokens = hhmmss.split(":");
		
		int hours = Integer.parseInt(timeTokens[0]);
		int mins = Integer.parseInt(timeTokens[1]);
		if(timeTokens.length>2)
			secs = Integer.parseInt(timeTokens[2]);
		
		secs += hours*3600 + mins*60;
		
		
		return secs;
	}
	public static int convertTimeToMins(Time tm){
		int time = 0;
		String tmStr = tm.toString();
		String strArr[] = tmStr.split(":");
		
		time = Integer.parseInt(strArr[0])*60;
		time += Integer.parseInt(strArr[1]);
		
		return time;
	}

	public static String convertSecsToTimeString(int secs){
		String time = "";
		//Calc hours
		int hours = secs / 3600;
		secs = secs - (hours*3600);
		//Calc minutes
		int mins = secs / 60;
		secs = secs - (mins*60);
		
		String hoursS = "";
		
		if(String.valueOf(hours).length()==2){
			hoursS = String.valueOf(hours);
		}else{
			hoursS = "0"+String.valueOf(hours);
		}
		
		time = hoursS + ":" + 
		intToTimeToken(mins,2) + ":" + 
		intToTimeToken(secs,2);
		
		if(time.contains("-1"))
			return null;
		
		return time;
	}
	
	private static String intToTimeToken(int tok, int length){
		String result = Integer.toString(tok);
		if (result.length()<length)
			for(int i=result.length(); i<length; i++)
				result = "0" + result;
		else if (result.length()==length)
			;
		else
			result = "-1";
		return result;
	}
	
	
	public static String getDateAsRFC822String(Date date)
	{
	  return RFC822DATEFORMAT.format(date);
	}
	public static String getDateAsRFC822StringGMT(Date date){
		RFC822DATEFORMATGMT.setTimeZone(TimeZone.getTimeZone("Greenwich Mean Time"));
		return RFC822DATEFORMATGMT.format(date);
	}

	
	
	
	public static String dateMonth(Date date){
		 String result="";
		 Calendar calendar=Calendar.getInstance();
		 calendar.setTime(date);
		 int month=0;
		 
		 try{
		   month=calendar.get(Calendar.MONTH);
		 }catch(Exception ex){}
		 switch(month){
		  case 0:
		    {
		      result="Enero";
		      break;
		    }
		  case 1:
		    {
		      result="Febrero";
		      break;
		    }
		  case 2:
		    {
		      result="Marzo";
		      break;
		    }
		  case 3:
		    {
		      result="Abril";
		      break;
		    }
		  case 4:
		    {
		      result="Mayo";
		      break;
		    }
		  case 5:
		    {
		      result="Junio";
		      break;
		    }
		  case 6:
		    {
		      result="Julio";
		      break;
		    }
		  case 7:
		    {
		      result="Agosto";
		      break;
		    }
		  case 8:
		    {
		      result="Septiembre";
		      break;
		    }
		  case 9:
		    {
		      result="Octubre";
		      break;
		    }
		  case 10:
		    {
		      result="Noviembre";
		      break;
		    }
		  case 11:
		    {
		      result="Diciembre";
		      break;
		    }
		  default:
		    {
		      result="Error";
		      break;
		    }
		 }
		 return result;
		}
	
	
	
	
	
	public static String dayMonth(Date date){
		 String result="";
		 Calendar calendar=Calendar.getInstance();
		 calendar.setTime(date);
		 int day=0;
		 
		 try{
			 day=(calendar.get(Calendar.DAY_OF_WEEK));
		 }catch(Exception ex){}
		 switch(day){
		  case 1:
		    {
		      result="Domingo";
		      break;
		    }
		  case 2:
		    {
		      result="Lunes";
		      break;
		    }
		  case 3:
		    {
		      result="Martes";
		      break;
		    }
		  case 4:
		    {
		      result="Miercoles";
		      break;
		    }
		  case 5:
		    {
		      result="Jueves";
		      break;
		    }
		  case 6:
		    {
		      result="Viernes";
		      break;
		    }
		  case 7:
		    {
		      result="Sabado";
		      break;
		    }
		  default:
		    {
		      result="Error";
		      break;
		    }
		 }
		 return result;
		}
	
	
	
	public static int dayMonthNumber(Date date){
		 Calendar calendar=Calendar.getInstance();
		 calendar.setTime(date);
		 int day=0;
		 
		 try{
			 day=(calendar.get(Calendar.DAY_OF_MONTH));
		 }catch(Exception ex){}
		
		 return day;
		}
	
	
	
	public static void main(String[] args){
		
		Date dt = DateTimeUtil.getNowUTC();
		System.out.println(dayMonth(dt));
	}
}