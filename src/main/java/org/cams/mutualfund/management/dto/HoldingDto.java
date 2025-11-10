package org.cams.mutualfund.management.dto;

import org.cams.mutualfund.management.entity.Holding;

public record HoldingDto(String id, String fund, long units, long value) {
    public static HoldingDto toDto(Holding holding) {
        return new HoldingDto(holding.getId(), holding.getFund().getName(), holding.getUnits(), holding.getValue());
    }
}
