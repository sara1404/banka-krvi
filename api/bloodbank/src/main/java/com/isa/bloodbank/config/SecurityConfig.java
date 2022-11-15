package com.isa.bloodbank.config;

import com.isa.bloodbank.repository.UserRepository;
import com.isa.bloodbank.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	private final UserService userService;
	private final UserRepository userRepository;
	private AuthenticationManager authenticationManager;


	@Autowired
	public SecurityConfig(final UserService userService, final UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@Bean
	public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
		final AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userService);
		authenticationManager = authenticationManagerBuilder.build();

		http.csrf().disable().cors().disable().authorizeHttpRequests()
			.antMatchers("/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.authenticationManager(authenticationManager)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}

}
