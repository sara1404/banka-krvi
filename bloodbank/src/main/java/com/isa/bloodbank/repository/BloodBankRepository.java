package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodBank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
	BloodBank findByAdministratorId(Long administratorId);
}
