package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.BloodType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BloodSupply extends BaseEntity {
	@Column
	Long bloodBankId;
	@Column
	double quantity;
	@Enumerated(EnumType.STRING)
	@Column
	BloodType bloodType;
	//@Column
	//Map<BloodType, Double> bloodSupply;
}
