package com.isa.bloodbank.dto;

import com.isa.bloodbank.entity.Address;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BloodBankDto {
	@NotNull
	String name;
	@NotNull
	Address address;
	@NotNull
	String description;
	double averageGrade;
}
