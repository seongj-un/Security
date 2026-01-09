package com.example.securityfinal.common.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            // 1. Authorization 헤더 추출
            String bearerToken = request.getHeader("Authorization");
            String token = null;

            // 2. Bearer 접두사 확인 및 토큰 분리
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
            }

            // 3. 토큰이 존재하고 유효하다면 인증 정보 설정
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getSubject(token);

                // ROLE_ 접두사를 붙여서 권한 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

                // 이 줄이 실행되어야 스프링이 "로그인 된 사용자"로 인식합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 4. 중요: 다음 필터로 반드시 넘겨주어야 합니다.
            filterChain.doFilter(request, response);
        }
    }