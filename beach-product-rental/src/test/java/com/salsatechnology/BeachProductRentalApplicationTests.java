package com.salsatechnology;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.model.ProductType;
import com.salsatechnology.repository.ProductOrderRepository;
import com.salsatechnology.service.ProductOrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class BeachProductRentalApplicationTests {

	@InjectMocks
	private ProductOrderService productOrderService;

	@Mock
	private ProductOrderRepository productOrderRepository;

	@Test
	public void testCreateOrder() {
		ProductOrderDTO productOrderDTO = new ProductOrderDTO();
		productOrderDTO.setUserName("João Maria");
		productOrderDTO.setProductType(ProductType.SURFBOARD);
		productOrderDTO.setTimeHour(2);

		ProductOrder productOrder = new ProductOrder();
		productOrder.setUserName(productOrderDTO.getUserName());
		productOrder.setProductType(productOrderDTO.getProductType());
		productOrder.setTimeHour(productOrderDTO.getTimeHour());

		productOrderService.createOrder(productOrderDTO);

		verify(productOrderRepository, times(1)).save(any(ProductOrder.class));
	}

	@Test
	public void testCreateThrowOrder() {
		// Arrange
		ProductOrderDTO productOrderDTO = new ProductOrderDTO();
		productOrderDTO.setUserName("João Erro");
		productOrderDTO.setProductType(ProductType.SURFBOARD);
		productOrderDTO.setTimeHour(2);

		ProductOrder productOrder = new ProductOrder();
		productOrder.setUserName(productOrderDTO.getUserName());
		productOrder.setProductType(productOrderDTO.getProductType());
		productOrder.setTimeHour(productOrderDTO.getTimeHour());

		doThrow(NullPointerException.class).when(productOrderRepository).save(any(ProductOrder.class));

		assertThrows(NullPointerException.class, () -> productOrderService.createOrder(productOrderDTO));

		verify(productOrderRepository, times(1)).save(any(ProductOrder.class));
	}

	@Test
	void testListRequests() {
		ProductOrderDTO filter = new ProductOrderDTO();
		List<ProductOrder> mockProductOrderList = new ArrayList<>();
		mockProductOrderList.add(new ProductOrder());
		when(productOrderRepository.filter(any(ProductOrderDTO.class))).thenReturn(mockProductOrderList);

		ResponseEntity<Object> response = productOrderService.listRequests(filter);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockProductOrderList, response.getBody());
	}
}
