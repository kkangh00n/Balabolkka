package org.project.balabolkka.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.config.JwtConfig;
import org.project.balabolkka.jwt.token.Token;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    public Token createJwtToken(String email){
        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();

        String accessToken = createAccessToken(claims, now);
        String refreshToken = createRefreshToken(claims, now);

        return new Token(accessToken, refreshToken, email);
    }

    public String createAccessToken(Claims claims, Date now){
        long expirySeconds = jwtConfig.getExpirySeconds()*1000L*60;

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySeconds))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getClientSecret())
            .compact();
    }

    private String createRefreshToken(Claims claims, Date now) {
        long expirySecondsRefresh = jwtConfig.getExpirySecondsRefresh()*1000L*60;

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySecondsRefresh))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getClientSecret())
            .compact();
    }
}
