package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.Address;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.Gender;
import com.isa.bloodbank.entity.enums.UserType;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
	@Id
	Long id;
	@NotNull
	String firstName;
	@NotNull
	String lastName;
	@NotNull
	Long jmbg;
	@NotNull
	@Email(message = "Email is not valid", regexp = "(/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/)")
	String email;
	@NotNull
	UserType userType;
	BloodType bloodType;
	@NotNull
	BloodBank bloodBank;
	@NotNull
	String phoneNumber;
	@NotNull
	Gender gender;
	String workplaceName;
	String jobTitle;
	Address address;
	@NotNull
	String password;
	int points;
}
