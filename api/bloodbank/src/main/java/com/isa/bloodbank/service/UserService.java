package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.UserType;
import com.isa.bloodbank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findByBloodBankId(final Long bloodBankId, final Long administratorId) {
		final List<User> bloodBanks = new ArrayList<User>();
		for (final User user : userRepository.findByBloodBankId(bloodBankId)) {
			if (user.getId() != administratorId) {
				bloodBanks.add(user);
			}
		}
		return bloodBanks;//userRepository.findByBloodBankId(bloodBankId);
	}

	public User registerCenterAdmin(final User centerAdmin) {
		return userRepository.save(centerAdmin);
	}

	public List<User> search(final String name, final String lastName) {
		final List<User> users = userRepository.getUsersByFirstNameContainsAndLastNameContains(name, lastName);
		return users;
	}

	public List<User> getAll(){
		return userRepository.findAll();
	}
}
