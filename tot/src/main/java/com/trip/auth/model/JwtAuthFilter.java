package com.trip.auth.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String JWT_TOKEN_PREFIX = "Bearer ";
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        String accessToken = resolveAccessToken(request);

        if (accessToken != null) {
            try {
                jwtProvider.validateToken(accessToken); // 토큰 유효성 검증
                Claims claims = jwtProvider.parseToken(accessToken); // 토큰 파싱
                String memberId = claims.getSubject();
                request.setAttribute("memberId", memberId);
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException e) {
                log.info("Invalid JWT token: {}", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith(JWT_TOKEN_PREFIX)) {
            return token.substring(JWT_TOKEN_PREFIX.length());
        }
        return null; // 토큰이 없거나 유효하지 않은 경우 null 반환
    }
}