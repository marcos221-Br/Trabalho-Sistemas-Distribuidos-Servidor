package com.example.trabalhosistemasdistribuidos;

import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTDecodeException;
//import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenGenerator {
    private final String secret = "fj32Jfv02Mq33g0f8ioDkw";

    public String createToken(String email){
        try {
            return JWT.create().withIssuer("auth0").withClaim("email", email).sign(Algorithm.HMAC256(secret)).substring(0,36);
        } catch (JWTCreationException exception){
            throw new RuntimeException("You need to enable Algorithm.HMAC256");
        }
    }

    //public String getEmailInToken(String token){
      //  try {
        //    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
          //          .withIssuer("auth0")
            //        .build();
            //DecodedJWT jwt = verifier.verify(token);
            //return jwt.getClaim("email").asString();
        //} catch (JWTDecodeException exception){
          //  return null;
        //}
    //}
}
