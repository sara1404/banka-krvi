package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.AppointmentService;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/for-user/{id}")
	public ResponseEntity<List<UserAppointmentDto>> findAllByUserId(@PathVariable("id") final Long id){
		return ResponseEntity.ok(appointmentService.findAllByUserId(id));
	}

	@PostMapping("/finish")
	public ResponseEntity<Boolean> finish(@RequestBody final Long id){
		return ResponseEntity.ok(appointmentService.finishAppointment(id));
	}

}
