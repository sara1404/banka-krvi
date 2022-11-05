package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.repository.BloodBankRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodBankService {
	@Autowired
	private BloodBankRepository bloodBankRepository;

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

	public List<BloodBank> search(final String name, final String city) {
		if (!name.equals("") && !city.equals("")) return searchByNameAndCity(name, city);
		else if (!name.equals("")) return searchByName(name);
		else if (!city.equals("")) return searchByCity(city);
		else return bloodBankRepository.findAll();
	}

	private List<BloodBank> searchByNameAndCity(final String name, final String city){
		final List<BloodBank> bloodBanks = new ArrayList<BloodBank>();
		for (final BloodBank bloodBank : bloodBankRepository.findAll()) {
			if (bloodBank.getName().toLowerCase().startsWith(name.toLowerCase()) && bloodBank.getAddress().getCity().toLowerCase().startsWith(city.toLowerCase())) {
				bloodBanks.add(bloodBank);
			}
		}
		return bloodBanks;
	}
	private List<BloodBank> searchByName(final String name){
		final List<BloodBank> bloodBanks = new ArrayList<BloodBank>();
		for (final BloodBank bloodBank : bloodBankRepository.findAll()) {
			if (bloodBank.getName().toLowerCase().startsWith(name.toLowerCase())) {
				bloodBanks.add(bloodBank);
			}
		}
		return bloodBanks;
	}

	private List<BloodBank> searchByCity(final String city){
		final List<BloodBank> bloodBanks = new ArrayList<BloodBank>();
		for (final BloodBank bloodBank : bloodBankRepository.findAll()) {
			if (bloodBank.getAddress().getCity().toLowerCase().startsWith(city.toLowerCase())) {
				bloodBanks.add(bloodBank);
			}
		}
		return bloodBanks;
	}

}
