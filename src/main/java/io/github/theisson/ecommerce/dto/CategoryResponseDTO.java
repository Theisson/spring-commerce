package io.github.theisson.ecommerce.dto;

import io.github.theisson.ecommerce.models.entities.Category;

public record CategoryResponseDTO(
    Long id,
    String name,
    String description
) {

    public CategoryResponseDTO(Category entity) {
        this(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
        );
    }
}
