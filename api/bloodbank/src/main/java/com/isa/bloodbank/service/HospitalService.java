package com.isa.bloodbank.service;

import com.isa.bloodbank.entity.MonthlyTransfer;
import com.isa.bloodbank.repository.MonthlyTransferRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {
	@Autowired
	private BloodSupplyService bloodSupplyService;
	@Autowired
	private MonthlyTransferRepository monthlyTransferRepository;

	public MonthlyTransfer monthlyTransfer() {
		for (final MonthlyTransfer mt : monthlyTransferRepository.findAll()) {
			if (mt.DateTime.isEqual(LocalDate.now()) && bloodSupplyService.checkAmountForMonthlyTransfer(mt)) {
				bloodSupplyService.removeMonthlyBlood(mt);
				return mt;
			}
			if (mt.DateTime.minusDays(2).equals(LocalDate.now())) {
				if (!bloodSupplyService.checkAmountForMonthlyTransfer(mt)) {
					return new MonthlyTransfer(mt.getDateTime(), mt.getBloodBank(), 0, 0, 0, 0, 0, 0, 0, 0);
				}
			}
		}
		return null;
	}

}
