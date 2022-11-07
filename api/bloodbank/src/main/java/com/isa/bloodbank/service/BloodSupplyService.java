package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.repository.BloodSupplyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodSupplyService {
	@Autowired
	private BloodSupplyRepository bloodSupplyRepository;

	public List<BloodSupply> findByBloodBankId(final Long bloodBankId) {
		return bloodSupplyRepository.findByBloodBankId(bloodBankId);
	}

}
