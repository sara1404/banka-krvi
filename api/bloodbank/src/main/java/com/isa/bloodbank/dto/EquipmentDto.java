package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.enums.EquipmentType;

import lombok.Data;

@Data
public class EquipmentDto {
	BloodBank bloodBank;
	EquipmentType equipmentType;
	int quantity;
}
