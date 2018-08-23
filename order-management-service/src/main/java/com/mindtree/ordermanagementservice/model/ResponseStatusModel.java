package com.mindtree.ordermanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseStatusModel {


	private int statusCode;
	private String status;
	private Object[] data;
	@JsonProperty(required = false)
	private String message;

	public ResponseStatusModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonProperty(required = false)
	private int orderId;

	/**
	 * 
	 * @return the statusCode
	 * 
	 */

	public int getStatusCode() {

		return statusCode;

	}

	public ResponseStatusModel(int statusCode, String status, String message, Object[] data,int orderId) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.data = data;
		this.orderId = orderId;
	}

	/**
	 * 
	 * @param statusCode
	 * 
	 *            the statusCode to set
	 * 
	 */

	public void setStatusCode(int statusCode) {

		this.statusCode = statusCode;

	}

	/**
	 * 
	 * @return the status
	 * 
	 */

	public String getStatus() {

		return status;

	}

	/**
	 * 
	 * @param status
	 * 
	 *            the status to set
	 * 
	 */

	public void setStatus(String status) {

		this.status = status;

	}

	/**
	 * 
	 * @return the message
	 * 
	 */

	public String getMessage() {

		return message;

	}

	/**
	 * 
	 * @param message
	 * 
	 *            the message to set
	 * 
	 */

	public void setMessage(String message) {

		this.message = message;

	}

	/**
	 * 
	 * @return the data
	 * 
	 */

	public Object[] getData() {

		return data;

	}

	/**
	 * 
	 * @param data
	 * 
	 *            the data to set
	 * 
	 */

	public void setData(Object[] data) {

		this.data = data;

	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}