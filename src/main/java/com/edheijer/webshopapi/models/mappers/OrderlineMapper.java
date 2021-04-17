package com.edheijer.webshopapi.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.edheijer.webshopapi.models.dtos.OrderlineDTO;
import com.edheijer.webshopapi.models.entities.Orderline;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, OrderMapper.class})
public interface OrderlineMapper extends EntityMapper<OrderlineDTO, Orderline> {
	
	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "order.id", target = "orderId")
	OrderlineDTO toDTO(Orderline orderline);
	
	@Mapping(source = "productId", target = "product")
	@Mapping(source = "orderId", target = "order")
	Orderline toEntity(OrderlineDTO orderlineDTO);
	
	default Orderline fromId(Long id) {
		if(id == null) {
			return null;
		}
		Orderline orderline = new Orderline();
		orderline.setId(id);
		return orderline;
	}
}
