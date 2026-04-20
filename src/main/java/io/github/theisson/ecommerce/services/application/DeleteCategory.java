package io.github.theisson.ecommerce.services.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.repositories.CategoryRepository;

@Service
public class DeleteCategory {

    private final CategoryRepository categoryRepository;

    public DeleteCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void execute(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada com id: " + id);
        }

        categoryRepository.removeAllProductAssociations(id);
        categoryRepository.deleteById(id);
    }
}
