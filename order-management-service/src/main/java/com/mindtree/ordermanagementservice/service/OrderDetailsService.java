package com.mindtree.ordermanagementservice.service;

import java.util.List;

import com.mindtree.ordermanagementservice.model.OrderDetails;

public interface OrderDetailsService {

	public OrderDetails create(OrderDetails orderDetails);

	public OrderDetails cancelOrder(int orderId);

	public OrderDetails getOrderDetailsByOrderId(int orderId);

	public List<OrderDetails> getOrderDetailsByCustomerId(int customerId);
	
	public OrderDetails update(int orderId,String status);

}
