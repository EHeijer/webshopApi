package com.edheijer.webshopapi.models.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderlineDTO implements Serializable{
	private Long id;
	private int quantity;
	private Double sumOfOrderline;
	private ProductDTO product;
	private Long orderId;
}
