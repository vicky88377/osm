package com.mindtree.ordermanagementservice.exception;

public class OrderManagementServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	//private String message;
	String message;
	int orderId;
	public OrderManagementServiceException(String message,int orderId) {
		//super(message);
		this.message= message;
		this.orderId = orderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public OrderManagementServiceException() {
		super();

	}
}
