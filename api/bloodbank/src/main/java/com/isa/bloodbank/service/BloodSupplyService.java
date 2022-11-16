package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.mapping.BloodSupplyMapper;
import com.isa.bloodbank.repository.BloodSupplyRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloodSupplyService {
	@Autowired
	private BloodSupplyRepository bloodSupplyRepository;
	@Autowired
	private BloodSupplyMapper bloodSupplyMapper;

	public List<BloodSupplyDto> findByBloodBankId(final Long bloodBankId) {
		return bloodSupplyMapper.bloodSuppliesToBloodSupplyDtos(bloodSupplyRepository.findByBloodBankId(bloodBankId));
	}

}
