package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AdministratorDto;
import com.isa.bloodbank.dto.PasswordChangeDto;
import com.isa.bloodbank.dto.RegisterUserDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/bloodBankId")
	public ResponseEntity<List<AdministratorDto>> findByAdministratorId() {
		final Long administratorId = (long) (3);
		final Long bloodBankId = userService.findById(administratorId).getBloodBank().getId();
		return ResponseEntity.ok(userService.findByBloodBankId(bloodBankId, administratorId));
	}

	@PostMapping("/register/admin")
	public ResponseEntity<RegisterUserDto> registerCenterAdmin(@Valid @RequestBody final RegisterUserDto centerAdmin) {
		System.out.println(centerAdmin + "e");
		centerAdmin.setPassword(centerAdmin.getPassword());
		return ResponseEntity.ok(userService.registerCenterAdmin(centerAdmin));
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> search(@RequestParam("name") final String name, @RequestParam("surname") final String lastName) {
		return ResponseEntity.ok(userService.search(name, lastName));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@GetMapping("/getUserProfile")
	public ResponseEntity<UserDto> getUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User user = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(userService.findById(user.getId()));
	}

	@PutMapping("/update/")
	private ResponseEntity<User> updateUser(@RequestBody final UserDto userDto) {
		System.out.println(userDto);
		return ResponseEntity.ok(userService.update(userDto));
	}

	@PostMapping("/penal-points")
	public ResponseEntity<Boolean> addPenalPoints(@RequestBody final Long id) {
		return ResponseEntity.ok(userService.addPenalPoints(id));
	}

	@GetMapping("/users/{pageNo}")
	public ResponseEntity<List<UserDto>> getAll(@PathVariable final int pageNo) {
		return ResponseEntity.ok(userService.getAll(pageNo));
	}

	@GetMapping("/users/count")
	public ResponseEntity<Integer> getAllCount() {
		return ResponseEntity.ok(userService.getUserCount());
	}

	@GetMapping("/center-admins")
	public ResponseEntity<List<UserDto>> getAvailableCenterAdmins() {
		return ResponseEntity.ok(userService.getAvailableCenterAdmins());
	}

	@PutMapping("/change-password")
	public ResponseEntity<Boolean> changePassword(@RequestBody final PasswordChangeDto passwordChangeDto,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		//final Long administratorId = (long) (3);
		//final User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(userService.changePassword(loggedUser, passwordChangeDto));
	}
}