package com.edheijer.webshopapi.models.mappers;

import org.mapstruct.Mapper;

import com.edheijer.webshopapi.models.dtos.ProductDTO;
import com.edheijer.webshopapi.models.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

	default Product fromId(Long id) {
		if(id == null) {
			return null;
		}
		Product product = new Product();
		product.setId(id);
		return product;
	}
}
