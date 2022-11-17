package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.repository.BloodBankRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodBankService {
	@Autowired
	private BloodBankRepository bloodBankRepository;
	@Autowired
	private BloodBankMapper bloodBankMapper;

	public List<BloodBank> findAll() {
		return bloodBankRepository.findAll();
	}

	public BloodBank findById(final Long id) {
		return bloodBankRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	/*
	public BloodBank findByAdministratorId(final Long id) {
		return bloodBankRepository.findByAdministratorId(id);
	}
	*/
	public BloodBank update(final BloodBank bloodBank) {
		findById(bloodBank.getId());
		return bloodBankRepository.save(bloodBank);
	}

	public List<BloodBank> searchAndFilter(final String name, final String city, final double averageGrade) {
		final List<BloodBank> bloodBanks = search(name, city);
		if (averageGrade != 0) {
			return filter(bloodBanks, averageGrade);
		} else {
			return bloodBanks;
		}
	}

	private List<BloodBank> search(final String name, final String city) {
		if (!name.equals("") && !city.equals("")) {
			return bloodBankRepository.findAllByNameContainingAndAddressId_CityContaining(name, city);
		} else if (!name.equals("")) {
			return bloodBankRepository.findByNameContaining(name);
		} else if (!city.equals("")) {
			return bloodBankRepository.findAllByAddressId_CityContaining(city);
		} else {
			return bloodBankRepository.findAll();
		}
	}

	private List<BloodBank> filter(final List<BloodBank> bloodBanks, final double averageGrade) {
		final List<BloodBank> filteredBloodBanks = new ArrayList<BloodBank>();
		for (final BloodBank bloodBank : bloodBanks) {
			if (bloodBank.getAverageGrade() >= averageGrade) {
				filteredBloodBanks.add(bloodBank);
			}
		}
		return filteredBloodBanks;
	}

	public BloodBankDto registerBloodBank(final BloodBankDto bloodBank) {
		return bloodBankMapper.bloodBankToBloodBankDto(bloodBankRepository.save(bloodBankMapper.bloodBankDtoToBloodBank(bloodBank)));
	}

}
