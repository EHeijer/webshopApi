package com.edheijer.webshopapi.models.mappers;

import org.mapstruct.Mapper;

import com.edheijer.webshopapi.models.dtos.UserDTO;
import com.edheijer.webshopapi.models.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
	
	default User fromId(Long id) {
		if(id == null) {
			return null;
		}
		User user= new User();
		user.setId(id);
		return user;
	}

}
