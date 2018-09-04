package com.mindtree.ordermanagementservice.bundle;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mindtree.ordermanagementservice.dto.DeliveryAddress;
import com.mindtree.ordermanagementservice.dto.OrderFoodInfoDto;
import com.mindtree.ordermanagementservice.dto.OrderRequest;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;

public class RequestMapper {

	public static DeliveryInfo deliveryInfoRequstBuilder(OrderRequest orderRequest) {
		DeliveryInfo deliveryInfo = new DeliveryInfo();
		DeliveryAddress address = orderRequest.getDeliveryaddress();
		deliveryInfo.setDeliveryAddress(
				address.getAddressLine1() + " " + address.getAddressLine2() + " " + address.getArea());
		deliveryInfo.setLatitude(address.getLatitude());
		deliveryInfo.setLongitude(address.getLongitude());
		return deliveryInfo;
	}

	public static OrderDetails orderDetailsRequstBuilder(DeliveryInfo deliveryInfo, OrderRequest orderRequest,
			double toatalPrice) {
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCustomerId((orderRequest.getCustomerDetails().getId()));
		orderDetails.setDeliveryId(deliveryInfo.getDeliveryId());
		Date date = new Date();
		orderDetails.setOrdredDate(new Timestamp(date.getTime()));
		orderDetails.setRestaurentId(orderRequest.getResturentId());
		orderDetails.setPaymentMode(orderRequest.getPaymentMode());
		System.out.println("resturentId" + orderDetails.getRestaurentId() + "===" + orderRequest.getResturentId());
		orderDetails.setTotalPrice(toatalPrice);
		orderDetails.setOrderStatus("P");
		return orderDetails;
	}

	public static OrderFoodInfo oderFoodInfoRequstBuilder(OrderDetails orderDetails) {
		OrderFoodInfo orderFoodInfo = new OrderFoodInfo();
		orderFoodInfo.setOrderId(orderDetails.getOrderId());
		return orderFoodInfo;
	}

	public static List<OrderFoodInfo> getOrderdFoodInfoRequstBuilder(List<OrderFoodInfoDto> listOfOrderFoodInfoDto) {
		List<OrderFoodInfo> listOfOrderFoodInfo = null;
		if (listOfOrderFoodInfoDto != null && !listOfOrderFoodInfoDto.isEmpty()) {
			for (OrderFoodInfoDto orderFoodInfoDto : listOfOrderFoodInfoDto)

				if (orderFoodInfoDto != null) {

					if (listOfOrderFoodInfo == null) {
						listOfOrderFoodInfo = new ArrayList<OrderFoodInfo>();
					}
					OrderFoodInfo orderFoodInfo = new OrderFoodInfo();
					orderFoodInfo.setFoodId(orderFoodInfoDto.getFoodId());
					orderFoodInfo.setAdditionalInfo(orderFoodInfoDto.getAdditionalInfo());
					orderFoodInfo.setQuantity(orderFoodInfoDto.getQuantity());

					listOfOrderFoodInfo.add(orderFoodInfo);
				}
		}
		return listOfOrderFoodInfo;
	}
}
