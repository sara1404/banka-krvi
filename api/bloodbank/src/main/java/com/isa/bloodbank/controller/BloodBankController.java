package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.service.BloodBankService;
import com.isa.bloodbank.service.UserService;

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
	@Autowired
	private BloodBankMapper bloodBankMapper;
	@Autowired
	private UserService userService;

	@GetMapping("/administrator")
	public ResponseEntity<BloodBankDto> findForAdministrator(/*@PathVariable("id") final Long id*/) {
		final Long administratorId = (long) (3);
		User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(bloodBankMapper.bloodBankToBloodBankDto(bloodBankService.findById(user.getBloodBank().getId())));
	}

	@PutMapping("/update")
	private ResponseEntity<BloodBank> updateBloodBank(@Valid @RequestBody final BloodBankDto bloodBankDto) {
		return ResponseEntity.ok(bloodBankService.update(bloodBankDto));
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
	public ResponseEntity<BloodBankDto> register(@RequestBody final BloodBankDto bloodBank) {
		return ResponseEntity.ok(bloodBankService.registerBloodBank(bloodBank));
	}

}
