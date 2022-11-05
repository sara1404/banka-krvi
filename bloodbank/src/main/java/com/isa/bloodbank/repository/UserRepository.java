package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.BloodType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
