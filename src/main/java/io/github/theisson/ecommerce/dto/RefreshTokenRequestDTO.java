package io.github.theisson.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(
    
    @NotBlank(message = "Campo requerido")
    String refreshToken
) {}
