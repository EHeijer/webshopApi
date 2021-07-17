package com.edheijer.webshopapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edheijer.webshopapi.models.dtos.ProductDTO;
import com.edheijer.webshopapi.services.ProductService;
import com.edheijer.webshopapi.services.utils.PaginationUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaginationUtils paginationUtils;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
		Page<ProductDTO> page = productService.findAll(pageable);
		String totalItems = String.valueOf(productService.findAll().size());
		HttpHeaders responseHeader = paginationUtils.getTotalItemHeader(totalItems);
		return ResponseEntity.ok().headers(responseHeader).body(page.getContent());
	}
	
	@GetMapping("/products/{category}")
	public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable String category, Pageable pageable) {
		Page<ProductDTO> page = productService.findByCategory(category, pageable);
		String totalItems = String.valueOf(productService.calculateSizeOfFindAllByCategories(category));
		HttpHeaders responseHeader = paginationUtils.getTotalItemHeader(totalItems);
		return ResponseEntity.ok().headers(responseHeader).body(page.getContent());
	}
	
	/*
	 * Returns products after search input by brand or product name
	 */
	@GetMapping("/products/search/{searchInput}")
	public ResponseEntity<List<ProductDTO>> getAllProductsAfterSearch(@PathVariable String searchInput, Pageable pageable) {
		Page<ProductDTO> page = productService.findProductsAfterSearch(searchInput, pageable);
		String totalItems = String.valueOf(page.getTotalElements());
		HttpHeaders responseHeader = paginationUtils.getTotalItemHeader(totalItems);
		return ResponseEntity.ok().headers(responseHeader).body(page.getContent());
	}
	
}
