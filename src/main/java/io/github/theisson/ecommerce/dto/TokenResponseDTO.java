package io.github.theisson.ecommerce.dto;

public record TokenResponseDTO(
    String accessToken,
    String refreshToken,
    String tokenType,
    Long expiresIn
) {}
