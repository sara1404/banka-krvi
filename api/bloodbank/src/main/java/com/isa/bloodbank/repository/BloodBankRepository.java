package com.isa.bloodbank.repository;

import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.entity.BloodBank;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
	Page<BloodBank> findByNameContainingIgnoreCase(String infix, PageRequest pageRequest);

	Page<BloodBank> findByAddressId_CityContainingIgnoreCase(String city, PageRequest pageRequest);

	Page<BloodBank> findByNameContainingIgnoreCaseAndAddressId_CityContainingIgnoreCase(String name, String city, PageRequest pageRequest);

	BloodBank save(BloodBank bloodBank);
	BloodBank findBloodBankByName(String name);

}
