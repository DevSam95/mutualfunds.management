package org.cams.mutualfund.management.repository;

import org.cams.mutualfund.management.entity.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutualFundRepository extends JpaRepository<MutualFund, String> {
}
