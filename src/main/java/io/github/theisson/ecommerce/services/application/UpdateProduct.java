package io.github.theisson.ecommerce.services.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.ProductRequestDTO;
import io.github.theisson.ecommerce.dto.ProductResponseDTO;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.models.types.Money;
import io.github.theisson.ecommerce.repositories.CategoryRepository;
import io.github.theisson.ecommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateProduct {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public UpdateProduct(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public ProductResponseDTO execute(Long id, ProductRequestDTO dto) {
        try {
            Product entity = productRepository.getReferenceById(id);

            entity.updateData(
                dto.name(),
                dto.description(),
                new Money(dto.price()),
                dto.imageUrl()
            );

            entity.clearCategories();
            for (Long categoryId : dto.categories()) {
                Category category = categoryRepository.getReferenceById(categoryId);
                entity.addCategory(category);
            }

            entity = productRepository.save(entity);

            return new ProductResponseDTO(entity);

        } 
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
        }
    }
}
