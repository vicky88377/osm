package com.mindtree.ordermanagementservice.service.imp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.repository.OrderFoodInfoRpository;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;

@RunWith(SpringRunner.class)
public class OrderFoodInfoServiceImplTest {

	@TestConfiguration
	static class OrderFoodInfoServiceImplTestContextConfiguration {

		@Bean
		public OrderFoodInfoService orderFoodInfoService() {
			return new OrderFoodInfoServiceImpl();
		}
	}

	@Autowired
	private OrderFoodInfoService orderFoodInfoService;

	@MockBean
	private OrderFoodInfoRpository orderFoodInfoRpository;

	OrderFoodInfo orderFoodInfo1;
	OrderFoodInfo orderFoodInfo2;

	@Before
	public void Setup() {
		orderFoodInfo1 = new OrderFoodInfo();
		orderFoodInfo1.setFoodId(1);
		orderFoodInfo1.setFoodPrice(200);
		orderFoodInfo1.setOrderFoodId(10);
		orderFoodInfo1.setQuantity(2);
		orderFoodInfo1.setOrderId(1);
		orderFoodInfo1.setOrderFoodId(10);
		orderFoodInfo1.setAdditionalInfo("best food");
		orderFoodInfo2 = new OrderFoodInfo();
		orderFoodInfo2.setFoodId(1);
		orderFoodInfo2.setFoodPrice(200);
		orderFoodInfo2.setOrderFoodId(10);
		orderFoodInfo2.setQuantity(2);
		orderFoodInfo2.setOrderId(1);
		orderFoodInfo2.setAdditionalInfo("food");
		List<OrderFoodInfo> listOfFoodIteam = new ArrayList<OrderFoodInfo>();
		listOfFoodIteam.add(orderFoodInfo1);
		listOfFoodIteam.add(orderFoodInfo2);
		int orderId = 1;
		Mockito.when(orderFoodInfoRpository.findOrderFoodInfosByOrderId(orderId)).thenReturn(listOfFoodIteam);
		Mockito.when(orderFoodInfoRpository.save(orderFoodInfo1)).thenReturn(orderFoodInfo2);
	}

	@Test
	public void getListOfFoodItemsOrderTest() {
		int id = 1;
		List<OrderFoodInfo> listOfFoodIteam = orderFoodInfoService.getListOfFoodItemsOrder(id);
		assertThat(listOfFoodIteam.size() == 2);
	}

	@Test
	public void createTest() {
		OrderFoodInfo savedOrderFoodInfo = orderFoodInfoService.create(orderFoodInfo1);
		assertThat(savedOrderFoodInfo.getQuantity() == 2);
		assertThat(savedOrderFoodInfo.getFoodId() == 1);
		assertThat(savedOrderFoodInfo.getOrderFoodId() == 10);
	}
}
