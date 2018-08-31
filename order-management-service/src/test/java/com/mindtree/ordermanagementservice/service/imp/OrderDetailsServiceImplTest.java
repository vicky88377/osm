package com.mindtree.ordermanagementservice.service.imp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.repository.OrderDetailsRepository;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;

@RunWith(SpringRunner.class)
public class OrderDetailsServiceImplTest {

	@TestConfiguration
	static class OrderDetailsServiceImplTestContextConfiguration {

		@Bean
		public OrderDetailsService orderDetailsService() {
			return new OrderDetailsServiceImpl();
		}
	}

	@Autowired
	private OrderDetailsService orderDetailsService;

	@MockBean
	private OrderDetailsRepository orderDetailsRepository;

	OrderDetails orderDetails1;
	OrderDetails orderDetails2;
	OrderDetails orderDetails3;

	@Before
	public void Setup() {
		orderDetails1 = new OrderDetails();
		orderDetails1.setCustomerId(2);
		orderDetails1.setDeliveryId(2);
		orderDetails1.setOrderId(2);
		orderDetails1.setOrderStatus("P");
		orderDetails1.setOrdredDate(new Date());
		orderDetails1.setRestaurentId(11);
		orderDetails1.setTotalPrice(2000);
		// ================================
		orderDetails2 = new OrderDetails();
		orderDetails2.setCustomerId(2);
		orderDetails2.setDeliveryId(2);
		orderDetails2.setOrderId(2);
		orderDetails2.setOrderStatus("P");
		orderDetails2.setOrdredDate(new Date());
		orderDetails2.setRestaurentId(11);
		orderDetails2.setTotalPrice(2000);
		// ===================================
		orderDetails3 = new OrderDetails();
		orderDetails3.setCustomerId(2);
		orderDetails3.setDeliveryId(2);
		orderDetails3.setOrderId(2);
		orderDetails3.setOrderStatus("C");
		orderDetails3.setOrdredDate(new Date());
		orderDetails3.setRestaurentId(11);
		orderDetails3.setTotalPrice(2000);
		Optional<OrderDetails> optional = Optional.of(orderDetails2);
		Optional<OrderDetails> ortional1 = Optional.empty();
		List<OrderDetails> listOrderDetails = new ArrayList<OrderDetails>();
		listOrderDetails.add(orderDetails1);
		listOrderDetails.add(orderDetails2);
		listOrderDetails.add(orderDetails3);
		int orderId = 2;
		int customerId = 2;
		int orderIdNotInDb = 5;
		Mockito.when(orderDetailsRepository.findById(orderId)).thenReturn(optional);
		Mockito.when(orderDetailsRepository.findById(orderIdNotInDb)).thenReturn(ortional1);

		Mockito.when(orderDetailsRepository.save(Mockito.anyObject())).thenReturn(orderDetails1);
		Mockito.when(orderDetailsRepository.findOrderFoodInfosByCustomerId(customerId)).thenReturn(listOrderDetails);
		Mockito.when(orderDetailsRepository.getOne(orderId)).thenReturn(orderDetails3);

	}

	@Test
	public void cancelOrderTest() {
		int id = 2;
		OrderDetails orderDetails = orderDetailsService.cancelOrder(id);
		assertThat(orderDetails.getOrderStatus().equals("C"));
	}

	@Test
	public void createTest() {
		OrderDetails orderDetails = orderDetailsService.create(orderDetails1);
		assertThat(orderDetails.getCustomerId() == 1);
		assertThat(orderDetails.getDeliveryId() == 2);
		assertThat(orderDetails.getOrderId() == 2);
		assertThat(orderDetails.getOrderStatus().equals("P"));

	}

	@Test
	public void getOrderDetailsByOrderIdTest() {
		int id = 2;
		OrderDetails orderDetails = orderDetailsService.getOrderDetailsByOrderId(id);
		assertThat(orderDetails.getOrderStatus().equals("C"));
		assertThat(orderDetails.getOrderId() == id);
	}

	@Test
	public void updateTest() {
		int id = 2;
		String status = "D";
		OrderDetails orderDetails = orderDetailsService.update(id, status);
		System.out.println(orderDetails == null);
		assertThat(orderDetails.getOrderStatus().equals("D"));
		assertThat(orderDetails.getOrderId() == id);
	}

	// @Test(expected = OrderManagementServiceException.class)
	// public void updateFailureTest() {
	// int id = 5;
	// String status = "D";
	// OrderDetails orderDetails = orderDetailsService.update(id, status);
	// assertThat(orderDetails.getOrderStatus().equals("D"));
	// assertThat(orderDetails.getOrderId() == id);
	// }

	@Test
	public void getOrderDetailsByCustomerIdTest() {
		int customerId = 2;
		List<OrderDetails> orderDetailsList = orderDetailsService.getOrderDetailsByCustomerId(customerId);
		assertThat(orderDetailsList.size() == 3);
	}

	// Cancel service Negative case
	@Test(expected = OrderManagementServiceException.class)
	public void cancelOrderTestNegative() {
		int id = 5;
		OrderDetails orderDetails = orderDetailsService.cancelOrder(id);
		// assertThat(orderDetails.getOrderStatus().equals("C"));
	}

}
