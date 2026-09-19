package io.github.theisson.ecommerce.services.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteProduct {
    
    private final ProductRepository productRepository;

    @Transactional
    public void execute(Long id) {
        Product product = productRepository.findActiveById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + id));

        if (productRepository.hasOrders(id)) {
            product.softDelete();
            productRepository.save(product);
            return;
        }

        productRepository.delete(product);
    }
}
