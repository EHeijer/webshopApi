package com.edheijer.webshopapi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edheijer.webshopapi.repositories.UserRepository;
import com.edheijer.webshopapi.security.payload.request.LoginRequest;
import com.edheijer.webshopapi.security.payload.request.SignupRequest;
import com.edheijer.webshopapi.security.payload.response.JwtResponse;
import com.edheijer.webshopapi.security.payload.response.MessageResponse;
import com.edheijer.webshopapi.security.services.UserDetailsImpl;
import com.edheijer.webshopapi.security.services.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		JwtResponse jwtResponse = userDetailsService.getJwtReponse(loginRequest);
		return ResponseEntity.ok(jwtResponse);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		if(!signupRequest.getConfirmPassword().equals(signupRequest.getPassword())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Password and confirm password doesn't match"));
		}
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		userDetailsService.createNewUserAccount(signupRequest);
		return ResponseEntity.ok(new MessageResponse("Congratulations " + signupRequest.getUsername() + ", you can now log in!"));
	}
	
	@GetMapping("/currentuser")
	public ResponseEntity<?> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return ResponseEntity.ok(userDetails);
	}
	
	@GetMapping("/token-check")
	public Boolean isTokenExpired(HttpServletRequest request) {
		return userDetailsService.isTokenExpired(request);
	}
}
