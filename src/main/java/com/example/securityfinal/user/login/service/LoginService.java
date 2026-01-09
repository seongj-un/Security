package com.example.securityfinal.user.login.service;


import com.example.securityfinal.common.JWT.JwtProvider;
import com.example.securityfinal.common.JWT.RefreshToken;
import com.example.securityfinal.common.JWT.refreshTokenRepository;
import com.example.securityfinal.user.User;
import com.example.securityfinal.user.dto.response.TokenResponse;
import com.example.securityfinal.user.repository.UserReposetory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class LoginService {

    private final UserReposetory  userReposetory;
    private final refreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse userLogin(String username, String password){
        User user = userReposetory.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다."));
        if(!encoder.matches(password, user.getPassword())){
            throw  new IllegalArgumentException("Invalid username or password");
        }

        String accessToken = jwtProvider.createToken(user.getUserName(),user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserName());

        refreshTokenRepository.findByUsername(username)
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshTokenEntity = new RefreshToken(username, refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);


        return new TokenResponse(accessToken, refreshToken);
    }
}
