package org.cams.mutualfund.management.service;

import java.time.LocalDate;

import org.cams.mutualfund.management.entity.AppUser;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.entity.Transaction;
import org.cams.mutualfund.management.repository.MutualFundRepository;
import org.cams.mutualfund.management.repository.TransactionLogRepository;
import org.cams.mutualfund.management.repository.UserRepository;
import org.cams.mutualfund.management.util.DateUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
@Transactional
public class TransactionService {

    private final TransactionLogRepository repo;
    private final MutualFundRepository mutualFundRepository;
    private final UserRepository userRepository;
    private final HoldingService holdingService;
    private final MutualFundService mutualFundService;

    public TransactionService(
        TransactionLogRepository repo,
        MutualFundRepository mutualFundRepository,
        UserRepository userRepository,
        HoldingService holdingService,
        MutualFundService mutualFundService
    ) {
        this.repo = repo;
        this.mutualFundRepository = mutualFundRepository;
        this.userRepository = userRepository;
        this.holdingService = holdingService;
        this.mutualFundService = mutualFundService;
    }

    public void buy(String id, long units, Authentication authentication) {

        MutualFund fund = mutualFundRepository.getReferenceById(id);

        if (!DateUtil.isCurrentDate(fund.getDate())) {
            throw new ValidationException("Only current date transactions are allowed");
        }

        User principal = (User) authentication.getPrincipal();
        AppUser appUser = userRepository.getReferenceByUsername(principal.getUsername())
                .orElseThrow();

        if (units > fund.getAvailableUnits()) {
            throw new ValidationException("Insufficient units available");
        }

        long assetValue = units * fund.getValue();

        holdingService.add(appUser, fund, units);

        mutualFundService.updateAvailableUnits(id, fund.getAvailableUnits() - units);

        Transaction txn = new Transaction();
        txn.setFund(fund);
        txn.setUser(appUser);
        txn.setType("BUY");
        txn.setUnits(units);
        txn.setValue(assetValue);
        txn.setDoneOn(LocalDate.now());

        repo.save(txn);
    }

    public void redeem(String id, long units, Authentication authentication) {

        MutualFund fund = mutualFundRepository.getReferenceById(id);

        User principal = (User) authentication.getPrincipal();
        AppUser appUser = userRepository.getReferenceByUsername(principal.getUsername()).orElseThrow();

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