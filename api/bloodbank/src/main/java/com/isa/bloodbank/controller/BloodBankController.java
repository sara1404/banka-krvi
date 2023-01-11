package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.WorkingHours;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.security.JwtUtils;
import com.isa.bloodbank.service.BloodBankService;
import com.isa.bloodbank.service.UserService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping("/administrator")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<BloodBankDto> findForAdministrator(
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		final User loggedUser = jwtUtils.getUserFromToken(authHeader);
		return ResponseEntity.ok(bloodBankMapper.bloodBankToBloodBankDto(bloodBankService.findById(loggedUser.getBloodBank().getId())));
	}

	@PutMapping("/update")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<BloodBank> updateBloodBank(@Valid @RequestBody final BloodBankDto bloodBankDto) {
		return ResponseEntity.ok(bloodBankService.update(bloodBankDto));
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<BloodBankDto>> findAll() {
		return ResponseEntity.ok(bloodBankService.findAll());
	}

	@GetMapping(value = "/searchAndFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<BloodBank>> searchAndFilter(
		@RequestParam("name") final String name,
		@RequestParam("city") final String city,
		@RequestParam("averageGrade") final double averageGrade,
		@RequestParam("lng") final double lng,
		@RequestParam("lat") final double lat,
		@RequestParam("distance") final double distance,
		@RequestParam("pageSize") final int pageSize,
		@RequestParam("pageNumber") final int pageNumber,
		@RequestParam("sortDirection") final String sortDirection,
		@RequestParam("sortBy") final String sortBy
	) {
		return ResponseEntity.ok(
			bloodBankService.searchAndFilter(name.trim(), city.trim(), averageGrade, lng, lat, distance, pageSize, pageNumber, sortBy, sortDirection));
	}

	@PostMapping("/register")
	@PreAuthorize("hasAuthority('ADMIN_SYSTEM')")
	public ResponseEntity<BloodBankDto> register(@Valid @RequestBody final BloodBankDto bloodBank) {
		return ResponseEntity.ok(bloodBankService.registerBloodBank(bloodBank));
	}

	@GetMapping(value = "/getWorkingHours", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkingHours> getWorkingHours() {
		final Long administratorId = (long) (3);
		return ResponseEntity.ok(bloodBankService.getWorkingHours(administratorId));
	}

}
