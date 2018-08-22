package com.mindtree.ordermanagementservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestaurantModel {
	@JsonProperty("restaurant_id")

	private String restaurantId;

	@JsonProperty("restaurant_name")

	private String restaurantName;

	@JsonProperty("restaurant_open_time")

	private String restaurantOpenTime;

	@JsonProperty("restaurant_close_time")

	private String restaurantCloseTime;

	@JsonProperty("minimum_order")

	private String minimumOrder;

	@JsonProperty("average_delivery_time")

	private String averageDeliveryTime;

	@JsonProperty("delivery_point")

	private String deliveryPoint;

	@JsonProperty("restaurant_image")

	private String restaurantImage;

	private String address;

	private String city;

	private String state;

	private String country;

	private String locality;

	@JsonProperty("locality_verbose")

	private String localityVerbose;

	private String rating;

	private String offer;

	private String latitude;

	private String longitude;

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantOpenTime() {
		return restaurantOpenTime;
	}

	public void setRestaurantOpenTime(String restaurantOpenTime) {
		this.restaurantOpenTime = restaurantOpenTime;
	}

	public String getRestaurantCloseTime() {
		return restaurantCloseTime;
	}

	public void setRestaurantCloseTime(String restaurantCloseTime) {
		this.restaurantCloseTime = restaurantCloseTime;
	}

	public String getMinimumOrder() {
		return minimumOrder;
	}

	public void setMinimumOrder(String minimumOrder) {
		this.minimumOrder = minimumOrder;
	}

	public String getAverageDeliveryTime() {
		return averageDeliveryTime;
	}

	public void setAverageDeliveryTime(String averageDeliveryTime) {
		this.averageDeliveryTime = averageDeliveryTime;
	}

	public String getDeliveryPoint() {
		return deliveryPoint;
	}

	public void setDeliveryPoint(String deliveryPoint) {
		this.deliveryPoint = deliveryPoint;
	}

	public String getRestaurantImage() {
		return restaurantImage;
	}

	public void setRestaurantImage(String restaurantImage) {
		this.restaurantImage = restaurantImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getLocalityVerbose() {
		return localityVerbose;
	}

	public void setLocalityVerbose(String localityVerbose) {
		this.localityVerbose = localityVerbose;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
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
