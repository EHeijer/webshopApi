package com.edheijer.webshopapi.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.webshopapi.models.dtos.OrderDTO;
import com.edheijer.webshopapi.models.dtos.OrderlineDTO;
import com.edheijer.webshopapi.models.entities.Order;
import com.edheijer.webshopapi.models.entities.Orderline;
import com.edheijer.webshopapi.models.mappers.OrderMapper;
import com.edheijer.webshopapi.models.mappers.OrderlineMapper;
import com.edheijer.webshopapi.repositories.OrderRepository;
import com.edheijer.webshopapi.repositories.OrderlineRepository;
import com.edheijer.webshopapi.services.OrderlineService;

@Service
@Transactional
public class OrderlineServiceImpl implements OrderlineService {
	
	@Autowired
	private OrderlineRepository orderlineRepository;
	
	@Autowired
	private OrderlineMapper orderlineMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderRepository orderRepository;

//	@Override
//	public Order handleOrderlines(OrderDTO orderDTO, Order order) {
//		List<OrderlineDTO> orderlines = new ArrayList<>();
//		orderDTO.getOrderlines().forEach(orderlineDTO -> {
//			order.addOrderline(orderlineMapper.toEntity(orderlineDTO));
//			orderlineDTO = orderlineMapper.toDTO(orderlineRepository.save(orderlineMapper.toEntity(orderlineDTO)));
//			orderlines.add(orderlineDTO);
//		});
//		return order = orderRepository.save(order);
//	}

}
