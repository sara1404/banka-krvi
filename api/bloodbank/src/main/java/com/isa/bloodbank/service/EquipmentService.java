package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.EquipmentDto;
import com.isa.bloodbank.entity.Equipment;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.repository.EquipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {
	@Autowired
	private EquipmentRepository equipmentRepository;
	@Autowired
	private UserService userService;

	public Boolean removeEquipment(final EquipmentDto dto, final User loggedUser) {
		final Long bloodBankId = loggedUser.getBloodBank().getId();
		final Equipment equipment = equipmentRepository.findByBloodBankIdAndEquipmentType(bloodBankId, dto.getEquipmentType());
		equipment.setQuantity(equipment.getQuantity() - dto.getQuantity());
		if (equipment.getQuantity() < 0) {
			return false;
		}
		equipmentRepository.save(equipment);
		return true;
	}

	public Equipment addEquipment(final Equipment equipment) {
		final Equipment existingEquipment = equipmentRepository.findByBloodBankIdAndEquipmentType(equipment.getBloodBank().getId(),
			equipment.getEquipmentType());
		if (existingEquipment == null) {
			equipmentRepository.save(equipment);
			return equipment;
		}
		existingEquipment.setQuantity(existingEquipment.getQuantity() + equipment.getQuantity());
		equipmentRepository.save(existingEquipment);
		return existingEquipment;
	}
}
