package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.dto.UserDto;
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
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<List<FreeAppointmentDto>> findById(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(appointmentService.findAvailableAppointments(loggedUser.getBloodBank().getId()));
	}

	@GetMapping("/for-user/{id}")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<List<UserAppointmentDto>> findAllByUserId(@PathVariable("id") final Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(appointmentService.findAllByUserId(id, loggedUser));
	}

	@PostMapping("/finish")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<Boolean> finish(@RequestBody final Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		return ResponseEntity.ok(appointmentService.finishAppointment(id));
	}

	@GetMapping(value = "/getAvailableMedicalStaff", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<List<UserDto>> findAvailableMedicalStaff(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@RequestParam("startTime") final String startTime,
		@RequestParam("duration") final double duration) {
		final Long adminId = jwtUtils.getUserFromToken(authHeader).getId();
		return ResponseEntity.ok(
			appointmentService.findAvailableMedicalStaff(userService.findById(adminId).getBloodBank().getId(), LocalDateTime.parse(startTime), duration));
	}

	@GetMapping("/appointments")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<List<AppointmentDto>> getAppointments(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@RequestParam("month") final int month, @RequestParam("year") final int year) {
		final Long administratorId = jwtUtils.getUserFromToken(authHeader).getId(); //na osnovu ulogovanog adminitratora trazimo id banke za koju pravi termine
		return ResponseEntity.ok(appointmentService.getAppointments(month, year, administratorId));
	}

	@PostMapping("/create")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('REGISTERED')")
	public ResponseEntity<AppointmentDto> createAppointment(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@Valid @RequestBody final Appointment appointmentDto) {
		final Long administratorId = jwtUtils.getUserFromToken(authHeader).getId(); //na osnovu ulogovanog adminitratora trazimo id banke za koju pravi termine
		return ResponseEntity.ok(appointmentService.createAppointment(appointmentDto, administratorId));
	}

	@GetMapping(value = "/recommend", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('REGISTERED')")
	public ResponseEntity<PageDto<AppointmentDto>> recommend(
		@RequestParam("startTime") final String startTime,
		@RequestParam("pageSize") final int pageSize,
		@RequestParam("pageNumber") final int pageNumber,
		@RequestParam("sortDirection") final String sortDirection,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		System.out.println(LocalDateTime.parse(startTime));
		return ResponseEntity.ok(appointmentService.getBloodBanksWithFreeAppointments(LocalDateTime.parse(startTime), pageSize, pageNumber, sortDirection));
	}

	@PutMapping("/schedule")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('REGISTERED')")
	public ResponseEntity<AppointmentDto> scheduleAppointment(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@Valid
		@RequestBody final AppointmentDto appointmentDto) {
		final Long userId = jwtUtils.getUserFromToken(authHeader).getId();
		return ResponseEntity.ok(appointmentService.scheduleAppointment(appointmentDto, userId));
	}

	@GetMapping(value = "/findBloodBanksWithAvailableMedicalStaff", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('REGISTERED')")
	public ResponseEntity<PageDto<UserDto>> getBloodBanksWithAvailableMedicalStaff(
		@RequestParam("startTime") final String startTime,
		@RequestParam("duration") final double duration,
		@RequestParam("pageSize") final int pageSize,
		@RequestParam("pageNumber") final int pageNumber,
		@RequestParam("sortDirection") final String sortDirection) {
		return ResponseEntity.ok(
			appointmentService.findBloodBanksWithAvailableMedicalStaff(LocalDateTime.parse(startTime), duration, pageSize, pageNumber, sortDirection));
	}

	@PostMapping("/userCreates")
	@PreAuthorize("hasAuthority('REGISTERED')")
	public ResponseEntity<AppointmentDto> userCreatesAppointment(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader,
		@Valid @RequestBody final AppointmentDto appointmentDto) {
		final Long userId = jwtUtils.getUserFromToken(authHeader).getId();
		return ResponseEntity.ok(appointmentService.userCreatesAppointment(appointmentDto, userId));
	}
}
