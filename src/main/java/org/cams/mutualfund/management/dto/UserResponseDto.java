package org.cams.mutualfund.management.dto;

import java.util.List;

import org.cams.mutualfund.management.entity.User;

public record UserResponseDto (String id, String username, String role, List<HoldingDto> holdings) {
    public static UserResponseDto map(User user) {
        List<HoldingDto> holdings = user.getHoldings().stream().map(HoldingDto::toDto).toList();
        return new UserResponseDto(user.getId(), user.getUsername(), user.getRole(), holdings);
    }
};
