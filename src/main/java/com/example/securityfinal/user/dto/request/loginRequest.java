package com.example.securityfinal.user.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class loginRequest {
    private String username;
    private String password;
}
