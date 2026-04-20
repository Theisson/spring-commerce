package io.github.theisson.ecommerce.services.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.CategoryResponseDTO;
import io.github.theisson.ecommerce.models.entities.Category;
import io.github.theisson.ecommerce.repositories.CategoryRepository;

@Service
public class ListCategories {

    private final CategoryRepository categoryRepository;

    public ListCategories(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> execute(Pageable pageable) {
        Page<Category> result = categoryRepository.findAll(pageable);
        return result.map(CategoryResponseDTO::new);
    }
}
