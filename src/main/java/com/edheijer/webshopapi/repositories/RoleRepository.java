package com.edheijer.webshopapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
