package com.mindtree.ordermanagementservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDetailsResponse {

	@JsonProperty("customer_id")
	private int id;
	@JsonProperty("customer_name")
	private String name;
	@JsonProperty("email_id")
	private String emailId;
	@JsonProperty("customer_pincode")
	private int pinCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getPinCode() {
		return pinCode;
	}
	public void setPinCode(int pinCode) {
		this.pinCode = pinCode;
	}

	
}
