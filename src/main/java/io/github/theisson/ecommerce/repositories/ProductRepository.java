package io.github.theisson.ecommerce.repositories;

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
                "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                "AND (:categoryId IS NULL OR c.id = :categoryId)",
        countQuery = "SELECT COUNT(DISTINCT p) FROM Product p LEFT JOIN p.categories c " +
                     "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                     "AND (:categoryId IS NULL OR c.id = :categoryId)"
    )
    Page<Product> findByFilters(
        @Param("name") String name,
        @Param("categoryId") Long categoryId,
        Pageable pageable
    );
}
