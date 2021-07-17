package com.edheijer.webshopapi.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edheijer.webshopapi.models.dtos.ProductDTO;

public interface ProductService {
	Page<ProductDTO> findByCategory(String category, Pageable pageable);
	
	Page<ProductDTO> findAll(Pageable pageable);
	
	List<ProductDTO> findAll();
	
	Page<ProductDTO> findProductsAfterSearch(String searchInput, Pageable pageable);

	long calculateSizeOfFindAllByCategories(String category);
}
