package org.cams.mutualfund.management.dto;

import java.time.LocalDate;

import org.cams.mutualfund.management.entity.MutualFund;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MutualFundDto(

    String id,
    
    @NotBlank(message = "Name is mandatory")
    String name,
    
    @NotNull(message = "Asset value is mandatory")
    long value,

    @NotNull(message = "Date is mandatory")
    LocalDate date

) {

    public static MutualFundDto toDto(MutualFund fund) {
        return new MutualFundDto(fund.getId(), fund.getName(), fund.getValue(), fund.getDate());
    }

    public static MutualFund toEntity(MutualFundDto fundDto) {
        MutualFund fund = new MutualFund();
        fund.setId(fundDto.id());
        fund.setName(fundDto.name());
        fund.setValue(fundDto.value());
        fund.setDate(fundDto.date());
        return fund;
    }
}
