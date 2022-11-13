package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.BloodBankDto;
import com.isa.bloodbank.dto.UserDto;
import com.isa.bloodbank.entity.BloodBank;

import com.isa.bloodbank.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")

public interface BloodBankMapper {
	BloodBank bloodBankDtoToBloodBank(BloodBankDto bloodBank);
	BloodBankDto bloodBankToBloodBankDto(BloodBank bloodBank);
	List<BloodBankDto> bloodBanksToBloodBankDtos(List<BloodBank> bloodBanks);
	List<BloodBank> bloodBankDtosToBloodBanks(List<BloodBankDto> bloodBankDtos);
}
