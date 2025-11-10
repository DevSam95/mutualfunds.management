package org.cams.mutualfund.management.service;

import org.cams.mutualfund.management.entity.Holding;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.entity.User;
import org.cams.mutualfund.management.repository.HoldingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class HoldingService {
 
    @Autowired
    private HoldingRepository repo;

    @Transactional
    public void updateHolding(User user, MutualFund fund, long units) {
        Holding userFundHolding = repo.findByUserAndFund(user, fund).orElse(null);
        if (userFundHolding == null) { userFundHolding = new Holding(); }

        userFundHolding.setFund(fund);
        userFundHolding.setUser(user);
        userFundHolding.setUnits(units);
        userFundHolding.setValue(units * fund.getValue());

        repo.save(userFundHolding);
    }
}
