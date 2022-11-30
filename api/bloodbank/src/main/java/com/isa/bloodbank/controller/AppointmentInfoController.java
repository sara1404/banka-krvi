package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AppointmentInfoDto;
import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.service.AppointmentInfoService;
import com.isa.bloodbank.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointmentinfo")
public class AppointmentInfoController {
	@Autowired
	private AppointmentInfoService appointmentInfoService;

	@PostMapping("/create")
	public ResponseEntity<AppointmentInfoDto> create( @RequestBody final AppointmentInfoDto appointmentInfoDto){
		return ResponseEntity.ok(appointmentInfoService.create(appointmentInfoDto));
	}
}
