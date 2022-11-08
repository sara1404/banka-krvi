package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.service.BloodBankService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/bloodbank")
public class BloodBankController {
	@Autowired
	private BloodBankService bloodBankService;

	@GetMapping(/*"/{id}/"*/"/administrator/")
	public ResponseEntity<BloodBank> findForAdministrator(/*@PathVariable("id") final Long id*/) {
		final Long bloodBankId = (long) 5; //zakucano
		return ResponseEntity.ok(bloodBankService.findById(bloodBankId));
	}

	@PutMapping("/update/")
	private ResponseEntity<BloodBank> updateBloodBank(@Valid @RequestBody final BloodBank bloodBank) {
		return ResponseEntity.ok(bloodBankService.update(bloodBank));
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<BloodBank>> findAll() {
		return ResponseEntity.ok(bloodBankService.findAll());
	}

	@GetMapping("/searchAndFilter")
	public ResponseEntity<List<BloodBank>> searchAndFilter(@RequestParam final String name, @RequestParam final String city,
		@RequestParam final double averageGrade) {
		return ResponseEntity.ok(bloodBankService.searchAndFilter(name.trim(), city.trim(), averageGrade));
	}

	@PostMapping("/register")
	public ResponseEntity<BloodBank> register(@RequestBody final BloodBank bloodBank) {
		return ResponseEntity.ok(bloodBankService.registerBloodBank(bloodBank));
	}

}
