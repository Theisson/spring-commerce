package io.github.theisson.ecommerce.services.application;

import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.RefreshTokenRequestDTO;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
import io.github.theisson.ecommerce.exceptions.InvalidRefreshTokenException;
import io.github.theisson.ecommerce.models.entities.RefreshToken;
import io.github.theisson.ecommerce.models.entities.User;
import io.github.theisson.ecommerce.repositories.RefreshTokenRepository;
import io.github.theisson.ecommerce.services.security.TokenProvider;

@Service
public class RefreshTokens {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public RefreshTokens(RefreshTokenRepository refreshTokenRepository, TokenProvider tokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public TokenResponseDTO execute(RefreshTokenRequestDTO dto) {
        String tokenHash = tokenProvider.hashToken(dto.refreshToken());

        RefreshToken refreshToken = refreshTokenRepository.findByToken(tokenHash)
            .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token inválido"));

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidRefreshTokenException("Refresh token inválido");
        }

        User user = refreshToken.getUser();
        TokenResponseDTO response = tokenProvider.generateTokenPair(user);
        refreshTokenRepository.delete(refreshToken);

        return response;
    }
}
