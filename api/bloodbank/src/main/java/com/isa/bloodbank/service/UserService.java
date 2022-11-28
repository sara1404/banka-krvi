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

	public List<UserDto> getAll(int pageNo){
		Pageable paging = PageRequest.of(pageNo, 4);
		Page<User> pagedResult = userRepository.findAll(paging);
		return userMapper.usersToUserDtos(pagedResult.toList());
	}

	public int getUserCount(){
		return userRepository.findAll().size();
	}

	public UserDto findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return userMapper.userToUserDto(userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}

	public User findUserById(Long id) {
		return (userRepository.findById(id).stream().findFirst().orElseThrow(UserNotFoundException::new));
	}
	public User update(UserDto newUserDto) {
		User user = userRepository.findById(newUserDto.getId()).get();
		User newUser = userMapper.userDtoToUser(newUserDto);

		newUser.setPassword(user.getPassword());
		newUser.setUserType(user.getUserType());

		return userRepository.save(newUser);
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

	public boolean changePassword(User user, PasswordChangeDto passwordChangeDto){
		//treba provera i da je prvi put logovan
		if(!user.getPassword().equals(passwordChangeDto.getOldPassword()))
		{
			return false;
		}
		user.setPassword(passwordChangeDto.getNewPassword());
		userRepository.save(user);
		return true;
	}

	public boolean addPenalPoints(Long id){
		User user = userRepository.findById(id).get();
		if(user == null){
			return false;
		}
		user.setPoints(user.getPoints() - 1);
		userRepository.save(user);
		return true;
	}
}
