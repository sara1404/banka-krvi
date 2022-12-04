package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.enums.BloodType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodSupplyRepository extends JpaRepository<BloodSupply, Long> {
	List<BloodSupply> findByBloodBankId(Long bloodBankId);
	BloodSupply findByBloodBankIdAndBloodType(Long bloodbankId, BloodType bloodType);
	BloodSupply save(BloodSupply bloodSupply);
}
