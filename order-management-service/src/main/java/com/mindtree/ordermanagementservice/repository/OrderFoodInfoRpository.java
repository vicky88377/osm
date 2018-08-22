package com.mindtree.ordermanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.ordermanagementservice.model.OrderFoodInfo;

public interface OrderFoodInfoRpository extends JpaRepository<OrderFoodInfo, Integer> {

}
