package io.github.theisson.ecommerce.services.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.LoginRequestDTO;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
import io.github.theisson.ecommerce.exceptions.InvalidCredentialsException;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.repositories.UserRepository;
import io.github.theisson.ecommerce.services.security.TokenProvider;

@Service
public class LoginUser {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final String pepper;

    public LoginUser(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        TokenProvider tokenProvider,
        @Value("${app.security.password.pepper}") String pepper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.pepper = pepper;
    }

    @Transactional
    public TokenResponseDTO execute(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.login())
            .or(() -> userRepository.findByUsername(dto.login()))
            .orElseThrow(() -> new InvalidCredentialsException("Credenciais inválidas"));

        if (!passwordEncoder.matches(dto.password() + pepper, user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        return tokenProvider.generateTokenPair(user);
    }
}
