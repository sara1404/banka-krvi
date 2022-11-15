package com.isa.bloodbank.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.isa.bloodbank.dto.LoginDto;
import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.service.UserService;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	//private final JwtTokenUtil jwtTokenUtil;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody @Valid final RegisterUserDto user) {
		if (userService.checkIfEmailAlreadyInUse(user.getEmail())) {
			return ResponseEntity.badRequest().build();
		}
		final var registeredUser = userService.registerUser(userMapper.registerUserDtoToUser(user));
		if (registeredUser != null) {
			return ResponseEntity.ok(registeredUser);
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody @Valid final LoginDto credentials) {
		try {
			/*
			Authentication authenticate = authenticationManager
				.authenticate(
					new UsernamePasswordAuthenticationToken(
						credentials.getEmail(), credentials.getPassword()
					)
				);

			User user = (User) authenticate.getPrincipal();
			*/
			User user = userService.findByEmail(credentials.getEmail());
			return ResponseEntity.ok()
				.header(
					HttpHeaders.AUTHORIZATION,
					JWT.create()
						.withIssuedAt(Instant.now())
						.withExpiresAt(Instant.now())
						.withClaim("id", user.getId())
						.sign(Algorithm.HMAC256("aaa"))
						.toString()
				)
				.body(true);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
