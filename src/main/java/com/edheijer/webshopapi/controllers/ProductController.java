package com.edheijer.webshopapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edheijer.webshopapi.models.dtos.ProductDTO;
import com.edheijer.webshopapi.services.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
		Page<ProductDTO> page = productService.findAll(pageable);
		return ResponseEntity.ok().headers(new HttpHeaders()).body(page.getContent());
	}
	
	@GetMapping("/products/{category}")
	public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable String category, Pageable pageable) {
		Page<ProductDTO> page = productService.findByCategory(category, pageable);
		return ResponseEntity.ok().headers(new HttpHeaders()).body(page.getContent());
	}
}
