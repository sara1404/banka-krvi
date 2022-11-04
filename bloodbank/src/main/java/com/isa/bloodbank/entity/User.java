package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "blood_bank_user")
public class User extends BaseEntity {
	@Column
	@Size(min = 1, message = "First name cannot be shorter than 1 characters!")
	@Size(max = 30, message = "First name cannot be longer than 30 characters!")
	String firstName;

	@Column
	@Size(min = 1, message = "Last name cannot be shorter than 1 characters!")
	@Size(max = 30, message = "Last name cannot be longer than 30 characters!")
	String lastName;

	@Column
	Long jmbg;

	@Column
	@NotBlank(message = "Email cannot be blank!")
	@Email(message = "Email not of correct format!")
	String email;

	@Column
	@Enumerated(EnumType.STRING)
	UserType userType;

	@Column
	@Enumerated(EnumType.STRING)
	BloodType bloodType;
	@Column
	String password;
	@Column
	boolean firstLogged;
	@ManyToOne()
	@JoinColumn(name = "bloodbank_id", referencedColumnName = "id")
	BloodBank bloodBank;

}
