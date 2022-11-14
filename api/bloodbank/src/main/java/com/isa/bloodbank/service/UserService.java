package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.UserType;
import com.isa.bloodbank.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

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

	public boolean checkIfEmailAlreadyInUse(final String email) {
		return userRepository.findByEmail(email) != null;
	}

	public User registerUser(final User user) {
		return userRepository.save(user);
	}

	public List<User> search(final String name, final String lastName) {
		final List<User> users = userRepository.getUsersByUserTypeAndFirstNameContainsAndLastNameContains(UserType.REGISTERED, name, lastName);
		return users;
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		var user = userRepository.findByEmail(email);
		if(user == null){
			throw new UsernameNotFoundException(email);
		}
		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).authorities(user.getUserType().toString()).build();
		return userDetails;
	}
}
