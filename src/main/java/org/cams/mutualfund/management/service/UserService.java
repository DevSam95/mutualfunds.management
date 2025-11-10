package org.cams.mutualfund.management.service;

import java.util.List;

import org.cams.mutualfund.management.dto.UserRequestDto;
import org.cams.mutualfund.management.dto.UserResponseDto;
import org.cams.mutualfund.management.entity.AppUser;
import org.cams.mutualfund.management.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.repo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = repo.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(
            user.getUsername(),
            user.getPassword(),
            user.isEnabled(),
            true, true, true,
            List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    public void enrollUser(UserRequestDto user) {
        AppUser newUser = new AppUser();
        newUser.setUsername(user.username());

        String encodedPwd = passwordEncoder.encode(user.password());
        newUser.setPassword(encodedPwd);

        newUser.setEnabled(true);
        newUser.setRole(user.role());

        repo.save(newUser);
    }

    public UserResponseDto getUserByUsername(String username) {
        AppUser user = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return UserResponseDto.map(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<AppUser> users = repo.findAll();
        return users.stream().map(UserResponseDto::map).toList();
    }

    @Transactional
    public void deleteUser(String username) {
        repo.deleteByUsername(username);
    }
}
