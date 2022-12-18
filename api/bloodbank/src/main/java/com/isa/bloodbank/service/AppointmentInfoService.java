package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentAndInfoDto;
import com.isa.bloodbank.dto.AppointmentInfoDto;
import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.User;
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
	@Autowired
	private BloodSupplyService bloodSupplyService;
	@Autowired
	private UserService userService;

	//izmenila za krv
	public AppointmentInfoDto create(final AppointmentAndInfoDto appointmentAndInfoDto, final User loggedUser) {
		//appointmentInfoRepository.save(appointmentInfoMapper.appointmentDtoToAppointment(appointmentAndInfoDto.getAppointmentInfoDto()));
		final AppointmentInfoDto dto = appointmentInfoMapper.appointmentToAppointmentInfoDto(
			appointmentInfoMapper.appointmentDtoToAppointment(appointmentAndInfoDto.getAppointmentInfoDto()));
		final AppointmentInfo appointmentInfo = appointmentInfoMapper.appointmentDtoToAppointment(dto);
		appointmentService.updateAppointmentInfo(appointmentAndInfoDto.getAppointmentId(), appointmentInfo);
		final User user = appointmentService.findById(appointmentAndInfoDto.getAppointmentId()).getUser();
		if (appointmentAndInfoDto.getAppointmentInfoDto().isAccepted()) {
			//bloodSupplyService.addBlood(appointmentAndInfoDto.getAppointmentInfoDto().getExamBloodType(),
			bloodSupplyService.addBlood(user.getBloodType(),
				appointmentAndInfoDto.getAppointmentInfoDto().getQuantity(), loggedUser);
		}
		appointmentService.finishAppointment(appointmentAndInfoDto.getAppointmentId());
		return dto;
	}
}
