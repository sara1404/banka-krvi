package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.AppointmentService;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private UserService userService;

	@GetMapping("/available") //{bloodBankId}/, @PathVariable("bloodBankId") final Long bloodBankId
	public ResponseEntity<List<Appointment>> findById() {
		final Long administratorId = (long) (3);
		User user = userService.findById(administratorId);
		return ResponseEntity.ok(appointmentService.findAvailableAppointments(user.getBloodBank().getId()));
	}

}
