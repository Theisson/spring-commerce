package io.github.theisson.ecommerce.services.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.github.theisson.ecommerce.dto.RefreshTokenRequestDTO;
import io.github.theisson.ecommerce.exceptions.InvalidRefreshTokenException;
import io.github.theisson.ecommerce.models.entities.RefreshToken;
import io.github.theisson.ecommerce.repositories.RefreshTokenRepository;
import io.github.theisson.ecommerce.services.security.TokenProvider;


@Service
public class LogoutUser {
    
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public LogoutUser(RefreshTokenRepository refreshTokenRepository, TokenProvider tokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public void execute(RefreshTokenRequestDTO dto, Long authenticatedUserId) {
        String tokenHash = tokenProvider.hashToken(dto.refreshToken());

        RefreshToken refreshToken = refreshTokenRepository.findByToken(tokenHash)
            .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token inválido"));
        
        if (!refreshToken.getUser().getId().equals(authenticatedUserId)) {
            throw new InvalidRefreshTokenException("Refresh token inválido");
        }

        refreshTokenRepository.delete(refreshToken);
    }
}
