package com.mindtree.ordermanagementservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.ordermanagementservice.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {

}
