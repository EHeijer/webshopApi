package com.edheijer.webshopapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edheijer.webshopapi.models.dtos.ProductDTO;

public interface ProductService {
	Page<ProductDTO> findByCategory(String category, Pageable pageable);
	
	Page<ProductDTO> findAll(Pageable pageable);
}
