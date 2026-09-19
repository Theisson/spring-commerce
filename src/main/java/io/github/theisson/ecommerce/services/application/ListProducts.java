package io.github.theisson.ecommerce.services.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.ProductResponseDTO;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListProducts {
    
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> execute(String name, Long categoryId, Pageable pageable) {
        Page<Product> result = productRepository.findByFilters(name, categoryId, pageable);

        return result.map(ProductResponseDTO::new);
    }
}
