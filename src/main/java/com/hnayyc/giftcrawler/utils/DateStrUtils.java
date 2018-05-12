package com.hnayyc.giftcrawler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 封装针对yyyy-MM-dd格式的日期字符串的常用操作
 * @author YangCheng
 */
public class DateStrUtils {
	
	public static String getCurrentDateStr() {
		Calendar c=Calendar.getInstance();
	    SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
	    return f.format(c.getTime());
	}
	
	/**
	 * 获取日期字符串的年份
	 * @param dateString
	 * @return
	 */
	public static int getYear(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar cal = Calendar.getInstance(); 
	    Date dt = null; 
	    try { 
	      dt = sdf.parse(dateString); 
	      cal.setTime(dt); 
	    } catch (ParseException e) { 
	      e.printStackTrace(); 
	    } 
		return cal.get(Calendar.YEAR); 
	}
	
	/**
	 * 获取日期字符串的月份
	 * @param dateString
	 * @return
	 */
	public static int getMonth(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar cal = Calendar.getInstance(); 
	    Date dt = null; 
	    try { 
	      dt = sdf.parse(dateString); 
	      cal.setTime(dt); 
	    } catch (ParseException e) { 
	      e.printStackTrace(); 
	    } 
	    return cal.get(Calendar.MONTH) + 1; 
	}
	
	/**
	 * 获取日期字符串的日期
	 * @param dateString
	 * @return
	 */
	public static int getDay(String dateString) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar cal = Calendar.getInstance(); 
	    Date dt = null; 
	    try { 
	      dt = sdf.parse(dateString); 
	      cal.setTime(dt); 
	    } catch (ParseException e) { 
	      e.printStackTrace(); 
	    } 
	    return cal.get(Calendar.DATE); 
	}
	
	/**
	 * 获取指定月份有多少天，如2020年2月份，返回29天。
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month -1, 1);
	    return cal.getActualMaximum(Calendar.DATE);
	}
	
	/**
	 * 计算两个日期之间相差天数
	 * @param startDateStr  日期字符串1
	 * @param endDateStr    日期字符串2
	 * @return
	 */
	public static int getDateDiff(String dateStr1, String dateStr2) {
		Calendar cal = Calendar.getInstance();  
		long time1 = 0;
		long time2 = 0;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			cal.setTime(sdf.parse(dateStr1));  
			time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(dateStr2));
			time2 = cal.getTimeInMillis(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long dateDiff = (time2-time1)/(1000*3600*24);
		return Integer.parseInt(String.valueOf(dateDiff));   
	}
	
	/**
	 * 对一个dateString日期加指定天数
	 * @param dateString  日期字符串，暂时仅支持"yyyy-MM-dd"格式。
	 * @param days        增加的天数
	 */
	public static String addDateDays(String dateString, int days) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DATE, days);
			endDate = cal.getTime();
			String endDateStr = format.format(endDate);
			return endDateStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String addDateMonths(String dateString, int months) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = format.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, months);
			endDate = cal.getTime();
			String endDateStr = format.format(endDate);
			return endDateStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 比较两个日期的大小
	 * @param dateStr1
	 * @param dateStr2
	 * @return dateStr1 == dateStr2 : 0
	 *         dateStr1 < dateStr2 : -1
	 *         dateStr1 > dateStr2 : 1
	 */
	public static int compareDate(String dateStr1, String dateStr2) {
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(dateStr1));
			c2.setTime(df.parse(dateStr2));
		}catch(ParseException e){
			e.printStackTrace();
		}
		int result = c1.compareTo(c2);
		if(result == 0) {
			return 0;
		}
		else if(result < 0) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	/**
	 * -----> 作为参考代码保留 <-----
	 * 解析日期字符串，从中获取年、月、日、时、分、秒的值
	 */
	private static void parseCurrentDatetime() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR_OF_DAY); 
		int minute = cal.get(Calendar.MINUTE); 
		int second = cal.get(Calendar.SECOND); 
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		System.out.println("===年===" + year); 
	    System.out.println("===月===" + month); 
	    System.out.println("===日===" + day); 
	    System.out.println("===时===" + hour); 
	    System.out.println("===分===" + minute); 
	    System.out.println("===秒===" + second); 
		System.out.println("day of week : " + dayOfWeek);
		System.out.println("day of month : " + dayOfMonth);
		System.out.println("day of year : " + dayOfYear);
	}
	
	/**
	 * -----> 作为参考代码保留 <-----
	 * 解析日期字符串，从中获取年、月、日、时、分、秒的值
	 * @param dateString
	 */
	private static void parseDateTime(String dateString) { 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar cal = Calendar.getInstance(); 
	    Date dt = null; 
	    try {
	    	dt = sdf.parse(dateString); 
	    	cal.setTime(dt); 
	    } catch (ParseException e) { 
	    	e.printStackTrace(); 
	    } 
	    int year = cal.get(Calendar.YEAR); 
	    int month = cal.get(Calendar.MONTH) + 1; 
	    int day = cal.get(Calendar.DAY_OF_MONTH); 
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
	    System.out.println("===年===" + year); 
	    System.out.println("===月===" + month); 
	    System.out.println("===日===" + day); 
		System.out.println("day of week : " + dayOfWeek);
		System.out.println("day of month : " + dayOfMonth);
		System.out.println("day of year : " + dayOfYear);
	} 
}
