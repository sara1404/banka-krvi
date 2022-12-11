package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.isa.bloodbank.repository.UserRepository;
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
	@Autowired
	private UserRepository userRepository;

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

	public AppointmentDto createAppointment(Appointment appointment, Long adminId){
		//Appointment appointment = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
		appointment.setBloodBank(userService.findById(adminId).getBloodBank());
		appointment.setAvailable(true);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public List<AppointmentDto> getBloodBanksWithFreeAppointments(LocalDateTime startTime, int pageSize, int pageNumber){
		List<Appointment> ret = new ArrayList<>();
		for (Appointment a:  appointmentRepository.findByStartTimeAndAvailable(startTime, true)) {
			boolean exists = false;
			for (Appointment a2 : ret) {
				if (a.getBloodBank().getId() == a2.getBloodBank().getId()) exists = true;
			}
			if (!exists) ret.add(a);
		}
		return appointmentMapper.appointmentsToAppointmentDtos(ret.subList((pageNumber - 1)*pageSize, (pageNumber - 1)*pageSize + pageSize - 1));
	}

	public AppointmentDto scheduleAppointment(AppointmentDto appointmentDto, Long userId)
	{
		Appointment appointment = appointmentRepository.findById(appointmentDto.getId()).stream().findFirst().orElseThrow(UserNotFoundException::new);
		appointment.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
		appointment.setAvailable(false);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);

	}
}
