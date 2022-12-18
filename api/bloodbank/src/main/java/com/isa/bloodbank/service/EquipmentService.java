package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.EquipmentDto;
import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.Equipment;
import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.EquipmentType;
import com.isa.bloodbank.repository.EquipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.EqualsAndHashCode;

@Service
public class EquipmentService {
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private UserService userService;

	public Boolean removeEquipment(EquipmentDto dto){
		final Long administratorId = (long) (3);
		Long bloodBankId = userService.findById(administratorId).getBloodBank().getId();
		Equipment equipment = equipmentRepository.findByBloodBankIdAndEquipmentType(bloodBankId, dto.getEquipmentType());
		equipment.setQuantity(equipment.getQuantity() - dto.getQuantity());
		equipmentRepository.save(equipment);
		return true;
	}
}
