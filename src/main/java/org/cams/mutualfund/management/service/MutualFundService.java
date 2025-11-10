package org.cams.mutualfund.management.service;

import org.cams.mutualfund.management.dto.MutualFundDto;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.repository.MutualFundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MutualFundService {
 
    @Autowired
    private MutualFundRepository repo;
        
    public MutualFundDto getMutualFundById(String id) {
        MutualFund fund = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Mutual fund not found"));
        return MutualFundDto.toDto(fund);
    }

    public void addNewMutualFund(MutualFundDto fundDto) {
        MutualFund fund = new MutualFund();
        fund.setName(fundDto.name());
        fund.setValue(fundDto.value());
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
