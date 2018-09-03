package com.mindtree.ordermanagementservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mindtree.ordermanagementservice.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
	
	List<OrderDetails> findOrderFoodInfosByCustomerId(@Param("customerId") int customerId);

}
