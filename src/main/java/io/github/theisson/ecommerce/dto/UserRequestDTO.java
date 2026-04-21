package io.github.theisson.ecommerce.dto;

import jakarta.validation.constraints.*;

public record UserRequestDTO(

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 20, message = "Nome de usuário deve ter entre 3 e 20 caracteres")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Nome de usuário deve conter apenas letras e números")
    String username,

    @NotBlank(message = "Campo requerido")
    @Email(message = "E-mail inválido")
    String email,

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha deve conter pelo menos 8 caracteres")
    String password
) {}
