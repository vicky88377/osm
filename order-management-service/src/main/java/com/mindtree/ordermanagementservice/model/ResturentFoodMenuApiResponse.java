package com.mindtree.ordermanagementservice.model;

import java.util.List;

import com.mindtree.ordermanagementservice.dto.ResturantMenuDto;

public class ResturentFoodMenuApiResponse {

	
	private int statusCode;

	private String status;
	
	private String message;
	
	private List<ResturantMenuDto> data;


	public ResturentFoodMenuApiResponse() {
		super();
	}

	public ResturentFoodMenuApiResponse(int statusCode, String status, Object data, String message, int orderId,
			int customerId) {
		super();
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ResturantMenuDto> getData() {
		return data;
	}

	public void setData(List<ResturantMenuDto> data) {
		this.data = data;
	}
}
