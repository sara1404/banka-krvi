package com.isa.bloodbank.mapping;

import com.isa.bloodbank.dto.BloodSupplyDto;
import com.isa.bloodbank.entity.BloodSupply;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BloodSupplyMapper {
	BloodSupply bloodSupplyDtoToBloodSupply(BloodSupplyDto bloodSupply);
	BloodSupplyDto bloodSupplyDtoTpBloodSupply(BloodSupply bloodSupply);

	List<BloodSupplyDto> bloodSuppliesToBloodSupplyDtos(List<BloodSupply> bloodSupplies);
	List<BloodSupply> bloodSupplyDtosToBloodSupplies(List<BloodSupplyDto> bloodSupplies);
}
