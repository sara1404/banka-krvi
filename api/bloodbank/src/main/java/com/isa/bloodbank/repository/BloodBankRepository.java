package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodBank;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
	List<BloodBank> findByNameContainingIgnoreCase(String infix);

	List<BloodBank> findByAddressId_CityContainingIgnoreCase(String city);

	List<BloodBank> findByNameContainingIgnoreCaseAndAddressId_CityContainingIgnoreCase(String name, String city);

	BloodBank save(BloodBank bloodBank);

	BloodBank findBloodBankByName(String name);
}
