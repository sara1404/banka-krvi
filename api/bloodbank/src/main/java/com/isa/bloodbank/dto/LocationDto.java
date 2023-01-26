package com.isa.bloodbank.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {
	private List<Double> startLocation;
	private List<Double> endLocation;
	private int frequency;
}
