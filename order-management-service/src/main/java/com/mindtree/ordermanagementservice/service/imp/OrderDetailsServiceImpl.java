package com.mindtree.ordermanagementservice.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.repository.OrderDetailsRepository;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.util.OrderMangementServiceUtil;

@Service(value = "OrderDetailsService")
public class OrderDetailsServiceImpl implements OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Override
	public OrderDetails create(OrderDetails orderDetails) {
		OrderDetails saveOrderDetails = orderDetailsRepository.save(orderDetails);
		System.out.println("Save Order  ::" + saveOrderDetails.getOrderId());
		return saveOrderDetails;
	}

	@Override
	public OrderDetails cancelOrder(int orderId) {
		OrderDetails order = null;
		System.out.println("Inside service method ::" + orderId);
		Optional<OrderDetails> orderDetail = orderDetailsRepository.findById(orderId);

		if (orderDetail.isPresent()) {
			order = orderDetail.get();

			if (order.getOrderStatus().equals("P")) {
				if (OrderMangementServiceUtil.getTimeDifference(order.getOrderDate()) >= 10) {
					throw new OrderManagementServiceException("Order can not be canceled after 10 mins of order placed",
							order.getOrderId());
				}
				order.setOrderStatus("C");
				return orderDetailsRepository.save(order);
			} else if (order.getOrderStatus().equals("D")) {
				throw new OrderManagementServiceException(
						"Order already delivered [we can't cancel the order once its delivered]", order.getOrderId());
			} else if (order.getOrderStatus().equals("C")) {
				throw new OrderManagementServiceException("Can't cancel the order as it is already been canceled",
						order.getOrderId());
			}

		} else {
			throw new OrderManagementServiceException("No Order present with requested order Id", orderId);
		}
		// OrderDetails orderDetail =
		// orderDetailsRepository.findById(orderId).get();
		return order;
	}

	@Override
	public OrderDetails getOrderDetailsByOrderId(int orderId) {
		OrderDetails orderDetails = orderDetailsRepository.getOne(orderId);
		return orderDetails;
	}

	@Override
	public List<OrderDetails> getOrderDetailsByCustomerId(int customerId) {
		List<OrderDetails> listOfOrderDetails = orderDetailsRepository.findOrderFoodInfosByCustomerId(customerId);
		return listOfOrderDetails;
	}

}
