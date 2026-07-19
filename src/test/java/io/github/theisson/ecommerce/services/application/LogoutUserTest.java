package io.github.theisson.ecommerce.services.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import io.github.theisson.ecommerce.dto.RefreshTokenRequestDTO;
import io.github.theisson.ecommerce.exceptions.InvalidRefreshTokenException;
import io.github.theisson.ecommerce.models.entities.RefreshToken;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.models.types.AuthProvider;
import io.github.theisson.ecommerce.models.types.Email;
import io.github.theisson.ecommerce.models.types.UserRole;
import io.github.theisson.ecommerce.models.types.Username;
import io.github.theisson.ecommerce.repositories.RefreshTokenRepository;
import io.github.theisson.ecommerce.services.security.TokenProvider;

@ExtendWith(MockitoExtension.class)
class LogoutUserTest {

    private static final String RAW_TOKEN = "raw-uuid-123";
    private static final String HASHED_TOKEN = "hashed-uuid-123";

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenProvider tokenProvider;

    private LogoutUser logoutUser;

    @BeforeEach
    void setUp() {
        logoutUser = new LogoutUser(refreshTokenRepository, tokenProvider);
    }

    private User buildUser(Long id) {
        User user = new User(
            new Username("testuser"),
            new Email("test@example.com"),
            "hashed-pwd",
            UserRole.CUSTOMER,
            AuthProvider.LOCAL
        );
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    private RefreshToken buildRefreshToken(User user, Instant expiresAt) {
        return new RefreshToken(HASHED_TOKEN, user, expiresAt);
    }

    @Test
    @DisplayName("Deve revogar o refresh token quando pertence ao usuário autenticado")
    void shouldRevokeTokenWhenOwnedByAuthenticatedUser() {
        User user = buildUser(1L);
        RefreshToken stored = buildRefreshToken(user, Instant.now().plusSeconds(3600));

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.of(stored));

        logoutUser.execute(new RefreshTokenRequestDTO(RAW_TOKEN), 1L);

        verify(refreshTokenRepository).delete(stored);
    }

    @Test
    @DisplayName("Deve lançar quando token não existe")
    void shouldThrowWhenTokenNotFound() {
        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.empty());

        InvalidRefreshTokenException ex = assertThrows(
            InvalidRefreshTokenException.class,
            () -> logoutUser.execute(new RefreshTokenRequestDTO(RAW_TOKEN), 1L)
        );
        assertEquals("Refresh token inválido", ex.getMessage());
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lançar quando token pertence a outro usuário (ownership mismatch)")
    void shouldThrowWhenTokenBelongsToAnotherUser() {
        User owner = buildUser(2L);
        RefreshToken stored = buildRefreshToken(owner, Instant.now().plusSeconds(3600));

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.of(stored));

        InvalidRefreshTokenException ex = assertThrows(
            InvalidRefreshTokenException.class,
            () -> logoutUser.execute(new RefreshTokenRequestDTO(RAW_TOKEN), 1L)
        );
        assertEquals("Refresh token inválido", ex.getMessage());
        verify(refreshTokenRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve revogar mesmo se o token estiver expirado (sem checagem de expiração)")
    void shouldRevokeEvenIfExpired() {
        User user = buildUser(1L);
        RefreshToken expired = buildRefreshToken(user, Instant.now().minusSeconds(60));

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.of(expired));

        logoutUser.execute(new RefreshTokenRequestDTO(RAW_TOKEN), 1L);

        verify(refreshTokenRepository).delete(expired);
    }

    @Test
    @DisplayName("Mensagem deve ser a mesma para token inexistente e ownership mismatch (anti-enumeração)")
    void shouldUseSameMessageForNotFoundAndOwnershipMismatch() {
        User owner = buildUser(2L);
        RefreshToken stored = buildRefreshToken(owner, Instant.now().plusSeconds(3600));

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(stored));

        InvalidRefreshTokenException exNotFound = assertThrows(
            InvalidRefreshTokenException.class,
            () -> logoutUser.execute(new RefreshTokenRequestDTO(RAW_TOKEN), 1L)
        );
        InvalidRefreshTokenException exOwnership = assertThrows(
            InvalidRefreshTokenException.class,
            () -> logoutUser.execute(new RefreshTokenRequestDTO(RAW_TOKEN), 1L)
        );

        assertEquals("Refresh token inválido", exNotFound.getMessage());
        assertEquals("Refresh token inválido", exOwnership.getMessage());
    }
}