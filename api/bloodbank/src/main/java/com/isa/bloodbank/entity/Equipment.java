package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.EquipmentType;

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
public class Equipment extends BaseEntity {
	@Column
	EquipmentType equipmentType;
	@Column
	int quantity;
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "bloodbank_id", referencedColumnName = "id")
	BloodBank bloodBank;

}
