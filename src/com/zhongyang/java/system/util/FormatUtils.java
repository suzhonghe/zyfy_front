package com.zhongyang.java.system.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatUtils {
	
	public static String dateFormat(Date date) {
		if (date == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		return simpleDateFormat.format(date);
	}

	public static String simpleFormat(Date date) {
		if (date == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		return simpleDateFormat.format(date);
	}

	public static String millisDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (date != null)
			return simpleDateFormat.format(date);
		return null;
	}
	
	public static String timeFormat(Date date) {
		if (date == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

		return simpleDateFormat.format(date);
	}
	
	
	
	public static Date dateFormat(String date){
		if(date == null)
			return null;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date birthDateFormat(String date){
		if(date == null)
			return null;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String afterNDay(int n) {
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		c.add(Calendar.DATE, n);
		Date d2 = c.getTime();
		String s = df.format(d2);
		return s;
	}
	public static Date timeFormat(String date){
		if(date == null)
			return null;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	  public static boolean timeDiff(Date time1,Date time2){
	    	 long result = (time1.getTime() - time2.getTime()) / 1000;// 这个的除以1000得到秒，相应的60000得到分，3600000得到小时  
	    	 if(result>0){
	    		 return true;
	    	 }
	    	 return false;
	    }
}
