package com.edheijer.webshopapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.webshopapi.models.dtos.ProductDTO;
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
		return productRepository.findByCategory(category, pageable)
				.map(productMapper::toDto);
	}

	@Override
	public Page<ProductDTO> findAll(Pageable pageable) {
		return productRepository.findAll(pageable)
				.map(productMapper::toDto);
	}

}
