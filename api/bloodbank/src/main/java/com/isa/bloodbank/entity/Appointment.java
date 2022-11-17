package com.isa.bloodbank.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Appointment extends BaseEntity {
	@Column
	Long bloodBankId;
	@Column
	LocalDateTime startTime;
	@Column
	LocalDateTime endTime;
	@ManyToMany()
	List<User> medicalStaff;
	@Column
	boolean available;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointment_info_id", referencedColumnName = "id")
	AppointmentInfo appointmentInfo;
	//@Column
	//boolean finished;
}
