package org.cams.mutualfund.management.service;

import java.time.LocalDate;

import org.cams.mutualfund.management.entity.AppUser;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.entity.Transaction;
import org.cams.mutualfund.management.repository.MutualFundRepository;
import org.cams.mutualfund.management.repository.TransactionLogRepository;
import org.cams.mutualfund.management.repository.UserRepository;
import org.cams.mutualfund.management.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private HoldingService holdingService;
 
    @Autowired
    private TransactionLogRepository repo;

    @Autowired
    private MutualFundRepository mutualFundRepository;

    @Autowired
    private UserRepository userRepository;

    public void buy(String id, long units) {
        MutualFund fund = mutualFundRepository.getReferenceById(id);

        if (! DateUtil.isCurrentDate(fund.getDate())) {
            throw new ValidationException("Fund out of date");
        }

        long assetValue = units * fund.getValue();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        AppUser appUser = userRepository.getReferenceByUsername(user.getUsername()).orElseThrow();

        holdingService.add(appUser, fund, units);

        Transaction txn = new Transaction();
        txn.setFund(fund);
        txn.setUser(appUser);
        txn.setType("BUY");
        txn.setUnits(units);
        txn.setValue(assetValue);
        txn.setDoneOn(LocalDate.now());

        repo.save(txn);
    }

    public void sell(String id, long units) {
        MutualFund fund =  mutualFundRepository.getReferenceById(id);;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        AppUser appUser = userRepository.getReferenceByUsername(user.getUsername()).orElseThrow();

        holdingService.remove(appUser, fund, units);

        Transaction txn = new Transaction();
        txn.setFund(fund);
        txn.setType("REDEEM");
        txn.setValue(units * fund.getValue());
        txn.setUnits(units);
        txn.setDoneOn(LocalDate.now());

        repo.save(txn);
    }
}
