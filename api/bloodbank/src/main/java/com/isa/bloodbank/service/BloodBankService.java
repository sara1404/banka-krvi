package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.WorkingHours;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.mapping.WorkingHoursMapper;
import com.isa.bloodbank.repository.AppointmentRepository;
import com.isa.bloodbank.repository.BloodBankRepository;
import com.isa.bloodbank.repository.WorkingHoursRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class BloodBankService {
	@Autowired
	private BloodBankRepository bloodBankRepository;
	@Autowired
	private BloodBankMapper bloodBankMapper;
	@Autowired
	private WorkingHoursMapper workingHoursMapper;
	@Autowired
	private WorkingHoursRepository workingHoursRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private AppointmentRepository appointmentRepository;

	public List<BloodBankDto> findAll() {
		return bloodBankMapper.bloodBanksToBloodBankDtos(bloodBankRepository.findAll());
	}

	public BloodBank findById(final Long id) {
		return bloodBankRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	/*
	public BloodBank findByAdministratorId(final Long id) {
		return bloodBankRepository.findByAdministratorId(id);
	}
	*/
	public BloodBank update(final BloodBankDto bloodBankDto) {
		final BloodBank bloodBank = bloodBankMapper.bloodBankDtoToBloodBank(bloodBankDto);
		bloodBank.setWorkingHours(findById(bloodBank.getId()).getWorkingHours());
		System.out.println(bloodBankDto.getId());
		return bloodBankRepository.save(bloodBank);
	}

	@RateLimiter(name = "findAllBloodBanks", fallbackMethod = "findAllBloodBanksFallback")
	public Page<BloodBank> searchAndFilter(final String name, final String city, final double averageGrade, final double lng, final double lat,
		final double distance, final int pageSize, final int pageNumber, String sortBy, final String sortDirection) {
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Direction.ASC : Direction.DESC;
		if (sortBy == null || sortBy.equals("")) {
			sortBy = "name";
		}
		if (sortBy.equals("averageGrade")) {
			sortBy = "average_grade";
		}
		if (sortBy.equals("address.city")) {
			sortBy = "a.city";
		}
		return bloodBankRepository.filterBloodBanks(lat, lng, distance, name, averageGrade, city,
			PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
	}

	public BloodBankDto registerBloodBank(final BloodBankDto bloodBank) {
		return bloodBankMapper.bloodBankToBloodBankDto(bloodBankRepository.save(bloodBankMapper.bloodBankDtoToBloodBank(bloodBank)));
	}

	public WorkingHours getWorkingHours(final Long adminId) {
		final WorkingHours workingHours = workingHoursRepository.getById(userService.findById(adminId).getBloodBank().getId());
		return workingHours;
	}

	public void updateAvailability(final boolean value, final BloodBank bank) {
		bank.setAvailable(value);
		bloodBankRepository.save(bank);

	}

	// Metoda koja ce se pozvati u slucaju RequestNotPermitted exception-a
	public Page<BloodBank> findAllBloodBanksFallback(final RequestNotPermitted rnp) {
		System.out.println("Prevazidjen broj poziva u ogranicenom vremenskom intervalu");
		// Samo prosledjujemo izuzetak -> global exception handler koji bi ga obradio :)
		throw rnp;
	}
}
