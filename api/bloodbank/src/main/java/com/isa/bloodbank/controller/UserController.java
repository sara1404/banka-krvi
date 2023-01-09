package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.*;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM')")
	public ResponseEntity<List<AdministratorDto>> findByAdministratorId(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader
	) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		final Long bloodBankId = loggedUser.getBloodBank().getId();
		return ResponseEntity.ok(userService.findByBloodBankId(bloodBankId, loggedUser.getId()));
	}

	@PostMapping("/register/admin")
	@PreAuthorize("hasAuthority('ADMIN_SYSTEM') or hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<RegisterUserDto> registerCenterAdmin(@Valid @RequestBody final RegisterUserDto centerAdmin) {
		System.out.println(centerAdmin.getBloodBank() + "e");
		centerAdmin.setPassword(centerAdmin.getPassword());
		return ResponseEntity.ok(userService.registerCenterAdmin(centerAdmin));
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM')")
	public ResponseEntity<List<UserDto>> search(@RequestParam("name") final String name, @RequestParam("surname") final String lastName) {
		return ResponseEntity.ok(userService.search(name, lastName));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM') or hasAuthority('REGISTERED')")
	public ResponseEntity<UserDto> findUserById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(userService.findById(id));
	}

	@GetMapping("/getUserProfile")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM') or hasAuthority('REGISTERED')")
	public ResponseEntity<UserDto> getUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User user = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(userService.findById(user.getId()));
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('REGISTERED')")
	public ResponseEntity<User> updateUserProfile(@RequestBody final UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.update(userDto));
        } catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.TOO_MANY_REQUESTS);
        }
	}

	@PostMapping("/penal-points")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<Boolean> addPenalPoints(@RequestBody final Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		return ResponseEntity.ok(userService.addPenalPoints(id));
	}

	@GetMapping("/users/{pageNo}")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM')")
	public ResponseEntity<List<UserDto>> getAll(@PathVariable final int pageNo) {
		return ResponseEntity.ok(userService.getAll(pageNo));
	}

	@GetMapping("/users/count")
	@PreAuthorize("hasAuthority('ADMIN_SYSTEM') or hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<Integer> getAllCount() {
		return ResponseEntity.ok(userService.getUserCount());
	}

	@GetMapping("/center-admins")
	@PreAuthorize("hasAuthority('ADMIN_SYSTEM') or hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<List<UserDto>> getAvailableCenterAdmins() {
		return ResponseEntity.ok(userService.getAvailableCenterAdmins());
	}

	@PostMapping("/activate/{email}")
	public ResponseEntity<?> confirmUserRegistration(@PathVariable("email") final String email) {
		if (userService.confirmUserRegistration(email) != null) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/change-password")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM') or hasAuthority('REGISTERED')")
	public ResponseEntity<Boolean> changePassword(@RequestBody final PasswordChangeDto passwordChangeDto,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		//final Long administratorId = (long) (3);
		//final User user = userService.findUserById(administratorId);
		return ResponseEntity.ok(userService.changePassword(loggedUser, passwordChangeDto));
	}
}