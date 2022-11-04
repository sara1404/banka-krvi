package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.service.BloodBankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloodbank")
public class BloodBankController {
	@Autowired
	private BloodBankService bloodBankService;

	@GetMapping("/{id}/")
	public ResponseEntity<BloodBank> findById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(bloodBankService.findById(id));
	}

	@GetMapping("/administratorId/{id}/")
	public ResponseEntity<BloodBank> findByAdministratorId(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(bloodBankService.findByAdministratorId(id));
	}
}
