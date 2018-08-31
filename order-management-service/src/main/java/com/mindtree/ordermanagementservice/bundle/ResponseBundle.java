package com.mindtree.ordermanagementservice.bundle;

import java.util.ArrayList;
import java.util.List;

import com.mindtree.ordermanagementservice.dto.OrderDetailsDto;
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
		responseStatusModel.setStatus("Success");
		responseStatusModel.setData((listOfOrderFoodInfo));

		responseStatusModel.setDeliveryInfo(deliveryInfo);

		return responseStatusModel;
	}

	public static ResponseStatusModel getViewAllOderResponsBuilder(List<OrderDetails> orderDetailsList) {
		List<OrderDetailsDto> orderDetailsDtos = new ArrayList<OrderDetailsDto>();
		for (OrderDetails orderDetail : orderDetailsList) {
			OrderDetailsDto orderDetailsDto = ResponseBundle.processOrderDetailsToOrderDetaisDto(orderDetail);
			orderDetailsDtos.add(orderDetailsDto);
		}
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setData(orderDetailsDtos);
		return responseStatusModel;
	}

	public static OrderDetailsDto processOrderDetailsToOrderDetaisDto(OrderDetails orderDetail) {
		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
		orderDetailsDto.setOrderId(orderDetail.getOrderId());
		orderDetailsDto.setOrderStatus(orderDetail.getOrderStatus());
		orderDetailsDto.setOrdredDate(orderDetail.getOrdredDate());
		orderDetailsDto.setTotalPrice(orderDetail.getTotalPrice());
		return orderDetailsDto;
	}
}
