package com.mindtree.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class OrderFoodInfo {
	@Id
	@GeneratedValue
	@JsonIgnore
	private int orderFoodId;
	private int quantity;
	private String additionalInfo;
	@JsonProperty("foodId")
	private int foodId;
	@JsonIgnore
	private int orderId;
	@JsonProperty("food_price")
	private double foodPrice;

	// // @Transient
	// private String descrption;
	// // @Transient
	// private String foodName;
	// // @Transient
	// @JsonProperty("availability_status")
	//
	// private String availabilityStatus;
	// // @Transient
	// @JsonProperty("restaurant_id")
	//
	// private String restaurantId;
	// // @Transient
	// private String cuisinesId;

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

	// public String getDescrption() {
	// return descrption;
	// }
	//
	// public void setDescrption(String descrption) {
	// this.descrption = descrption;
	// }
	//
	// public String getFoodName() {
	// return foodName;
	// }
	//
	// public void setFoodName(String foodName) {
	// this.foodName = foodName;
	// }
	//
	// public String getAvailabilityStatus() {
	// return availabilityStatus;
	// }
	//
	// public void setAvailabilityStatus(String availabilityStatus) {
	// this.availabilityStatus = availabilityStatus;
	// }
	//
	// public String getRestaurantId() {
	// return restaurantId;
	// }
	//
	// public void setRestaurantId(String restaurantId) {
	// this.restaurantId = restaurantId;
	// }
	//
	// public String getCuisinesId() {
	// return cuisinesId;
	// }
	//
	// public void setCuisinesId(String cuisinesId) {
	// this.cuisinesId = cuisinesId;
	// }

}
