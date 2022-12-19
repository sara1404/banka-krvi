package com.isa.bloodbank.dto;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Data
public class AppointmentDto {
	@Id
	Long id;
	@NotNull
	LocalDateTime startTime;
	@NotNull
	double duration;
	BloodBankDto bloodBank;
	UserDto nurse;
	UserDto user;
}
