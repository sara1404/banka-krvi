package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.enums.EquipmentType;

import lombok.Value;

@Value
public class EquipmentDto {
	EquipmentType equipmentType;
	int quantity;
}
