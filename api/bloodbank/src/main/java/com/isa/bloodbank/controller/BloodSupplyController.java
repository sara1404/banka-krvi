package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.BloodSupplyService;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloodsupply")
public class BloodSupplyController {
	@Autowired
	private BloodSupplyService bloodSupplyService;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping("/bloodbank")
	public ResponseEntity<List<BloodSupplyDto>> findById(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader
	) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(bloodSupplyService.findByBloodBankId(loggedUser.getBloodBank().getId()));
	}

}
