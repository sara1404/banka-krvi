package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.DonationSurveyDto;
import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.DonationSurvey;
import com.isa.bloodbank.entity.User;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper{
	User userDtoToUser(UserDto user);
	UserDto userToUserDto(User user);
	User registerUserDtoToUser(RegisterUserDto user);
	RegisterUserDto userToRegisterUserDto(User user);

	List<UserDto> usersToUserDtos(List<User> users);
	List<User> userDtosToUsers(List<UserDto> users);

	DonationSurvey surveyDtoToSurvey(DonationSurveyDto surveyDto);
}

