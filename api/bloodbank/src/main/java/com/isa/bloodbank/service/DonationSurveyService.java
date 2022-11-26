package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.UserSurveyDto;
import com.isa.bloodbank.entity.DonationSurvey;
import com.isa.bloodbank.mapping.SurveyMapper;
import com.isa.bloodbank.repository.DonationSurveyRepository;
import com.isa.bloodbank.repository.UserRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationSurveyService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DonationSurveyRepository surveyRepository;
	@Autowired
	private SurveyMapper surveyMapper;

	public boolean saveSurvey(DonationSurvey survey) {
		if (survey == null) {
			return false;
		}
		if (userRepository.findById(survey.getUserId()) == null) {
			return false;
		}
		survey.setDateCreated(LocalDateTime.now());
		return surveyRepository.save(survey) != null;
	}

	public UserSurveyDto findByUserId(Long userId){

		return surveyMapper.donationSurveyToUserSurveyDto(surveyRepository.findByUserId(userId));
	}
}
