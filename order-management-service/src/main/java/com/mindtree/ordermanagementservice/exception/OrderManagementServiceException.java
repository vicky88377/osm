package com.mindtree.ordermanagementservice.exception;

public class OrderManagementServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;

	public OrderManagementServiceException(String message) {
		super(message);

	}

	public OrderManagementServiceException() {
		super();

	}

	public String getMessage() {
		return message;
	}

}
