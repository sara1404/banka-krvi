package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findAllByBloodBankId(Long bloodBankId);

	List<Appointment> findAllByUserId(Long userId);

	Appointment save(Appointment appointment);

	List<Appointment> findByStartTimeAndAvailable(LocalDateTime startTime, boolean available, Sort sort);
}
