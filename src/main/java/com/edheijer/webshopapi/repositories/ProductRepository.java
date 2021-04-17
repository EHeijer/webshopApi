package com.edheijer.webshopapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findByCategory(String category, Pageable pageable);
}
