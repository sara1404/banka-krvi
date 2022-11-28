package com.isa.bloodbank.dto;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class UserAppointmentDto {
	Long id;
	UserDto user;
	LocalDateTime startTime;
	double duration;
}
