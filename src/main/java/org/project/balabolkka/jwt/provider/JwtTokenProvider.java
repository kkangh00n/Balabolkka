package org.project.balabolkka.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.project.balabolkka.jwt.config.JwtConfig;
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

    public Token createJwtToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);

        Date now = new Date();

        String accessToken = createAccessToken(claims, now);
        String refreshToken = createRefreshToken(claims, now);

        return new Token(accessToken, refreshToken, email);
    }

    public String createAccessToken(Claims claims, Date now) {
        long expirySeconds = jwtConfig.getExpiryMinute() * 1000L * 60;

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySeconds))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getClientSecret())
            .compact();
    }

    private String createRefreshToken(Claims claims, Date now) {
        long expirySecondsRefresh = jwtConfig.getExpiryMinuteRefresh() * 1000L * 60;

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + expirySecondsRefresh))
            .signWith(SignatureAlgorithm.HS256, jwtConfig.getClientSecret())
            .compact();
    }

    public boolean validateToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getClientSecret())
                .build()
                .parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String email = getEmail(token);
        UserDetails userDetails = securityService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    public String getEmail(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
            .setSigningKey(jwtConfig.getClientSecret())
            .build()
            .parseClaimsJws(token);

        return claims.getBody().getSubject();
    }
}
