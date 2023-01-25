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
import com.isa.bloodbank.mailer.MailService;
import com.isa.bloodbank.mapping.AppointmentMapper;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.AppointmentRepository;
import com.isa.bloodbank.repository.BloodBankRepository;
import com.isa.bloodbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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
	private DonationSurveyService donationSurveyService;
	@Autowired
	private QrCodeService qrCodeService;
	@Autowired
	MailService mailService;

	public List<FreeAppointmentDto> findAvailableAppointments(final Long bloodBankId) {
		final List<Appointment> availableAppointments = new ArrayList<Appointment>();
		for (final Appointment appointment : appointmentRepository.findAllByBloodBankId(bloodBankId)) {
			if ((appointment.isAvailable() == true && appointment.getStartTime().compareTo(LocalDateTime.now()) > 0)) {
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

	@Transactional(readOnly = false)
	public List<UserDto> findAvailableMedicalStaff(final Long bloodBankId, final LocalDateTime startTime, final double duration) {
		final List<User> notAvailableNurses = new ArrayList<>();
		for (final Appointment a : appointmentRepository.findAllByBloodBankId(bloodBankId)) {
			//System.out.println("prvi");
			if (startsAndEndDuringAppointment(a, startTime, duration) || startsBeforeAndEndDuringAppointment(a, startTime, duration) ||
				startsDuringAndEndAfterAppointment(a, startTime, duration)) {
				notAvailableNurses.add(a.getNurse());
			}
		}
		final List<User> availableNurses = new ArrayList<>();
		for (final User nurse : userRepository.findByBloodBankId(bloodBankId)) {
			//System.out.println("drugi");
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

	@Transactional(readOnly = false)
	public Boolean finishAppointment(final Long id) {
		final Appointment appointment = appointmentRepository.findById(id).get();
		if (appointment == null) {
			return false;
		}
		appointment.setFinished(true);
		appointmentRepository.save(appointment);
		return true;
	}

	@Transactional(readOnly = false)
	public AppointmentDto createAppointment(final Appointment appointment, final Long adminId) {

		BloodBank current;
		try {
			appointment.setBloodBank(userService.findById(adminId).getBloodBank());
			current = bloodBankRepository.findById(appointment.getBloodBank().getId()).get();
		} catch (final Exception e) {
			current = bloodBankRepository.getReferenceById(16l);
			appointment.setBloodBank(current);
		}
		if (!appointmentRepository.findAllByBloodBankId(current.getId()).stream().filter(x -> x.getStartTime().compareTo(appointment.getStartTime()) == 0)
			.toList()
			.isEmpty()) {
			throw new ObjectOptimisticLockingFailureException(BloodBank.class, appointment.getBloodBank());
		}
		appointment.setAvailable(true);
		appointment.setFinished(false);
		if (findAvailableMedicalStaff(appointment.getBloodBank().getId(), appointment.getStartTime(), appointment.getDuration()).size() != 0) {
			appointmentRepository.save(appointment);
		}
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public Appointment findById(final Long id) {
		return appointmentRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	@Transactional(readOnly = false)
	public Appointment updateAppointmentInfo(final Long id, final AppointmentInfo appointmentInfo) {
		final Appointment appointment = findById(id);
		appointment.setAppointmentInfo(appointmentInfo);
		return appointmentRepository.save(appointment);
	}

	@Cacheable(key = "#month + '_' + #year + '_' + #adminstatorId", unless = "#result == null", cacheNames = "appointments")
	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public List<AppointmentDto> getAppointments(final int month, final int year, final Long administratorId) {
		System.out.println("appointments triggered");
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public AppointmentDto scheduleAppointment(final AppointmentDto appointmentDto, final Long userId) throws Exception {
		final Appointment app = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
		if (canUserScheduleAppointment(userId, app.getStartTime()) && appointmentRepository.getPersonal(userId).size() <= 1) {
			app.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
			app.setAvailable(false);
			app.setAppointmentInfo(appointmentRepository.getById(appointmentDto.getId()).getAppointmentInfo());
			appointmentRepository.save(app);
			qrCodeService.generateAppointmentQrCode(app);
			mailService.sendEmailWithQrCode(app.getUser().getEmail(), app);
			return appointmentMapper.appointmentToAppointmentDto(app);
		}
		return null;
	}

	@Transactional
	public AppointmentDto userCreatesAppointment(final AppointmentDto appointmentDto, final Long userId) {
		System.out.println("udara " + appointmentDto.getBloodBank().getId());
		final Appointment appointment = appointmentMapper.appointmentDtoToAppointment(appointmentDto);
		final BloodBank current = bloodBankRepository.findById(appointment.getBloodBank().getId()).get();
		if (!appointmentRepository.findAllByBloodBankId(current.getId()).stream().filter(x -> x.getStartTime().compareTo(appointment.getStartTime()) == 0)
			.toList()
			.isEmpty()) {
			throw new ObjectOptimisticLockingFailureException(BloodBank.class, appointment.getBloodBank());
		}
		//if(!current.getAvailable())
		//throw new ObjectOptimisticLockingFailureException(BloodBank.class, appointment.getBloodBank());
		current.setAvailable(false);
		bloodBankRepository.save(current);

		System.out.print(current.getId());
		System.out.println(current.getAvailable());

		appointment.setAvailable(false);
		appointment.setFinished(false);
		appointment.setUser(userRepository.findById(userId).stream().findFirst().orElseThrow(UserNotFoundException::new));
		appointment.setBloodBank(current);
		appointmentRepository.save(appointment);
		current.setAvailable(true);
		bloodBankRepository.save(current);
		qrCodeService.generateAppointmentQrCode(appointment);
		mailService.sendEmailWithQrCode(appointment.getUser().getEmail(), appointment);
		return appointmentMapper.appointmentToAppointmentDto(appointment);
	}

	public List<Appointment> getPredefined(final int pageSize, final int pageNum, final String sortDirection) {
		final Direction direction = sortDirection.toUpperCase().equals("ASC") ? Direction.ASC : Direction.DESC;
		return appointmentRepository.getPredefined(LocalDateTime.now(), PageRequest.of(pageNum, pageSize, Sort.by(direction, "startTime")));
	}

	public List<Appointment> getPersonalAppointments(final Long userId, final int pageSize, final int pageNum) {
		return appointmentRepository.getPersonal(userId, PageRequest.of(pageNum, pageSize));
	}

	@Transactional(readOnly = false)
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

	public PageDto<AppointmentDto> getAllBloodUsers(final Long bloodBankId, final int pageSize, final int pageNumber, final String sortDirection,
		final String sortBy) {
		//final List<Appointment> appointments = appointmentRepository.findAllByBloodBankIdAndFinishedTrue(bloodBankId);
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Direction.ASC : Direction.DESC;

		final List<Appointment> ret = appointmentRepository.findAllByBloodBankIdAndFinishedTrue(bloodBankId, Sort.by(sortingDirection, sortBy));
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
		System.out.println(page.getContent());
		return page;
	}

}