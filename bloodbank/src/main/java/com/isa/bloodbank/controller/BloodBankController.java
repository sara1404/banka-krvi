package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.service.BloodBankService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public ResponseEntity<List<BloodBank>> findAll() {
		return ResponseEntity.ok(bloodBankService.findAll());
	}

	@GetMapping("/search")
	public ResponseEntity<List<BloodBank>> search(@RequestParam String name, @RequestParam String city) {
		return ResponseEntity.ok(bloodBankService.search(name.trim(), city.trim()));
	}

}
