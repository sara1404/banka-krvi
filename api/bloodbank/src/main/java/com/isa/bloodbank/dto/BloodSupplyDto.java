package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.BloodType;

import lombok.Data;

@Data
public class BloodSupplyDto {
	BloodType bloodType;
	double quantity;
}
