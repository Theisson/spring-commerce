package io.github.theisson.ecommerce.services.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.UserRequestDTO;
import io.github.theisson.ecommerce.dto.UserResponseDTO;
import io.github.theisson.ecommerce.exceptions.DatabaseException;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.models.types.*;
import io.github.theisson.ecommerce.repositories.UserRepository;

@Service
public class RegisterUser {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String pepper;

    public RegisterUser(
        UserRepository userRepository, 
        PasswordEncoder passwordEncoder, 
        @Value("${app.security.password.pepper}") String pepper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.pepper = pepper;
    }

    @Transactional
    public UserResponseDTO execute(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new DatabaseException("E-mail já cadastrado");
        }

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new DatabaseException("Nome de usuário já cadastrado");
        }

        String hash = passwordEncoder.encode(dto.password() + pepper);

        User user = new User(
            new Username(dto.username()),
            new Email(dto.email()),
            hash,
            UserRole.CUSTOMER,
            AuthProvider.LOCAL
        );

        user = userRepository.save(user);

        return new UserResponseDTO(user);
    }
}
