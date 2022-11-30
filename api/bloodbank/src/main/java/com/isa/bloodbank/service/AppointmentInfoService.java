package com.isa.bloodbank.service;

import com.isa.bloodbank.repository.AppointmentInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentInfoService {
	@Autowired
	private AppointmentInfoRepository appointmentInfoRepository;
}
