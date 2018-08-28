package com.mindtree.ordermanagementservice.bundle;

import java.util.List;

import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;

public class ResponseBundle {
	public static ResponseStatusModel getViewOderResponsBuilder(OrderDetails orderDetails, DeliveryInfo deliveryInfo,
			List<OrderFoodInfo> listOfOrderFoodInfo) {
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setOrderId(orderDetails.getOrderId());
		responseStatusModel.setStatusCode(200);
		
		responseStatusModel.setData((listOfOrderFoodInfo));
		
		responseStatusModel.setDeliveryInfo(deliveryInfo);
		
		return responseStatusModel;
	}

	public static ResponseStatusModel getViewAllOderResponsBuilder(List<OrderDetails> orderDetailsList) {
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setData(orderDetailsList);
		return responseStatusModel;
	}
}
