package org.cams.mutualfund.management.controller;

import java.util.List;

import org.cams.mutualfund.management.dto.UserRequestDto;
import org.cams.mutualfund.management.dto.UserResponseDto;
import org.cams.mutualfund.management.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);        
    }

    // Open endpoint for now. ideally secured with Api Key or SuperAdmin Credentials/JWT
    @PostMapping("/enroll")
    public void enrollUser(@Valid @RequestBody UserRequestDto user) {
        userService.enrollUser(user);
    }
}
