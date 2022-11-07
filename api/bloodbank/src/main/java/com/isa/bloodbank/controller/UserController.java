package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/bloodBankId/{bloodBankId}/{administratorId}/")
	public ResponseEntity<List<User>> findByAdministratorId(@PathVariable("bloodBankId") final Long bloodBankId,
		@PathVariable("administratorId") final Long administratorId) {
		return ResponseEntity.ok(userService.findByBloodBankId(bloodBankId, administratorId));
	}

	@PostMapping("/register/admin")
	public ResponseEntity<User> registerCenterAdmin(@RequestBody final User centerAdmin) {
		return ResponseEntity.ok(userService.registerCenterAdmin(centerAdmin));
	}

	@GetMapping("/search")
	public ResponseEntity<List<User>> search(@RequestParam("name") final String name, @RequestParam("surname") final String lastName) {
		return ResponseEntity.ok(userService.search(name, lastName));
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll(){
		return ResponseEntity.ok(userService.getAll());
	}
}

