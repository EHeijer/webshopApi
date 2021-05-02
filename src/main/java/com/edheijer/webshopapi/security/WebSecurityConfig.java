package com.edheijer.webshopapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static class RestSecurity extends WebSecurityConfigurerAdapter {
//		@Autowired
//		private AuthEntryPointJwt unauthorizedHandler;
//		
//		@Autowired
//		private UserDetailsServiceImpl userDetailsService;
//		
//		@Autowired
//		private BCryptPasswordEncoder passwordEncoder;
//
//		@Bean
//		public AuthTokenFilter authenticationJwtTokenFilter() {
//			return new AuthTokenFilter();
//		}
		
//		@Override
//		public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//			authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//		}
//
//		@Bean
//		@Override
//		public AuthenticationManager authenticationManagerBean() throws Exception {
//			return super.authenticationManagerBean();
//		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
//				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.antMatcher("/api/**").authorizeRequests()
				.antMatchers("/api/products").permitAll()
				.antMatchers("/api/login").permitAll()
				.antMatchers("/api/register").permitAll()
				.antMatchers("/api/employee-actions/**").hasAnyAuthority("ADMIN", "EMPLOYEE");
		}
		
		@Bean
		public CorsFilter corsFilter() {

		    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		    CorsConfiguration config = new CorsConfiguration();
		    config.setAllowCredentials(true); 
		    config.addAllowedOrigin("http://localhost:3000");
		    config.addAllowedHeader("*");
		    config.addAllowedMethod("OPTIONS");
		    config.addAllowedMethod("HEAD");
		    config.addAllowedMethod("GET");
		    config.addAllowedMethod("PUT");
		    config.addAllowedMethod("POST");
		    config.addAllowedMethod("DELETE");
		    config.addAllowedMethod("PATCH");
		    source.registerCorsConfiguration("/**", config);
		    return new CorsFilter(source);
		}
	}

}
