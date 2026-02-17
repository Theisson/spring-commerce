package io.github.theisson.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.theisson.ecommerce.models.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
