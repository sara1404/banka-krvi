package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.Hand;

import java.time.LocalDateTime;

import javax.persistence.Column;

import net.bytebuddy.asm.Advice.Local;

import lombok.Data;

@Data
public class AppointmentInfoDto {
	double cuso4;
	double hemoglobinometer;
	double ta;
	double tt;
	double tv;
	Hand hand;
	BloodType examBloodType;
	double quantity;
	LocalDateTime startBlood;
	LocalDateTime endBlood;
	boolean surveyAccepted;
	boolean accepted;
	String reason;
}
