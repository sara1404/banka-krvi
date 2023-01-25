package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.Address;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
	Long jmbg;
	BloodType bloodType;
	Address address;
	UserType userType;
	String phoneNumber;
	String gender;
	String workplaceName;
	String jobTitle;
	BloodBank bloodBank;
}
