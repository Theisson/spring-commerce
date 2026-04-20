package io.github.theisson.ecommerce.services.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.CategoryResponseDTO;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.repositories.CategoryRepository;

@Service
public class GetCategory {

    private final CategoryRepository categoryRepository;

    public GetCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO execute(Long id) {
        Category entity = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com id: " + id));

        return new CategoryResponseDTO(entity);
    }
}
