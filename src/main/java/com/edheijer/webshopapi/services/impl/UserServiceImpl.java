package com.edheijer.webshopapi.services.impl;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.webshopapi.models.dtos.UserDTO;
import com.edheijer.webshopapi.models.entities.User;
import com.edheijer.webshopapi.models.mappers.UserMapper;
import com.edheijer.webshopapi.repositories.UserRepository;
import com.edheijer.webshopapi.services.UserService;

import javassist.NotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Page<UserDTO> findAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable)
				.map(userMapper::toDto);
	}
	
	@Override
	public UserDTO getUserById(Long id) throws NotFoundException{
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return userMapper.toDto(optionalUser.get());
		} else {
			throw new NotFoundException("No user found with this ID");
		}
		
	}

	@Override
	public UserDTO save(UserDTO userDTO) {
		User user = userMapper.toEntity(userDTO);
		
		//Check if user already exist and encode password if it has been changed 
		Optional<User> optionalUserFromDatabase = userRepository.findById(user.getId());
		if(optionalUserFromDatabase.isPresent()) {
			User userFromDatabase = optionalUserFromDatabase.get();
			if(!(encoder.matches(user.getPassword(), userFromDatabase.getPassword()))) {
				user.setPassword(encoder.encode(user.getPassword()));
			}
		}
		user = userRepository.save(user);
		return userMapper.toDto(user);
	}

}
