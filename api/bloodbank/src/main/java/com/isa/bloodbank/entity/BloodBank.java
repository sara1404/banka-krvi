package com.isa.bloodbank.entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OptimisticLocking;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Entity
@OptimisticLocking
public class BloodBank extends BaseEntity {
	@Column(unique = true)
	String name;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	Address address;
	@Column
	String description;
	@Column
	double averageGrade;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "working_hours_id", referencedColumnName = "id")
	WorkingHours  workingHours;
	@Version
	Integer version;
	@Column
	Boolean available;
}
