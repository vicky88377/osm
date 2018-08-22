package com.mindtree.ordermanagementservice.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mindtree.ordermanagementservice.bundle.RequestBundle;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.OrderRequest;
import com.mindtree.ordermanagementservice.model.OrderResponse;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;
import com.mindtree.ordermanagementservice.model.RestaurantModel;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;

@RestController
@RequestMapping("/ordermanagement ")
public class OrderManagementRestApi {

	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	OrderFoodInfoService orderFoodInfoService;
	DeliveryInfoService deliveryInfoService;
	@Autowired
	private RestTemplate template;

	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel createOder(@RequestBody OrderRequest orderRequest) {
		// rest call to other servicse to resturent details best on the
		// resturent id;

		String getRestaurantApiUrl = "http://ZUUL-GATEWAY/PAYMENT-GATEWAY/restaurant/getRestaurant"
				+ orderRequest.getResturentId();
		ResponseStatusModel restaurantInfo = template.getForObject(getRestaurantApiUrl, ResponseStatusModel.class);
		Object[] objects = restaurantInfo.getData();
		RestaurantModel restaurantModel = null;
		for (Object object : objects) {
			restaurantModel = (RestaurantModel) object;
		}

		String validateRestaurantApiUrl = "http://ZUUL-GATEWAY/PAYMENT-GATEWAY/restaurant/validate"
				+ orderRequest.getResturentId() + orderRequest.getAddress().getLatitude()
				+ orderRequest.getAddress().getLongitude();

		ResponseStatusModel validateRestaurantResponse = template.getForObject(validateRestaurantApiUrl,

				ResponseStatusModel.class);
		// check Resturent provide food for user given place;
		if (validateRestaurantResponse.getMessage().equals("failure")) {
			// throw exception
		}
		double totalPrice = orderFoodInfoService.priceCalculation(orderRequest.getFoodItems());
		// validate minmum price
		if (totalPrice < Double.parseDouble((restaurantModel.getMinimumOrder()))) {
			// throw Exception
		}
		// create deliveryinfo Records
		DeliveryInfo deliveryInfo = RequestBundle.deliveryInfoRequstBuilder(orderRequest);
		DeliveryInfo savedDeliveryInfo = deliveryInfoService.create(deliveryInfo);

		// create OrderDetails Records
		OrderDetails orderDetails = RequestBundle.orderDetailsRequstBuilder(savedDeliveryInfo, orderRequest,
				totalPrice);
		OrderDetails savedOrderDetails = orderDetailsService.create(orderDetails);
		// To perfome orderFoodInfo creation one by one.
		List<OrderFoodInfo> listOfOrderFoodInfo = new ArrayList<OrderFoodInfo>();

		for (OrderFoodInfo orderFoodInfo : orderRequest.getFoodItems()) {
			orderFoodInfo.setOrderId(savedOrderDetails.getOrderId());
			OrderFoodInfo saveOrderFoodInfo = orderFoodInfoService.create(orderFoodInfo);
			listOfOrderFoodInfo.add(saveOrderFoodInfo);
		}
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatus("200");
		responseStatusModel.setMessage("order will reach you within 45 minutes");
		responseStatusModel.setOrderId(orderDetails.getOrderId());
		return responseStatusModel;
	}

	@RequestMapping(value = "/restaurant/viewOrder/{orderID}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public OrderResponse viewOder(@RequestBody OrderRequest orderRequest) {
		// return orderService.createOrder(order);
		return null;
	}
}
