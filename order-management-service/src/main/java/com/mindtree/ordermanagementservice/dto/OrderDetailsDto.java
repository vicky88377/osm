package com.mindtree.ordermanagementservice.dto;

import java.util.Date;

public class OrderDetailsDto {

	private double totalPrice;
	private Date ordredDate;
	private String orderStatus;
	private int orderId;

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrdredDate() {
		return ordredDate;
	}

	public void setOrdredDate(Date ordredDate) {
		this.ordredDate = ordredDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
