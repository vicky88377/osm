package com.mindtree.ordermanagementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.ordermanagementservice.model.OrderFoodInfo;

public interface OrderFoodInfoRpository extends JpaRepository<OrderFoodInfo, Integer> {
	List<OrderFoodInfo> findOrderFoodInfosByOrderId(int orderId);

}
