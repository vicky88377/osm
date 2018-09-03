package com.mindtree.ordermanagementservice.dto;

import java.util.List;

public class OrderRequest {
	private DeliveryAddress deliveryaddress;
	private CustomerDetailsDto customerDetails;
	private int resturentId;
	private String paymentMode;
	List<OrderFoodInfoDto> foodItems;

	public int getResturentId() {
		return resturentId;
	}

	public void setResturentId(int resturentId) {
		this.resturentId = resturentId;
	}

	public List<OrderFoodInfoDto> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<OrderFoodInfoDto> foodItems) {
		this.foodItems = foodItems;
	}

	public DeliveryAddress getDeliveryaddress() {
		return deliveryaddress;
	}

	public void setDeliveryaddress(DeliveryAddress deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}

	public CustomerDetailsDto getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetailsDto customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

}
