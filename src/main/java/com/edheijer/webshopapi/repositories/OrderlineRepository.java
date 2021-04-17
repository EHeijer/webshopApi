package com.edheijer.webshopapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.Orderline;

public interface OrderlineRepository extends JpaRepository<Orderline, Long> {

}
