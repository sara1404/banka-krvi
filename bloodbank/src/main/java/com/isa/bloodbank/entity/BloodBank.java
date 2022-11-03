package com.isa.bloodbank.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BloodBank extends BaseEntity {
	@Column
	String name;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "administrator_id", referencedColumnName = "id")
	User administrator;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	Address address;
	@Column
	String description;
	@Column
	double averageGrade;
}
