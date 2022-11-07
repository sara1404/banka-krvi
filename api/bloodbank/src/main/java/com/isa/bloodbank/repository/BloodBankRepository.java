package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodBank;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
	List<BloodBank> findByNameContaining(String infix);

	List<BloodBank> findAllByAddressId_CityContaining(String city);

	List<BloodBank> findAllByNameContainingAndAddressId_CityContaining(String name, String city);

	BloodBank save(BloodBank bloodBank);
	
}
