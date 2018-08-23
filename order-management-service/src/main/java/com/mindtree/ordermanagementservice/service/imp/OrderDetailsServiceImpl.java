package com.mindtree.ordermanagementservice.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.repository.OrderDetailsRepository;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.util.OrderMangementServiceUtil;

@Service(value = "OrderDetailsService")
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Override
	public OrderDetails create(OrderDetails orderDetails) {
		OrderDetails saveOrderDetails = orderDetailsRepository.save(orderDetails);
		return saveOrderDetails;
	}

	@Override
	public OrderDetails cancelOrder(int orderId) {
		OrderDetails order = null;
		System.out.println("Inside service method ::" + orderId);
		Optional<OrderDetails> orderDetail = orderDetailsRepository.findById(orderId);
		if(orderDetail.isPresent())
		 {
			order =  orderDetail.get();
		 }
		 //OrderDetails orderDetail = orderDetailsRepository.findById(orderId).get();
		System.out.println("Got the data" + order.getCustomerId());
		System.out.println("Got the data" + OrderMangementServiceUtil.getTimeDifference(order.getOrderDate())+"  "+order.getOrderDate());
		if (OrderMangementServiceUtil.getTimeDifference(order.getOrderDate())>=10)
		{
			throw new OrderManagementServiceException(
					"Order can not be canceled after 10 mins of order placed");
		}
		
		order.setOrderStatus("Order Canceled");
		
		return orderDetailsRepository.save(order);
		
		
	}

}
