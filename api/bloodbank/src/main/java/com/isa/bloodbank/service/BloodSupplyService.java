package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.MonthlyTransfer;
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

	public void removeMonthlyBlood(final MonthlyTransfer mt) {
		final List<BloodSupply> bloodSupplies = bloodSupplyRepository.findByBloodBankId(mt.bloodBank.getId());
		for (final BloodSupply bloodSupply : bloodSupplies) {
			if (bloodSupply.getBloodType() == BloodType.A_POSITIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.APlus);
			} else if (bloodSupply.getBloodType() == BloodType.A_NEGATIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.AMinus);
			} else if (bloodSupply.getBloodType() == BloodType.B_POSITIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.BPlus);
			} else if (bloodSupply.getBloodType() == BloodType.B_NEGATIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.BMinus);
			} else if (bloodSupply.getBloodType() == BloodType.AB_POSITIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.ABPlus);
			} else if (bloodSupply.getBloodType() == BloodType.AB_NEGATIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.ABMinus);
			} else if (bloodSupply.getBloodType() == BloodType.O_POSITIVE) {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.OPlus);
			} else {
				bloodSupply.setQuantity(bloodSupply.getQuantity() - mt.OMinus);
			}
			//bloodSupplyRepository.save(bloodSupply);
		}
		bloodSupplyRepository.saveAll(bloodSupplies);
	}

	public boolean checkAmountForMonthlyTransfer(final MonthlyTransfer mt) {
		final List<BloodSupply> bloodSupplies = bloodSupplyRepository.findByBloodBankId(mt.bloodBank.getId());
		for (final BloodSupply bloodSupply : bloodSupplies) {
			if (bloodSupply.getBloodType() == BloodType.A_POSITIVE && bloodSupply.getQuantity() < mt.getAPlus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.A_NEGATIVE && bloodSupply.getQuantity() < mt.getAMinus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.B_POSITIVE && bloodSupply.getQuantity() < mt.getBPlus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.B_NEGATIVE && bloodSupply.getQuantity() < mt.getBMinus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.AB_POSITIVE && bloodSupply.getQuantity() < mt.getABPlus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.AB_NEGATIVE && bloodSupply.getQuantity() < mt.getABMinus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.O_POSITIVE && bloodSupply.getQuantity() < mt.getOPlus()) {
				return false;
			} else if (bloodSupply.getBloodType() == BloodType.O_NEGATIVE && bloodSupply.getQuantity() < mt.getOMinus()) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
