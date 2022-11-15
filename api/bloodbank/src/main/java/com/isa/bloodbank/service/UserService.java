package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AdministratorDto;
import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.UserType;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.BloodBankRepository;
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
	@Autowired
	private BloodBankRepository bloodBankRepository;
	@Autowired
	private UserMapper userMapper;

	public List<AdministratorDto> findByBloodBankId(final Long bloodBankId, final Long administratorId) {
		final List<User> bloodBanks = new ArrayList<User>();
		for (final User user : userRepository.findByBloodBankId(bloodBankId)) {
			if (user.getId() != administratorId) {
				bloodBanks.add(user);
			}
		}
		return userMapper.usersToAdministratorDtos(bloodBanks);
		//return bloodBanks;//userRepository.findByBloodBankId(bloodBankId);
	}
	public boolean checkIfEmailAlreadyInUse(final String email) {
		return userRepository.findByEmail(email) != null;
	}

	public User registerUser(final User user) {
		return userRepository.save(user);
	}
	public RegisterUserDto registerCenterAdmin(final RegisterUserDto centerAdmin) {
		centerAdmin.setUserType(UserType.ADMIN_CENTER);
		//centerAdmin.setBloodBank(bloodBankRepository.findBloodBankByName(centerAdmin.getBloodBankName()));
		return userMapper.userToRegisterUserDto(userRepository.save(userMapper.registerUserDtoToUser(centerAdmin)));
	}

	public List<UserDto> search(final String name, final String lastName) {
		final List<User> users = userRepository.getUsersByFirstNameContainsAndLastNameContains(name, lastName);
		return userMapper.usersToUserDtos(users);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public List<UserDto> getAllDto(){
		return userMapper.usersToUserDtos(userRepository.findAll());
	}

	public UserDto findById(Long id) {
		return userMapper.userToUserDto(userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}

	public User findUserById(Long id) {
		return (userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}
	public User update(UserDto userDto) {
		User user = userMapper.userDtoToUser(userDto);
		findById(user.getId());
		return userRepository.save(user);
	}
	public List<UserDto> getAvailableCenterAdmins(){
		return userMapper.usersToUserDtos(userRepository.getUsersByUserTypeAndBloodBankIsNull(UserType.ADMIN_CENTER));
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
