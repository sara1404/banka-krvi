package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findAllByBloodBankId(Long bloodBankId);

	List<Appointment> findAllByBloodBankIdAndFinishedTrue(Long bloodBankId, Sort sort);

	List<Appointment> findAllByUserId(Long userId);

	Appointment save(Appointment appointments);

	@Query("select a from Appointment a where month(a.startTime) = ?1 and year(a.startTime) = ?2 and a.bloodBank.id = ?3")
	List<Appointment> findAllByStartTimeMonthValueAndStartTime_Year(int month, int year, Long bloodBankId);

	List<Appointment> findByStartTimeAndAvailable(LocalDateTime startTime, boolean available, Sort sort);

	@Query("select a from Appointment a where a.startTime > ?1 and a.nurse is not null and a.user is null")
	List<Appointment> getPredefined(LocalDateTime currentTime, Pageable pageable);

	@Query("select a from Appointment a where a.user.id = ?1")
	List<Appointment> getPersonal(Long userId, Pageable pageable);

	@Query("select a from Appointment a where a.user.id = ?1")
	List<Appointment> getPersonal(Long userId);
}
