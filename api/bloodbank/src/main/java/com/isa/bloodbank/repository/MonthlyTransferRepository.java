package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.MonthlyTransfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyTransferRepository extends JpaRepository<MonthlyTransfer, Long> {
}
