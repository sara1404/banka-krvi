package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentInfoDto;
import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.mapping.AppointmentInfoMapper;
import com.isa.bloodbank.repository.AppointmentInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentInfoService {
	@Autowired
	private AppointmentInfoRepository appointmentInfoRepository;
	@Autowired
	private AppointmentInfoMapper appointmentInfoMapper;

	public AppointmentInfoDto create(AppointmentInfoDto appointmentInfoDto){
		return appointmentInfoMapper.appointmentToAppointmentInfoDto(appointmentInfoRepository.save(appointmentInfoMapper.appointmentDtoToAppointment(appointmentInfoDto)));
	}
}
