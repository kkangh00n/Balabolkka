package org.project.balabolkka.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.config.JwtConfig;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    public String createToken(String email){
        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();
        long expirySeconds = jwtConfig.getExpirySeconds()*1000L*60;

        String accessToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySeconds))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getClientSecret())
            .compact();

        return accessToken;
    }

}
