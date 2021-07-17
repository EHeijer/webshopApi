package com.edheijer.webshopapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.Product;
import com.edheijer.webshopapi.models.entities.ProductCategories;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByCategory(ProductCategories category, Pageable pageable);
}
