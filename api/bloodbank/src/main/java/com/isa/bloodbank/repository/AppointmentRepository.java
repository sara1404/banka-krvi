package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findAllByBloodBankId(Long bloodBankId);
	List<Appointment> findAllByStartTimeAndAvailable(LocalDateTime startTime, boolean available);
}
