package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.EquipmentDto;
import com.isa.bloodbank.entity.Equipment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface EquipmentMapper {
	Equipment equipmentDtoToEquipment(EquipmentDto equipmentDto);

	EquipmentDto equipmentToEquipmentDto(Equipment equipment);

}
