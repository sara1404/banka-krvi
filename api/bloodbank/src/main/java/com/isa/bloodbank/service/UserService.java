package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.AdministratorDto;
import com.isa.bloodbank.dto.PasswordChangeDto;
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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private BloodBankRepository bloodBankRepository;

	public List<AdministratorDto> findByBloodBankId(final Long bloodBankId, final Long administratorId) {
		final List<User> bloodBanks = new ArrayList<User>();
		for (final User user : userRepository.findByBloodBankId(bloodBankId)) {
			if (user.getId() != administratorId) {
				bloodBanks.add(user);
			}
		}
		return userMapper.usersToAdministratorDtos(bloodBanks);
	}

	public boolean checkIfEmailAlreadyInUse(final String email) {
		return userRepository.findByEmail(email) != null;
	}

	public User registerUser(final User user) {
		user.setUserType(UserType.REGISTERED);
		final String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

	public RegisterUserDto registerCenterAdmin(final RegisterUserDto centerAdmin) {
		centerAdmin.setUserType(UserType.ADMIN_CENTER);
		final String encodedPassword = encoder.encode(centerAdmin.getPassword());
		centerAdmin.setPassword(encodedPassword);
		//centerAdmin.setBloodBank(bloodBankRepository.findBloodBankByName(centerAdmin.getBloodBankName()));
		return userMapper.userToRegisterUserDto(userRepository.save(userMapper.registerUserDtoToUser(centerAdmin)));
	}

	public List<UserDto> search(final String name, final String lastName) {
		final List<User> users = userRepository.getUsersByFirstNameContainsAndLastNameContains(name, lastName);
		return userMapper.usersToUserDtos(users);
	}

	public List<UserDto> getAll(final int pageNo) {
		final Pageable paging = PageRequest.of(pageNo, 4);
		final Page<User> pagedResult = userRepository.findAll(paging);
		return userMapper.usersToUserDtos(pagedResult.toList());
	}

	public int getUserCount() {
		return userRepository.findAll().size();
	}

	public UserDto findById(final Long id) {
		final Optional<User> user = userRepository.findById(id);
		return userMapper.userToUserDto(userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}

	public User findUserById(final Long id) {
		return (userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}

	public User update(final UserDto newUserDto) {
		final User user = userRepository.findById(newUserDto.getId()).get();
		final User newUser = userMapper.userDtoToUser(newUserDto);

		newUser.setPassword(user.getPassword());
		newUser.setUserType(user.getUserType());

		return userRepository.save(newUser);
	}

	public List<UserDto> getAvailableCenterAdmins() {
		return userMapper.usersToUserDtos(userRepository.getUsersByUserTypeAndBloodBankIsNull(UserType.ADMIN_CENTER));
	}

	public User findByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public boolean changePassword(final User user, final PasswordChangeDto passwordChangeDto) {
		//treba provera i da je prvi put logovan
		if (encoder.matches(user.getPassword(), passwordChangeDto.getOldPassword())) {
			return false;
		}
		user.setPassword(encoder.encode(passwordChangeDto.getNewPassword()));
		userRepository.save(user);
		return true;
	}

	public boolean addPenalPoints(final Long id) {
		final User user = userRepository.findById(id).get();
		if (user == null) {
			return false;
		}
		user.setPoints(user.getPoints() - 1);
		userRepository.save(user);
		return true;
	}
}
