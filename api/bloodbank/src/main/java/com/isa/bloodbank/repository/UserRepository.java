package com.isa.bloodbank.repository;

import com.isa.bloodbank.entity.User;
import com.isa.bloodbank.entity.enums.UserType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	//@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "0") })
	List<User> findByBloodBankId(Long bloodBankId);

	User findByEmail(String email);

	User save(User administrator);

	List<User> getUsersByUserTypeAndFirstNameContainsAndLastNameContains(UserType type, String firstName, String lastName);

	List<User> getUsersByFirstNameContainsAndLastNameContains(String firstName, String lastName);

	List<User> getUsersByUserTypeAndBloodBankIsNull(UserType type);
}
