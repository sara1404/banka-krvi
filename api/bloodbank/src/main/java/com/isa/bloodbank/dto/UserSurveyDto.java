package com.isa.bloodbank.dto;

import lombok.Value;

@Value
public class UserSurveyDto {
	Long userId;
	double weight;
	boolean fluSymptoms;
	boolean skinIrritations;
	boolean abnormalBloodPressure;
	boolean tookAntibiotics;
	boolean onPeriod;
	boolean dentistIntervention;
	boolean piercingOrTattoo;
}
