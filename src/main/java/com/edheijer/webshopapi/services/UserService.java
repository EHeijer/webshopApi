package com.edheijer.webshopapi.services;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edheijer.webshopapi.models.dtos.UserDTO;

import javassist.NotFoundException;

public interface UserService {

	Page<UserDTO> findAllUsers(Pageable pageable);

	UserDTO getUserById(Long id) throws NotFoundException;

	UserDTO save(UserDTO userDTO);
}
