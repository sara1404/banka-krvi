package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.Address;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class RegisterUserDto {
	@Email
	String email;
	@NotBlank
	String password;
	@NotBlank
	String firstName;
	@NotBlank
	String lastName;
	@NotNull
	Address address;
	@NotNull
	Long jmbg;
	String phoneNumber;
	String gender;
	String workplaceName;
	String jobTitle;
}
