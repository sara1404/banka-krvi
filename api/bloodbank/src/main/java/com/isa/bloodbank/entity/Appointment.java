package com.isa.bloodbank.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Appointment extends BaseEntity {
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "blood_bank_id", referencedColumnName = "id")
	BloodBank bloodBank;
	@Column
	LocalDateTime startTime;
	@Column
	double duration;
	@ManyToMany()
	List<User> medicalStaff;
	@Column
	boolean available;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointment_info_id", referencedColumnName = "id")
	AppointmentInfo appointmentInfo;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
}
