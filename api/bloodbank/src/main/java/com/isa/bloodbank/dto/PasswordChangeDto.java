package com.isa.bloodbank.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Value;

@Data
public class PasswordChangeDto {
	String oldPassword;
	String newPassword;
}
