package org.cams.mutualfund.management.repository;

import org.cams.mutualfund.management.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends JpaRepository<Transaction, String> {
}
