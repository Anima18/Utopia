package com.chris.utopia.common.util;

import android.annotation.SuppressLint;

import com.chris.utopia.common.constant.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2015/9/2.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
	private static final int FIRST_DAY = Calendar.MONDAY;

	public static String toString(Date date, String pattern) {
		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date toDate(String str, String fromPattern) {
		try {
			return (new SimpleDateFormat(fromPattern)).parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

    public static Date createDate(Date date, int seconds) {
          Calendar c = Calendar. getInstance();
          c.setTime(date);
          c.add(Calendar. SECOND, seconds);          
          return c.getTime();
    }

    public static Date addDay(Date date, int day) {
    	Calendar c = Calendar. getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }
    
	public static String toString(String str, String fromPattern, String toPattern ) {
		try {
			if(StringUtil.isNotEmpty(str)) {
				Date d = (new SimpleDateFormat(fromPattern)).parse(str);
				return (new SimpleDateFormat(toPattern)).format(d);
			}
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> getALlweekDays(Date date) {
		List<String>lst = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		setToFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			String day = printDay(calendar);
			lst.add(day);
			calendar.add(Calendar.DATE, 1);
		}
		return lst;
	}

	public static List<String> getALlweekDays() {
		List<String>lst = new ArrayList();
		Calendar calendar = Calendar.getInstance();
		setToFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			String day = printDay(calendar);
			lst.add(day);
			calendar.add(Calendar.DATE, 1);
		}
		return lst;
	}

	public static String getALlweekDayStr() {
		StringBuffer sb = new StringBuffer("");
		Calendar calendar = Calendar.getInstance();
		setToFirstDay(calendar);
		for (int i = 0; i < 7; i++) {
			String day = printDay(calendar);
			sb.append("'");
			sb.append(day);
			sb.append("'");
			if(i < 6) {
				sb.append(",");
			}
			calendar.add(Calendar.DATE, 1);
		}
		return sb.toString();
	}

	private static void setToFirstDay(Calendar calendar) {
		while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
			calendar.add(Calendar.DATE, -1);
		}
	}

	private static String  printDay(Calendar calendar) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATETIME_FORMAT_4);
		return dateFormat.format(calendar.getTime());
	}

	public static String getWeek(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(date);
		return week;
	}

	public static String getWeek(String dateStr){
		Date date = toDate(dateStr, Constant.DATETIME_FORMAT_4);
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(date);
		return week;
	}
}
