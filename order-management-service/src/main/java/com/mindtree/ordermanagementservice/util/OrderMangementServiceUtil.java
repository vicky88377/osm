package com.mindtree.ordermanagementservice.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mindtree.ordermanagementservice.bundle.RequestMapper;
import com.mindtree.ordermanagementservice.dto.OrderRequest;
import com.mindtree.ordermanagementservice.dto.ResturantMenuDto;
import com.mindtree.ordermanagementservice.model.AddressResponse;
import com.mindtree.ordermanagementservice.model.CustomerDetailsResponse;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.ResturentFoodMenuApiResponse;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;

@Component
public class OrderMangementServiceUtil {

	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	OrderFoodInfoService orderFoodInfoService;
	@Autowired
	DeliveryInfoService deliveryInfoService;
	@Autowired
	private RestTemplate template;

	@Value("${restaurant.service.url}")
	private String restaurantAddressUrl;

	@Value("${customer.detail.service}")
	private String customerDetailUrl;

	public static long getTimeDifference(Date givenDate) {
		Date date = new Date();

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(givenDate);
		c2.setTime(date);

		long sec = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000;
		long min = sec / 60;

		return min;
	}

	public double totalbillableprice(List<OrderFoodInfo> customerOrderFoodList, List<ResturantMenuDto> foodMenu) {

		Map<Integer, Double> foodIdToFoodPrice = null;

		double totalPrice = 0.0;

		if (foodMenu != null && customerOrderFoodList != null) {
			for (ResturantMenuDto menuItem : foodMenu) {
				if (foodIdToFoodPrice == null) {
					foodIdToFoodPrice = new HashMap<Integer, Double>();
					foodIdToFoodPrice.put(menuItem.getFoodId(), menuItem.getFoodPrice());

				}
				foodIdToFoodPrice.put(menuItem.getFoodId(), menuItem.getFoodPrice());
			}

			for (OrderFoodInfo customerOrderFood : customerOrderFoodList) {
				if (customerOrderFood != null) {

					double price = foodIdToFoodPrice.get(customerOrderFood.getFoodId());
					customerOrderFood.setFoodPrice(price);
					totalPrice = totalPrice + (price * customerOrderFood.getQuantity());

				}
			}

		}
		System.out.println("price" + totalPrice);

		return totalPrice;
	}

	public int getCustomerDetails(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		System.out.println("URL :: " + customerDetailUrl);
		HttpEntity http = new HttpEntity<>(headers);
		ResponseEntity<CustomerDetailsResponse> resp = template.exchange(customerDetailUrl, HttpMethod.GET, http,
				CustomerDetailsResponse.class);

		System.out.println("URL :: " + resp.getStatusCode());
		int customerId = 0;

		if (resp.getStatusCode() == HttpStatus.OK) {
			customerId = resp.getBody().getId();

		}
		return customerId;
	}

	public boolean getRestaurantAddress(int restaurantId, String latitude, String longitude) {

		System.out.println("URL :: " + restaurantAddressUrl + restaurantId + "/validate/" + latitude + "/" + longitude);

		ResponseEntity<AddressResponse> resp = template.getForEntity(
				restaurantAddressUrl + restaurantId + "/validate/" + latitude + "/" + longitude, AddressResponse.class);

		if (resp.getStatusCode() == HttpStatus.OK) {
			return resp.getBody().getResult();
		}
		return false;
	}

	public List<ResturantMenuDto> getRestaurantMenu(int restaurantId) {

		ResponseEntity<ResturentFoodMenuApiResponse> resp = template
				.getForEntity(restaurantAddressUrl + restaurantId + "/menu", ResturentFoodMenuApiResponse.class);

		if (resp.getStatusCode() == HttpStatus.OK) {
			ResturentFoodMenuApiResponse body = resp.getBody();
			if (body.getData() != null) {
				return body.getData();
			}
		}

		return null;
	}

	public OrderDetails saveRecord(OrderRequest orderRequest, double totalPrice,
			List<OrderFoodInfo> listOfOrderFoodInfo) {

		DeliveryInfo deliveryInfo = RequestMapper.deliveryInfoRequstBuilder(orderRequest);
		DeliveryInfo savedDeliveryInfo = deliveryInfoService.create(deliveryInfo);

		// create OrderDetails Records
		OrderDetails orderDetails = RequestMapper.orderDetailsRequstBuilder(savedDeliveryInfo, orderRequest,
				totalPrice);
		OrderDetails savedOrderDetails = orderDetailsService.create(orderDetails);

		for (OrderFoodInfo orderFoodInfo : listOfOrderFoodInfo) {
			orderFoodInfo.setOrderId(savedOrderDetails.getOrderId());
			orderFoodInfoService.create(orderFoodInfo);
		}
		return savedOrderDetails;
	}

}
