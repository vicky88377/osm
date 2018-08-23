package com.mindtree.ordermanagementservice.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.repository.OrderFoodInfoRpository;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;

@Service(value = "OrderFoodInfoService")
public class OrderFoodInfoServiceImpl implements OrderFoodInfoService {

	@Autowired
	OrderFoodInfoRpository orderFoodInfoReposityory;

	@Override
	public double priceCalculation(List<OrderFoodInfo> orderFoodInfos) {
		double totalPrice = 0.0;
		for (OrderFoodInfo orderFoodInfo : orderFoodInfos) {
			totalPrice = totalPrice + orderFoodInfo.getFoodPrice();
		}

		return totalPrice;
	}

	@Override
	public OrderFoodInfo create(OrderFoodInfo orderFoodInfo) {
		OrderFoodInfo saveOrderFoodInfo = orderFoodInfoReposityory.save(orderFoodInfo);
		return saveOrderFoodInfo;
	}
}
