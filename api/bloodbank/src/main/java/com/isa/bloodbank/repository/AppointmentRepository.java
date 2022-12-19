package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findAllByBloodBankId(Long bloodBankId);

	List<Appointment> findAllByUserId(Long userId);

	Appointment save(Appointment appointments);

	@Query("select a from Appointment a where month(a.startTime) = ?1 and year(a.startTime) = ?2")
	List<Appointment> findAllByStartTimeMonthValueAndStartTime_Year(int month, int year);

	List<Appointment> findByStartTimeAndAvailable(LocalDateTime startTime, boolean available, Sort sort);
}
