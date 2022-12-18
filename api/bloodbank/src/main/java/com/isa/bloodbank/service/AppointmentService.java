package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.repository.AppointmentRepository;
import com.isa.bloodbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	public List<UserAppointmentDto> findAllByUserId(final Long userId, final User loggedUser) {
		final List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
		final List<Appointment> availableAppointments = new ArrayList<Appointment>();
		for (final Appointment appointment : appointments) {
			if (appointment.isFinished() == false && appointment.getStartTime().compareTo(LocalDateTime.now()) > 0 &&
				appointment.getBloodBank() == loggedUser.getBloodBank()) {
				availableAppointments.add(appointment);
			}
		}
		return appointmentMapper.appointmentsToUserAppointmentDto(availableAppointments);
	}

	public Boolean finishAppointment(final Long id) {
		final Appointment appointment = appointmentRepository.findById(id).get();
		if (appointment == null) {
			return false;
		}
		appointment.setFinished(true);
		appointmentRepository.save(appointment);
		return true;
	}

	public AppointmentDto createAppointment(final Appointment appointment, final Long adminId) {
		//Appointment appointment = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
		appointment.setBloodBank(userService.findById(adminId).getBloodBank());
		appointment.setAvailable(true);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public Appointment findById(final Long id) {
		return appointmentRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	public Appointment updateAppointmentInfo(final Long id, final AppointmentInfo appointmentInfo) {
		final Appointment appointment = findById(id);
		appointment.setAppointmentInfo(appointmentInfo);
		return appointmentRepository.save(appointment);
	}

	public PageDto<AppointmentDto> getBloodBanksWithFreeAppointments(final LocalDateTime startTime, final int pageSize, final int pageNumber,
		final String sortDirection) {
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		final List<Appointment> ret = new ArrayList<>();
		for (final Appointment a : appointmentRepository.findByStartTimeAndAvailable(startTime, true, Sort.by(sortingDirection, "bloodBank.averageGrade"))) {
			boolean exists = false;
			for (final Appointment a2 : ret) {
				if (a.getBloodBank().getId() == a2.getBloodBank().getId()) {
					exists = true;
				}
			}
			if (!exists) {
				ret.add(a);
			}
		}
		final PageDto<AppointmentDto> page = new PageDto();
		page.setTotalElements(ret.size());
		final List<AppointmentDto> retDto = appointmentMapper.appointmentsToAppointmentDtos(ret);
		if (ret.isEmpty()) {
			page.setContent(retDto);
		} else if ((pageNumber + 1) * pageSize > ret.size()) {
			page.setContent(retDto.subList(pageNumber * pageSize, ret.size()));
		} else {
			page.setContent(retDto.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize));
		}
		return page;
	}

	public AppointmentDto scheduleAppointment(final AppointmentDto appointmentDto, final Long userId) {
		final Appointment appointment = appointmentRepository.findById(appointmentDto.getId()).stream().findFirst().orElseThrow(UserNotFoundException::new);
		appointment.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
		appointment.setAvailable(false);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}
}
