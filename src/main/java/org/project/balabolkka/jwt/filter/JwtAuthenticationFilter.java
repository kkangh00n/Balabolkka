package org.project.balabolkka.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.domain.RefreshToken;
import org.project.balabolkka.jwt.provider.JwtTokenProvider;
import org.project.balabolkka.jwt.service.RefreshTokenService;
import org.project.balabolkka.jwt.token.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        String accessToken = ((HttpServletRequest) request).getHeader("AccessToken");
        String refreshToken = ((HttpServletRequest) request).getHeader("RefreshToken");

        if (accessToken != null) {
            //AccessToken 유효 시간 지나기 전
            if (jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            //AccessToken 유효 시간이 지났을 때
            else {
                //RefreshToken 유효 시간 지나기 전
                if (jwtTokenProvider.validateToken(refreshToken)) {
                    RefreshToken refreshTokenEntity = refreshTokenService.getRefreshTokenByToken(
                        refreshToken);
                    String email = refreshTokenEntity.getEmail();
                    Token newToken = jwtTokenProvider.createJwtToken(email);

                    ((HttpServletResponse) response).setHeader("AccessToken",
                        newToken.getAccessToken());
                    Authentication authentication = jwtTokenProvider.getAuthentication(
                        newToken.getAccessToken());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                //RefreshToken 유효 시간이 지났을 때
                else {
                    throw new RuntimeException("다시 로그인 해 주세요");
                }
            }
        }

        chain.doFilter(request, response);
    }
}
