package com.isa.bloodbank.repository;

import com.isa.bloodbank.dto.PageDto;
import com.isa.bloodbank.entity.BloodBank;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
	Page<BloodBank> findByNameContainingIgnoreCase(String infix, PageRequest pageRequest);
	Page<BloodBank> findByAddressId_CityContainingIgnoreCase(String city, PageRequest pageRequest);
	Page<BloodBank> findByNameContainingIgnoreCaseAndAddressId_CityContainingIgnoreCase(String name, String city, PageRequest pageRequest);
	Page<BloodBank> findByAverageGradeGreaterThanEqual(double averageGrade, PageRequest pageRequest);
	Page<BloodBank> findByNameContainingIgnoreCaseAndAverageGradeGreaterThanEqual(String infix, double averageGrade, PageRequest pageRequest);
	Page<BloodBank> findByAverageGradeGreaterThanEqualAndAddressId_CityContainingIgnoreCase(double averageGrade, String city, PageRequest pageRequest);
	Page<BloodBank> findByNameContainingIgnoreCaseAndAverageGradeGreaterThanEqualAndAddressId_CityContainingIgnoreCase(String name, double averageGrade, String city, PageRequest pageRequest);
	BloodBank save(BloodBank bloodBank);
	BloodBank findBloodBankByName(String name);

	String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) *" +
			" cos(radians(a.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(a.latitude))))";
	@Query(nativeQuery = true, value = "SELECT * FROM blood_bank b join address a on a.id = b.address_id WHERE " + HAVERSINE_FORMULA + " < :distance AND UPPER(b.name) LIKE UPPER(CONCAT('%', :name, '%')) AND UPPER(a.city) LIKE UPPER(CONCAT('%', :city, '%')) AND b.average_grade >= :average_grade"
	, countQuery = "SELECT count(*) FROM blood_bank b join address a on a.id = b.address_id WHERE " + HAVERSINE_FORMULA + " < :distance AND UPPER(b.name) LIKE UPPER(CONCAT('%', :name, '%')) AND UPPER(a.city) LIKE UPPER(CONCAT('%', :city, '%')) AND b.average_grade >= :average_grade")
	Page<BloodBank> filterBloodBanks(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distanceWithInKM, @Param("name") String name, @Param("average_grade") double averageGrade,@Param("city") String city, Pageable pageable);
}
