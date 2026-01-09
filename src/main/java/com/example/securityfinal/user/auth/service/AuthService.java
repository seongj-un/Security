package com.example.securityfinal.user.auth;

import com.example.securityfinal.user.User;
import com.example.securityfinal.user.dto.request.authRequest;
import com.example.securityfinal.user.repository.userReposetory; // 실제 파일명이 userReposetory인지 확인 필요
import com.example.securityfinal.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final userReposetory userRepository;
    private final PasswordEncoder encoder;

    public void createAuth(authRequest authRequest) {

        User user = User.builder()
                .userName(authRequest.getUsername())
                .password(encoder.encode(authRequest.getPassword()))
                .role(Role.USER)
                .build();


        userRepository.save(user);
    }
}