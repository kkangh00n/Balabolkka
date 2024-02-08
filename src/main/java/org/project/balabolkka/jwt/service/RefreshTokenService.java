package org.project.balabolkka.jwt.service;

import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.domain.RefreshToken;
import org.project.balabolkka.jwt.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createRefreshToken(String refreshToken, String email) {

        if (refreshTokenRepository.existsRefreshTokenByEmail(email)) {
            refreshTokenRepository.deleteRefreshTokenByEmail(email);
        }

        RefreshToken refreshTokenEntity = RefreshToken.builder()
            .token(refreshToken)
            .email(email)
            .build();

        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteRefreshTokenByEmail(email);
    }

    @Transactional
    public RefreshToken getRefreshTokenByToken(String refreshToken) {
        return refreshTokenRepository.findRefreshTokenByToken(refreshToken)
            .orElseThrow();
    }

}
