package com.trip.auth.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${JWT_SECRET_KEY}")
    private String jwtSecretKey;

    @Value("${JWT_EXPIRED_IN}")
    private long jwtExpiredIn;

    @Value("${JWT_REFRESH_SECRET_KEY}")
    private String jwtRefreshSecretKey;

    @Value("${JWT_REFRESH_EXPIRED_IN}")
    private long jwtRefreshExpiredIn;

    public String createAccessToken(String email) {
        return createToken(email, jwtExpiredIn, jwtSecretKey);
    }

    public String createRefreshToken(String email) {
        return createToken(email, jwtRefreshExpiredIn, jwtRefreshSecretKey);
    }

    private String createToken(String email, long expirationTime, String secretKey) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String token = Jwts.builder()
            .setSubject(String.valueOf(email))
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
        log.info("생성된 토큰: {}", token);
        return token;
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey).build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            log.error("유효 기간이 만료된 JWT 토큰 입니다.: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("애플리케이션이 지원하지 않는 JWT 토큰 형식 입니다.: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("구조적인 문제가 있는 JWT 토큰 입니다.: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException | JwtException e) {
            log.error("기타 JWT 토큰 에러 입니다.: {}", e.getMessage());
            throw e;
        }
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey).build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            log.error("토큰 만료 오류: {}", e.getMessage());
            throw e;
        } catch (JwtException e) {
            log.error("토큰 유효성 검사 오류: {}", e.getMessage());
            throw e;
        }
    }

    public Authentication getAuthentication(String token) {
        String id = getIdFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private String getIdFromToken(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
            .setSigningKey(jwtSecretKey)
            .build()
            .parseClaimsJws(token);

        Claims claims = jws.getBody();
        String subject = claims.getSubject();
        if (subject != null) {
            return subject;
        } else {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }

}