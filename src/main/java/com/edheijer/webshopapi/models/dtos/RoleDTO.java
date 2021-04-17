package com.edheijer.webshopapi.models.dtos;

import java.io.Serializable;

import com.edheijer.webshopapi.models.entities.RoleTypes;

import lombok.Data;

@Data
public class RoleDTO implements Serializable{
	private long id;
	private RoleTypes roleName;
}
