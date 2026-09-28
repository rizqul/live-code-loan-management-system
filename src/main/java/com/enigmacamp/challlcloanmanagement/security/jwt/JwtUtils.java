package com.enigmacamp.challlcloanmanagement.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.jwt-expiration-ms}")
    private int jwtExpirationMs;

    @Value("${app.jwt-secret}")
    private String jwtSecret;


    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        String role = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("User roles are invalid"));

        log.debug("isi dari role adalah : "+role);
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date((new Date().getTime() + jwtExpirationMs)))
                .withClaim("role", role)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public boolean validateJwtToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();

            verifier.verify(token);

            return true;
        } catch (JWTVerificationException e) {
            log.error("JSON Web Token is not valid : " + e.getMessage());
            return false;
        }
    }

    public String getUsernameFromJwtToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public String getRoleFromJwtToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("role").toString();
    }
}
