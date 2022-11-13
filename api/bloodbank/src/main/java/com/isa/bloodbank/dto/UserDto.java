package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
	@NotNull
	String firstName;
	@NotNull
	String lastName;
	@NotNull
	@Size(min = 13, message = "{validation.name.size.too_short}")
	@Size(max = 13, message = "{validation.name.size.too_long}")
	Long jmbg;
	@NotNull
	@Email(message = "Email is not valid", regexp = "(/^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$/)")
	String email;
	@NotNull
	UserType userType;
	@NotNull
	BloodType bloodType;
	@NotNull
	BloodBank bloodBank;
}
