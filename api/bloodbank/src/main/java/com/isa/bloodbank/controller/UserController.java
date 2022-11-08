package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

	@GetMapping("/bloodBankId/") //bilo i /{bloodBankId}/ @PathVariable("bloodBankId") final Long bloodBankId
	public ResponseEntity<List<User>> findByAdministratorId() {
		final Long administratorId = (long) (3);
		final Long bloodBankId = (long) 5;
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

	@GetMapping("/{id}/")
	public ResponseEntity<User> findById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PatchMapping("/update/")
	private ResponseEntity<User> updateUser(@Valid @RequestBody final User user) {
		return ResponseEntity.ok(userService.update(user));
	}
}

