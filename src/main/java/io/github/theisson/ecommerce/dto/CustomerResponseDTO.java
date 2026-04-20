package io.github.theisson.ecommerce.dto;

import java.time.LocalDate;

import io.github.theisson.ecommerce.models.entities.Customer;

public record CustomerResponseDTO(
    Long id,
    String username,
    String email,
    String cpf,
    LocalDate birthDate,
    String phoneNumber
) {
    
    public CustomerResponseDTO(Customer entity) {
        this(
            entity.getId(),
            entity.getUser().getUsername(),
            entity.getUser().getEmail(),
            entity.getCpf(),
            entity.getBirthDate(),
            entity.getPhoneNumber()
        );
    }
}
