package com.isa.bloodbank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Address extends BaseEntity {
	@Column
	String street;
	@Column
	int number;
	@Column
	String city;
	@Column
	int zipcode;
	@Column
	String country;
	@Column
	double longitude;
	@Column
	double latitude;
}
