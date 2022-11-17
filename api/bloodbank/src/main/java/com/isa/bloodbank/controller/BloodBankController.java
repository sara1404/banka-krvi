package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.service.BloodBankService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloodbank")
public class BloodBankController {
	@Autowired
	private BloodBankService bloodBankService;

	@GetMapping("/{id}/")
	public ResponseEntity<BloodBank> findById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(bloodBankService.findById(id));
	}

	@PatchMapping("/update/")
	private ResponseEntity<BloodBank> updateBloodBank(@Valid @RequestBody final BloodBank bloodBank) {
		return ResponseEntity.ok(bloodBankService.update(bloodBank));
	}
	@GetMapping("/findAll")
	public ResponseEntity<List<BloodBankDto>> findAll() {
		return ResponseEntity.ok(bloodBankService.findAll());
	}

	@GetMapping("/searchAndFilter")
	public ResponseEntity<List<BloodBankDto>> searchAndFilter(@RequestParam final String name, @RequestParam final String city,
		@RequestParam final double averageGrade) {
		return ResponseEntity.ok(bloodBankService.searchAndFilter(name.trim(), city.trim(), averageGrade));
	}

	@PostMapping("/register")
	public ResponseEntity<BloodBankDto> register(@Valid @RequestBody final BloodBankDto bloodBank) {
		return ResponseEntity.ok(bloodBankService.registerBloodBank(bloodBank));
	}

}
