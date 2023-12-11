package com.salsatechnology.controller;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.service.ProductOrderService;

@RestController
@RequestMapping("/orders")
@Api(value = "Vendas", description = "Endpoint para operações relacionadas a vendas")
public class ProductOrderController {
	
	@Autowired
	private ProductOrderService productOrderService;

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void createOrder(@RequestBody @Valid ProductOrderDTO productOrderDTO) {
		productOrderService.createOrder(productOrderDTO);
	}

	@ApiOperation(value = "Listagem de vendas permitindo a aplicação de filtros para uma visualização mais personalizada.")
	@PostMapping("/list")
	public ResponseEntity<Object> listRequests(@RequestBody ProductOrderDTO productOrderDTO) {
		return productOrderService.listRequests(productOrderDTO);
	}
}
