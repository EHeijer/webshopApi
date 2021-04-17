package com.edheijer.webshopapi.models.dtos;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class UserDTO implements Serializable{
	private Long id;
	private String username;
	private String email;
	private String password;
	private boolean enabled;
	private Set<RoleDTO> roles;
	private Set<OrderDTO> orders;
}
