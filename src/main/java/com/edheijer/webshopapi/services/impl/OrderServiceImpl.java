package com.edheijer.webshopapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.webshopapi.models.dtos.OrderDTO;
import com.edheijer.webshopapi.models.entities.Order;
import com.edheijer.webshopapi.models.entities.Orderline;
import com.edheijer.webshopapi.models.mappers.OrderMapper;
import com.edheijer.webshopapi.repositories.OrderRepository;
import com.edheijer.webshopapi.services.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	@Transactional
	public OrderDTO save(OrderDTO orderDTO) {
		Order order = orderMapper.toEntity(orderDTO);
		for(Orderline orderline: order.getOrderlines()) {
			order.addOrderline(orderline);
		}
		order = orderRepository.save(order);
		return orderMapper.toDto(order);
	}

	@Override
	@Transactional
	public Page<OrderDTO> findAllByCurrentUser(Pageable pageable) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		return orderRepository.findAllByCurrentUser(pageable, username)
				.map(orderMapper::toDto);
	}

}
