package io.github.theisson.ecommerce.services.application;

import org.springframework.stereotype.Service;
import io.github.theisson.ecommerce.dto.ProductRequestDTO;
import io.github.theisson.ecommerce.dto.ProductResponseDTO;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.models.types.Money;
import io.github.theisson.ecommerce.repositories.CategoryRepository;
import io.github.theisson.ecommerce.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class CreateProduct {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CreateProduct(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponseDTO execute(ProductRequestDTO dto) {
        Product entity = new Product(
            dto.name(),
            dto.description(),
            new Money(dto.price()),
            dto.imageUrl()
        );

        for (Long categoryId : dto.categories()) {
            Category category = categoryRepository.getReferenceById(categoryId);
            entity.addCategory(category);
        }

        entity = productRepository.save(entity);

        return new ProductResponseDTO(entity);
    }
}
