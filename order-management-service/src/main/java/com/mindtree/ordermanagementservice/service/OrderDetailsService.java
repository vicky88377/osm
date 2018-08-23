package com.mindtree.ordermanagementservice.service;

import com.mindtree.ordermanagementservice.model.OrderDetails;

public interface OrderDetailsService {

	public OrderDetails create(OrderDetails orderDetails);
	
	public OrderDetails cancelOrder(int orderId);

}
