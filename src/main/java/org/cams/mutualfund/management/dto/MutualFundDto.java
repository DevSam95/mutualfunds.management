package org.cams.mutualfund.management.dto;

import org.cams.mutualfund.management.entity.MutualFund;

public record MutualFundDto(String id, String name, long value) {
    public static MutualFundDto toDto(MutualFund fund) {
        return new MutualFundDto(fund.getId(), fund.getName(), fund.getValue());
    }

    public static MutualFund toEntity(MutualFundDto fundDto) {
        MutualFund fund = new MutualFund();
        fund.setId(fundDto.id());
        fund.setName(fundDto.name());
        fund.setValue(fundDto.value());
        return fund;
    }
}
