package com.mindtree.ordermanagementservice.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class OrderMangementServiceUtil {

	public static long getTimeDifference(Date givenDate) {
		Date date = new Date();
	/*	
		//new Timestamp(date.getTime());
		System.out.println("systim e ::" + date.getTime());
		System.out.println("givenDate e ::" + givenDate.);
		long sec = (givenDate.getTime() - date.getTime()) / 1000;
		long min = sec / 60;
		return min;*/
		
		Calendar c1 =Calendar.getInstance();
		Calendar c2 =Calendar.getInstance();
		
		c1.setTime(givenDate);
		c2.setTime(date);
		
		System.out.println( "Test" + (c2.getTimeInMillis() - c1.getTimeInMillis())/1000);
		long sec = (c2.getTimeInMillis() - c1.getTimeInMillis())/1000;
		long min = sec/60;
		
		return min;
	}

}
