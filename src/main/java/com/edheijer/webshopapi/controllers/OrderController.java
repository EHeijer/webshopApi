package com.edheijer.webshopapi.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edheijer.webshopapi.errors.BadRequestAlertException;
import com.edheijer.webshopapi.models.dtos.OrderDTO;
import com.edheijer.webshopapi.services.OrderService;
import com.edheijer.webshopapi.services.utils.PaginationUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaginationUtils paginationUtils;
	
	@PostMapping("/orders")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException, BadRequestAlertException {
		if (orderDTO.getId() != null) {
			throw new BadRequestAlertException("A new order cannot already have an ID");
		}
		OrderDTO result = orderService.save(orderDTO);
		return ResponseEntity
				.created(URI.create("/api/orders/" + result.getId()))
				.body(result);
	}
	
	@GetMapping("/orders-by-user")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<List<OrderDTO>> getOrderByCurrentUser(Pageable pageable) {
		Page<OrderDTO> page = orderService.findAllByCurrentUser(pageable);
		String totalItems = String.valueOf(page.getTotalElements());
		HttpHeaders responseHeader = paginationUtils.getTotalItemHeader(totalItems);
		return ResponseEntity.ok().headers(responseHeader).body(page.getContent());
	}
}
