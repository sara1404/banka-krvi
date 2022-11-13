package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.entity.BloodBank;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BloodBankMapper {
	BloodBank bloodBankDtoToBloodBank(BloodBankDto bloodBank);
	BloodBankDto bloodBankToBloodBankDto(BloodBank bloodBank);
}
