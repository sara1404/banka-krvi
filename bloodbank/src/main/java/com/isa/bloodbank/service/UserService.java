package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(final Long id) {
		return userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	public User create(final User user) {
		return userRepository.save(user);
	}

	public User update(final User user) {
		findById(user.getId());
		return userRepository.save(user);
	}

	public void delete(final Long userId) {
		userRepository.deleteById(userId);
	}

	public List<User> findByBloodType(final BloodType bloodType) {
		return userRepository.findUsersByBloodType(bloodType);
	}

	public List<User> findByBloodBankId(final Long bloodBankId, final Long administratorId) {
		final List<User> bloodBanks = new ArrayList<User>();
		for (final User user : userRepository.findByBloodBankId(bloodBankId)) {
			if (user.getId() != administratorId) {
				bloodBanks.add(user);
			}
		}
		return bloodBanks;//userRepository.findByBloodBankId(bloodBankId);
	}
}
