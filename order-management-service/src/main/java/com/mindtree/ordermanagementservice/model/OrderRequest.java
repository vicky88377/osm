package com.mindtree.ordermanagementservice.model;

import java.util.List;

public class OrderRequest {
	private int resturentId;
	List<OrderFoodInfo> foodItems;
	private Address address;

	public int getResturentId() {
		return resturentId;
	}

	public void setResturentId(int resturentId) {
		this.resturentId = resturentId;
	}

	public List<OrderFoodInfo> getFoodItems() {
		return foodItems;
	}

	public void setFoodItems(List<OrderFoodInfo> foodItems) {
		this.foodItems = foodItems;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
