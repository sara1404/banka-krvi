package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.UserType;
import com.isa.bloodbank.exception.UserNotFoundException;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.repository.BloodBankRepository;
import com.isa.bloodbank.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BloodBankRepository bloodBankRepository;
	@Autowired
	private UserMapper userMapper;

	public List<User> findByBloodBankId(final Long bloodBankId, final Long administratorId) {
		final List<User> bloodBanks = new ArrayList<User>();
		for (final User user : userRepository.findByBloodBankId(bloodBankId)) {
			if (user.getId() != administratorId) {
				bloodBanks.add(user);
			}
		}
		return bloodBanks;//userRepository.findByBloodBankId(bloodBankId);
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

	public List<UserDto> getAll(){
		return userMapper.usersToUserDtos(userRepository.findAll());
	}

	public UserDto findById(Long id) {
		return userMapper.userToUserDto(userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}

	public User update(UserDto userDto) {
		User user = userMapper.userDtoToUser(userDto);
		findById(user.getId());
		return userRepository.save(user);
	}

	public List<UserDto> getAvailableCenterAdmins(){
		return userMapper.usersToUserDtos(userRepository.getUsersByUserTypeAndBloodBankIsNull(UserType.ADMIN_CENTER));
	}
}
