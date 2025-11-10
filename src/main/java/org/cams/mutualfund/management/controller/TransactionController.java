package org.cams.mutualfund.management.controller;

import org.cams.mutualfund.management.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/txn")
public class TransactionController {
    
    @Autowired
    private TransactionService txnService;

    @PostMapping("/buy/{id}/units/{units}")
    public String buy(@PathVariable String fundId, @PathVariable long units) {
        txnService.buy(fundId, units);
        return "Mutual fund units purchased successfully";
    }

    @PostMapping("/sell/{id}/units/{units}")
    public String sell(@PathVariable String fundId, @PathVariable long units) {
        txnService.sell(fundId, units);
        return "Mutual fund units sold successfully";
    }
}
