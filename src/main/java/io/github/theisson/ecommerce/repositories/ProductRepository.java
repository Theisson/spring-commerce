package io.github.theisson.ecommerce.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.github.theisson.ecommerce.models.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
        value = "SELECT DISTINCT p FROM Product p LEFT JOIN p.categories c " +
                "WHERE p.deletedAt IS NULL " +
                "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                "AND (:categoryId IS NULL OR c.id = :categoryId)",
        countQuery = "SELECT COUNT(DISTINCT p) FROM Product p LEFT JOIN p.categories c " +
                     "WHERE p.deletedAt IS NULL " +
                     "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                     "AND (:categoryId IS NULL OR c.id = :categoryId)"
    )
    Page<Product> findByFilters(
        @Param("name") String name,
        @Param("categoryId") Long categoryId,
        Pageable pageable
    );

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<Product> findActiveById(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN TRUE ELSE FALSE END " +
           "FROM OrderItem oi WHERE oi.id.product.id = :productId")
    boolean hasOrders(@Param("productId") Long productId);
}
