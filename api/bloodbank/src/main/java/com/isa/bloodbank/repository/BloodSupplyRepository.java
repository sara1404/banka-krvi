package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodSupply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodSupplyRepository extends JpaRepository<BloodSupply, Long> {
	List<BloodSupply> findByBloodBankId(Long bloodBankId);
}
