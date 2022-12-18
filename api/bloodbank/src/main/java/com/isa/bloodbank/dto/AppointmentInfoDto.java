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
	//Hand handEnum;
	BloodType examBloodType;
	double quantity;
	boolean surveyAccepted;
	boolean accepted;
	String reason;
}
