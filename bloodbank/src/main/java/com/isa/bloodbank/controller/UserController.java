package com.isa.bloodbank.controller;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/{id}/")
	public ResponseEntity<User> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@PatchMapping("/update/")
	private ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.update(user));
	}

	@PostMapping("/new/")
	private ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.create(user));
	}

	@DeleteMapping("/delete/{id}/")
	private ResponseEntity deleteUser(@PathVariable("id") Long id) {
		userService.delete(id);
		return ResponseEntity.ok().build();
	}
}
