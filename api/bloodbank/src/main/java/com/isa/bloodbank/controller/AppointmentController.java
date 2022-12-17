package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.*;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.AppointmentService;
import com.isa.bloodbank.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

	@GetMapping("/available")
	public ResponseEntity<List<FreeAppointmentDto>> findById() {
		final Long administratorId = (long) (3);
		User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(appointmentService.findAvailableAppointments(user.getBloodBank().getId()));
	}

	@PostMapping("/create")
	public ResponseEntity<AppointmentDto> createAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader, @Valid @RequestBody final Appointment appointmentDto) {
		Long administratorId = jwtUtils.getUserFromToken(authHeader).getId(); //na osnovu ulogovanog adminitratora trazimo id banke za koju pravi termine
		return ResponseEntity.ok(appointmentService.createAppointment(appointmentDto, administratorId));
	}

	@GetMapping(value = "/recommend" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PageDto<AppointmentDto>> recommend(@RequestParam("startTime") final String startTime,
															 @RequestParam("pageSize") final int pageSize,
															 @RequestParam("pageNumber") final int pageNumber,
											 				 @RequestParam("sortDirection") final String sortDirection){
		System.out.println(LocalDateTime.parse(startTime));
		return ResponseEntity.ok(appointmentService.getBloodBanksWithFreeAppointments(LocalDateTime.parse(startTime), pageSize, pageNumber, sortDirection));
	}
	@PutMapping("/schedule")
	public ResponseEntity<AppointmentDto> scheduleAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader, @Valid @RequestBody final AppointmentDto appointmentDto) {
		Long userId = jwtUtils.getUserFromToken(authHeader).getId();
		return ResponseEntity.ok(appointmentService.scheduleAppointment(appointmentDto, userId));
	}
}
