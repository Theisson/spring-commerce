package io.github.theisson.ecommerce.services.application;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.exceptions.DatabaseException;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.repositories.ProductRepository;

@Service
public class DeleteProduct {
    private final ProductRepository productRepository;
    
    public DeleteProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void execute(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com id: " + id);
        }

        try {
            productRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial. Produto já possui pedidos.");
        }
    }
}
