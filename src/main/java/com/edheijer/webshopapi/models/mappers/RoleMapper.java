package com.edheijer.webshopapi.models.mappers;

import org.mapstruct.Mapper;

import com.edheijer.webshopapi.models.dtos.RoleDTO;
import com.edheijer.webshopapi.models.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

	default Role fromId(Long id) {
		if(id == null) {
			return null;
		}
		Role role = new Role();
		role.setId(id);
		return role;
	}
}
