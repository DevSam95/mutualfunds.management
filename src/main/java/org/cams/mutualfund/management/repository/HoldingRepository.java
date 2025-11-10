package org.cams.mutualfund.management.repository;

import java.util.Optional;

import org.cams.mutualfund.management.entity.Holding;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoldingRepository extends JpaRepository<Holding, String> {
    public Optional<Holding> findByUserAndFund(AppUser user, MutualFund fund);
}
