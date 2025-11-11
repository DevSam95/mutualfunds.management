package org.cams.mutualfund.management.repository;

import org.cams.mutualfund.management.entity.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, String> {
    @Modifying
	@Transactional
	@Query(value = "UPDATE mutual_fund SET available_units = :availableUnits WHERE id = :id", nativeQuery = true)
	int updateAvailableUnitsByFundId(@Param("id") String id, @Param("availableUnits") long availableUnits);
}
