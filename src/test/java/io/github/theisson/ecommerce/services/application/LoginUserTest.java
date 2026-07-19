package io.github.theisson.ecommerce.services.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.github.theisson.ecommerce.dto.LoginRequestDTO;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
import io.github.theisson.ecommerce.exceptions.InvalidCredentialsException;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.models.types.AuthProvider;
import io.github.theisson.ecommerce.models.types.Email;
import io.github.theisson.ecommerce.models.types.UserRole;
import io.github.theisson.ecommerce.models.types.Username;
import io.github.theisson.ecommerce.repositories.UserRepository;
import io.github.theisson.ecommerce.services.security.TokenProvider;

@ExtendWith(MockitoExtension.class)
class LoginUserTest {

    private static final String PEPPER = "test-pepper";
    private static final String RAW_PASSWORD = "12345678";
    private static final String HASHED_PASSWORD = "$argon2id$stored-hash";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    private LoginUser loginUser;

    @BeforeEach
    void setUp() {
        loginUser = new LoginUser(userRepository, passwordEncoder, tokenProvider, PEPPER);
    }

    private User buildUser() {
        return new User(
            new Username("testuser"),
            new Email("test@example.com"),
            HASHED_PASSWORD,
            UserRole.CUSTOMER,
            AuthProvider.LOCAL
        );
    }

    @Test
    @DisplayName("Deve logar com sucesso usando e-mail")
    void shouldLoginSuccessfullyWithEmail() {
        LoginRequestDTO dto = new LoginRequestDTO("test@example.com", RAW_PASSWORD);
        TokenResponseDTO expected = new TokenResponseDTO("access", "refresh", "Bearer", 3600L);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches(RAW_PASSWORD + PEPPER, HASHED_PASSWORD)).thenReturn(true);
        when(tokenProvider.generateTokenPair(any(User.class))).thenReturn(expected);

        TokenResponseDTO response = loginUser.execute(dto);

        assertEquals(expected, response);
        verify(tokenProvider).generateTokenPair(any(User.class));
    }

    @Test
    @DisplayName("Deve logar com sucesso usando username quando e-mail não existe")
    void shouldLoginSuccessfullyWithUsername() {
        LoginRequestDTO dto = new LoginRequestDTO("testuser", RAW_PASSWORD);
        TokenResponseDTO expected = new TokenResponseDTO("access", "refresh", "Bearer", 3600L);

        when(userRepository.findByEmail("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches(RAW_PASSWORD + PEPPER, HASHED_PASSWORD)).thenReturn(true);
        when(tokenProvider.generateTokenPair(any(User.class))).thenReturn(expected);

        TokenResponseDTO response = loginUser.execute(dto);

        assertEquals(expected, response);
    }

    @Test
    @DisplayName("Deve lançar InvalidCredentialsException quando usuário não existe")
    void shouldThrowWhenUserNotFound() {
        LoginRequestDTO dto = new LoginRequestDTO("nobody@example.com", RAW_PASSWORD);

        when(userRepository.findByEmail("nobody@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("nobody@example.com")).thenReturn(Optional.empty());

        InvalidCredentialsException ex = assertThrows(
            InvalidCredentialsException.class,
            () -> loginUser.execute(dto)
        );
        assertEquals("Credenciais inválidas", ex.getMessage());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(tokenProvider, never()).generateTokenPair(any());
    }

    @Test
    @DisplayName("Deve lançar InvalidCredentialsException quando senha não confere")
    void shouldThrowWhenPasswordDoesNotMatch() {
        LoginRequestDTO dto = new LoginRequestDTO("test@example.com", "wrong-password");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("wrong-password" + PEPPER, HASHED_PASSWORD)).thenReturn(false);

        InvalidCredentialsException ex = assertThrows(
            InvalidCredentialsException.class,
            () -> loginUser.execute(dto)
        );
        assertEquals("Credenciais inválidas", ex.getMessage());
        verify(tokenProvider, never()).generateTokenPair(any());
    }

    @Test
    @DisplayName("Mensagem de erro deve ser a mesma para usuário inexistente e senha errada (anti-enumeração)")
    void shouldUseSameMessageForUserNotFoundAndPasswordMismatch() {
        LoginRequestDTO dtoNotFound = new LoginRequestDTO("ghost@example.com", RAW_PASSWORD);
        LoginRequestDTO dtoWrongPwd = new LoginRequestDTO("test@example.com", "wrong-password");

        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("ghost@example.com")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches("wrong-password" + PEPPER, HASHED_PASSWORD)).thenReturn(false);

        InvalidCredentialsException exNotFound = assertThrows(
            InvalidCredentialsException.class,
            () -> loginUser.execute(dtoNotFound)
        );
        InvalidCredentialsException exWrongPwd = assertThrows(
            InvalidCredentialsException.class,
            () -> loginUser.execute(dtoWrongPwd)
        );

        assertEquals(exNotFound.getMessage(), exWrongPwd.getMessage());
    }

    @Test
    @DisplayName("Deve concatenar pepper à senha antes de validar")
    void shouldConcatenatePepperBeforeMatches() {
        LoginRequestDTO dto = new LoginRequestDTO("test@example.com", RAW_PASSWORD);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(buildUser()));
        when(passwordEncoder.matches(RAW_PASSWORD + PEPPER, HASHED_PASSWORD)).thenReturn(true);
        when(tokenProvider.generateTokenPair(any(User.class)))
            .thenReturn(new TokenResponseDTO("a", "r", "Bearer", 3600L));

        loginUser.execute(dto);

        verify(passwordEncoder).matches(RAW_PASSWORD + PEPPER, HASHED_PASSWORD);
    }
}