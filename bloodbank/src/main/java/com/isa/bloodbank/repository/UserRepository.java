package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByBloodBankId(Long bloodBankId);
}
