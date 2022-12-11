package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.WorkingHoursDto;
import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.WorkingHours;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.mapping.PageMapper;
import com.isa.bloodbank.mapping.WorkingHoursMapper;
import com.isa.bloodbank.repository.AppointmentRepository;
import com.isa.bloodbank.repository.BloodBankRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.isa.bloodbank.repository.WorkingHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

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
		findById(bloodBank.getId());
		return bloodBankRepository.save(bloodBank);
	}

	public Page<BloodBank> searchAndFilter(final String name, final String city, final double averageGrade, final int pageSize, final int pageNumber,
		String sortBy, final String sortDirection) {
		final Sort.Direction sortingDirection = sortDirection.equals("ASC") ? Direction.ASC : Direction.DESC;
		if (sortBy == null || sortBy.equals("")) {
			sortBy = "name";
		}
		if (!name.equals("") && !city.equals("") && averageGrade != 0) {
			return bloodBankRepository.findByNameContainingIgnoreCaseAndAverageGradeGreaterThanEqualAndAddressId_CityContainingIgnoreCase(name, averageGrade,
				city, PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else if (!name.equals("") && !city.equals("")) {
			return bloodBankRepository.findByNameContainingIgnoreCaseAndAddressId_CityContainingIgnoreCase(name, city,
				PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else if (!name.equals("") && averageGrade != 0) {
			return bloodBankRepository.findByNameContainingIgnoreCaseAndAverageGradeGreaterThanEqual(name, averageGrade,
				PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else if (!city.equals("") && averageGrade != 0) {
			return bloodBankRepository.findByAverageGradeGreaterThanEqualAndAddressId_CityContainingIgnoreCase(averageGrade, city,
				PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else if (!name.equals("")) {
			return bloodBankRepository.findByNameContainingIgnoreCase(name, PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else if (!city.equals("")) {
			return bloodBankRepository.findByAddressId_CityContainingIgnoreCase(city, PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else if (averageGrade != 0) {
			return bloodBankRepository.findByAverageGradeGreaterThanEqual(averageGrade,
				PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		} else {
			return bloodBankRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortingDirection, sortBy)));
		}
	}

	public BloodBankDto registerBloodBank(final BloodBankDto bloodBank) {
		return bloodBankMapper.bloodBankToBloodBankDto(bloodBankRepository.save(bloodBankMapper.bloodBankDtoToBloodBank(bloodBank)));
	}

	public WorkingHours getWorkingHours(Long adminId) {
		WorkingHours workingHours = workingHoursRepository.getById(userService.findById(adminId).getBloodBank().getId());
		return workingHours;
	}
}
