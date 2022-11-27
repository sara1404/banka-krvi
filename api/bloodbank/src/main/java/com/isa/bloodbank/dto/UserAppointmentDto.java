package com.isa.bloodbank.dto;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class UserAppointmentDto {
	UserDto user;
	LocalDateTime startTime;
	LocalDateTime endTime;
}
