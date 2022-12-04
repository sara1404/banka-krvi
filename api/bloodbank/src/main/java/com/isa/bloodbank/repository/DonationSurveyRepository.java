package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.DonationSurvey;
import com.isa.bloodbank.entity.enums.BloodType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationSurveyRepository extends JpaRepository<DonationSurvey, Long> {
	DonationSurvey findByUserId(Long userId);
}
