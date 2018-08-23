package com.mindtree.ordermanagementservice.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.repository.OrderDetailsRepository;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;

@Service(value = "OrderDetailsService")
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Override
	public OrderDetails create(OrderDetails orderDetails) {
		OrderDetails saveOrderDetails = orderDetailsRepository.save(orderDetails);
		return saveOrderDetails;
	}

}
