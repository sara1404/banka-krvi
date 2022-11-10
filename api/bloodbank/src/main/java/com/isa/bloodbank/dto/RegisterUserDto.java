package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.UserType;

import lombok.Data;

@Data
public class RegisterUserDto {
	String firstName;
	String lastName;
	Long jmbg;
	String email;
	UserType userType;
	BloodType bloodType;
	BloodBank bloodBank;
	String password;
}
