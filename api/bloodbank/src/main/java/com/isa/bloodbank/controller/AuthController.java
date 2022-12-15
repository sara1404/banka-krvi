package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.LoginDto;
import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.dto.TokenDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody @Valid final RegisterUserDto user) {
		if (userService.checkIfEmailAlreadyInUse(user.getEmail())) {
			return ResponseEntity.badRequest().build();
		}
		final var userEntity = userMapper.registerUserDtoToUser(user);
		final var registeredUser = userService.registerUser(userEntity);
		if (registeredUser != null) {
			return ResponseEntity.ok(registeredUser);
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody final LoginDto loginRequest) {

		final Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = jwtUtils.generateJwtToken(authentication);
		return ResponseEntity.ok(new TokenDto(jwt));
	}

	@GetMapping("/current")
	public ResponseEntity<?> current(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		return ResponseEntity.ok(userMapper.userToUserDto(jwtUtils.getUserFromToken(authHeader)));
	}
}
