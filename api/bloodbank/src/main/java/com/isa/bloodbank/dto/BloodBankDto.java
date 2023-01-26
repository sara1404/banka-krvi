package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.Address;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BloodBankDto {
	@Id
	Long id;
	@NotNull
	String name;
	@NotNull
	Address address;
	@NotNull
	String description;
	double averageGrade;
	Integer version;
}
