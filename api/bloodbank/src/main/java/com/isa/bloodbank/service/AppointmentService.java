package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.isa.bloodbank.repository.UserRepository;
import net.bytebuddy.asm.Advice;
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
	@Autowired
	private UserMapper userMapper;

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

	public List<UserDto> findAvailableMedicalStaff(Long adminId, LocalDateTime startTime, double duration) {
		BloodBank bloodBank = userService.findById(adminId).getBloodBank();
		List<User> notAvailableNurses = new ArrayList<>();
		for (Appointment a: appointmentRepository.findAllByBloodBankId(bloodBank.getId())){
			if (startsAndEndDuringAppointment(a, startTime, duration) || startsBeforeAndEndDuringAppointment(a, startTime, duration) || startsDuringAndEndAfterAppointment(a, startTime, duration)){
				notAvailableNurses.add(a.getNurse());
			}
		}
		List<User> availableNurses = new ArrayList<>();
		for (User nurse : userRepository.findByBloodBankId(bloodBank.getId())){
			boolean isAvailable = true;
			for (User notAvailableNurse : notAvailableNurses){
				if (nurse.getId() == notAvailableNurse.getId()) isAvailable = false;
			}
			if (isAvailable) availableNurses.add(nurse);
		}
		return userMapper.usersToUserDtos(availableNurses);
	}

	private boolean startsAndEndDuringAppointment(Appointment appointment, LocalDateTime startTime, double duration) {
		return (startTime.isAfter(appointment.getStartTime()) && startTime.plusMinutes((long)duration).isBefore(appointment.getStartTime().plusMinutes((long)appointment.getDuration()))) ||
				startTime.isEqual(appointment.getStartTime()) ||
				startTime.plusMinutes((long)duration).isEqual(appointment.getStartTime().plusMinutes((long)appointment.getDuration()));
	}

	private boolean startsBeforeAndEndDuringAppointment(Appointment a, LocalDateTime startTime, double duration){
		return startTime.isBefore(a.getStartTime()) && startTime.plusMinutes((long)duration).isAfter(a.getStartTime());
	}

	private boolean startsDuringAndEndAfterAppointment(Appointment a, LocalDateTime startTime, double duration){
		return startTime.isBefore(a.getStartTime().plusMinutes((long)a.getDuration())) && startTime.plusMinutes((long)duration).isAfter((a.getStartTime().plusMinutes(((long)a.getDuration()))));
	}
	public AppointmentDto createAppointment(Appointment appointment, Long adminId){
		appointment.setBloodBank(userService.findById(adminId).getBloodBank());
		appointment.setAvailable(true);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public PageDto<AppointmentDto> getBloodBanksWithFreeAppointments(LocalDateTime startTime, int pageSize, int pageNumber, final String sortDirection){
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		List<Appointment> ret = new ArrayList<>();
		for (Appointment a:  appointmentRepository.findByStartTimeAndAvailable(startTime, true, Sort.by(sortingDirection, "bloodBank.averageGrade"))) {
			boolean exists = false;
			for (Appointment a2 : ret) {
				if (a.getBloodBank().getId() == a2.getBloodBank().getId()) exists = true;
			}
			if (!exists) ret.add(a);
		}
		PageDto<AppointmentDto> page = new PageDto();
		page.setTotalElements(ret.size());
		List<AppointmentDto> retDto = appointmentMapper.appointmentsToAppointmentDtos(ret);
		if (ret.isEmpty()) page.setContent(retDto);
		else if ((pageNumber + 1)*pageSize > ret.size()) page.setContent(retDto.subList(pageNumber*pageSize, ret.size()));
		else page.setContent(retDto.subList(pageNumber*pageSize, pageNumber*pageSize + pageSize));
		return page;
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
