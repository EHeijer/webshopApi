package com.edheijer.webshopapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edheijer.webshopapi.models.dtos.OrderDTO;

public interface OrderService {

	OrderDTO save(OrderDTO orderDTO);

	Page<OrderDTO> findAllByCurrentUser(Pageable pageable);

}
