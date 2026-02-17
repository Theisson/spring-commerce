package io.github.theisson.ecommerce.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.models.entities.Product;
import jakarta.validation.constraints.*;

public record ProductRequestDTO(

    @NotBlank(message = "Campo requerido")
    @Size(min = 3, max = 60, message = "Nome deve ter entre 3 e 60 caracteres")
    String name,

    @Size(min = 10, message = "Descrição deve ter no mínimo 10 caracteres")
    String description,

    @NotNull(message = "Campo requerido")
    @Positive(message = "O preço deve ser positivo")
    BigDecimal price,

    @Size(max = 255, message = "URL da imagem deve ter no máximo 255 caracteres")
    String imageUrl,

    @NotEmpty(message = "Deve possuir pelo menos uma categoria")
    Set<Long> categories
) {
    public ProductRequestDTO(Product entity) {
        this(
            entity.getName(),
            entity.getDescription(),
            entity.getPrice().toBigDecimal(),
            entity.getImageUrl(),
            entity.getCategories().stream().map(Category::getId).collect(Collectors.toSet())
        );
    }
}
