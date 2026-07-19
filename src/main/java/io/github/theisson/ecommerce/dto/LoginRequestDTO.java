package io.github.theisson.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
    
    @NotBlank(message = "Campo requerido")
    String login,

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres")
    String password
) {}
