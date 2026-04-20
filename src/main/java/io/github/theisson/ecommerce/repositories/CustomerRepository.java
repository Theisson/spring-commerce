package io.github.theisson.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.theisson.ecommerce.models.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    @Query("SELECT c FROM Customer c WHERE c.cpf.cpf = :cpf")
    Optional<Customer> findByCpf(@Param("cpf") String cpf);
}
