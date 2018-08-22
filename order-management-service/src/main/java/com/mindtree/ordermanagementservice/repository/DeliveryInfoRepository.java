package com.mindtree.ordermanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.ordermanagementservice.model.DeliveryInfo;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Integer> {

}
