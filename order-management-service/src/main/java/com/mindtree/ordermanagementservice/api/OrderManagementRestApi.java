package com.mindtree.ordermanagementservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.ordermanagementservice.model.OrderRequest;
import com.mindtree.ordermanagementservice.model.OrderResponse;
import com.mindtree.ordermanagementservice.service.OrderService;

@RestController
@RequestMapping("/ordermanagement ")
public class OrderManagementRestApi {

	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/restaurant/orders/{resturantId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public OrderResponse createOder(@RequestBody OrderRequest orderRequest) {
		// return orderService.createOrder(order);
		return null;
	}

	@RequestMapping(value = "/restaurant/viewOrder/{orderID}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public OrderResponse viewOder(@RequestBody OrderRequest orderRequest) {
		// return orderService.createOrder(order);
		return null;
	}
}
