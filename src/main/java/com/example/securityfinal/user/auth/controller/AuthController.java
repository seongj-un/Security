package com.example.securityfinal.user.auth.controller;

import com.example.securityfinal.user.auth.service.AuthService;
import lombok.Getter;
import com.example.securityfinal.user.dto.request.authRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public String auth(@RequestBody authRequest authRequest) {
        authService.createAuth(authRequest);
        return "auth success";
    }
}