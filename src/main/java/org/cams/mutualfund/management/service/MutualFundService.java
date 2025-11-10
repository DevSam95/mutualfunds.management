package org.cams.mutualfund.management.service;

import java.time.LocalDate;
import java.util.List;

import org.cams.mutualfund.management.dto.MutualFundDto;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.repository.MutualFundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class MutualFundService {
 
    @Autowired
    private MutualFundRepository repo;

    public List<MutualFundDto> getAllFunds() {
        List<MutualFund> fund = repo.findAll();
        return fund.stream().map(MutualFundDto::toDto).toList();
    }

    public MutualFundDto getMutualFundById(String id) {
        MutualFund fund = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Mutual fund not found"));
        return MutualFundDto.toDto(fund);
    }

    @Transactional
    public void addNewMutualFund(MutualFundDto fundDto) {
        if (fundDto.date().isAfter(LocalDate.now())) {
            throw new ValidationException("Future dates are not allowed");
        }

        MutualFund fund = new MutualFund();
        fund.setName(fundDto.name());
        fund.setValue(fundDto.value());
        fund.setDate(fundDto.date());
        repo.save(fund);
    }

    public void updateMutualFund(String id, long nav) {
        MutualFund fund = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Mutual fund not found"));
        fund.setValue(nav);
        repo.save(fund);
    }

    public void deleteMutualFund(String id) {
        repo.deleteById(id);
    }
}
