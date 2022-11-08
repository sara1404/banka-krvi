package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.service.AppointmentService;

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

	@GetMapping("/available/") //{bloodBankId}/, @PathVariable("bloodBankId") final Long bloodBankId
	public ResponseEntity<List<Appointment>> findById() {
		final Long bloodBankId = (long) 5;
		return ResponseEntity.ok(appointmentService.findAvailableAppointments(bloodBankId));
	}

}
