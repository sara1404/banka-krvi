package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findAllByBloodBankId(Long bloodBankId);
}
