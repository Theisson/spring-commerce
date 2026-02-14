package io.github.theisson.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.github.theisson.ecommerce.models.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
