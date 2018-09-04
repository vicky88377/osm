package com.mindtree.ordermanagementservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import com.mindtree.ordermanagementservice.api.OrderManagementRestApi;
import com.mindtree.ordermanagementservice.dto.OrderFoodInfoDto;
import com.mindtree.ordermanagementservice.exception.GlobleExceptionHandler;
import com.mindtree.ordermanagementservice.exception.OrderManagementServiceException;
import com.mindtree.ordermanagementservice.model.DeliveryInfo;
import com.mindtree.ordermanagementservice.model.OrderDetails;
import com.mindtree.ordermanagementservice.model.OrderFoodInfo;
import com.mindtree.ordermanagementservice.model.ResponseStatusModel;
import com.mindtree.ordermanagementservice.repository.OrderDetailsRepository;
import com.mindtree.ordermanagementservice.service.DeliveryInfoService;
import com.mindtree.ordermanagementservice.service.OrderDetailsService;
import com.mindtree.ordermanagementservice.service.OrderFoodInfoService;
import com.mindtree.ordermanagementservice.util.OrderMangementServiceUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderManagementServiceApplicationTests {

	
	private MockMvc mockMvc;
	
	
	OrderDetails orderDetails;
	DeliveryInfo deliveryInfo;
	OrderFoodInfo orderFoodInfo;
	List<OrderFoodInfo> listOrderFoodInfo;
	List<OrderDetails> listOrderDetails;
	Date date;
	
	@InjectMocks
	OrderManagementRestApi mockOrderManagementRestApi;

	@Mock
	OrderDetailsService mockOrderDetailsService;
	@Mock
	DeliveryInfoService mockDeliveryInfoService;
	@Mock
	OrderFoodInfoService orderFoodInfoService;
	
	OrderDetailsRepository mockOrderDetailRepository;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(mockOrderManagementRestApi).build();
		
	}
	@Before
	public void Setup() throws ParseException {
		
		SimpleDateFormat  inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		date = inputFormatter.parse("2018-08-28T13:11:34.000+0000");
		
		
		orderDetails = new OrderDetails();
		orderDetails.setCustomerId(1);
		orderDetails.setDeliveryId(1);
		orderDetails.setOrderId(1);
		orderDetails.setOrderStatus("P");
		orderDetails.setOrdredDate(date);
		orderDetails.setRestaurentId(101);
		
		deliveryInfo = new DeliveryInfo();
		deliveryInfo.setDeliveryAddress("BTM");
		deliveryInfo.setDeliveryBoyContactNumber(8147892042L);
		deliveryInfo.setDeliveryBoyName("Kumar");
		deliveryInfo.setDeliveryId(1);
		deliveryInfo.setDeliveryStatus("I");
		deliveryInfo.setLatitude("1032");
		deliveryInfo.setLongitude("2433");
		
		orderFoodInfo = new OrderFoodInfo();
		orderFoodInfo.setAdditionalInfo("Extra curd");
		orderFoodInfo.setFoodId(101);
		orderFoodInfo.setFoodPrice(556);
		orderFoodInfo.setOrderFoodId(10);
		orderFoodInfo.setOrderId(1);
		orderFoodInfo.setQuantity(2);
		
		listOrderFoodInfo = new ArrayList<>();
		listOrderFoodInfo.add(orderFoodInfo);
		
		listOrderDetails = new ArrayList<>();
		listOrderDetails.add(orderDetails);
	}
	
	// ================ Cancel Order- Success
	//@Test
	public void testCancelOrderPositiveCase() throws Exception
	{		
		String result ="{  \"statusCode\": 200,  \"status\": \"success\",  \"orderId\": 1,  \"message\": \"order has been canceled\",  \"data\": null,   \"deliveryInfo\": null}";
		when(mockOrderDetailsService.cancelOrder(Mockito.anyInt())).thenReturn(orderDetails);
		mockMvc.perform(MockMvcRequestBuilders.delete("/ordermanagement/order/{orderId}", 1)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().is2xxSuccessful())
		.andExpect(content().json(result));
	}
	
	// ================ view Order ($Order_id} - Success
		//@Test
		public void testViewOrderPositiveCase() throws Exception
		{		
			String result ="{\n \"statusCode\": 200,\n \"status\": \"Success\",\n \"orderId\": 1,\n \"message\": null,\n \"data\": [ {\n \"quantity\": 2,\n \"additionalInfo\": \"Extra curd\",\n \"foodId\": 101,\n \"foodPrice\": 556.0\n }],\n \"deliveryInfo\": {\n \"deliveryId\": 1,\n \"deliveryBoyName\": \"Kumar\",\n \"deliveryBoyContactNumber\": 8147892042,\n \"deliveryAddress\": \"BTM\",\n \"deliveryStatus\": \"I\",\n \"latitude\": \"1032\",\n \"longitude\": \"2433\"\n }\n}";
			when(mockOrderDetailsService.getOrderDetailsByOrderId(Mockito.anyInt())).thenReturn(orderDetails);
			when(mockDeliveryInfoService.getDeliveryInfoByDeliveryId(Mockito.anyInt())).thenReturn(deliveryInfo);
			when(orderFoodInfoService.getListOfFoodItemsOrder(Mockito.anyInt())).thenReturn(listOrderFoodInfo);
			mockMvc.perform(MockMvcRequestBuilders.get("/ordermanagement/order/{orderId}", 1)).andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().is2xxSuccessful())
			.andExpect(content().json(result));
		}
		
		// ================ view Order ($Customer_id} - Positive
			//	@Test
				public void testViewOrderByCustomerIdPositiveCase() throws Exception
				{		
					String result ="{\n \"statusCode\": 200,\n \"data\": [ {\n \"totalPrice\": 556,\n \"ordredDate\": \"2018-08-28T13:11:34.000+0000\",\n \"orderStatus\": \"P\",\n \"orderId\": 1\n }]\n}";
					when(mockOrderDetailsService.getOrderDetailsByCustomerId(Mockito.anyInt())).thenReturn(listOrderDetails);
			
					mockMvc.perform(MockMvcRequestBuilders.get("/ordermanagement/orders/{customerId}", 1))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andExpect(status().is2xxSuccessful());
				 	//  .andExpect(contOrderManagementServiceExceptionent().json(result));
				}
				
		// ================ view Order ($Customer_id} - Negative
		//@Test(expected=OrderManagementServiceException.class)
		//@Test
		public void testViewOrderByCustomerIdNegativeCase() throws Exception
		{	List<OrderDetails> orderDetailsList1 = new ArrayList<OrderDetails>();
			String result ="{\n   \"statusCode\": 404,\n   \"status\": \"failed\",\n   \"orderId\": 2,\n   \"message\": \" data not found in records  \"\n}";
			when(mockOrderDetailsService.getOrderDetailsByCustomerId(Mockito.anyInt())).thenReturn(orderDetailsList1);
			
			mockMvc.perform(MockMvcRequestBuilders.get("/ordermanagement/orders/{customerId}", 1))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
			.andExpect(status().is4xxClientError())
			.andExpect(content().json(result));
		}		
				
				
		// ================ update Order ($Order Id} and ($Status) - Success
		//	@Test
			public void testUpdateOrderStatus() throws Exception
			{		
				String result ="{\n   \"statusCode\": 200,\n   \"orderId\": 1,\n   \"message\": \"order has been updated\"\n}";
				when(mockOrderDetailsService.update(Mockito.anyInt(),Mockito.anyString())).thenReturn(orderDetails);		
				mockMvc.perform(MockMvcRequestBuilders.put("/ordermanagement/orders/{orderId}/{orderStatus}", 1,"C").contentType(MediaType.ALL_VALUE))
					.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andExpect(status().is2xxSuccessful())
					.andExpect(content().json(result));   
				}
			   
	//Util class test cases
			
		@Test
		public void testGetTimeDifferenceMethod()
		{
			long timeDifferece = OrderMangementServiceUtil.getTimeDifference(new java.util.Date(System.currentTimeMillis()));
			
			Assert.assertEquals(0, timeDifferece);	
		}
			
		@Test
		public void testTotalbillableprice()
		{
			
			
			
			OrderFoodInfo orderFoodInfo1 = new OrderFoodInfo();
			OrderFoodInfo orderFoodInfo2 = new OrderFoodInfo();
			
			orderFoodInfo1.setFoodId(101);
			orderFoodInfo1.setQuantity(2);
			orderFoodInfo1.setAdditionalInfo("Extra curd");
			
			orderFoodInfo2.setFoodId(102);
			orderFoodInfo2.setQuantity(4);
			orderFoodInfo2.setAdditionalInfo("aba");
			
			
			List <OrderFoodInfo> listOrderFoodInfo= new ArrayList<OrderFoodInfo>();
			
			listOrderFoodInfo.add(orderFoodInfo1);
			listOrderFoodInfo.add(orderFoodInfo2);
			
			
			
			List<OrderFoodInfo> listOfOrderFood = new ArrayList<OrderFoodInfo>();
			OrderFoodInfo orderFoodInfo3 = new OrderFoodInfo();
			orderFoodInfo3.setAdditionalInfo("normal cooked chiken better spice nuts");
			orderFoodInfo3.setFoodId(101);
			orderFoodInfo3.setFoodPrice(278);

			OrderFoodInfo orderFoodInfo4 = new OrderFoodInfo();
			orderFoodInfo4.setAdditionalInfo("chiken better spice nuts");
			orderFoodInfo4.setFoodId(102);
			orderFoodInfo4.setFoodPrice(200d);
			listOfOrderFood.add(orderFoodInfo3);
			listOfOrderFood.add(orderFoodInfo4);

		//	double totalPrice =OrderMangementServiceUtil.totalbillableprice(listOrderFoodInfo, listOfOrderFood);
		//	Assert.assertEquals(1356.00, totalPrice,0.001);
			
		}
		
		//Exception class test cases
		//@Test
		public void testGlobalExceptionHandler()
		{
			GlobleExceptionHandler globalException = new GlobleExceptionHandler();
			OrderManagementServiceException orderManagementServiceExceptionObj = new OrderManagementServiceException();
			ResponseEntity<ResponseStatusModel> responseEntity= null;
			
		// orderManagementServiceExceptionObj.setS);;
			
			responseEntity = globalException.customException(orderManagementServiceExceptionObj);
			
			
			//Assert.assertEquals(responseEntity., actual);
			
			
			
			
		}
		
}
