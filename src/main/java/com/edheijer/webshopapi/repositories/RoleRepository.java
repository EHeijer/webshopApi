package com.edheijer.webshopapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.webshopapi.models.entities.Role;
import com.edheijer.webshopapi.models.entities.RoleTypes;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(RoleTypes name);
}
