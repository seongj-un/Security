package com.example.securityfinal.common.JWT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface refreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUsername(String username);


    Optional<RefreshToken> findByToken(String token);

    void deleteByUsername(String username);
}
