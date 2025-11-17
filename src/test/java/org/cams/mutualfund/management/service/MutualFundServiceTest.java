package org.cams.mutualfund.management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.cams.mutualfund.management.dto.MutualFundDto;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.repository.MutualFundRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MutualFundServiceTest {
    @Mock
    private MutualFundRepository repo;


    @InjectMocks
    private MutualFundService service;

    @BeforeEach
    void setup() {
    }

    @Test
    void testGetAllFunds() {
        when(repo.findAll()).thenReturn(List.of(Mockito.mock(MutualFund.class)));
        var res = service.getAllFunds();
        assertEquals(1, res.size());
        verify(repo).findAll();
    }
    
    @Test
    void testGetMutualFundById_found() {
        MutualFund fund = Mockito.mock(MutualFund.class);
        when(repo.findById("DLSS")).thenReturn(Optional.of(fund));
        MutualFundDto result = service.getMutualFundById("DLSS");
        assertNotNull(result);
        assertEquals(fund.getId(), result.id());
        verify(repo).findById("DLSS");
    }

    // Adding only this negative case for reference. All others will be happy paths
    @Test
    void testGetMutualFundById_notFound() {
        when(repo.findById("UNKNOWN")).thenReturn(Optional.empty());
        var exception = assertThrows(EntityNotFoundException.class, () -> {
            service.getMutualFundById("UNKNOWN");
        });
        assertEquals("Mutual fund not found", exception.getMessage());
        verify(repo).findById("UNKNOWN");
    }

    @Test
    void testDeleteMutualFund() {
        service.deleteMutualFund("DLSS");
        verify(repo).deleteById("DLSS");
    }

    @Test
    void testUpdateMutualFund() {
        MutualFund fund = Mockito.mock(MutualFund.class);
        when(repo.findById("DLSS")).thenReturn(Optional.of(fund));

        service.updateMutualFund("DLSS", 5000L);

        verify(repo).findById("DLSS");
        verify(fund).setValue(5000L);
        verify(repo).save(fund);
    }

    @Test
    void testAddNewMutualFund_valid() {
        MutualFundDto dto = new MutualFundDto("DLSS", "Tax Saver", 1000L, LocalDate.now(), 1000L);
        service.addNewMutualFund(dto);
        verify(repo).save(any(MutualFund.class));
    }
}
