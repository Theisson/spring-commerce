package io.github.theisson.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.models.entities.Product;

public record ProductDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    String imageUrl,
    List<String> categories
) {
    
    public ProductDTO(Product entity) {
        this(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getPrice().toBigDecimal(),
            entity.getImageUrl(),
            entity.getCategories().stream().map(Category::getName).sorted().toList()
        );
    }
}
