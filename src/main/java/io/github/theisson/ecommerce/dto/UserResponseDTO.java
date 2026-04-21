package io.github.theisson.ecommerce.dto;

import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.models.types.AuthProvider;
import io.github.theisson.ecommerce.models.types.UserRole;

public record UserResponseDTO(
    Long id,
    String username,
    String email,
    UserRole role,
    AuthProvider authProvider
) {

    public UserResponseDTO(User entity) {
        this(
            entity.getId(),
            entity.getUsername(),
            entity.getEmail(),
            entity.getRole(),
            entity.getAuthProvider()
        );
    }
}
