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
import io.github.theisson.ecommerce.dto.RefreshTokenRequestDTO;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
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
class RefreshTokensTest {

    private static final String RAW_TOKEN = "raw-uuid-123";
    private static final String HASHED_TOKEN = "hashed-uuid-123";

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenProvider tokenProvider;

    private RefreshTokens refreshTokens;

    @BeforeEach
    void setUp() {
        refreshTokens = new RefreshTokens(refreshTokenRepository, tokenProvider);
    }

    private User buildUser() {
        return new User(
            new Username("testuser"),
            new Email("test@example.com"),
            "hashed-pwd",
            UserRole.CUSTOMER,
            AuthProvider.LOCAL
        );
    }

    private RefreshToken buildValidRefreshToken(User user) {
        return new RefreshToken(HASHED_TOKEN, user, Instant.now().plusSeconds(3600));
    }

    @Test
    @DisplayName("Deve rotar tokens quando refresh token é válido")
    void shouldRotateTokensWhenValid() {
        User user = buildUser();
        RefreshToken stored = buildValidRefreshToken(user);
        TokenResponseDTO expectedNewPair =
            new TokenResponseDTO("new-access", "new-refresh", "Bearer", 3600L);

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.of(stored));
        when(tokenProvider.generateTokenPair(user)).thenReturn(expectedNewPair);

        TokenResponseDTO response = refreshTokens.execute(new RefreshTokenRequestDTO(RAW_TOKEN));

        assertEquals(expectedNewPair, response);
        verify(refreshTokenRepository).delete(stored);
    }

    @Test
    @DisplayName("Deve lançar InvalidRefreshTokenException quando token não existe")
    void shouldThrowWhenTokenNotFound() {
        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.empty());

        InvalidRefreshTokenException ex = assertThrows(
            InvalidRefreshTokenException.class,
            () -> refreshTokens.execute(new RefreshTokenRequestDTO(RAW_TOKEN))
        );
        assertEquals("Refresh token inválido", ex.getMessage());
        verify(refreshTokenRepository, never()).delete(any());
        verify(tokenProvider, never()).generateTokenPair(any());
    }

    @Test
    @DisplayName("Deve deletar e lançar quando token está expirado")
    void shouldDeleteAndThrowWhenExpired() {
        User user = buildUser();
        RefreshToken expired = new RefreshToken(
            HASHED_TOKEN, user, Instant.now().minusSeconds(60)
        );

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.of(expired));

        InvalidRefreshTokenException ex = assertThrows(
            InvalidRefreshTokenException.class,
            () -> refreshTokens.execute(new RefreshTokenRequestDTO(RAW_TOKEN))
        );
        assertEquals("Refresh token inválido", ex.getMessage());
        verify(refreshTokenRepository).delete(expired);
        verify(tokenProvider, never()).generateTokenPair(any());
    }

    @Test
    @DisplayName("Mesmo erro (inválido) para token inexistente e token expirado (anti-enumeração)")
    void shouldUseSameMessageForNotFoundAndExpired() {
        RefreshToken expired = new RefreshToken(
            HASHED_TOKEN, buildUser(), Instant.now().minusSeconds(60)
        );

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(expired));

        InvalidRefreshTokenException exNotFound = assertThrows(
            InvalidRefreshTokenException.class,
            () -> refreshTokens.execute(new RefreshTokenRequestDTO(RAW_TOKEN))
        );
        InvalidRefreshTokenException exExpired = assertThrows(
            InvalidRefreshTokenException.class,
            () -> refreshTokens.execute(new RefreshTokenRequestDTO(RAW_TOKEN))
        );

        assertEquals("Refresh token inválido", exNotFound.getMessage());
        assertEquals("Refresh token inválido", exExpired.getMessage());
    }

    @Test
    @DisplayName("Deve hashear o refresh token recebido antes de buscar no banco")
    void shouldHashTokenBeforeLookup() {
        User user = buildUser();
        RefreshToken stored = buildValidRefreshToken(user);
        TokenResponseDTO newPair =
            new TokenResponseDTO("a", "r", "Bearer", 3600L);

        when(tokenProvider.hashToken(RAW_TOKEN)).thenReturn(HASHED_TOKEN);
        when(refreshTokenRepository.findByToken(HASHED_TOKEN)).thenReturn(Optional.of(stored));
        when(tokenProvider.generateTokenPair(user)).thenReturn(newPair);

        refreshTokens.execute(new RefreshTokenRequestDTO(RAW_TOKEN));

        verify(tokenProvider).hashToken(RAW_TOKEN);
        verify(refreshTokenRepository).findByToken(HASHED_TOKEN);
        verify(refreshTokenRepository, never()).findByToken(RAW_TOKEN);
    }
}