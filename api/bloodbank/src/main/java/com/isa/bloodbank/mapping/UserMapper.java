package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.entity.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User registerUserDtoToUser(RegisterUserDto user);
}
