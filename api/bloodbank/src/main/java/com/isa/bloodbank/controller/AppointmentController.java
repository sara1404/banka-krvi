package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.AppointmentService;
import com.isa.bloodbank.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		final User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(appointmentService.findAvailableAppointments(user.getBloodBank().getId()));
	}

	@GetMapping("/for-user/{id}")
	public ResponseEntity<List<UserAppointmentDto>> findAllByUserId(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(appointmentService.findAllByUserId(id));
	}

	@PostMapping("/finish")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<Boolean> finish(@RequestBody final Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		return ResponseEntity.ok(appointmentService.finishAppointment(id));
	}

	@PostMapping("/create")
	public ResponseEntity<AppointmentDto> createAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@Valid @RequestBody final Appointment appointmentDto) {
		final Long administratorId = jwtUtils.getUserFromToken(authHeader).getId(); //na osnovu ulogovanog adminitratora trazimo id banke za koju pravi termine
		return ResponseEntity.ok(appointmentService.createAppointment(appointmentDto, administratorId));
	}

	@GetMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PageDto<AppointmentDto>> recommend(@RequestParam("startTime") final String startTime,
		@RequestParam("pageSize") final int pageSize,
		@RequestParam("pageNumber") final int pageNumber,
		@RequestParam("sortDirection") final String sortDirection) {
		System.out.println(LocalDateTime.parse(startTime));
		return ResponseEntity.ok(appointmentService.getBloodBanksWithFreeAppointments(LocalDateTime.parse(startTime), pageSize, pageNumber, sortDirection));
	}

	@PutMapping("/schedule")
	public ResponseEntity<AppointmentDto> scheduleAppointment(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@Valid @RequestBody final AppointmentDto appointmentDto) {
		final Long userId = jwtUtils.getUserFromToken(authHeader).getId();
		return ResponseEntity.ok(appointmentService.scheduleAppointment(appointmentDto, userId));
	}
}
