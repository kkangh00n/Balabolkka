package org.project.balabolkka.jwt.service;

import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.domain.RefreshToken;
import org.project.balabolkka.jwt.repository.RefreshTokenRepository;
import org.project.balabolkka.jwt.token.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void createRefreshToken(Token token) {

        String email = token.getEmail();
        if (refreshTokenRepository.existsRefreshTokenByEmail(email)) {
            refreshTokenRepository.deleteRefreshTokenByEmail(email);
        }

        RefreshToken refreshToken = RefreshToken.builder()
            .token(token.getRefreshToken())
            .email(token.getEmail())
            .build();

        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken getRefreshTokenByToken(String refreshToken) {
        return refreshTokenRepository.findRefreshTokenByToken(refreshToken)
            .orElseThrow();
    }

}
