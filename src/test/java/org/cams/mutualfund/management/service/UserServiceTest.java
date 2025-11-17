package org.cams.mutualfund.management.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.cams.mutualfund.management.dto.UserRequestDto;
import org.cams.mutualfund.management.dto.UserResponseDto;
import org.cams.mutualfund.management.entity.AppUser;
import org.cams.mutualfund.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = new AppUser();
        appUser.setId("U001");
        appUser.setUsername("sam");
        appUser.setPassword("encoded");
        appUser.setEnabled(true);
        appUser.setRole("USER");
    }

    @Test
    void testloadUserByUsername() {
        when(repo.findByUsername("sam")).thenReturn(Optional.of(appUser));

        var userDetails = userService.loadUserByUsername("sam");

        assertEquals("sam", userDetails.getUsername());
        assertEquals("encoded", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(
            userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
        );

        verify(repo).findByUsername("sam");
    }

    @Test
    void testEnrollUser() {
        UserRequestDto dto = new UserRequestDto("sam", "pwd123", "ADMIN", null, 0L);

        when(passwordEncoder.encode("pwd123")).thenReturn("encodedPwd");

        userService.enrollUser(dto);

        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(repo).save(captor.capture());

        AppUser saved = captor.getValue();
        assertEquals("sam", saved.getUsername());
        assertEquals("encodedPwd", saved.getPassword());
        assertTrue(saved.isEnabled());
        assertEquals("ADMIN", saved.getRole());

        verify(passwordEncoder).encode("pwd123");
    }
}
