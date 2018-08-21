package com.mindtree.ordermanagementservice.model;

public class DeliveryInfo {
	private int id;
	private String deliveryBoyName;
	private long deliveryBoyContactNumber;
	private String deliveryAddress;
	private int deliveryStatus;
	private int pincode;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeliveryBoyName() {
		return deliveryBoyName;
	}

	public void setDeliveryBoyName(String deliveryBoyName) {
		this.deliveryBoyName = deliveryBoyName;
	}

	public long getDeliveryBoyContactNumber() {
		return deliveryBoyContactNumber;
	}

	public void setDeliveryBoyContactNumber(long deliveryBoyContactNumber) {
		this.deliveryBoyContactNumber = deliveryBoyContactNumber;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public int getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(int deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	

}
