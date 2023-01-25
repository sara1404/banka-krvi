package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.AppointmentInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentInfoRepository extends JpaRepository<Appointment, Long> {
	AppointmentInfo save(AppointmentInfo appointmentInfo);
}
