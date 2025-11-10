package org.cams.mutualfund.management.dto;

public record UserRequestDto(String username, String password, String role, String contact, Long nav) {}
