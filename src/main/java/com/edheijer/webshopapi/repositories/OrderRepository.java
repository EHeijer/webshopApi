package com.edheijer.webshopapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
