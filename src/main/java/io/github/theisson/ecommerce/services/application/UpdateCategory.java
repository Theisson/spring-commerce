package io.github.theisson.ecommerce.services.application;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.CategoryRequestDTO;
import io.github.theisson.ecommerce.dto.CategoryResponseDTO;
import io.github.theisson.ecommerce.exceptions.DatabaseException;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateCategory {

    private final CategoryRepository categoryRepository;

    public UpdateCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponseDTO execute(Long id, CategoryRequestDTO dto) {
        try {
            Category entity = categoryRepository.getReferenceById(id);
            entity.updateData(dto.name(), dto.description());
            entity = categoryRepository.saveAndFlush(entity);
            return new CategoryResponseDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Categoria não encontrada com id: " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Já existe uma categoria com o nome: " + dto.name());
        }
    }
}
