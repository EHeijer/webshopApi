package com.edheijer.webshopapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
