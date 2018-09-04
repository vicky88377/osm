package com.mindtree.ordermanagementservice.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.mindtree.ordermanagementservice.bundle.RequestMapper;
import com.mindtree.ordermanagementservice.bundle.ResponseMapper;
import com.mindtree.ordermanagementservice.dto.OrderRequest;
import com.mindtree.ordermanagementservice.dto.ResturantMenuDto;
import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;
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
	OrderMangementServiceUtil orderMangementServiceUtil;

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel createOrder(@RequestHeader(value = "token") String token,
			@RequestBody OrderRequest orderRequest) {

		// validate token
		validateToken(token);

		// Call Restaurant Api to validate the address
		if (!orderMangementServiceUtil.getRestaurantAddress(orderRequest.getResturentId(),
				orderRequest.getDeliveryaddress().getLatitude(), orderRequest.getDeliveryaddress().getLongitude())) {
			throw new OrderManagementServiceException("Resturent not provide the delivery for the given address");
		}

		/* Call Customer Details Api to get the user Id */

		int customerId = orderMangementServiceUtil.getCustomerDetails(token);

		if (!(customerId == orderRequest.getCustomerDetails().getId())) {
			throw new OrderManagementServiceException("Customer Id mismatch");
		}

		// Call Restaurant Api to get the Menu Details
		List<ResturantMenuDto> foodMenu = orderMangementServiceUtil.getRestaurantMenu(orderRequest.getResturentId());

		if (foodMenu.isEmpty()) {
			throw new OrderManagementServiceException("No food available in Resturent");
		}

		// list of customer order food
		List<OrderFoodInfo> listOfOrderFoodInfo = RequestMapper
				.getOrderdFoodInfoRequstBuilder(orderRequest.getFoodItems());

		//Calculate bill amount
		double totalPrice = orderMangementServiceUtil.totalbillableprice(listOfOrderFoodInfo, foodMenu);

		//Insert record into the database
		OrderDetails savedOrderDetails = orderMangementServiceUtil.saveRecord(orderRequest, totalPrice,
				listOfOrderFoodInfo);
		if (savedOrderDetails == null) {
			throw new OrderManagementServiceException("failed to save record");
		}

		return createResponse(savedOrderDetails.getOrderId(), "order will reach you within 45 minutes");

	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public ResponseStatusModel viewOder(@RequestHeader(value = "token") String token, @PathVariable int orderId) {
		ResponseStatusModel responseStatusModel = null;

		// validate token
		validateToken(token);
		/* Call Customer Details Api to get the user Id */
		int customerId = orderMangementServiceUtil.getCustomerDetails(token);

		OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
		
		if (!(customerId == orderDetails.getCustomerId())) {
			throw new OrderManagementServiceException("Customer Id mismatch");
		}

		DeliveryInfo deliveryInfo = deliveryInfoService.getDeliveryInfoByDeliveryId(orderDetails.getDeliveryId());
		List<OrderFoodInfo> listOfOrderFoodInfo = orderFoodInfoService.getListOfFoodItemsOrder(orderId);
		responseStatusModel = ResponseMapper.getViewOderResponsBuilder(orderDetails, deliveryInfo, listOfOrderFoodInfo);
		return responseStatusModel;
	}

	@RequestMapping(value = "/orders/{customerId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseStatusModel viewOderHistory(@RequestHeader(value = "token") String token,
			@PathVariable int customerId) {

		// validate token
		validateToken(token);

		/* Call Customer Details Api to get the user Id */

		int customerid = orderMangementServiceUtil.getCustomerDetails(token);

		if (!(customerid == customerId)) {
			throw new OrderManagementServiceException("Customer Id mismatch");
		}

		ResponseStatusModel responseStatusModel = null;
		// try {
		List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByCustomerId(customerId);

		if (orderDetailsList.size() <= 0) {
			throw new OrderManagementServiceException(customerId, "data not found in records");
		}
		responseStatusModel = ResponseMapper.getViewAllOderResponsBuilder(orderDetailsList);
		responseStatusModel.setCustomerId(customerId);

		return responseStatusModel;

	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
	public ResponseStatusModel cancelOrder(@RequestHeader(value = "token") String token, @PathVariable int orderId)
			throws InterruptedException {
		// validate token
		validateToken(token);
		int customerId = orderMangementServiceUtil.getCustomerDetails(token);
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
		if (customerId != orderDetails.getCustomerId()) {
			throw new OrderManagementServiceException("Customer Id mismatch");
		}

		OrderDetails orderDetail = orderDetailsService.cancelOrder(orderId);

		return createResponse(orderDetail.getOrderId(), "order has been canceled");
	}

	@RequestMapping(value = "/orders/{orderId}/{orderStatus}", method = RequestMethod.PUT)
	public ResponseStatusModel updateOrderStatus(@RequestHeader(value = "token") String token,
			@PathVariable int orderId, @PathVariable String orderStatus) {

		// validate token
		validateToken(token);
		int customerId = orderMangementServiceUtil.getCustomerDetails(token);
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
		if (customerId != orderDetails.getCustomerId()) {
			throw new OrderManagementServiceException("Customer Id mismatch");
		}

		OrderDetails orderDetail = orderDetailsService.update(orderId, orderStatus);
		return createResponse(orderDetail.getOrderId(), "order has been updated");

	}

	private ResponseStatusModel createResponse(int orderId, String message) {
		ResponseStatusModel responseStatusModel = new ResponseStatusModel();
		responseStatusModel.setStatusCode(200);
		responseStatusModel.setStatus("success");
		responseStatusModel.setMessage(message);
		responseStatusModel.setOrderId(orderId);
		return responseStatusModel;
	}

	private void validateToken(String token) {
		boolean isvalid = FireBaseAuthHelper.isValidToken(token);
		if (!isvalid) {
			throw new OrderManagementServiceException("not a valid token please try with valid token");
		}
	}
}
