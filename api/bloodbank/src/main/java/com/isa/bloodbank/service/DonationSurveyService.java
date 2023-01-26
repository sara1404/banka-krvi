package com.isa.bloodbank.service;

import com.isa.bloodbank.dto.UserSurveyDto;
import com.isa.bloodbank.entity.DonationSurvey;
import com.isa.bloodbank.mapping.SurveyMapper;
import com.isa.bloodbank.repository.DonationSurveyRepository;
import com.isa.bloodbank.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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
		System.out.println("id " + userId);

		List<DonationSurvey> surveys = surveyRepository.findByUserId(userId);
		System.out.println("duzina " + surveys.size());
		DonationSurvey last = surveys.get(0);
		for(DonationSurvey survey : surveys){
			if(survey.getDateCreated().compareTo(last.getDateCreated()) > 0){
				last = survey;
			}
		}
		return surveyMapper.donationSurveyToUserSurveyDto(last);
	}
}
