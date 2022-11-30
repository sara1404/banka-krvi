package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.HandEnum;

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
	HandEnum handEnum;
	@Column
	double quantity;
	@Column
	LocalDateTime startBlood;
	@Column
	LocalDateTime endBlood;
}
