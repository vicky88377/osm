package com.mindtree.ordermanagementservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DeliveryInfo {
	@Id
	@GeneratedValue
	private int id;
	private String deliveryBoyName;
	private long deliveryBoyContactNumber;
	private String deliveryAddress;
	private int deliveryStatus;
	private String latitude;

	private String longitude;

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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
