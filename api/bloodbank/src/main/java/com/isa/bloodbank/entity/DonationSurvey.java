package com.isa.bloodbank.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class DonationSurvey extends BaseEntity {
	@Column
	Long userId;
	@Column
	double weight;
	@Column
	boolean fluSymptoms;
	@Column
	boolean skinIrritations;
	@Column
	boolean abnormalBloodPressure;
	@Column
	boolean tookAntibiotics;
	@Column
	boolean onPeriod;
	@Column
	boolean dentistIntervention;
	@Column
	boolean piercingOrTattoo;
	@Column
	LocalDateTime dateCreated;
}
