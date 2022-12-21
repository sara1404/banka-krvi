package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.AppointmentDto;
import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.dto.WorkingHoursDto;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.WorkingHours;
import com.isa.bloodbank.mapping.BloodBankMapper;
import com.isa.bloodbank.service.BloodBankService;
import com.isa.bloodbank.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity<BloodBankDto> findForAdministrator(/*@PathVariable("id") final Long id*/) {
		final Long administratorId = (long) (3);
		final User user = userService.findUserById(administratorId);
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

	@GetMapping(value = "/searchAndFilter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<BloodBank>> searchAndFilter(
		@RequestParam("name") final String name,
		@RequestParam("city") final String city,
		@RequestParam("averageGrade") final double averageGrade,
		@RequestParam("pageSize") final int pageSize,
		@RequestParam("pageNumber") final int pageNumber,
		@RequestParam("sortDirection") final String sortDirection,
		@RequestParam("sortBy") final String sortBy
	) {
		return ResponseEntity.ok(bloodBankService.searchAndFilter(name.trim(), city.trim(), averageGrade, pageSize, pageNumber, sortBy, sortDirection));
	}

	@PostMapping("/register")
	@PreAuthorize("hasAuthority('ADMIN_SYSTEM') or hasAuthority('ADMIN_CENTER')")

	public ResponseEntity<BloodBankDto> register(@Valid @RequestBody final BloodBankDto bloodBank) {
		return ResponseEntity.ok(bloodBankService.registerBloodBank(bloodBank));
	}

	@GetMapping(value = "/getWorkingHours", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<WorkingHours> getWorkingHours() {
		final Long administratorId = (long) (3);
		return ResponseEntity.ok(bloodBankService.getWorkingHours(administratorId));
	}

}
