package com.isa.bloodbank.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.Value;

@Value
public class DonationSurveyDto {
	@NotNull
	double weight;
	@NotNull
	boolean fluSymptoms;
	@NotNull
	boolean skinIrritations;
	@NotNull
	boolean abnormalBloodPressure;
	@NotNull
	boolean tookAntibiotics;
	@NotNull
	boolean onPeriod;
	@NotNull
	boolean dentistIntervention;
	@NotNull
	boolean piercingOrTattoo;
}
