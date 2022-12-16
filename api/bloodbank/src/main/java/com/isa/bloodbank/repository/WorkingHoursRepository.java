package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.Appointment;
import com.isa.bloodbank.entity.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {
}
