package com.edheijer.webshopapi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edheijer.webshopapi.models.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT userOrder FROM Order userOrder where userOrder.user.username =:username")
	Page<Order> findAllByCurrentUser(Pageable pageable, @Param("username") String username);
	
}
