package com.mindtree.ordermanagementservice.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.mindtree.ordermanagementservice.model.ResturentFoodManuApiResponse;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;
import com.mindtree.ordermanagementservice.util.FireBaseAuthHelper;
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
	public ResponseStatusModel createOder(@RequestHeader(value = "token") String token,
			@RequestBody OrderRequest orderRequest) {
		System.out.println("Rest create corder" + orderRequest.getResturentId());

		// validate address
		String getRestaurantValidetoreApiUrl = "http://demojenkins3.southeastasia.cloudapp.azure.com:5665/restaurants/"
				+ orderRequest.getResturentId() + "/validate/" + orderRequest.getDeliveryaddress().getLatitude() + "/"
				+ orderRequest.getDeliveryaddress().getLongitude();

		System.out.println("getRestaurantValidetoreApiUrl " + getRestaurantValidetoreApiUrl);
		boolean isvalid = FireBaseAuthHelper.isValidToken(token);
		if (!isvalid) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		Map<String, String> info;
		try {
			info = FireBaseAuthHelper.getUserInfo(token);
		} catch (InterruptedException e) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		String emailId = info.get("email");
		// cutomerId call for customerId
		System.out.println(emailId);

		String resValidate = template.getForObject(getRestaurantValidetoreApiUrl, String.class);
		System.out.println(resValidate);

		if (!resValidate.contains("Delivery is available for your area")) {
			throw new OrderManagementServiceException("Resturent not provide the delivery for the given address", 0, 0);
		}

		System.out.println("################# Validated");
		// Menu Details

		String getRestaurantApiUrl = "http://demojenkins3.southeastasia.cloudapp.azure.com:5665/restaurants/"
				+ orderRequest.getResturentId() + "/menu";

		ResturentFoodManuApiResponse restaurantMenu = template.getForObject(getRestaurantApiUrl,
				ResturentFoodManuApiResponse.class);
		System.out.println("Menu Size :: " + restaurantMenu.getData().size());

		if (restaurantMenu == null) {
			throw new OrderManagementServiceException("Resturent Menu not present", 0, 0);
		}

		List<OrderFoodInfo> listOfOrderFoodInfo = RequestBundle
				.getOrderdFoodInfoRequstBuilder(orderRequest.getFoodItems());
		System.out.println(restaurantMenu.getData().get(0));

		List<OrderFoodInfo> foodMenu = restaurantMenu.getData();
		System.out.println(
				foodMenu.get(0).getAdditionalInfo() + foodMenu.get(0).getFoodId() + foodMenu.get(0).getFoodPrice());

		double totalPrice = OrderMangementServiceUtil.totalbillableprice(listOfOrderFoodInfo, foodMenu);

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

	@RequestMapping(value = "/testorder", method = RequestMethod.POST)

	// @ResponseStatus(value = HttpStatus.CREATED)
	public String testCreateOrder(@RequestBody OrderRequest orderRequest) {

		String getRestaurantApiUrl = "http://demojenkins3.southeastasia.cloudapp.azure.com:5665/restaurants/"
				+ orderRequest.getResturentId();

		String restaurantInfo = template.getForObject(getRestaurantApiUrl, String.class);
		System.out.println(restaurantInfo);

		return restaurantInfo;

	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	// @ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel viewOder(@RequestHeader(value = "token") String token, @PathVariable int orderId) {
		ResponseStatusModel responseStatusModel = null;
		boolean isvalid = FireBaseAuthHelper.isValidToken(token);
		if (!isvalid) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		Map<String, String> info;
		try {
			info = FireBaseAuthHelper.getUserInfo(token);
		} catch (InterruptedException e) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		String emailId = info.get("email");
		// cutomerId call for customerId
		try {
			OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
			DeliveryInfo deliveryInfo = deliveryInfoService.getDeliveryInfoByDeliveryId(orderDetails.getDeliveryId());
			List<OrderFoodInfo> listOfOrderFoodInfo = orderFoodInfoService.getListOfFoodItemsOrder(orderId);
			responseStatusModel = ResponseBundle.getViewOderResponsBuilder(orderDetails, deliveryInfo,
					listOfOrderFoodInfo);
			return responseStatusModel;
		} catch (Exception e) {
			throw new OrderManagementServiceException(" data not found in records  ", orderId, 0);
		}
	}

	@RequestMapping(value = "/orders/{customerId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel viewOderHistory(@RequestHeader(value = "token") String token,
			@PathVariable int customerId) {
		boolean isvalid = FireBaseAuthHelper.isValidToken(token);
		if (!isvalid) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		Map<String, String> info;
		try {
			info = FireBaseAuthHelper.getUserInfo(token);
		} catch (InterruptedException e) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		String emailId = info.get("email");
		// cutomerId call for customerId
		ResponseStatusModel responseStatusModel = null;
		// try {
		List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByCustomerId(customerId);

		if (orderDetailsList.size() <= 0) {
			throw new OrderManagementServiceException("data not found in records", 0, customerId);
		}
		responseStatusModel = ResponseBundle.getViewAllOderResponsBuilder(orderDetailsList);
		responseStatusModel.setCustomerId(customerId);
		System.out.println("customer Id" + responseStatusModel.getCustomerId());

		return responseStatusModel;
		// } catch (Exception e) {
		// throw new OrderManagementServiceException(" data not found in records
		// ", customerId);
		// }
	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
	public ResponseStatusModel cancelOrder(@RequestHeader(value = "token") String token, @PathVariable int orderId)
			throws InterruptedException {
		//
		boolean isvalid = FireBaseAuthHelper.isValidToken(token);
		if (!isvalid) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		Map<String, String> info;
		try {
			info = FireBaseAuthHelper.getUserInfo(token);
		} catch (InterruptedException e) {
			throw new OrderManagementServiceException("not a valid token please try with valid token", 0, 0);
		}
		String emailId = info.get("email");
		// cutomerId call for customerId

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
