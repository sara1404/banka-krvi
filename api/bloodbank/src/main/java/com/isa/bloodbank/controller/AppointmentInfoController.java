package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.service.AppointmentInfoService;
import com.isa.bloodbank.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointmentinfo")
public class AppointmentInfoController {
	@Autowired
	private AppointmentInfoService appointmentInfoService;
}
