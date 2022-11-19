package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.mapping.PageMapper;
import com.isa.bloodbank.repository.BloodBankRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BloodBankService {
	@Autowired
	private BloodBankRepository bloodBankRepository;
	@Autowired
	private BloodBankMapper bloodBankMapper;
	@Autowired
	private PageMapper pageMapper;

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
	public BloodBank update(BloodBankDto bloodBankDto) {
		BloodBank bloodBank = bloodBankMapper.bloodBankDtoToBloodBank(bloodBankDto);
		findById(bloodBank.getId());
		return bloodBankRepository.save(bloodBank);
	}

	public Page<BloodBank> searchAndFilter(final String name, final String city, final double averageGrade, final int pageSize, final int pageNumber) {
		final Page<BloodBank> bloodBanks = search(name, city, pageSize, pageNumber);
		/*if (averageGrade != 0) {
			return bloodBankMapper.bloodBanksToBloodBankDtos(filter(bloodBanks, averageGrade));
		} else {
			return bloodBankMapper.bloodBanksToBloodBankDtos(bloodBanks);
		}*/
		return bloodBanks;
	}

	private Page<BloodBank> search(final String name, final String city, final int pageSize, final int pageNumber) {
		if (!name.equals("") && !city.equals("")) {
			return bloodBankRepository.findByNameContainingIgnoreCaseAndAddressId_CityContainingIgnoreCase(name, city, PageRequest.of(pageNumber, pageSize));
			//return pageMapper.pageToPageDto(page);
		} else if (!name.equals("")) {
			return bloodBankRepository.findByNameContainingIgnoreCase(name, PageRequest.of(pageNumber, pageSize));
			//return pageMapper.pageToPageDto(page);
		} else if (!city.equals("")) {
			return bloodBankRepository.findByAddressId_CityContainingIgnoreCase(city, PageRequest.of(pageNumber, pageSize));
			//return pageMapper.pageToPageDto(page);
		} else {
			return bloodBankRepository.findAll(PageRequest.of(pageNumber, pageSize));
			//return pageMapper.pageToPageDto(page);
		}
	}

	/*private PageDto<BloodBank> filter(final PageDto<BloodBank> bloodBanks, final double averageGrade) {
		final List<BloodBank> filteredBloodBanks = new ArrayList<BloodBank>();
		for (final BloodBank bloodBank : bloodBanks) {
			if (bloodBank.getAverageGrade() >= averageGrade) {
				filteredBloodBanks.add(bloodBank);
			}
		}
		return filteredBloodBanks;
	}*/

	public BloodBankDto registerBloodBank(final BloodBankDto bloodBank) {
		return bloodBankMapper.bloodBankToBloodBankDto(bloodBankRepository.save(bloodBankMapper.bloodBankDtoToBloodBank(bloodBank)));
	}

}
