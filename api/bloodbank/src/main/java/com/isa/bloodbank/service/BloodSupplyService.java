package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.enums.BloodType;
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
	@Autowired
	private UserService userService;

	public List<BloodSupplyDto> findByBloodBankId(final Long bloodBankId) {
		return bloodSupplyMapper.bloodSuppliesToBloodSupplyDtos(bloodSupplyRepository.findByBloodBankId(bloodBankId));
	}

	public boolean addBlood(BloodType bloodType, double quantity){
		final Long administratorId = (long) (3);
		Long bloodBankId = userService.findById(administratorId).getBloodBank().getId();
		BloodSupply bloodSupply = bloodSupplyRepository.findByBloodBankIdAndBloodType(bloodBankId, bloodType);
		bloodSupply.setQuantity(bloodSupply.getQuantity() + quantity);
		bloodSupplyRepository.save(bloodSupply);
		return true;
	}

}
