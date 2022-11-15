package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.BloodSupply;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.BloodSupplyService;
import com.isa.bloodbank.service.UserService;

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
	@Autowired
	private UserService userService;

	@GetMapping("/bloodbank") ///{bloodBankId} @PathVariable("bloodBankId") final Long bloodBankId
	public ResponseEntity<List<BloodSupplyDto>> findById() {
		final Long administratorId = (long) (3);
		User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(bloodSupplyService.findByBloodBankId(user.getBloodBank().getId()));
	}

}
