package org.cams.mutualfund.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.validation.ValidationException;

import org.cams.mutualfund.management.entity.AppUser;
import org.cams.mutualfund.management.entity.MutualFund;
import org.cams.mutualfund.management.entity.Transaction;
import org.cams.mutualfund.management.repository.MutualFundRepository;
import org.cams.mutualfund.management.repository.TransactionLogRepository;
import org.cams.mutualfund.management.repository.UserRepository;
import org.cams.mutualfund.management.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionLogRepository repo;

    @InjectMocks
    private TransactionService service;

    @Mock
    private MutualFundRepository mutualFundRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HoldingService holdingService;

    @Mock
    private MutualFundService mutualFundService;

    @Mock
    private Authentication authentication;

    @Mock
    DateUtil dateUtil;
    
    private MutualFund fund;
    private AppUser appUser;
    private User principalUser;

    @BeforeEach
    void setUp() {
        fund = new MutualFund();
        fund.setId("DLSS");
        fund.setValue(10);
        fund.setAvailableUnits(100);
        fund.setDate(LocalDate.now());

        appUser = new AppUser();
        appUser.setId("user");
        appUser.setUsername("sam");

        principalUser = new User("sam", "pass", List.of());
    }

    @Test
    void testBuySuccess() {
        when(mutualFundRepository.getReferenceById("DLSS")).thenReturn(fund);
        when(authentication.getPrincipal()).thenReturn(principalUser);
        when(userRepository.getReferenceByUsername("sam")).thenReturn(Optional.of(appUser));

        try (MockedStatic<DateUtil> mocked = mockStatic(DateUtil.class)) {
            mocked.when(() -> DateUtil.isCurrentDate(fund.getDate())).thenReturn(true);

            service.buy("DLSS", 20, authentication);

            verify(holdingService).add(appUser, fund, 20);
            verify(mutualFundService).updateAvailableUnits("DLSS", 80);
            verify(repo).save(any(Transaction.class));
        }
    }

    @Test
    void testRedeem() {
        when(mutualFundRepository.getReferenceById("DLSS")).thenReturn(fund);
        when(authentication.getPrincipal()).thenReturn(principalUser);
        when(userRepository.getReferenceByUsername("sam")).thenReturn(Optional.of(appUser));

        service.redeem("DLSS", 10, authentication);

        verify(holdingService).remove(appUser, fund, 10);
        verify(repo).save(any(Transaction.class));
    }

    @Test
    void testBuyInsufficientUnits() {
        when(mutualFundRepository.getReferenceById("DLSS")).thenReturn(fund);
        when(authentication.getPrincipal()).thenReturn(principalUser);
        when(userRepository.getReferenceByUsername("sam")).thenReturn(Optional.of(appUser));
        try (MockedStatic<DateUtil> mocked = mockStatic(DateUtil.class)) {
            mocked.when(() -> DateUtil.isCurrentDate(fund.getDate())).thenReturn(true);

            var exception = assertThrows(ValidationException.class, () -> {
                service.buy("DLSS", 200, authentication);
            });

            assertEquals("Insufficient units available", exception.getMessage());

            verify(holdingService, never()).add(any(), any(), anyLong());
            verify(mutualFundService, never()).updateAvailableUnits(anyString(), anyLong());
            verify(repo, never()).save(any(Transaction.class));
        }
    }
}