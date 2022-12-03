package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentAndInfoDto;
import com.isa.bloodbank.dto.AppointmentInfoDto;
import com.isa.bloodbank.entity.Appointment;
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
	@Autowired
	private AppointmentService appointmentService;

	public AppointmentInfoDto create(AppointmentAndInfoDto appointmentAndInfoDto){
		AppointmentInfoDto dto = appointmentInfoMapper.appointmentToAppointmentInfoDto(appointmentInfoRepository.save(appointmentInfoMapper.appointmentDtoToAppointment(appointmentAndInfoDto.getAppointmentInfoDto())));
		AppointmentInfo appointmentInfo = appointmentInfoMapper.appointmentDtoToAppointment(dto);
		appointmentService.updateAppointmentInfo(appointmentAndInfoDto.getAppointmentId(), appointmentInfoMapper.appointmentDtoToAppointment(dto));
		return dto;
	}
}
