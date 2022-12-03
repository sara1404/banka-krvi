package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.Hand;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class AppointmentInfo extends BaseEntity {
	@Column
	double cuso4;
	@Column
	double hemoglobinometer;
	@Column
	BloodType examBloodType;
	@Column
	double ta;
	@Column
	double tt;
	@Column
	double tv;
	@Column
	Hand handEnum;
	@Column
	double quantity;
	@Column
	boolean surveyAccepted;
	@Column
	boolean accepted;
	@Column
	String reason;
}
