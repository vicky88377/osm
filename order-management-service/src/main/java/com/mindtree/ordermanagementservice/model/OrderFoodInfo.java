package com.mindtree.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderFoodInfo {
	@Id
	@GeneratedValue
	private int orderFoodId;
	private int quantity;
	private String additionalInfo;
	private int foodId;
	private int orderId;
	private float foodPrice;

	public int getOrderFoodId() {
		return orderFoodId;
	}

	public void setOrderFoodId(int orderFoodId) {
		this.orderFoodId = orderFoodId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public float getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(float foodPrice) {
		this.foodPrice = foodPrice;
	}

}
