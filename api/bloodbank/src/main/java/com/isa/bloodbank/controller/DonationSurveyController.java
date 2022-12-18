package com.isa.bloodbank.controller;

import com.isa.bloodbank.dto.DonationSurveyDto;
import com.isa.bloodbank.mapping.UserMapper;
import com.isa.bloodbank.service.DonationSurveyService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity fillSurvey(@RequestBody @Valid DonationSurveyDto survey) {
		var saveSurvey = userMapper.surveyDtoToSurvey(survey);
		saveSurvey.setUserId(5l);
		if (surveyService.saveSurvey(saveSurvey)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/for-user/{id}")
	public ResponseEntity findByUser(@PathVariable("id") final Long id)
	{
		return ResponseEntity.ok(surveyService.findByUserId(id));
	}
}
