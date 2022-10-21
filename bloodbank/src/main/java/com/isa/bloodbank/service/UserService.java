package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.repository.UserRepository;

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

	public User findById(Long id) {
		return userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new);
	}

	public User create(User user) {
		return userRepository.save(user);
	}

	public User update(User user) {
		findById(user.getId());
		return userRepository.save(user);
	}

	public void delete(Long userId) {
		userRepository.deleteById(userId);
	}

	public List<User> findByBloodType(BloodType bloodType){
		return userRepository.findUsersByBloodType(bloodType);
	}
}
