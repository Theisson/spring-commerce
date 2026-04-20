package io.github.theisson.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.github.theisson.ecommerce.models.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Modifying
    @Query(value = "DELETE FROM product_category WHERE category_id = :categoryId", nativeQuery = true)
    void removeAllProductAssociations(@Param("categoryId") Long categoryId);
}
