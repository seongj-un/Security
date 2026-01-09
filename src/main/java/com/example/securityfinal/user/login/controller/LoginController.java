package com.example.securityfinal.user.login.controller;

import com.example.securityfinal.user.dto.response.TokenResponse;
import com.example.securityfinal.user.dto.request.loginRequest;
import com.example.securityfinal.user.login.service.LoginService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Getter
@RequiredArgsConstructor
@RequestMapping("/Login")
public class LoginController{

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<TokenResponse> login(@RequestBody loginRequest request){
        TokenResponse tokenResponse = loginService.userLogin(request.getUsername(), request.getPassword());


        return ResponseEntity.ok(tokenResponse);
    }
}
