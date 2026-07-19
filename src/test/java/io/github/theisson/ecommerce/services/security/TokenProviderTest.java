package io.github.theisson.ecommerce.services.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
import io.github.theisson.ecommerce.models.entities.RefreshToken;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.models.types.AuthProvider;
import io.github.theisson.ecommerce.models.types.Email;
import io.github.theisson.ecommerce.models.types.UserRole;
import io.github.theisson.ecommerce.models.types.Username;
import io.github.theisson.ecommerce.repositories.RefreshTokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    private static final String RAW_SHA256_OF_ABC =
        "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtEncoder jwtEncoder;

    @Mock
    private Jwt jwt;

    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(refreshTokenRepository, jwtEncoder, 3600L, 604800L);
    }

    private User buildUser() {
        return new User(
            new Username("testuser"),
            new Email("test@example.com"),
            "$argon2id$placeholder-hash-not-used-here",
            UserRole.CUSTOMER,
            AuthProvider.LOCAL
        );
    }

    private void stubEncoder() {
        when(jwt.getTokenValue()).thenReturn("mock-access-token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);
    }

    @Test
    @DisplayName("hashToken deve ser determinístico (mesmo input -> mesmo output)")
    void hashTokenShouldBeDeterministic() {
        String hash1 = tokenProvider.hashToken("abc");
        String hash2 = tokenProvider.hashToken("abc");

        assertEquals(RAW_SHA256_OF_ABC, hash1);
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("hashToken deve retornar SHA-256 hexadecimal de 64 caracteres")
    void hashTokenShouldReturnSha256Hex() {
        String hash = tokenProvider.hashToken("qualquer-coisa");

        assertEquals(64, hash.length());
        assertTrue(hash.matches("^[0-9a-f]{64}$"));
    }

    @Test
    @DisplayName("hashToken deve produzir hashes distintos para inputs distintos")
    void hashTokenShouldProduceDifferentHashesForDifferentInputs() {
        String hash1 = tokenProvider.hashToken("input-A");
        String hash2 = tokenProvider.hashToken("input-B");

        assertNotEquals(hash1, hash2);
    }

    @Test
    @DisplayName("generateTokenPair deve retornar accessToken do JwtEncoder e refreshToken UUID")
    void generateTokenPairShouldReturnTokens() {
        stubEncoder();
        TokenResponseDTO response = tokenProvider.generateTokenPair(buildUser());

        assertNotNull(response.accessToken());
        assertNotNull(response.refreshToken());
        assertEquals("mock-access-token", response.accessToken());
        assertEquals("Bearer", response.tokenType());
        assertEquals(3600L, response.expiresIn());
    }

    @Test
    @DisplayName("generateTokenPair deve persistir o HASH do refresh token (nunca o raw)")
    void generateTokenPairShouldPersistHashedRefreshToken() {
        stubEncoder();
        TokenResponseDTO response = tokenProvider.generateTokenPair(buildUser());

        ArgumentCaptor<RefreshToken> captor = ArgumentCaptor.forClass(RefreshToken.class);
        verify(refreshTokenRepository).save(captor.capture());

        RefreshToken saved = captor.getValue();
        assertNotEquals(response.refreshToken(), saved.getToken(),
            "O persistido deve ser o HASH, nunca o raw retornado ao cliente");
        assertEquals(tokenProvider.hashToken(response.refreshToken()), saved.getToken(),
            "O hash persistido deve bater com hashToken(raw)");
        assertNotNull(saved.getExpiresAt());
        assertTrue(saved.getExpiresAt().isAfter(Instant.now()));
    }

    @Test
    @DisplayName("generateTokenPair deve construir o access token com sub, roles, iss, exp")
    void generateTokenPairShouldBuildAccessTokenWithCorrectClaims() {
        stubEncoder();
        User user = buildUser();

        tokenProvider.generateTokenPair(user);

        ArgumentCaptor<JwtEncoderParameters> captor = ArgumentCaptor.forClass(JwtEncoderParameters.class);
        verify(jwtEncoder).encode(captor.capture());

        JwtClaimsSet claims = captor.getValue().getClaims();
        assertEquals("spring-commerce", claims.getClaim("iss"));
        assertNotNull(claims.getSubject());
        assertNotNull(claims.getExpiresAt());
        assertNotNull(claims.getIssuedAt());
        assertTrue(claims.getExpiresAt().isAfter(claims.getIssuedAt()));

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) claims.getClaim("roles");
        assertEquals(List.of("CUSTOMER"), roles);
    }

    @Test
    @DisplayName("generateTokenPair deve chamar save exatamente uma vez (um refresh token por login)")
    void generateTokenPairShouldSaveExactlyOneRefreshToken() {
        stubEncoder();
        tokenProvider.generateTokenPair(buildUser());

        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }
}