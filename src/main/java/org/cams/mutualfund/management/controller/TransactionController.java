package org.cams.mutualfund.management.controller;

import org.cams.mutualfund.management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/txn")
public class TransactionController {
    
    @Autowired
    private TransactionService txnService;

    @PostMapping("/buy/{fundId}/units/{units}")
    public String buy(@PathVariable String fundId, @PathVariable long units) {
        txnService.buy(fundId, units, SecurityContextHolder.getContext().getAuthentication());
        return "Mutual fund units purchased successfully";
    }

    @PostMapping("/redeem/{fundId}/units/{units}")
    public String redeem(@PathVariable String fundId, @PathVariable long units) {
        txnService.redeem(fundId, units, SecurityContextHolder.getContext().getAuthentication());
        return "Mutual fund units sold successfully";
    }
}
