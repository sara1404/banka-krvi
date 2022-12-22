package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.DonationSurveyDto;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.service.DonationSurveyService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/survey")
public class DonationSurveyController {
	@Autowired
	private DonationSurveyService surveyService;
	@Autowired
	private UserMapper userMapper;

	@PostMapping("/fill")
	@PreAuthorize("hasAuthority('ADMIN_CENTER') or hasAuthority('ADMIN_SYSTEM') or hasAuthority('REGISTERED')")
	public ResponseEntity fillSurvey(@RequestBody @Valid final DonationSurveyDto survey) {
		final var saveSurvey = userMapper.surveyDtoToSurvey(survey);
		saveSurvey.setUserId(5l);
		if (surveyService.saveSurvey(saveSurvey)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/for-user/{id}")
	@PreAuthorize("hasAuthority('ADMIN_CENTER')")
	public ResponseEntity findByUser(@PathVariable("id") final Long id,
		@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
		return ResponseEntity.ok(surveyService.findByUserId(id));
	}
}
