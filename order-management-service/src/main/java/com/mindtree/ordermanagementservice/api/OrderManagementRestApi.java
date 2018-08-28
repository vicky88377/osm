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
import com.mindtree.ordermanagementservice.dto.OrderRequest;
import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;
import com.mindtree.ordermanagementservice.model.RestaurantModel;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;
import com.mindtree.ordermanagementservice.util.OrderMangementServiceUtil;

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

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel createOder(@RequestBody OrderRequest orderRequest) {
		// rest call to other services to restaurant details best on the
		// Restaurant id;
		// System.out.println("Rest create corder" +
		// orderRequest.getResturentId());

		List<OrderFoodInfo> listOfOrderFood = new ArrayList<OrderFoodInfo>();
		OrderFoodInfo orderFoodInfo1 = new OrderFoodInfo();
		orderFoodInfo1.setAdditionalInfo("normal cooked chiken better spice nuts");
		orderFoodInfo1.setFoodId(26);
		orderFoodInfo1.setFoodPrice(278);

		OrderFoodInfo orderFoodInfo2 = new OrderFoodInfo();
		orderFoodInfo2.setAdditionalInfo("chiken better spice nuts");
		orderFoodInfo2.setFoodId(27);
		orderFoodInfo2.setFoodPrice(200);
		listOfOrderFood.add(orderFoodInfo1);
		listOfOrderFood.add(orderFoodInfo2);

		Object resaray = new Object();
		RestaurantModel res = new RestaurantModel();
		res.setRestaurantId("101");
		res.setLatitude("3224");
		res.setLongitude("ew224");
		res.setMinimumOrder("60");
		resaray = res;

		/*
		 * String getRestaurantApiUrl =
		 * "http://ZUUL-GATEWAY/PAYMENT-GATEWAY/restaurant/getRestaurant" +
		 * orderRequest.getResturentId();
		 */
		ResponseStatusModel mockResponseStatusModel = new ResponseStatusModel();
		mockResponseStatusModel.setStatusCode(200);
		mockResponseStatusModel.setStatus("succ");
		mockResponseStatusModel.setData(listOfOrderFood);

		// ResponseStatusModel restaurantInfo =
		// template.getForObject(getRestaurantApiUrl,
		// ResponseStatusModel.class);
		RestaurantModel restaurantModel = null;
		// restaurantModel = (RestaurantModel) restaurantInfo.getData();
		// System.out.println("After loopinmg" +
		// restaurantModel.getRestaurantId());
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
		
		
		List<OrderFoodInfo> listOfOrderFoodInfo = RequestBundle
				.getOrderdFoodInfoRequstBuilder(orderRequest.getFoodItems());
		
		
		List<OrderFoodInfo> foodMenu = mockResponseStatusModel.getData();
		
		double totalPrice = OrderMangementServiceUtil.totalbillableprice(listOfOrderFoodInfo, foodMenu);

	/*	System.out.println("total price ::" + totalPrice);
		System.out.println("minimum order price" + restaurantModel.getMinimumOrder());*/

		// validate minimum price
		/*if (totalPrice < Double.parseDouble((restaurantModel.getMinimumOrder()))) {
			// throw Exception
			throw new OrderManagementServiceException(
					"minimum order should grater than " + Double.parseDouble((restaurantModel.getMinimumOrder())), 0);

		}*/

		

		// create deliveryinfo Records
		DeliveryInfo deliveryInfo = RequestBundle.deliveryInfoRequstBuilder(orderRequest);
		
		DeliveryInfo savedDeliveryInfo = deliveryInfoService.create(deliveryInfo);
		

		// create OrderDetails Records
		OrderDetails orderDetails = RequestBundle.orderDetailsRequstBuilder(savedDeliveryInfo, orderRequest,
				totalPrice);
		OrderDetails savedOrderDetails = orderDetailsService.create(orderDetails);
		// To orderFoodInfo creation one by one.
		List<OrderFoodInfo> saveListOfOrderFoodInfo = new ArrayList<OrderFoodInfo>();
		

		for (OrderFoodInfo orderFoodInfo : listOfOrderFoodInfo) {

			orderFoodInfo.setOrderId(savedOrderDetails.getOrderId());
			
			OrderFoodInfo saveOrderFoodInfo = orderFoodInfoService.create(orderFoodInfo);
			

			
			saveListOfOrderFoodInfo.add(saveOrderFoodInfo);
		}
		// Object
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setStatus("success");
		responseStatusModel.setMessage("order will reach you within 45 minutes");
		responseStatusModel.setOrderId(savedOrderDetails.getOrderId());
		return responseStatusModel;
	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel viewOder(@PathVariable int orderId) {
		ResponseStatusModel responseStatusModel = null;
		try {
			OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);

		

			DeliveryInfo deliveryInfo = deliveryInfoService.getDeliveryInfoByDeliveryId(orderDetails.getDeliveryId());

	

			List<OrderFoodInfo> listOfOrderFoodInfo = orderFoodInfoService.getListOfFoodItemsOrder(orderId);

			

			responseStatusModel = ResponseBundle.getViewOderResponsBuilder(orderDetails, deliveryInfo,
					listOfOrderFoodInfo);
		
			return responseStatusModel;
		} catch (Exception e) {
			throw new OrderManagementServiceException(" data not found in records  ", orderId);
		}
	}

	@RequestMapping(value = "/orders/{customerId}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
	public ResponseStatusModel cancelOrder(@PathVariable int orderId) {

		OrderDetails orderDetail = orderDetailsService.cancelOrder(orderId);

		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setStatus("success");
		responseStatusModel.setMessage("order has been canceled");
		responseStatusModel.setOrderId(orderDetail.getOrderId());
		return responseStatusModel;
	}

	@RequestMapping(value = "/orders/{orderId}/{orderStatus}", method = RequestMethod.PUT)
	public ResponseStatusModel updateOrderStatus(@PathVariable int orderId, @PathVariable String orderStatus) {

		OrderDetails orderDetail = orderDetailsService.update(orderId, orderStatus);

		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setMessage("order has been updated");
		responseStatusModel.setOrderId(orderDetail.getOrderId());
		return responseStatusModel;
	}
}
