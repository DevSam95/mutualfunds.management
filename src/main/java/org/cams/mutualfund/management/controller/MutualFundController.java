package org.cams.mutualfund.management.controller;

import java.util.List;

import org.cams.mutualfund.management.dto.MutualFundDto;
import org.cams.mutualfund.management.service.MutualFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funds")
public class MutualFundController {
    
    @Autowired
    private MutualFundService mutualFundService;

    @GetMapping
    public List<MutualFundDto> getAllFunds() {
        return mutualFundService.getAllFunds();
    }

    @GetMapping("/{id}")
    public MutualFundDto getMutualFundById(@PathVariable String id) {
        return mutualFundService.getMutualFundById(id);
    }

    @PostMapping
    public void addNewMutualFund(@Valid @RequestBody MutualFundDto fund) {
        mutualFundService.addNewMutualFund(fund);
    }

    @PutMapping("/{id}/nav/{nav}")
    public void updateMutualFundNav(@PathVariable String id, @PathVariable long nav) {
        mutualFundService.updateMutualFund(id, nav);
    }

    @DeleteMapping("/{id}")
    public void deleteMutualFund(@PathVariable String id) {
        mutualFundService.deleteMutualFund(id);
    }
}
