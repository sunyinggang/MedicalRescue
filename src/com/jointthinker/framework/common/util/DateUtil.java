package com.jointthinker.framework.common.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	
	private static final String DATE_YMD = "yyyy-MM-dd";
	private static final String DATE_YMDHmS = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_YMDHm = "yyyy-MM-dd HH:mm";
	private static final String DATE_MDHM = "MM-dd HH:mm";
	private static final String DATE_HM = "HH:mm";
	private static final String DATE_MD = "MM-dd";
	private static final String DATE_YMD_CN = "yyyy年MM月dd日";
	
	public static final String getDATE_YMD_CN(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_YMD_CN);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	public static final String getDATE_YMD(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_YMD);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	
	public static final String getDATE_YMDHmS(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_YMDHmS);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	
	public static final String getDATE_HM(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_HM);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	
	public static final String getDATE_YMDHM(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_YMDHm);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	
	public static final String getDATE_MDHM(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_MDHM);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	
	public static final String getDATE_MD(Date date){
		SimpleDateFormat format = new SimpleDateFormat(DATE_MD);
		if(date instanceof Date)
			return format.format(date);
		return "";
	}
	
	public static void main(String[] args) {
		
		Date date1 = DateUtil.toDate("2014-06-08 09:53:52");//发送时间	
		String am = "09:00:00";
		String pm = "21:00:00";
		long alertterms = 57600000L;//16小时
//		int days = new DateUtil().getDaysOfTwoDate(date1, date2);
//		System.out.println("days:" + days);
		String tt = DateUtil.getDaysAfterDate(new Date(), 40);
		long aa = DateUtil.getWorktimemillsOfDay(am, pm);//12小时
		long bb = alertterms%aa;
		System.out.println(bb);
	}
	
	public static synchronized String getDayAfterDateByDays(String date, int days) {
	    	Date date1 = toDate(date);
	        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
	        gc.setTime(date1);
	        gc.add(Calendar.DATE, days);
	        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,19);
	}   
	/**
     * ORA标准时间格式
     */
    private static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat(
            "yyyyMMddHHmm");

    /**
     * 带时分秒的ORA标准时间格式
     */
    private static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    /**
     * 带时分秒的ORA标准时间格式
     */
    private static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd");
    
    private static final SimpleDateFormat ORA_DATE_FORMAT2 = new SimpleDateFormat(
    		"yyyy/MM/dd");
    /**
     * 创建一个标准ORA时间格式的克隆
     *
     * @return 标准ORA时间格式的克隆
     */
    private static synchronized DateFormat getOraDateTimeFormat() {
        SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_FORMAT
                .clone();
        theDateTimeFormat.setLenient(false);
        return theDateTimeFormat;
    }

    public static String toOnlyDateFormat(Date d){
    	try{
    		return ORA_DATE_FORMAT.format(d);
    	}catch(Exception ex){
    		return "";
    	}    	
    }
    
    public static String toOnlyDateFormat2(Date d){
    	try{
    		return ORA_DATE_FORMAT2.format(d);
    	}catch(Exception ex){
    		return "";
    	}    	
    }
    

    private static synchronized DateFormat getOraExtendDateTimeFormat() {
        SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_EXTENDED_FORMAT
                .clone();
        theDateTimeFormat.setLenient(false);
        return theDateTimeFormat;
    }

    public static String getSystemCurrentYear(){
    	Calendar c = Calendar.getInstance();
    	c.setTimeInMillis(System.currentTimeMillis());
        return String.valueOf(c.get(Calendar.YEAR));
    }
    
    public static String getSystemCurrentMonth(){
    	Calendar c = Calendar.getInstance();
    	c.setTimeInMillis(System.currentTimeMillis());
        return String.valueOf(c.get(Calendar.MONTH)+1);
    }
    
    public static String getSystemCurrentYearLast2Bits(){
    	String year = getSystemCurrentYear();
    	return year.length()>2?year.substring(year.length()-2):year;
    }
    
    public static synchronized long compareDateForLong(Date date1,Date date2){
 	   Calendar calendar = Calendar.getInstance();
 	   calendar.setTime(date1);
 	   Calendar calendar1 = Calendar.getInstance();
 	   calendar1.setTime(date2);
 	   long timeLong = calendar1.getTimeInMillis()- calendar.getTimeInMillis();
 	   return timeLong;
    }
    
    public static synchronized Date getDateAfterDate(Date date, int days) {
    	//System.out.println("---days:" + days);
    	GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, days);
        return gc.getTime();
    }
    
    /**
     * 获得指定日期 指定天数后的日期
     * @param date
     * @param days
     * @return
     */
    public static synchronized String getDaysAfterDate(Date date, int days) {
    	
    	GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, days);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    }
    
    public static synchronized int getDaysOfTwoDate(Date date1,Date date2) {
    	int daylocal =0;
    	Calendar cd1 = Calendar.getInstance();
    	cd1.setTime(date1);
    	cd1.set(Calendar.HOUR_OF_DAY,0);
    	cd1.set(Calendar.MINUTE,0);
    	cd1.set(Calendar.SECOND,0);
    	//int day1 = cd1.get(Calendar.DAY_OF_YEAR);
    	Calendar cd2 = Calendar.getInstance();
    	cd2.setTime(date2);
    	cd2.set(Calendar.HOUR_OF_DAY,0);
    	cd2.set(Calendar.MINUTE,0);
    	cd2.set(Calendar.SECOND,0);
    	//int day2 = cd2.get(Calendar.DAY_OF_YEAR);
    	//day = day2 - day1; 
    	//System.out.println("----cd1:" + DateUtil.toDefaultString(cd1.getTime()) + "----cd2:" + DateUtil.toDefaultString(cd2.getTime()));
    	while(cd1.before(cd2) && !DateUtil.toDefaultString(cd1.getTime()).equals(DateUtil.toDefaultString(cd2.getTime()))) {
    		daylocal ++ ;
    		//System.out.println("-----day:" + daylocal);
    		cd1.add(Calendar.DAY_OF_YEAR, 1);
    		//System.out.println("----cd1----new:" + DateUtil.toDefaultString(cd1.getTime()));
    	}
    	return daylocal;
    }
    
    /**
     * 获取两个日期之间相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static synchronized int getDayscompareTwoDate(Date date1,Date date2){	 	       	
    	Calendar calendar = Calendar.getInstance();
	 	   calendar.setTime(date1);
	 	   calendar.set(Calendar.HOUR_OF_DAY,0);
	 	   calendar.set(Calendar.MINUTE,0);
	 	   calendar.set(Calendar.SECOND,0);
	 	   Calendar calendar1 = Calendar.getInstance();
	 	   calendar1.setTime(date2);
	 	   calendar1.set(Calendar.HOUR_OF_DAY,0);
		   calendar1.set(Calendar.MINUTE,0);
		   calendar1.set(Calendar.SECOND,0);
	 	   double timeLong = calendar1.getTimeInMillis()- calendar.getTimeInMillis();
	 	   //System.out.println("---timeLong:" + timeLong);
	 	   if(timeLong <0 ) return -1;
	 	   int hourNum=(int)(timeLong/1000/3600);
	 	   int day = 0;
	 	   //System.out.println("---hourNum:" + hourNum);
		   if(hourNum >=24) {		   
			   day = hourNum/24;
		   }	   
	 	   return day;
	 }
    /**
     * 得到系统当前的日期 格式为YYYY-MM-DD
     * 
     * @return
     */
    public static String getSystemCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return doTransform(calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }
    
    public static String getDateByTimeMillis(long timemillis) {
    	 Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(timemillis);
         return doTransform(calendar.get(Calendar.YEAR), calendar
                 .get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 返回格式为YYYY-MM-DD
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return
     */
    private static String doTransform(int year, int month, int day) {
        StringBuffer result = new StringBuffer();
        result.append(String.valueOf(year)).append("-").append(
                month < 10 ? "0" + String.valueOf(month) : String
                        .valueOf(month)).append("-").append(
                day < 10 ? "0" + String.valueOf(day) : String.valueOf(day));
        return result.toString();
    }

/**
 * 得到系统当前的日期和时间 格式为YYYY-MM-DD hh:mm:ss
 * @return  字符串
 */
    public static final String getSystemCurrentDateTime() {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(System.currentTimeMillis());
        int year = newCalendar.get(Calendar.YEAR);
        int month = newCalendar.get(Calendar.MONTH) + 1;
        int day = newCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);
        int second = newCalendar.get(Calendar.SECOND);
        return doTransform(year, month, day, hour, minute, second);
    }

    /**
     * 计算每天的工作时间
     * @param amstart  09:00:00
     * @param pmend  18:00:00
     * @return
     */
    public static final double getWorktimeOfDay(String amstart,String pmend) {
    	
    	Date dt = new Date();  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    String nowdate = sdf2.format(dt);// 取出当前时间的日期
	    String vamstart = nowdate + " " + amstart;// 拼当前时间当天上午的上班时间
	    String vpmend = nowdate + " " + pmend;// 拼传入时间当天下午的下班时间
	    double dvalue = 0.00;
	    try {
			Date amst = sdf.parse(vamstart);
		    Date pmen = sdf.parse(vpmend);
		    long amterm = DateUtil.compareDateLong(amst, pmen);
		    BigDecimal bd1 = new BigDecimal(amterm); 
			BigDecimal bd2 = new BigDecimal(1000*60*60); 
			dvalue =  bd1.divide (bd2,2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     return dvalue;
    }
    
    /**
     * 得到系统当前的日期 格式为YYYY-MM-DD
     * 
     * @return
     */
    public static final String getCurrentDate() {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(System.currentTimeMillis());
        int year = newCalendar.get(Calendar.YEAR);
        int month = newCalendar.get(Calendar.MONTH) + 1;
        int day = newCalendar.get(Calendar.DAY_OF_MONTH);
        return doTransformDate(year, month, day);
    }
    
    
    private static final String doTransformDate(int year, int month, int day) {
        StringBuffer result = new StringBuffer();
        result.append(String.valueOf(year)).append("-")
        .append(month < 10 ? "0" + String.valueOf(month) : String.valueOf(month)).append("-")
        .append(day < 10 ? "0" + String.valueOf(day) : String.valueOf(day));
        return result.toString();
    }
    
    /**
     * 得到系统当前的日期和时间 格式为YYYY-MM-DD hh:mm:ss
     * 
     * @return
     */
    public static final String getSystemCurrentDateTime(long datetimes) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(datetimes);
        int year = newCalendar.get(Calendar.YEAR);
        int month = newCalendar.get(Calendar.MONTH) + 1;
        int day = newCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);
        int second = newCalendar.get(Calendar.SECOND);
        return doTransform(year, month, day, hour, minute, second);
    }
    
    public static final String getCurrentTime(long datetimes) {
        return getSystemCurrentDateTime(datetimes).substring(11, 19);
    }
    
    /**
     * 得到系统当前的时间 格式为hh:mm:ss
     * @return
     */
    public static final String getSystemCurrentTime() {
        return getSystemCurrentDateTime().substring(11, 19);
    }
    
    /**
     * 返回格式为YYYY-MM-DD hh:mm:ss
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            小时
     * @param minute
     *            分钟
     * @param second
     *            秒
     * @return
     */
    private static final String doTransform(int year, int month, int day,int hour, int minute, int second) {
    	
        StringBuffer result = new StringBuffer();
        result.append(String.valueOf(year)).append("-")
        .append(month < 10 ? "0" + String.valueOf(month) : String.valueOf(month)).append("-")
        .append(day < 10 ? "0" + String.valueOf(day) : String.valueOf(day)).append(" ")
        .append(hour < 10 ? "0" + String.valueOf(hour) : String.valueOf(hour)).append(":")
        .append(minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute)).append(":")
        .append(second < 10 ? "0" + String.valueOf(second) : String.valueOf(second));
        return result.toString();
    }
    
    /**
     * 获得昨天的日期
     * 
     * @return 指定日期的下一天 格式:YYYY-MM-DD
     */
    public static synchronized String getDayBeforeToday() {
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, -1);
        return doTransform(toString(gc.getTime(),getOraDateTimeFormat()));
    }
    
    public static synchronized String getDayBeforeTodayByTerm(int days) {
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, days);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,9);
    }
    
    /**
     * 获得明天的日期
     * 
     * @return 指定日期的下一天 格式:YYYY-MM-DD
     */
    public static synchronized String getDayAfterToday() {
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, 1);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,9);
    }
    /**
     * 获得明天的日期
     * 
     * @return 指定日期的下一天 格式:YYYY-MM-DD
     */
    public static synchronized String getNextDay(String date) {
    	Date date1 = toSimpleDate(date);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date1);
        gc.add(Calendar.DATE, 1);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    }    
    
    /**
     * 获得以后几个月的日期
     * 
     * @return 指定日期的后面几个月 格式:YYYY-MM-DD
     */
    public static synchronized Date getDayAfterMonth(int months){
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.MONTH, months);
        return gc.getTime();
    }
    
    /**
     * 返回格式为YYYY-MM-DD hh:mm:ss
     * @param date 输入格式为ORA标准时间格式
     * @return
     */
    public static String doTransform(String date){
        StringBuffer buffer = new StringBuffer();
        buffer.append(date.substring(0,4));
        buffer.append("-");
        buffer.append(date.substring(4,6));
        buffer.append("-");
        buffer.append(date.substring(6,8));
        buffer.append(" ");
        buffer.append(date.substring(8,10));
        buffer.append(":");
        buffer.append(date.substring(10,12));
        buffer.append(":");
        buffer.append(date.substring(12,14));
        return buffer.toString();
    }
    
  /**
   * 将传入的日期只取日期时间补0
   * @param currentdate
   * @return
   */
    public static String getDateByDate(Date currentdate) {
    	
    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    String nowdate = sdf2.format(currentdate);//当前日期
	    String cudatetime = nowdate + " 00:00:00";
	    return cudatetime;
    }
    
    /**
     * 将一个日期对象转换成为指定日期、时间格式的字符串。 如果日期对象为空，返回一个空字符串对象.
     * 
     * @param theDate
     *            要转换的日期对象
     * @param theDateFormat
     *            返回的日期字符串的格式
     * @return 转换结果
     */
    public static synchronized String toString(Date theDate,DateFormat theDateFormat) {
    	
        if (theDate == null) {
            return "";
        } else {
            return theDateFormat.format(theDate);
        }
    }
    
    /**
     * 将Date类型转换后返回本系统默认的日期格式 YYYY-MM-DD hh:mm:ss
     * @param theDate
     * @return
     */
    public static synchronized String toDefaultString(Date theDate){
        if(theDate == null){
           return "";    
        }
        return  doTransform(toString(theDate,getOraExtendDateTimeFormat()));
    }
 
    public static synchronized String toVocString(Date theDate){
        if(theDate == null){
           return "";    
        }
        return  doTransformVoc(toString(theDate,getOraExtendDateTimeFormat()));
    }
    
    
    public static String doTransformVoc(String date){
        StringBuffer buffer = new StringBuffer();
        buffer.append(date.substring(0,4));
        buffer.append(date.substring(4,6));
        buffer.append(date.substring(6,8));
        buffer.append("-");
        buffer.append(date.substring(8,10));
        buffer.append(date.substring(10,12));
        buffer.append(date.substring(12,14)); 
        return buffer.toString();
    }
    

    public static final long getWorktimemillsOfDay(String amstart,String pmend) {
    	
    	Date dt = new Date();  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    String nowdate = sdf2.format(dt);// 取出当前时间的日期
	    String vamstart = nowdate + " " + amstart;// 拼当前时间当天上午的上班时间
	    String vpmend = nowdate + " " + pmend;// 拼传入时间当天下午的下班时间
	    long amterm = 0L;
	    try {
			Date amst = sdf.parse(vamstart);
		    Date pmen = sdf.parse(vpmend);
		    amterm = DateUtil.compareDateLong(amst, pmen);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     return amterm;
    }
    
   //获得指定时间 
  public static synchronized Date getDefinedDateTime(Date cudatetime, String amstr, long alertterms ) {

    	Calendar calendarPara=Calendar.getInstance();
        calendarPara.setTime(cudatetime);
        String[] am = amstr.split(":");
        calendarPara.set(Calendar.HOUR_OF_DAY,new Integer(am[0]).intValue());
        calendarPara.set(Calendar.MINUTE,new Integer(am[1]).intValue());
        calendarPara.set(Calendar.SECOND,new Integer(am[2]).intValue());
        long cterms = calendarPara.getTime().getTime();//起始时间
		long tt = cterms + alertterms;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tt);
        return calendar.getTime();
    }
    
    //获得告警时间
    public static synchronized Date getDeadlineDatetime(Date cudatetime, String amstr, String pmstr, long alertterms) {
    	
    	 Date resultDate = cudatetime;
    	 long hours = DateUtil.getWorktimemillsOfDay(amstr, pmstr);//每天工作时间
    	 Calendar calendarPara=Calendar.getInstance();
         calendarPara.setTime(cudatetime);
         String[] am = amstr.split(":");
         calendarPara.set(Calendar.HOUR_OF_DAY,new Integer(am[0]).intValue());
         calendarPara.set(Calendar.MINUTE,new Integer(am[1]).intValue());
         calendarPara.set(Calendar.SECOND,new Integer(am[2]).intValue());
         Calendar calendarPara2=Calendar.getInstance();
         calendarPara2.setTime(cudatetime);
         String[] pm = pmstr.split(":");
         calendarPara2.set(Calendar.HOUR_OF_DAY,new Integer(pm[0]).intValue());
         calendarPara2.set(Calendar.MINUTE,new Integer(pm[1]).intValue());
         calendarPara2.set(Calendar.SECOND,new Integer(pm[2]).intValue());        
         long diff = DateUtil.compareDateLong(cudatetime, calendarPara.getTime());//小于9点
         long diff2 = DateUtil.compareDateLong(calendarPara2.getTime(),cudatetime);//大于18:00:00
         if(diff>0){
        	 //00：01-09：00  小于上午工作时间点
        	 if(alertterms <= hours) {
        		 resultDate = DateUtil.getDefinedDateTime(cudatetime, amstr, alertterms);
        	 }else {
        		 Date date = DateUtil.getDateAfterDate(cudatetime, new Long(alertterms/hours).intValue());
        		 resultDate = DateUtil.getDefinedDateTime(date, amstr, alertterms%hours);
        	 }
        	 
         }else if(diff2>0) {
        	 //21:01-24:00 大于下午工作时间点
        	 if(alertterms <= hours) {
        		 Date date = DateUtil.getDateAfterDate(cudatetime, 1);
        		 resultDate = DateUtil.getDefinedDateTime(date, amstr, alertterms);
        	 }else {
        		 Date date = DateUtil.getDateAfterDate(cudatetime, (new Long(alertterms/hours).intValue()) + 1);
        		 resultDate = DateUtil.getDefinedDateTime(date, amstr, alertterms%hours);
        	 }
        	
         }else {
        	 //属于工作时间
        	 long diffterm = DateUtil.getWorktimemillsOfDay(DateUtil.toDefaultString(cudatetime).substring(11,19), pmstr);
        	 if(alertterms<=diffterm) {
        		 long cterms = cudatetime.getTime();//起始时间
        		 long tt = cterms + alertterms;
        	 	 Calendar calendar = Calendar.getInstance();
        		 calendar.setTimeInMillis(tt);
        		 resultDate = calendar.getTime();
        	 }else {
        		 long minusterm = alertterms - diffterm;
        		 Date date = DateUtil.getDateAfterDate(cudatetime, (new Long(minusterm/hours).intValue())+1);
        		 //System.out.println("---minusterm:" + minusterm + "---days:" + new Long(minusterm/hours).intValue() + "---diff:" + minusterm%hours);
        		 resultDate = DateUtil.getDefinedDateTime(date, amstr, minusterm%hours);
        	 }
         }
    	 return resultDate;
    }
    
    
/**
 * <9:00算到9:00  >18:00:00算到18:00:00 
 * @param cudatetime
 * @param amstr  09:00:00
 * @param pmstr  18:00:00
 * @return
 */ 
  public static synchronized Date getWorkDateTime(Date cudatetime, String amstr, String pmstr ) {
    	
	    Date resultDate = cudatetime;
    	Calendar calendarPara=Calendar.getInstance();
        calendarPara.setTime(cudatetime);
        String[] am = amstr.split(":");
        calendarPara.set(Calendar.HOUR_OF_DAY,new Integer(am[0]).intValue());
        calendarPara.set(Calendar.MINUTE,new Integer(am[1]).intValue());
        calendarPara.set(Calendar.SECOND,new Integer(am[2]).intValue());
        Calendar calendarPara2=Calendar.getInstance();
        calendarPara2.setTime(cudatetime);
        String[] pm = pmstr.split(":");
        calendarPara2.set(Calendar.HOUR_OF_DAY,new Integer(pm[0]).intValue());
        calendarPara2.set(Calendar.MINUTE,new Integer(pm[1]).intValue());
        calendarPara2.set(Calendar.SECOND,new Integer(pm[2]).intValue());        
        long diff = DateUtil.compareDateLong(cudatetime, calendarPara.getTime());//小于9点
        long diff2 = DateUtil.compareDateLong(calendarPara2.getTime(),cudatetime);//大于18:00:00
        if(diff>0) resultDate = calendarPara.getTime();
        if(diff2>0) resultDate = calendarPara2.getTime();
        return resultDate;
    }
  
  public static synchronized boolean isWorkDateTime(Date cudatetime, String amstr, String pmstr ) { 

  	  Calendar calendarPara=Calendar.getInstance();
      calendarPara.setTime(cudatetime);
      String[] am = amstr.split(":");
      calendarPara.set(Calendar.HOUR_OF_DAY,new Integer(am[0]).intValue());
      calendarPara.set(Calendar.MINUTE,new Integer(am[1]).intValue());
      calendarPara.set(Calendar.SECOND,new Integer(am[2]).intValue());
      Calendar calendarPara2=Calendar.getInstance();
      calendarPara2.setTime(cudatetime);
      String[] pm = pmstr.split(":");
      calendarPara2.set(Calendar.HOUR_OF_DAY,new Integer(pm[0]).intValue());
      calendarPara2.set(Calendar.MINUTE,new Integer(pm[1]).intValue());
      calendarPara2.set(Calendar.SECOND,new Integer(pm[2]).intValue());        
      long diff = DateUtil.compareDateLong(cudatetime, calendarPara.getTime());//小于9点
      long diff2 = DateUtil.compareDateLong(calendarPara2.getTime(),cudatetime);//大于18:00:00
      if(diff>0 || diff2>0) return false;//非工作时间
	  return true;
  }
    
    /**
     * 将输入格式为2004-8-13 12:31:22类型的字符串转换为标准的Date类型
     * @param dateStr 
     * @return
     */
    public static synchronized Date toDate(String dateStr){
        String[] list0 = dateStr.split(" ");
        String date = list0[0];
        String time = list0[1];
        String[] list1 = date.split("-");
        int year = new Integer(list1[0]).intValue();
        int month = new Integer(list1[1]).intValue();
        int day = new Integer(list1[2]).intValue();
        String[] list2 = time.split(":");
        int hour = new Integer(list2[0]).intValue();
        int min = new Integer(list2[1]).intValue();
        int second = new Integer(list2[2]).intValue();
        Calendar cale =  Calendar.getInstance();
        cale.set(year,month-1,day,hour,min,second);
        return cale.getTime();    
    }
    
    /**
     * 将日期字符串按照传入的格式格式化
     * @param dateStr
     * @param formatStr   YYYY-MM-DD HH:mm:ss
     * @return
     */
    public static synchronized Date toDate(String dateStr,String formatStr){
    	DateFormat dd=new SimpleDateFormat(formatStr);
    	Date date=null;
    	try {			
    		date = dd.parse(dateStr);
    	} catch (ParseException e) {
    		e.printStackTrace();		
    	}		
    	return date;    
    }    
    /**
     * 将输入格式为2004-08-13 06:01:22类型的字符串转换为显示到时间点的格式,如:2010年1月2日15点
     * @param dateStr
     * @return
     */
    public static synchronized String toTimeDate(String dateStr) {
    	
    	 String[] list0 = dateStr.split(" ");
         String date = list0[0];
         String time = list0[1];
         String[] list1 = date.split("-");
         int year = new Integer(list1[0]).intValue();
         int month = new Integer(list1[1]).intValue();
         int day = new Integer(list1[2]).intValue();
         String[] list2 = time.split(":");
         int hour = new Integer(list2[0]).intValue();
         int min = new Integer(list2[1]).intValue();
         int second = new Integer(list2[2]).intValue();
         String timeStr = year + "年" + month + "月" + day + "日" + hour + "点";
         return timeStr;    
    	
    }
    
    
    /**
     * 将输入格式为2004-8-13类型的字符串转换为标准的Date类型
     * @param dateStr 
     * @return
     */
    public static synchronized Date toDtDate(String dateStr){
    	
        String[] list1 = dateStr.split("-");
        int year = new Integer(list1[0]).intValue();
        int month = new Integer(list1[1]).intValue();
        int day = new Integer(list1[2]).intValue();
        Calendar cale =  Calendar.getInstance();
        cale.set(year,month-1,day);
        return cale.getTime();  
    }
    
    /**
     * 将输入格式为2009/3/10 13:34:22类型的字符串转换为标准的Date类型
     * @param datestr
     * @return
     */
    public static synchronized Date toDate2(String datestr) {
    	
    	String[] list = datestr.split(" ");
    	String date = list[0];
    	String time = list[1];
    	String[] list1 = date.split("/");
    	int year = new Integer(list1[0]).intValue();
    	int month = new Integer(list1[1]).intValue();
    	int day = new Integer(list1[2]).intValue();
    	String[] list2 = time.split(":");
    	int hour = new Integer(list2[0]).intValue();
    	int min = new Integer(list2[1]).intValue();
    	int second = new Integer(list2[2]).intValue();
    	Calendar cale = Calendar.getInstance();
    	cale.set(year, month-1, day, hour, min, second);
    	return cale.getTime();
    }
    
    
    /**
     * 将输入格式为2004-8-13类型的字符串转换为标准的Date类型
     * @param dateStr 
     * @return
     */
    public static synchronized Date toSimpleDate(String dateStr){
    	try{
    		String[] list = dateStr.split("-");
            int year = new Integer(list[0]).intValue();
            int month = new Integer(list[1]).intValue();
            int day = new Integer(list[2]).intValue();
            Calendar cale =  Calendar.getInstance();
            cale.set(year,month-1,day,0,0,0);
            return cale.getTime();
    	}catch(Exception ex){
    		return null;
    	}
    }
    
    public static synchronized Date toSimpleDate(String year, String month, String date){
    	try{
    		String str = year + "-" +
			Converter.padding(month, 2, '0', true) + "-" +
			Converter.padding(date, 2, '0', true);
    		return toSimpleDate(str);
    	}catch(Exception ex){
    		return null;
    	}
    }
    
    /**
     * 将输入格式为2004-8-13,2004-10-8类型的字符串转换为标准的Date类型,这种Date类型
     * 对应的日期格式为YYYY-MM-DD 00:00:00,代表一天的开始时刻
     * @param dateStr 
     * @return
     */
    public static synchronized Date toDayStartDate(String dateStr){
        String[] list = dateStr.split("-");
        int year = new Integer(list[0]).intValue();
        int month = new Integer(list[1]).intValue();
        int day = new Integer(list[2]).intValue();
        Calendar cale =  Calendar.getInstance();
        cale.set(year,month-1,day,0,0,0);
        return cale.getTime();
        
    }
    
    /**
     * 将输入格式为2004-8-13,2004-10-8类型的字符串转换为标准的Date类型,这种Date类型
     * 对应的日期格式为YYYY-MM-DD 23:59:59,代表一天的结束时刻
     * @param dateStr 输入格式:2004-8-13,2004-10-8
     * @return
     */
    public static synchronized Date toDayEndDate(String dateStr){
        String[] list = dateStr.split("-");
        int year = new Integer(list[0]).intValue();
        int month = new Integer(list[1]).intValue();
        int day = new Integer(list[2]).intValue();
        Calendar cale =  Calendar.getInstance();
        cale.set(year,month-1,day,23,59,59);
        return cale.getTime();
    
    }
    
   public static synchronized String compareTwoDate(Date date1,Date date2){
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(date1);
	   Calendar calendar1 = Calendar.getInstance();
	   calendar1.setTime(date2);
	   double timeLong = calendar1.getTimeInMillis()- calendar.getTimeInMillis();
	   int hourNum=(int)(timeLong/1000/3600);
	   int minuteNum = (int)((timeLong%(1000*3600))/1000/60);
	   int secondNum = (int)(((timeLong%(1000*3600))%(1000*60))/1000);
	   String hourStr="";
	   if(hourNum<10){
		   hourStr="0"+new Integer(hourNum).toString();
	   }else{
		   hourStr= new Integer(hourNum).toString();
	   }
	   String minuteStr="";
	   if(minuteNum<10){
		   minuteStr="0"+new Integer(minuteNum).toString();
	   }else{
		   minuteStr=new Integer(minuteNum).toString();
	   }
	   String secondStr="";
	   if(secondNum<10){
		   secondStr="0"+new Integer(secondNum).toString();
	   }else{
		   secondStr=new Integer(secondNum).toString();
	   }	   
	   String time = hourStr+":"+minuteStr+":"+secondStr;
	   return time;
   }
   
   public static synchronized double compareDate(Date date1,Date date2){
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(date1);
	   Calendar calendar1 = Calendar.getInstance();
	   calendar1.setTime(date2);
	   double timeLong = calendar1.getTimeInMillis()- calendar.getTimeInMillis();
	   return timeLong;
   }
   
   
   public static synchronized long compareDateLong(Date date1,Date date2){
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(date1);
	   Calendar calendar1 = Calendar.getInstance();
	   calendar1.setTime(date2);
	   BigDecimal bd1 = new BigDecimal(calendar1.getTimeInMillis()); 
	   BigDecimal bd2 = new BigDecimal(calendar.getTimeInMillis()); 
	   long timeLong = bd1.subtract(bd2).longValue();
	   return timeLong;
   }
   
   /**
    * 传入的时间与当前时间相比较,返回long值
    * 如果>0,则当前时间已超出截止时间;如果<=0,则在正常时间范围内
    * @param date1
    * @param date2
    * @return
    */
   public static synchronized long compareCDateAndFinishDate(Date date){
	   
	   String currentdt = DateUtil.getCurrentDate();
	   Date c_dt = DateUtil.toDtDate(currentdt);//当前时间
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(date);
	   Calendar calendar1 = Calendar.getInstance();
	   calendar1.setTime(c_dt);
	   long timeLong = calendar1.getTimeInMillis()- calendar.getTimeInMillis();
	   return timeLong;
   }

    /**
     * 将两个scorm时间相加
     * @param scormTime1 scorm时间,格式为00:00:00(1..2).0(1..3) 
     * @param scormTime2 scorm时间,格式为00:00:00(1..2).0(1..3)
     * @return
     */
    public static synchronized String addTwoScormTime(String scormTime1,String scormTime2){
        int dotIndex1 = scormTime1.indexOf(".");
        int hh1 = Integer.parseInt(scormTime1.substring(0,2));
        int mm1 = Integer.parseInt(scormTime1.substring(3,5));
        int ss1 = Integer.parseInt(scormTime1.substring(6,dotIndex1));
        int ms1 = Integer.parseInt(scormTime1.substring(dotIndex1 + 1,scormTime1.length()));
        
        int dotIndex2 = scormTime2.indexOf(".");
        int hh2 = Integer.parseInt(scormTime2.substring(0,2));
        int mm2 = Integer.parseInt(scormTime2.substring(3,5));
        int ss2 = Integer.parseInt(scormTime2.substring(6,dotIndex2));
        int ms2 = Integer.parseInt(scormTime2.substring(dotIndex1 + 1,scormTime2.length()));
       
        int hh = 0; 
        int mm = 0;
        int ss = 0;
        int ms = 0;
        
        if(ms1+ms2 >= 1000){
            ss = 1;
            ms = ms1+ms2-1000;
        }else{
            ms = ms1+ms2;
        }
        if(ss1+ss2+ss >= 60){
            mm = 1;
            ss = ss1+ss2+ss-60;
        }else{
            ss = ss1+ss2+ss;
        }
        if(mm1+mm2+mm>=60){
            hh = 1;
            hh = mm1+mm2+mm-60;
        }
        hh = hh + hh1 + hh2;
        
        StringBuffer sb = new StringBuffer();
        if(hh<10){
           sb.append("0").append(hh);    
        }else{
           sb.append(hh);    
        }
        sb.append(":");
        if(mm<10){
           sb.append("0").append(mm);    
        }else{
           sb.append(mm);    
        }
        sb.append(":");
        if(ss<10){
           sb.append("0").append(ss);  
        }else{
           sb.append(ss);     
        }
        sb.append(".");
        if(ms<10){
           sb.append(ms).append("00");
        }else if(ms<100){
           sb.append(ms).append("0");    
        }else{
           sb.append(ms);  
        }
        return sb.toString();
    }
    
    /**
     * 根据timeType返回当前日期与传入日期的差值（当前日期减传入日期）
     * 当要求返回月份的时候，date的日期必须和当前的日期相等，
     * 否则返回0（例如：2003-2-23 和 2004-6-12由于23号和12号不是同一天，固返回0，
     *          2003-2-23 和 2005-6-23 则需计算相差的月份，包括年，此例应返回28（个月）。
     *          2003-2-23 和 2001-6-23 也需计算相差的月份，包括年，此例应返回-20（个月））
     * 
     * @param date 要与当前日期比较的日期
     * @param timeType 0代表返回两个日期相差月数，1代表返回两个日期相差天数
     * 
     * @return 根据timeType返回当前日期与传入日期的差值
     */
    public static int CompareDateWithNow(Date date,int timeType){
        Date now = Calendar.getInstance().getTime();
        
        Calendar calendarNow=Calendar.getInstance();
        calendarNow.setTime(now);
        calendarNow.set(Calendar.HOUR,0);
        calendarNow.set(Calendar.MINUTE,0);
        calendarNow.set(Calendar.SECOND,0);        
        Calendar calendarPara=Calendar.getInstance();
        calendarPara.setTime(date);
        calendarPara.set(Calendar.HOUR,0);
        calendarPara.set(Calendar.MINUTE,0);
        calendarPara.set(Calendar.SECOND,0);       
        float nowTime=now.getTime();
        float dateTime=date.getTime();
        
        if(timeType==0){
        	if(calendarNow.get(Calendar.DAY_OF_YEAR)==calendarPara.get(Calendar.DAY_OF_YEAR))
        		return 0;
        	return (calendarNow.get(Calendar.YEAR)-calendarPara.get(Calendar.YEAR))*12+calendarNow.get(Calendar.MONTH)-calendarPara.get(Calendar.MONTH);
        }
        else{
        	float result=nowTime-dateTime;
        	float day=24*60*60*1000;
            result=(result>0)?result:-result;
        	result=result/day;
        	Float resultFloat=new Float(result);
        	float fraction=result-resultFloat.intValue();
        	if(fraction>0.5){
        		return resultFloat.intValue()+1;
        	}else{
        		return resultFloat.intValue();
        	}                	        	
        }
    }
    
    public static boolean isSameDate(Date d1, Date d2){
    	if(d1==null || d2==null)return false;
    	return d1.getYear()==d2.getYear() &&
    			d1.getMonth()==d2.getMonth() &&
    			d1.getDate()==d2.getDate();
    }
    
    public static boolean lessOrEqual(Date d1, Date d2){
    	Calendar c1 = Calendar.getInstance();
    	c1.setTime(d1);
    	Calendar c2 = Calendar.getInstance();
    	c2.setTime(d2);
    	if( c1.before(c2)){
    		return true;
    	}else{
    		return isSameDate(d1, d2);
    	}
    }
    
    public static String addDate(String date, int cnt){
    	Calendar c = Calendar.getInstance();
    	c.setTime(DateUtil.toSimpleDate(date));
    	c.add(Calendar.DATE, cnt);
    	return DateUtil.toOnlyDateFormat(c.getTime());
    }
    
    public static String getYear(Date d){
    	return d==null?"":String.valueOf(1900+d.getYear());
    }
    
    public static String getMonth(Date d){
    	return d==null?"":String.valueOf(d.getMonth()+1);
    }
    
    public static String getDate(Date d){
    	return d==null?"":String.valueOf(d.getDate());
    }
    
    public static String getYear(String d){
    	return getYear(DateUtil.toSimpleDate(d));
    }
    
    public static String getMonth(String d){
    	return getMonth(DateUtil.toSimpleDate(d));
    }
    
    public static String getDate(String d){
    	return getDate(DateUtil.toSimpleDate(d));
    }
    
    public static int getDayCountInMonth(Date d){
    	if(d==null)return 0;
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
    	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 根据月份取该月的日期集合，以“，”隔开返回字符串  传入参数：201210 得到结果：10月01日,10月02日,...10月31日
     * @param mon
     * @return
     */
    public static String getDayofMonthByMon(String mon) {
    	
    	String datestr = "";
    	Calendar calendar = Calendar.getInstance();
        calendar.set(new Integer(mon.substring(0, 4)).intValue(),new Integer(mon.substring(5)).intValue()-1,1);
        int month = calendar.get(Calendar.MONTH);
        int maxdate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mindate = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        String dat1 = "";
        String dat2 = "";
        for(int i=mindate; i<maxdate+1; i++) {
        	
        	dat2 = (month+1) + "月" + i + "日";
        	dat1 = "d" + (i<10?"0" + String.valueOf(i):String.valueOf(i));
        	datestr += "\""+dat1 + "\":\"" + dat2 + "\",";
        }
    	return datestr;
    	
    }
    
    /**
	 * 根据时间差值，返回时间串表达式,按工作时间计算
	 * @param termmills
	 * @return
	 */
    public static synchronized String compareTimeDiffToString(long termmills,double hours){
 	   
 	   //double timeLong =  termmills;
       long timeLong = termmills;
 	   //System.out.println("---timeLong---" + timeLong);
 	   int hourNum=(int)(timeLong/1000/3600);
 	   int minuteNum = (int)((timeLong%(1000*3600))/1000/60);
 	   int secondNum = (int)(((timeLong%(1000*3600))%(1000*60))/1000);
 	   String dayStr = "";
 	   String hourStr="";
 	   if(new Double(hourNum + "").doubleValue() >= hours) {
 		   //大于工作时间,计算天数
 		  dayStr = new Double(hourNum/hours).intValue() + "";//天数
 		  int dihour = new Double(hourNum%hours).intValue();
		   if(dihour < 10) {
			   
			  hourStr="0"+new Integer(dihour).toString();
		   }else {
			   
			  hourStr= new Integer(dihour).toString();//小时数
		   }
 	   }else {
 		  if(hourNum < 10) {
			   
			  hourStr="0"+new Integer(hourNum).toString();
		   }else {
			   
			  hourStr= new Integer(hourNum).toString();//小时数
		   }
 	   }
 	   String minuteStr="";
 	   if(minuteNum<10){
 		   minuteStr="0"+new Integer(minuteNum).toString();
 	   }else{
 		   minuteStr=new Integer(minuteNum).toString();
 	   }
 	   String secondStr="";
 	   if(secondNum<10){
 		   secondStr="0"+new Integer(secondNum).toString();
 	   }else{
 		   secondStr=new Integer(secondNum).toString();
 	   }
 	   StringBuffer buffer = new StringBuffer();
 	   if(!"".equals(dayStr) && !"0".equals(dayStr) && !"00".equals(dayStr)) {
 		   
 		   buffer.append(dayStr + "天");
 	   }
 	   if(!"".equals(hourStr) && !"0".equals(hourStr) && !"00".equals(hourStr)) {
 		   
 		   buffer.append(hourStr + "小时");
 	   }
 	   if(!"".equals(minuteStr) && !"0".equals(minuteStr) && !"00".equals(minuteStr)) {
 		   
 		   buffer.append(minuteStr + "分");
 	   }
// 	   if(!"".equals(secondStr) && !"0".equals(secondStr) && !"00".equals(secondStr)) {
// 		   
// 		   buffer.append(secondStr + "秒");
// 	   }
 	   String timeString = buffer.toString();
 	   return timeString;
    }
    
    /**
	 * 根据时间差值，返回时间串表达式和当前预警类型 
	 * @param termmills
	 * @return
	 */
    public static synchronized String[] compareTimeDiffToStringType(long termmills,double hours,long firstterms,long alertterms){
 	   
 	   //double timeLong =  termmills;
        String[] result = new String[3];
       long timeLong = termmills;
 	   //System.out.println("---timeLong---" + timeLong);
       long difftime = alertterms-timeLong;
       String resultstr = "";
       if(difftime<0) {
    	   difftime = timeLong-alertterms;
    	   resultstr = "已超";
       }
 	   int hourNum=(int)(timeLong/1000/3600);
 	   int minuteNum = (int)((timeLong%(1000*3600))/1000/60);
 	   int secondNum = (int)(((timeLong%(1000*3600))%(1000*60))/1000);
 	   int hourNum2=(int)(difftime/1000/3600);
	   int minuteNum2 = (int)((difftime%(1000*3600))/1000/60);
	   int secondNum2 = (int)(((difftime%(1000*3600))%(1000*60))/1000);
 	   String dayStr = "";
 	   String hourStr="";
 	   String dayStr2 = "";
	   String hourStr2="";
 	   if(new Double(hourNum + "").doubleValue() >= hours) {
 		   //大于工作时间,计算天数
 		  dayStr = new Double(hourNum/hours).intValue() + "";//天数
 		  int dihour = new Double(hourNum%hours).intValue();
		   if(dihour < 10) {
			   
			  hourStr="0"+new Integer(dihour).toString();
		   }else {
			   
			  hourStr= new Integer(dihour).toString();//小时数
		   }
 	   }else {
 		  if(hourNum < 10) {
			   
			  hourStr="0"+new Integer(hourNum).toString();
		   }else {
			   
			  hourStr= new Integer(hourNum).toString();//小时数
		   }
 	   }
 	  if(new Double(hourNum2 + "").doubleValue() >= hours) {
		   //大于工作时间,计算天数
		  dayStr2 = new Double(hourNum2/hours).intValue() + "";//天数
		  int dihour = new Double(hourNum2%hours).intValue();
		   if(dihour < 10) {
			   
			  hourStr2="0"+new Integer(dihour).toString();
		   }else {
			   
			  hourStr2= new Integer(dihour).toString();//小时数
		   }
	   }else {
		  if(hourNum2 < 10) {
			   
			  hourStr2="0"+new Integer(hourNum2).toString();
		   }else {
			   
			  hourStr2= new Integer(hourNum2).toString();//小时数
		   }
	   }
 	   String minuteStr="";
 	  String minuteStr2="";
 	   if(minuteNum<10){
 		   minuteStr="0"+new Integer(minuteNum).toString();
 	   }else{
 		   minuteStr=new Integer(minuteNum).toString();
 	   }
 	   if(minuteNum2<10){
		   minuteStr2="0"+new Integer(minuteNum2).toString();
	   }else{
		   minuteStr2=new Integer(minuteNum2).toString();
	   }
 	   String secondStr="";
 	  String secondStr2="";
 	   if(secondNum<10){
 		   secondStr="0"+new Integer(secondNum).toString();
 	   }else{
 		   secondStr=new Integer(secondNum).toString();
 	   }
 	  if(secondNum2<10){
		   secondStr2="0"+new Integer(secondNum2).toString();
	   }else{
		   secondStr2=new Integer(secondNum2).toString();
	   }
 	   StringBuffer buffer = new StringBuffer();
 	  StringBuffer buffer2 = new StringBuffer();
 	   if(!"".equals(dayStr) && !"0".equals(dayStr) && !"00".equals(dayStr)) {
 		   
 		   buffer.append(dayStr + "天");
 	   }
 	   if(!"".equals(hourStr) && !"0".equals(hourStr) && !"00".equals(hourStr)) {
 		   
 		   buffer.append(hourStr + "小时");
 	   }
 	   if(!"".equals(minuteStr) && !"0".equals(minuteStr) && !"00".equals(minuteStr)) {
 		   
 		   buffer.append(minuteStr + "分");
 	   }
// 	   if(!"".equals(secondStr) && !"0".equals(secondStr) && !"00".equals(secondStr)) {
// 		   
// 		   buffer.append(secondStr + "秒");
// 	   }
 	  if(!"".equals(dayStr2) && !"0".equals(dayStr2) && !"00".equals(dayStr2)) {
		   
		   buffer2.append(dayStr2 + "天");
	   }
	   if(!"".equals(hourStr2) && !"0".equals(hourStr2) && !"00".equals(hourStr2)) {
		   
		   buffer2.append(hourStr2 + "小时");
	   }
	   if(!"".equals(minuteStr2) && !"0".equals(minuteStr2) && !"00".equals(minuteStr2)) {
		   
		   buffer2.append(minuteStr2 + "分");
	   }
 	   String timeString = buffer.toString();
 	   String diffstring = resultstr + buffer2.toString();
 	   String termtype = "";
 	   	if(timeLong < firstterms) {
			termtype = "正常";
		}else if(timeLong>= firstterms && timeLong<=alertterms) {
			termtype="预警";
		}else{
			termtype="超期";
		}
 	   	result[0] = termtype;
 	   	result[1] = timeString;
 	   	result[2] = diffstring;
 	   return result;
    }
    
    
    /**
	 * 根据时间差值，返回时间串表达式
	 * @param termmills
	 * @return
	 */
    public static synchronized String compareAlertDiffToString(long termmills,double hours,String alerttype){
 	   
       long difftime = termmills;
       String resultstr = "";
       if("超期".equals(alerttype)) {
    	   resultstr = "已超";
       }
 	   int hourNum2=(int)(difftime/1000/3600);
	   int minuteNum2 = (int)((difftime%(1000*3600))/1000/60);
	   int secondNum2 = (int)(((difftime%(1000*3600))%(1000*60))/1000);
 	   String dayStr2 = "";
	   String hourStr2="";
 	  if(new Double(hourNum2 + "").doubleValue() >= hours) {
		   //大于工作时间,计算天数
		  dayStr2 = new Double(hourNum2/hours).intValue() + "";//天数
		  int dihour = new Double(hourNum2%hours).intValue();
		   if(dihour < 10) {
			   
			  hourStr2="0"+new Integer(dihour).toString();
		   }else {
			   
			  hourStr2= new Integer(dihour).toString();//小时数
		   }
	   }else {
		  if(hourNum2 < 10) {
			   
			  hourStr2="0"+new Integer(hourNum2).toString();
		   }else {
			   
			  hourStr2= new Integer(hourNum2).toString();//小时数
		   }
	   }
 	  String minuteStr2="";
 	   if(minuteNum2<10){
		   minuteStr2="0"+new Integer(minuteNum2).toString();
	   }else{
		   minuteStr2=new Integer(minuteNum2).toString();
	   }
 	  String secondStr2="";
 	  if(secondNum2<10){
		   secondStr2="0"+new Integer(secondNum2).toString();
	   }else{
		   secondStr2=new Integer(secondNum2).toString();
	   }
 	  StringBuffer buffer2 = new StringBuffer();
 	  if(!"".equals(dayStr2) && !"0".equals(dayStr2) && !"00".equals(dayStr2)) {
		   
		   buffer2.append(dayStr2 + "天");
	   }
	   if(!"".equals(hourStr2) && !"0".equals(hourStr2) && !"00".equals(hourStr2)) {
		   
		   buffer2.append(hourStr2 + "小时");
	   }
	   if(!"".equals(minuteStr2) && !"0".equals(minuteStr2) && !"00".equals(minuteStr2)) {
		   
		   buffer2.append(minuteStr2 + "分");
	   }
 	   String diffstring = resultstr + buffer2.toString();
 	   return diffstring;
    }
}
