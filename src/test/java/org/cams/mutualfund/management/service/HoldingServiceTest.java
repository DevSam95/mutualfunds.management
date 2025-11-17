package org.cams.mutualfund.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.cams.mutualfund.management.entity.AppUser;
import org.cams.mutualfund.management.entity.Holding;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.repository.HoldingRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HoldingServiceTest {

    @Mock
    private HoldingRepository repo;

    @InjectMocks
    private HoldingService service;

    @BeforeEach
    void setup() {}

    @Test
    void testAddHolding_valid() {
        Holding holding = new Holding();
        holding.setUnits(50);
        holding.setValue(100);
        holding.setUser(Mockito.mock(AppUser.class));

        MutualFund fund = Mockito.mock(MutualFund.class);
        when(fund.getValue()).thenReturn(2L);
        holding.setFund(fund);

        when(repo.findByUserAndFund(any(), any())).thenReturn(Optional.of(holding));
        
        service.add(holding.getUser(), holding.getFund(), 50);

        verify(repo).save(holding);
        assertEquals(100, holding.getUnits());
        assertEquals(200, holding.getValue());
    }

    @Test
    void testRemoveHolding_valid() {
        Holding holding = new Holding();
        holding.setUnits(100);
        holding.setValue(200);
        holding.setUser(Mockito.mock(AppUser.class));

        MutualFund fund = Mockito.mock(MutualFund.class);
        when(fund.getValue()).thenReturn(2L);
        holding.setFund(fund);

        when(repo.findByUserAndFund(any(), any())).thenReturn(Optional.of(holding));
        
        service.remove(holding.getUser(), holding.getFund(), 50);
        
        verify(repo).save(holding);
        assertEquals(50, holding.getUnits());
        assertEquals(100, holding.getValue());
    }
}
