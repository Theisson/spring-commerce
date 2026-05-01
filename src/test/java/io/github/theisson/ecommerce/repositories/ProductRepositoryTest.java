package io.github.theisson.ecommerce.repositories;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import io.github.theisson.ecommerce.models.entities.Product;
import io.github.theisson.ecommerce.models.types.Money;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager em;

    // --- findActiveById ---

    @Test
    @DisplayName("findActiveById deve retornar produto ativo existente (seed)")
    void findActiveByIdShouldReturnActiveProduct() {
        // Seed: produto id=1 é "The Lord of the Rings", ativo
        Optional<Product> result = productRepository.findActiveById(1L);

        assertTrue(result.isPresent());
        assertEquals("The Lord of the Rings", result.get().getName());
    }

    @Test
    @DisplayName("findActiveById deve retornar vazio para id inexistente")
    void findActiveByIdShouldReturnEmptyForNonExistentId() {
        Optional<Product> result = productRepository.findActiveById(9999L);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("findActiveById deve retornar vazio para produto com soft delete")
    void findActiveByIdShouldReturnEmptyForSoftDeletedProduct() {
        Product product = new Product(
            "ZZZ_Produto_Deletado",
            "Descrição de teste",
            new Money(new BigDecimal("19.99")),
            "http://img.test/deleted.jpg",
            5
        );

        em.persist(product);
        em.flush();

        product.softDelete();
        em.flush();
        em.clear();

        Optional<Product> result = productRepository.findActiveById(product.getId());
        assertFalse(result.isPresent());
    }

    // --- findByFilters ---

    @Test
    @DisplayName("findByFilters deve retornar todos os produtos ativos quando sem filtros")
    void findByFiltersShouldReturnAllActiveProductsWithNoFilters() {
        // Seed carrega 30 produtos ativos
        Page<Product> result = productRepository.findByFilters(null, null, Pageable.unpaged());
        assertEquals(30, result.getTotalElements());
    }

    @Test
    @DisplayName("findByFilters deve filtrar por nome de forma case-insensitive")
    void findByFiltersShouldFilterByNameCaseInsensitive() {
        // Seed: "Clean Code" (id=7)
        Page<Product> result = productRepository.findByFilters("clean", null, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Clean Code", result.getContent().get(0).getName());
    }

    @Test
    @DisplayName("findByFilters deve excluir produtos com soft delete")
    void findByFiltersShouldExcludeSoftDeletedProducts() {
        Product deleted = new Product(
            "ZZZ_Produto_Oculto",
            "Não deve aparecer",
            new Money(new BigDecimal("9.99")),
            "",
            1
        );
        em.persist(deleted);
        em.flush();
        deleted.softDelete();
        em.flush();
        em.clear();

        // Ainda deve haver apenas os 30 do seed (sem o produto deletado)
        Page<Product> result = productRepository.findByFilters(null, null, Pageable.unpaged());
        assertEquals(30, result.getTotalElements());
    }

    // --- hasOrders ---

    @Test
    @DisplayName("hasOrders deve retornar true quando produto possui itens de pedido (seed)")
    void hasOrdersShouldReturnTrueWhenProductHasOrders() {
        // Seed: produto id=1 tem order_items nas orders 1 e 2
        assertTrue(productRepository.hasOrders(1L));
    }

    @Test
    @DisplayName("hasOrders deve retornar false quando produto não possui itens de pedido (seed)")
    void hasOrdersShouldReturnFalseWhenProductHasNoOrders() {
        // Seed: produto id=4 ("PC Gamer") não tem nenhum order_item
        assertFalse(productRepository.hasOrders(4L));
    }

    @Test
    @DisplayName("hasOrders deve retornar false para produto recém-criado sem pedidos")
    void hasOrdersShouldReturnFalseForNewProduct() {
        Product product = new Product(
            "ZZZ_Produto_Novo",
            "Produto sem pedidos",
            new Money(new BigDecimal("99.00")),
            "",
            10
        );
        em.persistAndFlush(product);

        assertFalse(productRepository.hasOrders(product.getId()));
    }
}
