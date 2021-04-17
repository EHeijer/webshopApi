package com.edheijer.webshopapi.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.edheijer.webshopapi.models.dtos.OrderDTO;
import com.edheijer.webshopapi.models.entities.Order;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

	@Mapping(source = "user.id", target = "userId")
	OrderDTO toDto(Order order);
	
	@Mapping(source = "userId", target = "user")
	Order toEntity(OrderDTO orderDTO);
	
	default Order fromId(Long id) {
		if(id == null) {
			return null;
		}
		Order order = new Order();
		order.setId(id);
		return order;
	}
}
