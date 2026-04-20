package io.github.theisson.ecommerce.services.application;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import io.github.theisson.ecommerce.dto.CategoryRequestDTO;
import io.github.theisson.ecommerce.dto.CategoryResponseDTO;
import io.github.theisson.ecommerce.exceptions.DatabaseException;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.repositories.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CreateCategory {

    private final CategoryRepository categoryRepository;

    public CreateCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponseDTO execute(CategoryRequestDTO dto) {
        try {
            Category entity = new Category(dto.name(), dto.description());
            entity = categoryRepository.saveAndFlush(entity);
            return new CategoryResponseDTO(entity);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Já existe uma categoria com o nome: " + dto.name());
        }
    }
}
