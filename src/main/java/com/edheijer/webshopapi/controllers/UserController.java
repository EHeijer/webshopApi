package com.edheijer.webshopapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edheijer.webshopapi.errors.BadRequestAlertException;
import com.edheijer.webshopapi.models.dtos.UserDTO;
import com.edheijer.webshopapi.models.entities.User;
import com.edheijer.webshopapi.security.jwt.JwtUtils;
import com.edheijer.webshopapi.security.services.UserDetailsImpl;
import com.edheijer.webshopapi.services.UserService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable){
		Page<UserDTO> page = userService.findAllUsers(pageable);
		return ResponseEntity.ok().body(page.getContent());
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") int id) throws NotFoundException{
		UserDTO user = userService.getUserById(Integer.toUnsignedLong(id));
		return ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/users")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @RequestHeader(value = "currentUserId") String currentUserId) throws BadRequestAlertException{
		if(userDTO == null) {
			throw new BadRequestAlertException("Invalid ID, id is null");
		}
		UserDTO result = userService.save(userDTO);
		HttpHeaders jwtHeader = new HttpHeaders();
		if(Long.parseLong(currentUserId) == result.getId()) {
			String jwt = jwtUtils.generateNewTokenWhenCurrentUserUpdatesEmail(result);
			jwtHeader.set("x-jwt", jwt);
		}
		
		return ResponseEntity.ok().headers(jwtHeader).body(result);
	}
}
