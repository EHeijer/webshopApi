package com.edheijer.webshopapi.models.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO implements Serializable{
	
	private Long id;
	private LocalDate dateCreated;
	private boolean orderSent;
	private Long userId;
	private Double orderSum;
	private List<OrderlineDTO> orderlines;
}
