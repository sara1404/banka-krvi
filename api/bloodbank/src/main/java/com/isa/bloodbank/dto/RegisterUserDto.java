package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.Address;
import com.isa.bloodbank.entity.enums.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

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
	Address address;
	@NotNull
	Long jmbg;
	UserType userType;
	String phoneNumber;
	String gender;
	String workplaceName;
	String jobTitle;
}
