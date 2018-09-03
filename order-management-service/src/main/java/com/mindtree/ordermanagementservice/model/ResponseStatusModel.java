package com.mindtree.ordermanagementservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ResponseStatusModel {

	private int statusCode;

	private String status;
	@JsonProperty(required = false)
	@JsonIgnore
	private Integer orderId;
	@JsonProperty(required = false)
	private String message;
	private List data;

	@JsonProperty(required = false)
	private DeliveryInfo deliveryInfo;

	// @JsonIgnore
	private Integer customerId;
	@JsonIgnore
	private String orderStatus;

	public ResponseStatusModel() {
		super();
	}

	public ResponseStatusModel(int statusCode, String status, Object data, String message, int orderId,int customerId) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
		this.orderId = orderId;
		this.customerId=customerId;
	}

	@JsonProperty(required = false)

	/**
	 * 
	 * @return the statusCode
	 * 
	 */

	public int getStatusCode() {

		return statusCode;

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

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}



	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}