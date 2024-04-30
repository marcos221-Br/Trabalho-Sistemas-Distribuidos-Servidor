package com.example.trabalhosistemasdistribuidos;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class TokenGenerator {
    private final String secret = "fj32Jfv02Mq33g0f8ioDkw";

    public String createToken(String email){
        try {
            return JWT.create().withIssuer("auth0").withClaim("email", email).sign(Algorithm.HMAC256(secret)).substring(0,36);
        } catch (JWTCreationException exception){
            throw new RuntimeException("You need to enable Algorithm.HMAC256");
        }
    }
}
