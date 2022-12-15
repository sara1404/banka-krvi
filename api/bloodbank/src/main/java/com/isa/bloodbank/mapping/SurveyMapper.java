package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.UserSurveyDto;
import com.isa.bloodbank.entity.DonationSurvey;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SurveyMapper {
	UserSurveyDto donationSurveyToUserSurveyDto(DonationSurvey donationSurvey);
	DonationSurvey userSurveyDtoToDonationSurvey(UserSurveyDto userSurveyDto);
}
