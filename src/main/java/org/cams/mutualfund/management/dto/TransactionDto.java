package org.cams.mutualfund.management.dto;

import java.sql.Date;

import org.cams.mutualfund.management.entity.Transaction;

public record TransactionDto(String id, String type, String fundId, String userId, long units, long value, Date doneOn) {
    public static TransactionDto toDto(Transaction txn) {
        return new TransactionDto(
            txn.getId(),
            txn.getType(),
            txn.getFund().getId(),
            txn.getUser().getId(),
            txn.getUnits(),
            txn.getValue(),
            txn.getDoneOn()
        );
    }
}
