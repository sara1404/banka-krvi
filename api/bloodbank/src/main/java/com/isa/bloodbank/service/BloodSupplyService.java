package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.User;
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

	public boolean addBlood(final BloodType bloodType, final double quantity, final User loggedUser) {
		final Long bloodBankId = loggedUser.getBloodBank().getId();
		BloodSupply bloodSupply = bloodSupplyRepository.findByBloodBankIdAndBloodType(bloodBankId, bloodType);
		if (bloodSupply == null) {
			bloodSupply = new BloodSupply();
			bloodSupply.setQuantity(0);
			bloodSupply.setBloodType(bloodType);
			bloodSupply.setBloodBankId(bloodBankId);
			bloodSupplyRepository.save(bloodSupply);
		}
		bloodSupply.setQuantity(bloodSupply.getQuantity() + quantity);
		bloodSupplyRepository.save(bloodSupply);
		return true;
	}

}
