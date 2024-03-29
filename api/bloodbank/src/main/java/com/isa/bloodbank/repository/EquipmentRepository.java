package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Equipment;
import com.isa.bloodbank.entity.enums.EquipmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
	Equipment findByBloodBankIdAndEquipmentType(Long bloodbankId, EquipmentType equipmentType);

	Equipment save(Equipment equipment);

}
