package org.project.balabolkka.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.exception.ValidationException;
import org.project.balabolkka.jwt.config.JwtConfig;
import org.project.balabolkka.jwt.exception.JwtErrorMessage;
import org.project.balabolkka.jwt.service.RefreshTokenService;
import org.project.balabolkka.jwt.service.SecurityService;
import org.project.balabolkka.jwt.token.Token;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private final SecurityService securityService;
    private final RefreshTokenService refreshTokenService;

    byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getClientSecret());
    private final Key signKey = Keys.hmacShaKeyFor(keyBytes);

    public Token createJwtToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();

        String accessToken = createAccessToken(claims, now);
        String refreshToken = createRefreshToken(claims, now);

        //refreshToken DB 저장
        refreshTokenService.createRefreshToken(refreshToken, email);

        return new Token(accessToken, refreshToken, email);
    }

    public String getEmail(String token) {
        Jws<Claims> claims = getClaims(token);

        return claims.getBody().getSubject();
    }

    public Authentication getAuthentication(String email) {
        UserDetails userDetails = securityService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    private String createAccessToken(Claims claims, Date now) {
        long expirySeconds = jwtConfig.getExpiryMinute() * 1000L * 60;

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySeconds))
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact();
    }

    private String createRefreshToken(Claims claims, Date now) {
        long expirySecondsRefresh = jwtConfig.getExpiryMinuteRefresh() * 1000L * 60;

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySecondsRefresh))
            .signWith(signKey, SignatureAlgorithm.HS256)
            .compact();
    }

    private Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ValidationException(JwtErrorMessage.JWT_TOKEN_EXPIRED.getMessage());
        } catch (
            SecurityException |
            MalformedJwtException |
            UnsupportedJwtException |
            IllegalArgumentException e
        ) {
            throw new ValidationException(JwtErrorMessage.JWT_TOKEN_INVALID.getMessage());
        }
    }
}
