package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.AppointmentService;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/available")
	public ResponseEntity<List<FreeAppointmentDto>> findById() {
		final Long administratorId = (long) (3);
		User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(appointmentService.findAvailableAppointments(user.getBloodBank().getId()));
	}

	@PostMapping("/create")
	public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody final Appointment appointmentDto) {
		final Long administratorId = (long) (3); //na osnovu ulogovanog adminitratora trazimo id banke za koju pravi termine
		return ResponseEntity.ok(appointmentService.createAppointment(appointmentDto, administratorId));
	}
}
