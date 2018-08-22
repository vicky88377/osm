package com.mindtree.ordermanagementservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mindtree.ordermanagementservice.model.OrderRequest;
import com.mindtree.ordermanagementservice.model.OrderResponse;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;
import com.mindtree.ordermanagementservice.model.RestaurantModel;
import com.mindtree.ordermanagementservice.service.OrderService;

@RestController
@RequestMapping("/ordermanagement ")
public class OrderManagementRestApi {

	@Autowired
	OrderService orderService;
	@Autowired
	private RestTemplate template;

	@RequestMapping(value = "/restaurant/orders/{resturantId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public OrderResponse createOder(@RequestBody OrderRequest orderRequest) {
		// rest call to other servicse to resturent details best on the
		// resturent id;
		
		
		//gitcommit test

		String url = "http://ZUUL-GATEWAY/PAYMENT-GATEWAY/payNow/" + orderRequest.getResturentId();
		ResponseStatusModel responseModel = template.getForObject(url, ResponseStatusModel.class);
		Object[] objects = responseModel.getData();
		RestaurantModel restaurantModel = null;
		for (Object object : objects) {
			restaurantModel = (RestaurantModel) object;
		}
		String pincode = restaurantModel.getPincode();
		if(!orderService.pinValidation(pincode))
		{
			//exception
		}
		
		
		

		return null;
	}

	@RequestMapping(value = "/restaurant/viewOrder/{orderID}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public OrderResponse viewOder(@RequestBody OrderRequest orderRequest) {
		// return orderService.createOrder(order);
		return null;
	}
}
