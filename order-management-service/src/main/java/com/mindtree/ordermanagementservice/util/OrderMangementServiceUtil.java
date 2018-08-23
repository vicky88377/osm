package com.mindtree.ordermanagementservice.util;

import java.sql.Timestamp;
import java.util.Date;

public class OrderMangementServiceUtil {

	public static long getTimeDifference(Date givenDate) {
		Date date = new Date();
		new Timestamp(date.getTime());
		long sec = (givenDate.getTime() - date.getTime()) / 1000;
		long min = sec / 60;
		return min;
	}

}
