package com.isa.bloodbank.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Value;

@Value
public class LoginDto {
	@NotBlank
	@Email
	String email;
	@NotBlank
	String password;
}
