package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.*;
import com.isa.bloodbank.entity.BloodBank;
import com.isa.bloodbank.entity.DonationSurvey;
import com.isa.bloodbank.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageMapper {
	PageDto<BloodBankDto> pageToPageDto(Page<BloodBank> page);
}

