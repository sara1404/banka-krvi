package com.isa.bloodbank.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Appointment extends BaseEntity {
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "blood_bank_id", referencedColumnName = "id")
	BloodBank bloodBank;
	@Column
	LocalDateTime startTime;
	@Column
	double duration;
	@Column
	boolean available;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "appointment_info_id", referencedColumnName = "id")
	AppointmentInfo appointmentInfo;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	@Column
	boolean finished;
	@ManyToOne
	@JoinColumn(name = "nurse_id", referencedColumnName = "id")
	User nurse;
	@Version
	Long version;
}
