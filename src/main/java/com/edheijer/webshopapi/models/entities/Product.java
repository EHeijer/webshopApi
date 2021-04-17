package com.edheijer.webshopapi.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String brand;
	private String productName;
	private Double price;
	private String imageUrl;
	
	@Column(columnDefinition = "boolean default false")
	private boolean removedFromShop;
	
	private String shelfNumber;
	
	@Enumerated(EnumType.STRING)
	private ProductCategories category;
}
