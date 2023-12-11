package com.salsatechnology.service;

import javax.transaction.Transactional;

import com.salsatechnology.exception.NotFoundException;
import com.salsatechnology.model.ProductInfo;
import com.salsatechnology.model.ProductOrderBuilder;
import com.salsatechnology.model.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.model.ProductOrder;
import com.salsatechnology.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.salsatechnology.util.ProductsCommissionsConstant.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductOrderService {

	@Autowired
	private final ProductOrderRepository productOrderRepository;

	private static final Map<ProductType, ProductInfo> productInfoMap = new HashMap<>();

	static {
		productInfoMap.put(ProductType.SURFBOARD, new ProductInfo(PRICE_SURFBOARD, PERCENT_SURFBOARD));
		productInfoMap.put(ProductType.BEACH_CHAIR, new ProductInfo(PRICE_BEACH_CHAIR, PERCENT_BEACH_CHAIR));
		productInfoMap.put(ProductType.SUNSHADE, new ProductInfo(PRICE_SUNSHADE, PERCENT_SUNSHADE));
		productInfoMap.put(ProductType.SAND_BOARD, new ProductInfo(PRICE_SAND_BOARD, PERCENT_SAND_BOARD));
		productInfoMap.put(ProductType.BEACH_TABLE, new ProductInfo(PRICE_BEACH, PERCENT_BEACH_TABLE));
	}

	@Transactional
	public void createOrder(ProductOrderDTO productOrderDTO) {
		ProductOrder productOrder = ProductOrderBuilder.buildFromDTO(productOrderDTO);
		ProductInfo productInfo = getProductInfo(productOrder.getProductType());

		double productValue = calculateProductValue(productInfo);
		double productTotal = calculateProductTotal(productValue, productOrder.getTimeHour());
		double userAmount = calculateUserAmount(productTotal, productInfo);

		productOrder.setProductValue(convertToCents(productValue));
		productOrder.setProductTotal(convertToCents(productTotal));
		productOrder.setUserAmount(convertToCents(userAmount));

		productOrderRepository.save(productOrder);
	}

	private ProductInfo getProductInfo(ProductType productType) {
		ProductInfo productInfo = productInfoMap.get(productType);
		if (productInfo != null) {
			return productInfo;
		} else {
			throw new IllegalArgumentException("Tipo de produto desconhecido: " + productType);
		}
	}

	private long convertToCents(double value) {
		return (long) (value * 100);
	}

	private double calculateProductValue(ProductInfo productInfo) {
		return productInfo.getBaseValue();
	}

	private double calculateProductTotal(double productValue, int timeHour) {
		return productValue * timeHour;
	}

	private double calculateUserAmount(double productTotal, ProductInfo productInfo) {
		double commissionPercentage = productInfo.getCommissionPercentage();
		return productTotal * (commissionPercentage / 100.0);
	}

	public ResponseEntity<Object> listRequests(ProductOrderDTO filter) {
		try {
			List<ProductOrder> productOrderList = productOrderRepository.filter(filter);
			if (validListRequest(productOrderList)) {
				return new ResponseEntity<>(productOrderList, HttpStatus.OK);
			} else {
				return new ResponseEntity<>( "Nenhum registro encontrado", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean validListRequest(List<ProductOrder> productOrderList) {
		return !productOrderList.isEmpty();
	}


}
