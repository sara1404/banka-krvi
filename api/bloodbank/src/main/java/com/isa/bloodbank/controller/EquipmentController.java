package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.EquipmentDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.EquipmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/used")
	//@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public Boolean removeEquipment(@RequestBody final EquipmentDto equipmentDto,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		return equipmentService.removeEquipment(equipmentDto, loggedUser);
	}
}
