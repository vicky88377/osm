package com.mindtree.ordermanagementservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="delivery_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class DeliveryInfo {
	@Id
	@Column(name="delivery_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int deliveryId;
	@Column(name="delivery_boy_name")
	private String deliveryBoyName;  
	@Column(name="delivery_boy_contact_number")
	private long deliveryBoyContactNumber;
	@Column(name="delivery_address")
	private String deliveryAddress;
	@Column(name="delivery_status")
	private String deliveryStatus;
	@Column(name="latitude")
	private String latitude;
	@Column(name="longitude")
	private String longitude;



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

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
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

	public int getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

}
