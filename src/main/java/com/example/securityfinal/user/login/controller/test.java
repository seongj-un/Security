package com.example.securityfinal.user.login.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/api/test")
    public String test() {
        // SecurityContextHolder에서 현재 인증된 사용자의 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "인증되지 않은 사용자입니다.";
        }

        String username = authentication.getName(); // JwtFilter에서 넣어준 이름
        return "인증 성공! 현재 접속 유저: " + username;
    }
}