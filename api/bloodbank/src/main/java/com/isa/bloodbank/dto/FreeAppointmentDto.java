package com.isa.bloodbank.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FreeAppointmentDto {
	LocalDateTime startTime;
	LocalDateTime endTime;
}
