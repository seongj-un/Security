package com.example.securityfinal.user.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class authRequest {
    private String username;
    private String password;
}
