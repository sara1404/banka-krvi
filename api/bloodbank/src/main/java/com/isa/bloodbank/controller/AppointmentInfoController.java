package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AppointmentAndInfoDto;
import com.isa.bloodbank.dto.AppointmentInfoDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.AppointmentInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointmentinfo")
public class AppointmentInfoController {
	@Autowired
	private AppointmentInfoService appointmentInfoService;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<AppointmentInfoDto> create(@RequestBody final AppointmentAndInfoDto appointmentAndInfoDto,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(appointmentInfoService.create(appointmentAndInfoDto, loggedUser));
	}
}
