package com.mindtree.ordermanagementservice.exception;

public class OrderManagementServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;
	private int orderId;
	private int customerId;

	public OrderManagementServiceException(String message, int orderId, int customerId) {
		this.message = message;
		this.orderId = orderId;
		this.customerId = customerId;
	}

	public String getMessage() {
		return message;
	}

	public OrderManagementServiceException() {
		super();

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getCustomerId() {
		return customerId;
	}
}
