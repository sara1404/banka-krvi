package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentInfoRepository extends JpaRepository<Appointment, Long> {
}
