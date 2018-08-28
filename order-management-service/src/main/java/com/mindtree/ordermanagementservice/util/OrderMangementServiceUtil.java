package com.mindtree.ordermanagementservice.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mindtree.ordermanagementservice.model.OrderFoodInfo;

public class OrderMangementServiceUtil {

	public static long getTimeDifference(Date givenDate) {
		Date date = new Date();
	

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(givenDate);
		c2.setTime(date);

		
		long sec = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000;
		long min = sec / 60;

		return min;
	}

	public static double totalbillableprice(List<OrderFoodInfo> customerOderFoodList, List<OrderFoodInfo> foodMenu) {
		Map<Integer, Double> foodIdToFoodPrice = null;
		
		System.out.println("totalbillableprice") ;
		
		
		double totalPrice = 0.0;
		if (foodMenu != null && customerOderFoodList != null) {
			for (OrderFoodInfo foodInfo : foodMenu) {
				if (foodIdToFoodPrice == null) {
					foodIdToFoodPrice = new HashMap<Integer, Double>();
					foodIdToFoodPrice.put(foodInfo.getFoodId(), foodInfo.getFoodPrice());
					
					
				}
				foodIdToFoodPrice.put(foodInfo.getFoodId(), foodInfo.getFoodPrice());
			
			}

			for (OrderFoodInfo customerOrderFood : customerOderFoodList) {
			
				double price = foodIdToFoodPrice.get(customerOrderFood.getFoodId());
				totalPrice = totalPrice + (price * customerOrderFood.getQuantity());
			
			}

		}
  
		return totalPrice;
	}

}
