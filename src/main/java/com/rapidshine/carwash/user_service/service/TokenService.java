package com.rapidshine.carwash.user_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final Environment env;
    public String generateM2MToken(String clientId,String clientSecret){
        String registeredSecret = env.getProperty("m2m-clients."+clientId+".client-secret");
        if(registeredSecret == null || !registeredSecret.equals(clientSecret)){
            throw  new RuntimeException("Invalid client credentials");
        }
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+3600000);
        return Jwts.builder()
                .setSubject(clientId)
                .claim("role", new String[]{"M2M"})
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(env.getProperty("jwt.secret").getBytes(StandardCharsets.UTF_8)))

                .compact();


    }

}
