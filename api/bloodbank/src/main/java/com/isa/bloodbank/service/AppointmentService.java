package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.FreeAppointmentDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.UserAppointmentDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.WorkingHours;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.AppointmentRepository;
import com.isa.bloodbank.repository.BloodBankRepository;
import com.isa.bloodbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
	@Autowired
	private BloodBankRepository bloodBankRepository;
	@Autowired
	private BloodBankService bloodBankService;

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

	private boolean compareWorkingHoursAndStartTime(final WorkingHours workingHours, final LocalDateTime startTime, final double duration) {
		if (startTime.getHour() < workingHours.getStartTime().getHour() ||
			(startTime.getHour() == workingHours.getStartTime().getHour() && startTime.getMinute() < workingHours.getStartTime().getMinute())) {
			return false;
		} else if (startTime.plusMinutes((long) duration).getHour() > workingHours.getEndTime().getHour() ||
			(startTime.plusMinutes((long) duration).getHour() == workingHours.getEndTime().getHour() &&
				startTime.plusMinutes((long) duration).getMinute() > workingHours.getEndTime().getMinute())) {
			return false;
		} else {
			return true;
		}
	}

	public PageDto<UserDto> findBloodBanksWithAvailableMedicalStaff(final LocalDateTime startTime, final double duration, final int pageSize,
		final int pageNumber, final String sortDirection) {
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		final List<UserDto> nurses = new ArrayList<>();
		for (final BloodBank b : bloodBankRepository.findAll(Sort.by(sortingDirection, "averageGrade"))) {
			if (!compareWorkingHoursAndStartTime(b.getWorkingHours(), startTime, duration)) {
				continue;
			}
			final List<UserDto> availableNurses = findAvailableMedicalStaff(b.getId(), startTime, duration);
			if (!availableNurses.isEmpty()) {
				nurses.add(availableNurses.get(0)); //u medicinskoj sestri pise bloodBank za koji radi
			}
		}
		final PageDto<UserDto> page = new PageDto();
		page.setTotalElements(nurses.size());
		if (nurses.isEmpty()) {
			page.setContent(nurses);
		} else if ((pageNumber + 1) * pageSize > nurses.size()) {
			page.setContent(nurses.subList(pageNumber * pageSize, nurses.size()));
		} else {
			page.setContent(nurses.subList(pageNumber * pageSize, pageNumber * pageSize + pageSize));
		}
		return page;
	}

	public List<UserDto> findAvailableMedicalStaff(final Long bloodBankId, final LocalDateTime startTime, final double duration) {
		final List<User> notAvailableNurses = new ArrayList<>();
		for (final Appointment a : appointmentRepository.findAllByBloodBankId(bloodBankId)) {
			if (startsAndEndDuringAppointment(a, startTime, duration) || startsBeforeAndEndDuringAppointment(a, startTime, duration) ||
				startsDuringAndEndAfterAppointment(a, startTime, duration)) {
				notAvailableNurses.add(a.getNurse());
			}
		}
		final List<User> availableNurses = new ArrayList<>();
		for (final User nurse : userRepository.findByBloodBankId(bloodBankId)) {
			boolean isAvailable = true;
			for (final User notAvailableNurse : notAvailableNurses) {
				if (nurse.getId() == notAvailableNurse.getId()) {
					isAvailable = false;
				}
			}
			if (isAvailable) {
				availableNurses.add(nurse);
			}
		}
		return userMapper.usersToUserDtos(availableNurses);
	}

	private boolean startsAndEndDuringAppointment(final Appointment appointment, final LocalDateTime startTime, final double duration) {
		return (startTime.isAfter(appointment.getStartTime()) &&
			startTime.plusMinutes((long) duration).isBefore(appointment.getStartTime().plusMinutes((long) appointment.getDuration()))) ||
			startTime.isEqual(appointment.getStartTime()) ||
			startTime.plusMinutes((long) duration).isEqual(appointment.getStartTime().plusMinutes((long) appointment.getDuration()));
	}

	private boolean startsBeforeAndEndDuringAppointment(final Appointment a, final LocalDateTime startTime, final double duration) {
		return startTime.isBefore(a.getStartTime()) && startTime.plusMinutes((long) duration).isAfter(a.getStartTime());
	}

	private boolean startsDuringAndEndAfterAppointment(final Appointment a, final LocalDateTime startTime, final double duration) {
		return startTime.isBefore(a.getStartTime().plusMinutes((long) a.getDuration())) &&
			startTime.plusMinutes((long) duration).isAfter((a.getStartTime().plusMinutes(((long) a.getDuration()))));
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
		appointment.setBloodBank(userService.findById(adminId).getBloodBank());
		appointment.setAvailable(true);
		appointment.setFinished(false);
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

	public List<AppointmentDto> getAppointments(final int month, final int year, final Long administratorId) {
		final User administator = userService.findUserById(administratorId);
		final List<Appointment> appointments = appointmentRepository.findAllByStartTimeMonthValueAndStartTime_Year(month, year,
			administator.getBloodBank().getId());
		return appointmentMapper.appointmentsToAppointmentDtos(appointments);
	}

	public PageDto<AppointmentDto> getBloodBanksWithFreeAppointments(final LocalDateTime startTime, final int pageSize, final int pageNumber,
		final String sortDirection) {
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		final List<Appointment> ret = new ArrayList<>();
		for (final Appointment a : appointmentRepository.findByStartTimeAndAvailable(startTime, true,
			Sort.by(sortingDirection, "bloodBank.averageGrade"))) {
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
		final Appointment appointment = appointmentRepository.findById(appointmentDto.getId()).stream().findFirst()
			.orElseThrow(UserNotFoundException::new);
		appointment.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
		appointment.setAvailable(false);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public AppointmentDto scheduleAppointmentById(final Long appointmentId, final Long userId) {
		final Appointment appointment = appointmentRepository.findById(appointmentId).stream().findFirst().orElseThrow(UserNotFoundException::new);
		appointment.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
		appointment.setAvailable(false);
		appointment.setFinished(false);
		appointmentRepository.save(appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	@Transactional
	public AppointmentDto userCreatesAppointment(final AppointmentDto appointmentDto, final Long userId) {
		System.out.println("udara " + appointmentDto.getBloodBank().getId());

		final Appointment appointment = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
		BloodBank current = bloodBankRepository.findById(appointment.getBloodBank().getId()).get();
		System.out.print(current.getId());
		System.out.println(current.getAvailable());
		//if(!current.getAvailable())
			//throw new ObjectOptimisticLockingFailureException(BloodBank.class, appointment.getBloodBank());
		current.setAvailable(false);
		bloodBankRepository.save(current);
		appointment.setAvailable(false);
		appointment.setFinished(false);
		appointment.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
		appointment.setBloodBank(current);
		appointmentRepository.save(appointment);
		current.setAvailable(true);
		bloodBankRepository.save(current);

		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public List<Appointment> getPredefined(final int pageSize, final int pageNum) {
		return appointmentRepository.getPredefined(LocalDateTime.now(), PageRequest.of(pageNum, pageSize));
	}

	public List<Appointment> getPersonalAppointments(final Long userId, final int pageSize, final int pageNum) {
		return appointmentRepository.getPersonal(userId, PageRequest.of(pageNum, pageSize));
	}

	public boolean cancelAppointment(final Long appointmentId, final Long userId) {
		final Appointment appointment = appointmentRepository.findById(appointmentId).stream().findFirst().orElseThrow(UserNotFoundException::new);
		if (!appointment.isFinished() && appointment.getUser().getId() == userId &&
			LocalDateTime.now().plusHours(24).isBefore(appointment.getStartTime())) {
			appointment.setAvailable(true);
			appointment.setUser(null);
			appointmentRepository.save(appointment);
			return true;
		}
		return false;
	}

	public boolean canUserScheduleAppointment(final Long userId, final LocalDateTime startTime) {
		final List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);
		boolean canSchedule = true;
		for (final Appointment a : appointments) {
			if (a.getStartTime().plusMonths(6).isAfter(startTime) || startTime.isBefore(a.getStartTime())) {
				canSchedule = false;
			}
		}
		final User user = userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new);
		if (user.getPoints() > 2) {
			canSchedule = false;
		}
		return canSchedule;
	}

}