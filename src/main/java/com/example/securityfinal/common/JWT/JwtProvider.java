package com.example.securityfinal.common.JWT;


import com.example.securityfinal.user.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final String SECRET_KEY="fndfjkdsfifjdksalfnlsdalbjfnsaexrycvgybhunjifvvdwscdvgybdvfgmfugyevufbjebgayvbfvhgfvdfyugbfgvdeubhsbfebvghdbvgafdbhdbyiwbdyedd";
    private final Long EXPIRATION_TIME=1000L*60*30;
    private final Long REFRESH_TOKEN = 1000L * 60 * 60 * 24 *7;
    private Key key;

    @PostConstruct
    public void init(){
        this.key= Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String username, Role role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role",role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean  validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
    public String getSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String createRefreshToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ REFRESH_TOKEN))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}
