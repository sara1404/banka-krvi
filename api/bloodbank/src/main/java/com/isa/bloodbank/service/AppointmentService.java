package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.AppointmentRepository;

import java.time.LocalDateTime;
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

	public List<UserAppointmentDto> findAllByUserId(Long userId){
		List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
		List<Appointment> availableAppointments = new ArrayList<Appointment>();
		for(Appointment appointment : appointments){
			if(appointment.isFinished() == false && appointment.getStartTime().compareTo(LocalDateTime.now()) > 0){
				availableAppointments.add(appointment);
			}
		}
		return appointmentMapper.appointmentsToUserAppointmentDto(availableAppointments);
	}

	public Boolean finishAppointment(Long id){
		Appointment appointment = appointmentRepository.getReferenceById(id);
		if(appointment == null){
			return false;
		}
		appointment.setFinished(true);
		appointmentRepository.save(appointment);
		return true;
	}
}
