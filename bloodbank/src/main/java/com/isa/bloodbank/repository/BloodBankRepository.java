package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodBank;

import com.isa.bloodbank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    List<BloodBank> findByNameContaining(String infix);
    List<BloodBank> findAllByAddressId_CityContaining(String city);
    List<BloodBank> findAllByNameContainingAndAddressId_CityContaining(String name, String city);
}
