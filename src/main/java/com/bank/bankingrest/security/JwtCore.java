package com.bank.bankingrest.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtCore {
    @Value(value = "${banking.app.secret}")
    private String secret;
    //private final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Value("${banking.app.lifetime}")
    private int lifetime;

    public String generateToken(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject((userDetails.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + lifetime))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public String getNameFromJwt(String token){
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token)
                .getBody().getSubject();
    }
}
