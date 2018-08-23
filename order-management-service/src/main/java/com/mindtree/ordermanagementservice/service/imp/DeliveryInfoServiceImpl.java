package com.mindtree.ordermanagementservice.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.repository.DeliveryInfoRepository;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;

@Service(value = "DeliveryInfoService")
public class DeliveryInfoServiceImpl implements DeliveryInfoService {

	@Autowired
	DeliveryInfoRepository deliveryInfoRepository;

	@Override
	public DeliveryInfo create(DeliveryInfo deliveryInfo) {
		DeliveryInfo savedDeliveryInfo = deliveryInfoRepository.save(deliveryInfo);
		return savedDeliveryInfo;
	}

}
