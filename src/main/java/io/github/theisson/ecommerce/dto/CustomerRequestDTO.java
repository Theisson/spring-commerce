package io.github.theisson.ecommerce.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record CustomerRequestDTO(

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 20, message = "Nome de usuário deve ter entre 3 e 20 caracteres")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Nome de usuário deve conter apenas letras e números")
    String username,

    @NotBlank(message = "Campo requerido")
    @Email(message = "E-mail inválido")
    String email,

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha deve conter pelo menos 8 caracteres")
    String password,

    @NotBlank(message = "Campo requerido")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos numéricos")
    String cpf,

    @NotNull(message = "Campo requerido")
    @Past(message = "A data de nascimento deve ser uma data passada")
    LocalDate birthDate,

    @NotBlank(message = "Campo requerido")
    @Pattern(regexp = "^[1-9]{2}(?:[2-8]\\d{7}|9\\d{8})$", message = "Número de telefone inválido")
    String phoneNumber
) {}
