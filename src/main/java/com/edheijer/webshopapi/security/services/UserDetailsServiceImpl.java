package com.edheijer.webshopapi.security.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.edheijer.webshopapi.models.entities.Role;
import com.edheijer.webshopapi.models.entities.RoleTypes;
import com.edheijer.webshopapi.models.entities.User;
import com.edheijer.webshopapi.repositories.RoleRepository;
import com.edheijer.webshopapi.repositories.UserRepository;
import com.edheijer.webshopapi.security.jwt.AuthTokenFilter;
import com.edheijer.webshopapi.security.jwt.JwtUtils;
import com.edheijer.webshopapi.security.payload.request.LoginRequest;
import com.edheijer.webshopapi.security.payload.request.SignupRequest;
import com.edheijer.webshopapi.security.payload.response.JwtResponse;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Since I am using email to authenticate instead of username, I'm fetching user from email
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

		return UserDetailsImpl.build(user);
	}
	
	@Transactional
	public JwtResponse getJwtReponse(@Valid LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
	}

	public void createNewUserAccount(@Valid SignupRequest signupRequest) {
		
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
		Set<String> strRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role customerRole = roleRepository.findByRoleName(RoleTypes.ROLE_CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
			roles.add(customerRole);
		} else {
			strRoles.forEach(role -> {
				switch(role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByRoleName(RoleTypes.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(adminRole);
					break;
				case "ROLE_EMPLOYEE":
					Role employeeRole = roleRepository.findByRoleName(RoleTypes.ROLE_EMPLOYEE)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(employeeRole);
					break;
				default: 
					Role customerRole = roleRepository.findByRoleName(RoleTypes.ROLE_CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
					roles.add(customerRole); 
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
	}
	
	public Boolean isTokenExpired(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			String token = headerAuth.substring(7, headerAuth.length());
			if(jwtUtils.validateJwtToken(token)) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}
}
