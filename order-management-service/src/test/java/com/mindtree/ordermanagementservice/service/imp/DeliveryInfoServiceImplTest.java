package com.mindtree.ordermanagementservice.service.imp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.repository.DeliveryInfoRepository;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;

@RunWith(SpringRunner.class)
public class DeliveryInfoServiceImplTest {
	@TestConfiguration
	static class DeliveryInfoServiceImplTestContextConfiguration {

		@Bean
		public DeliveryInfoService deliveryInfoService() {
			return new DeliveryInfoServiceImpl();
		}
	}

	@Autowired
	private DeliveryInfoService deliveryInfoService;

	@MockBean
	private DeliveryInfoRepository deliveryInfoRepository;

	DeliveryInfo deliveryInfo1;
	DeliveryInfo deliveryInfo2;

	@Before
	public void Setup() {
		deliveryInfo1 = new DeliveryInfo();
		deliveryInfo1.setDeliveryAddress("btm");
		deliveryInfo1.setDeliveryBoyContactNumber(809928272);
		deliveryInfo1.setDeliveryBoyName("raju");
		deliveryInfo1.setDeliveryId(89);
		deliveryInfo1.setDeliveryStatus("C");
		deliveryInfo1.setLatitude("24345");
		deliveryInfo1.setLongitude("sj2q23");

		deliveryInfo2 = new DeliveryInfo();
		deliveryInfo2.setDeliveryAddress("btm");
		deliveryInfo2.setDeliveryBoyContactNumber(809928272);
		deliveryInfo2.setDeliveryBoyName("raju");
		deliveryInfo2.setDeliveryId(89);
		deliveryInfo2.setDeliveryStatus("C");
		deliveryInfo2.setLatitude("24345");
		deliveryInfo2.setLongitude("sj2q23");

		int deliveryrId = 89;
		Mockito.when(deliveryInfoRepository.getOne(deliveryrId)).thenReturn(deliveryInfo1);
		Mockito.when(deliveryInfoRepository.save(deliveryInfo1)).thenReturn(deliveryInfo2);
	}

	@Test
	public void getDeliveryInfoByDeliveryIdTest() {
		int id = 89;
		DeliveryInfo deliveryInfo2 = deliveryInfoService.getDeliveryInfoByDeliveryId(id);
		assertThat(deliveryInfo2.getDeliveryId() == 89);
	}

	@Test
	public void createTest() {
		DeliveryInfo deliveryInfo = deliveryInfoService.create(deliveryInfo1);
		assertThat(deliveryInfo.getDeliveryStatus().equals("C")) ;
		assertThat(deliveryInfo.getDeliveryId() == 89);
	}
}
