package com.isa.bloodbank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class AppointmentInfo extends BaseEntity {
	@Column
	double cuso4;
	@Column
	double hemoglobinometer;
	@Column
	double ta;
	@Column
	double tt;
	@Column
	double tv;
	String hand;
	@Column
	double quantity;
	@Column
	boolean surveyAccepted;
	@Column
	boolean accepted;
	@Column
	String reason;
}
