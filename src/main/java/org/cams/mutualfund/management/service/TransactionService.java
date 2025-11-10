package org.cams.mutualfund.management.service;

import java.sql.Date;
import java.time.Instant;

import org.cams.mutualfund.management.dto.MutualFundDto;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.entity.Transaction;
import org.cams.mutualfund.management.entity.User;
import org.cams.mutualfund.management.repository.TransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private HoldingService holdingService;
 
    @Autowired
    private TransactionLogRepository repo;

    @Autowired
    private MutualFundService fundSvc;

    public void buy(String id, long units) {
        MutualFundDto fund = fundSvc.getMutualFundById(id);
        long assetValue = units * fund.value();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Transaction txn = new Transaction();
        txn.setFund(MutualFundDto.toEntity(fund));
        txn.setUser(user);
        txn.setType("BUY");
        txn.setUnits(units);
        txn.setValue(assetValue);
        txn.setDoneOn(new Date(Instant.now().toEpochMilli()));

        repo.save(txn);
    }

    public void sell(String id, long units) {
        MutualFundDto fundDto = fundSvc.getMutualFundById(id);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        
        MutualFund fund = MutualFundDto.toEntity(fundDto);
        holdingService.updateHolding(user, fund, units);

        Transaction txn = new Transaction();
        txn.setFund(fund);
        txn.setType("REDEEM");
        txn.setValue(units * fund.getValue());
        txn.setUnits(units);
        txn.setDoneOn(new Date(Instant.now().toEpochMilli()));

        repo.save(txn);
    }
}
