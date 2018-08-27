package com.mindtree.ordermanagementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.ordermanagementservice.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
	List<OrderDetails> findOrderFoodInfosByCustomerId(int orderId);

}
