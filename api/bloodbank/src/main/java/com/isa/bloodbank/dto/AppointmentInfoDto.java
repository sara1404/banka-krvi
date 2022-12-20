package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.enums.BloodType;

import lombok.Data;

@Data
public class AppointmentInfoDto {
	double cuso4;
	double hemoglobinometer;
	double ta;
	double tt;
	double tv;
	String hand;
	BloodType examBloodType;
	double quantity;
	boolean surveyAccepted;
	boolean accepted;
	String reason;
}
