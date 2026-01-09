package com.example.securityfinal.common.config;

import com.example.securityfinal.common.JWT.JwtFilter;
import com.example.securityfinal.common.JWT.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. JWT 방식이므로 CSRF, FormLogin, HttpBasic 비활성화
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // 2. 세션을 상태 유지 방식으로 사용하지 않음 (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Login", "/auth/**").permitAll() // 로그인 관련은 모두 허용
                        .anyRequest().authenticated()                      // 나머지는 인증 필요
                )

                // 4. JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 배치
                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 비밀번호 암호화 빈 추가
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}