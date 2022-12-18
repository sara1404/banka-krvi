package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.AppointmentInfo;
import com.isa.bloodbank.entity.BloodBank;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentInfoRepository extends JpaRepository<Appointment, Long> {
	AppointmentInfo save(AppointmentInfo appointmentInfo);
}
