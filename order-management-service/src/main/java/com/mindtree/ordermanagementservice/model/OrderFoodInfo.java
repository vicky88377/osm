package com.mindtree.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OrderFoodInfo {
	@Id
	@GeneratedValue
	@JsonIgnore
	private int orderFoodId;
	private int quantity;
	private String additionalInfo;
	private int foodId;
	@JsonIgnore
	private int orderId;
	private double foodPrice;
	

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

	public double getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}

	
}
