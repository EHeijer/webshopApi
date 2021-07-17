package com.edheijer.webshopapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.webshopapi.models.dtos.ProductDTO;
import com.edheijer.webshopapi.models.entities.ProductCategories;
import com.edheijer.webshopapi.models.mappers.ProductMapper;
import com.edheijer.webshopapi.repositories.ProductRepository;
import com.edheijer.webshopapi.services.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper productMapper;

	@Override
	public Page<ProductDTO> findByCategory(String category, Pageable pageable) {
		
		return productRepository.findByCategory(ProductCategories.valueOf(category), pageable)
				.map(productMapper::toDto);
	}

	@Override
	public Page<ProductDTO> findAll(Pageable pageable) {
		return productRepository.findAll(pageable)
				.map(productMapper::toDto);
	}

	@Override
	public List<ProductDTO> findAll() {
		return productMapper.toDto(productRepository.findAll());
	}
	
	@Override
	public long calculateSizeOfFindAllByCategories(String category) {
		return productRepository.findAll()
			.stream()
			.filter(product -> product.getCategory() == ProductCategories.valueOf(category))
			.count();
	}

	@Override
	public Page<ProductDTO> findProductsAfterSearch(String searchInput, Pageable pageable) {
		//Filter product list by product name or brand and then convert that list to a page
		List<ProductDTO> allProductsList = findAll()
				.stream()
				.filter(product -> product.getBrand().toLowerCase().contains(searchInput.trim().toLowerCase()) || product.getProductName().toLowerCase().contains(searchInput.trim().toLowerCase()))
				.collect(Collectors.toList());
		Page<ProductDTO> page = new PageImpl<ProductDTO>(allProductsList, pageable, allProductsList.size());
		return page;
	}

}
