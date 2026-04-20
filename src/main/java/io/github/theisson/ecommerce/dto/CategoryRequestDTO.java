package io.github.theisson.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 20, message = "Nome deve ter entre 3 e 20 caracteres")
    String name,

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    String description
) {}
