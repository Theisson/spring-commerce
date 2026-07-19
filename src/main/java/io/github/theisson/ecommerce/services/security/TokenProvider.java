package io.github.theisson.ecommerce.services.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Component;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
import io.github.theisson.ecommerce.models.entities.RefreshToken;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.repositories.RefreshTokenRepository;

@Component
public class TokenProvider {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtEncoder jwtEncoder;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenProvider(
        RefreshTokenRepository refreshTokenRepository,
        JwtEncoder jwtEncoder,
        @Value("${app.security.jwt.access-token.expiration-seconds}") long accessTokenExpiration,
        @Value("${app.security.jwt.refresh-token.expiration-seconds}") long refreshTokenExpiration
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtEncoder = jwtEncoder;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    public TokenResponseDTO generateTokenPair(User user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateAndStoreRefreshToken(user);
        return new TokenResponseDTO(accessToken, refreshToken, "Bearer", accessTokenExpiration);
    }

    public String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));

            StringBuilder hex = new StringBuilder();
            for (byte b : hashBytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponível", e);
        }
    }

    private String generateAccessToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("spring-commerce")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(accessTokenExpiration))
            .subject(String.valueOf(user.getId()))
            .claim("username", user.getUsername())
            .claim("roles", List.of(user.getRole().name()))
            .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    private String generateAndStoreRefreshToken(User user) {
        String rawToken = UUID.randomUUID().toString();
        String tokenHash = hashToken(rawToken);
        Instant expiresAt = Instant.now().plusSeconds(refreshTokenExpiration);

        refreshTokenRepository.save(new RefreshToken(tokenHash, user, expiresAt));

        return rawToken;
    }
}
