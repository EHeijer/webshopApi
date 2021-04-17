package com.edheijer.webshopapi.models.dtos;

import java.io.Serializable;

import com.edheijer.webshopapi.models.entities.ProductCategories;

import lombok.Data;

@Data
public class ProductDTO implements Serializable{
	private Long id;
	private String brand;
	private String productName;
	private Double price;
	private String imageUrl;
	private boolean removedFromShop;
	private String shelfNumber;
	private ProductCategories category;
}
