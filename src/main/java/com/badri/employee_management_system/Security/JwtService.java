package com.badri.employee_management_system.Security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey (){

        System.out.println("Secret Length  "+secretKey.length());
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken (String username){

        return Jwts.builder().subject(username).issuedAt(new java.util.Date()).expiration(new java.util.Date( System.currentTimeMillis() + expiration )).signWith(getSigningKey()).compact();
    }

    public String extractUsername(String token){

        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
