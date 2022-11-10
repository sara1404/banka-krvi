package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.UserType;

import lombok.Data;

@Data
public class UserDto {
	String firstName;
	String lastName;
	Long jmbg;
	String email;
	UserType userType;
	BloodType bloodType;
	BloodBank bloodBank;

	//public UserDto(String firstName, String lastName, Long jmbg, String email, UserType userType, BloodType bloodType, BloodBank bloodBank){
	//	this.firstName = firstName;
	//	this.lastName = lastName;
	//	this.jmbg = jmbg;
	//	this.email = email;
	//	this.userType = userType;
	//	this.bloodType = bloodType;
	//	this.bloodBank = bloodBank;
}
