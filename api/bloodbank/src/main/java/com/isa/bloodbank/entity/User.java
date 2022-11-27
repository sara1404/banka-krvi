package com.isa.bloodbank.entity;

import com.isa.bloodbank.entity.enums.BloodType;
import com.isa.bloodbank.entity.enums.Gender;
import com.isa.bloodbank.entity.enums.UserType;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

	@Column(unique = true)
	Long jmbg;

	@Column(unique = true)
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

	@Column
	String phoneNumber;
	@Column
	Gender gender;
	@Column
	String workplaceName;
	@Column
	String jobTitle;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	Address address;

	@ManyToOne
	@JoinColumn(name = "bloodbank_id", referencedColumnName = "id")
	BloodBank bloodBank;
	@ManyToMany()
	List<Appointment> appointments;

}
