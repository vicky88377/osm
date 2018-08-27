package com.mindtree.ordermanagementservice.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mindtree.ordermanagementservice.bundle.RequestBundle;
import com.mindtree.ordermanagementservice.bundle.ResponseBundle;
import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.OrderRequest;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;
import com.mindtree.ordermanagementservice.model.RestaurantModel;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;

@RestController
@RequestMapping("/ordermanagement")
public class OrderManagementRestApi {

	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	OrderFoodInfoService orderFoodInfoService;
	@Autowired
	DeliveryInfoService deliveryInfoService;
	@Autowired
	private RestTemplate template;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel createOder(@RequestBody OrderRequest orderRequest) {
		// rest call to other services to restaurant details best on the
		// Restaurant id;
		System.out.println("Rest create corder" + orderRequest.getResturentId());

		Object[] resaray = new Object[1];
		RestaurantModel res = new RestaurantModel();
		res.setRestaurantId("101");
		res.setLatitude("3224");
		res.setLongitude("ew224");
		res.setMinimumOrder("500");
		resaray[0] = res;

		/*
		 * String getRestaurantApiUrl =
		 * "http://ZUUL-GATEWAY/PAYMENT-GATEWAY/restaurant/getRestaurant" +
		 * orderRequest.getResturentId();
		 */
		ResponseStatusModel restaurantInfo = new ResponseStatusModel();
		restaurantInfo.setStatusCode(200);
		restaurantInfo.setStatus("succ");
		restaurantInfo.setData(resaray);

		// ResponseStatusModel restaurantInfo =
		// template.getForObject(getRestaurantApiUrl,
		// ResponseStatusModel.class);
		RestaurantModel restaurantModel = null;
		System.out.println("Array loopinmg");
		restaurantModel = (RestaurantModel) restaurantInfo.getData();
		System.out.println("After loopinmg" + restaurantModel.getRestaurantId());
		// rest call to get particular restaurant is delivered food for
		// particular given delivered address

		/*
		 * String validateRestaurantApiUrl =
		 * "http://ZUUL-GATEWAY/PAYMENT-GATEWAY/restaurant/validate" +
		 * orderRequest.getResturentId() +
		 * orderRequest.getAddress().getLatitude() +
		 * orderRequest.getAddress().getLongitude();
		 * 
		 * ResponseStatusModel validateRestaurantResponse =
		 * template.getForObject(validateRestaurantApiUrl,
		 * 
		 * ResponseStatusModel.class);
		 */
		ResponseStatusModel validateRestaurantResponse = new ResponseStatusModel();
		validateRestaurantResponse.setMessage("failu");

		// check Restaurant provide food for user given place;
		if (validateRestaurantResponse.getMessage().equals("failure")) {
			// throw exception
			throw new OrderManagementServiceException("Resturent not provide the delivery for the given address", 0);
		}
		double totalPrice = orderFoodInfoService.priceCalculation(orderRequest.getFoodItems());

		System.out.println("total price ::" + totalPrice);
		System.out.println("minimum order price" + restaurantModel.getMinimumOrder());

		// validate minimum price
		if (totalPrice < Double.parseDouble((restaurantModel.getMinimumOrder()))) {
			// throw Exception
			throw new OrderManagementServiceException(
					"minimum order should grater than " + Double.parseDouble((restaurantModel.getMinimumOrder())), 0);

		}

		System.out.println("Start Db");

		// create deliveryinfo Records
		DeliveryInfo deliveryInfo = RequestBundle.deliveryInfoRequstBuilder(orderRequest);
		System.out.println("Start Db1" + deliveryInfo.getDeliveryAddress());
		DeliveryInfo savedDeliveryInfo = deliveryInfoService.create(deliveryInfo);
		System.out.println("Start Db2" + savedDeliveryInfo.getDeliveryId());

		// create OrderDetails Records
		OrderDetails orderDetails = RequestBundle.orderDetailsRequstBuilder(savedDeliveryInfo, orderRequest,
				totalPrice);
		OrderDetails savedOrderDetails = orderDetailsService.create(orderDetails);
		// To orderFoodInfo creation one by one.
		List<OrderFoodInfo> listOfOrderFoodInfo = new ArrayList<OrderFoodInfo>();
		System.out.println("No of food Iteams" + orderRequest.getFoodItems().size());

		for (OrderFoodInfo orderFoodInfo : orderRequest.getFoodItems()) {
			orderFoodInfo.setOrderId(savedOrderDetails.getOrderId());
			System.out.println("get foodInfo foodId" + orderFoodInfo.getFoodId());
			OrderFoodInfo saveOrderFoodInfo = orderFoodInfoService.create(orderFoodInfo);
			System.out.println("get foodInfo OrderFoodId" + orderFoodInfo.getOrderFoodId());

			System.out.println();
			listOfOrderFoodInfo.add(saveOrderFoodInfo);
		}
		// Object
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setStatus("success");
		responseStatusModel.setMessage("order will reach you within 45 minutes");
		responseStatusModel.setOrderId(savedOrderDetails.getOrderId());
		return responseStatusModel;
	}

	@RequestMapping(value = "/order/view/{orderId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel viewOder(@PathVariable int orderId) {
		ResponseStatusModel responseStatusModel = null;
		try {
			OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);

			System.out.println(orderDetails.getOrderId());

			DeliveryInfo deliveryInfo = deliveryInfoService.getDeliveryInfoByDeliveryId(orderDetails.getDeliveryId());

			System.out.println(deliveryInfo.getDeliveryAddress());

			List<OrderFoodInfo> listOfOrderFoodInfo = orderFoodInfoService.getListOfFoodItemsOrder(orderId);

			System.out.println(listOfOrderFoodInfo.size());

			responseStatusModel = ResponseBundle.getViewOderResponsBuilder(orderDetails, deliveryInfo,
					listOfOrderFoodInfo);
			System.out.println("responseStatusModel" + responseStatusModel.getStatusCode());
			return responseStatusModel;
		} catch (Exception e) {
			throw new OrderManagementServiceException(" data not found in records  ", orderId);
		}
	}

	@RequestMapping(value = "/order/all/{customerId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel viewOderHistory(@PathVariable int customerId) {
		ResponseStatusModel responseStatusModel = null;
		try {
			List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByCustomerId(customerId);
			if (orderDetailsList.size() <= 0) {
				throw new OrderManagementServiceException(" data not found in records  ", customerId);
			}
			responseStatusModel = ResponseBundle.getViewAllOderResponsBuilder(orderDetailsList);
			return responseStatusModel;
		} catch (Exception e) {
			throw new OrderManagementServiceException(" data not found in records  ", customerId);
		}
	}

	@RequestMapping(value = "/restaurant/cancelOrder/{orderId}", method = RequestMethod.GET)
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel cancelOrder(@PathVariable int orderId) {

		OrderDetails orderDetail = orderDetailsService.cancelOrder(orderId);

		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setStatus("success");
		responseStatusModel.setMessage("order has been canceled");
		responseStatusModel.setOrderId(orderDetail.getOrderId());
		return responseStatusModel;
	}
}
