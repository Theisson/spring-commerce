package io.github.theisson.ecommerce.services.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.theisson.ecommerce.exceptions.ResourceNotFoundException;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.models.types.Money;
import io.github.theisson.ecommerce.repositories.ProductRepository;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class DeleteProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProduct deleteProduct;

    private Product buildProduct() {
        return new Product("Test Product", "A description", new Money(new BigDecimal("49.99")), "http://img.test/p.jpg", 10);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando produto não existe")
    void shouldThrowWhenProductNotFound() {
        when(productRepository.findActiveById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deleteProduct.execute(99L));

        verify(productRepository, never()).save(any());
        verify(productRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve realizar soft delete quando produto possui pedidos")
    void shouldSoftDeleteWhenProductHasOrders() {
        Product product = buildProduct();
        when(productRepository.findActiveById(1L)).thenReturn(Optional.of(product));
        when(productRepository.hasOrders(1L)).thenReturn(true);

        assertNull(product.getDeletedAt());

        deleteProduct.execute(1L);

        assertNotNull(product.getDeletedAt());
        verify(productRepository).save(product);
        verify(productRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve realizar hard delete quando produto não possui pedidos")
    void shouldHardDeleteWhenProductHasNoOrders() {
        Product product = buildProduct();
        when(productRepository.findActiveById(2L)).thenReturn(Optional.of(product));
        when(productRepository.hasOrders(2L)).thenReturn(false);

        deleteProduct.execute(2L);

        verify(productRepository).delete(product);
        verify(productRepository, never()).save(any());
        assertNull(product.getDeletedAt());
    }
}
