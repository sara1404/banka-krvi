package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private AppointmentMapper appointmentMapper;
	@Autowired
	private UserService userService;

	public List<FreeAppointmentDto> findAvailableAppointments(final Long bloodBankId) {
		final List<Appointment> availableAppointments = new ArrayList<Appointment>();
		for (final Appointment appointment : appointmentRepository.findAllByBloodBankId(bloodBankId)) {
			if ((appointment.isAvailable() == true)) {
				availableAppointments.add(appointment);
			}
		}
		return appointmentMapper.appointmentsToFreeAppointmentDtos(availableAppointments);
		//return availableAppointments;
	}

	public AppointmentDto createAppointment(AppointmentDto appointmentDto, Long adminId){
		Appointment appointment = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
		appointment.setBloodBankId(userService.findById(adminId).getBloodBank().getId());
		appointment.setAvailable(true);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}
}
