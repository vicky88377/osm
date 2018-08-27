package com.mindtree.ordermanagementservice.service;

import com.mindtree.ordermanagementservice.model.DeliveryInfo;

public interface DeliveryInfoService {
	public DeliveryInfo create(DeliveryInfo deliveryInfo);

	public DeliveryInfo getDeliveryInfoByDeliveryId(int deliveryId);
}
