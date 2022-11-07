package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.service.BloodSupplyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloodsupply")
public class BloodSupplyController {
	@Autowired
	private BloodSupplyService bloodSupplyService;

	@GetMapping("/bloodbank/{bloodBankId}/")
	public ResponseEntity<List<BloodSupply>> findById(@PathVariable("bloodBankId") final Long bloodBankId) {
		return ResponseEntity.ok(bloodSupplyService.findByBloodBankId(bloodBankId));
	}

}
