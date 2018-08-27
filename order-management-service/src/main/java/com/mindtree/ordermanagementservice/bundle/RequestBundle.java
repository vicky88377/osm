package com.mindtree.ordermanagementservice.bundle;

import java.sql.Timestamp;
import java.util.Date;

import com.mindtree.ordermanagementservice.model.Address;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.OrderRequest;

public class RequestBundle {

	public static DeliveryInfo deliveryInfoRequstBuilder(OrderRequest orderRequest) {
		DeliveryInfo deliveryInfo = new DeliveryInfo();
		Address address = orderRequest.getAddress();
		deliveryInfo.setDeliveryAddress(
				address.getAddressLine1() + " " + address.getAddressLine2() + " " + address.getArea());
		deliveryInfo.setLatitude(address.getLatitude());
		deliveryInfo.setLongitude(address.getLongitude());
		return deliveryInfo;
	}

	public static OrderDetails orderDetailsRequstBuilder(DeliveryInfo deliveryInfo, OrderRequest orderRequest,
			double toatalPrice) {
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCustomerId(Integer.parseInt(orderRequest.getAddress().getUserId()));
		System.out.println("delivery  :" + deliveryInfo.getDeliveryId());
		orderDetails.setDeliveryId(deliveryInfo.getDeliveryId());

		Date date = new Date();
		orderDetails.setOrderDate(new Timestamp(date.getTime()));
		orderDetails.setRestaurentId(orderRequest.getResturentId());
		orderDetails.setTotalPrice(toatalPrice);
		orderDetails.setOrderStatus("P");
		return orderDetails;
	}

	public static OrderFoodInfo oderFoodInfoRequstBuilder(OrderDetails orderDetails) {
		OrderFoodInfo orderFoodInfo = new OrderFoodInfo();
		orderFoodInfo.setOrderId(orderDetails.getOrderId());
		return orderFoodInfo;
	}

}
