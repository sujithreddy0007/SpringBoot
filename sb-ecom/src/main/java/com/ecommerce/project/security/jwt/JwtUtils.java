package com.ecommerce.project.security.jwt;

import com.ecommerce.project.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger =
            LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMS}")
    private int jwtExpirationMS;

    @Value("${spring.ecom.app.jwtCookieName}")
    private String jwtCookie;



    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request,jwtCookie);
        if(cookie != null) {
            return cookie.getValue();
        } else{
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
        String jwt = generateTokenFromUserName(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie,jwt)
                .path("/api")
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();
        return cookie;
    }


    // Generating token from username
    public String generateTokenFromUserName(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                new Date().getTime() + jwtExpirationMS
                        )
                )
                .signWith(key())
                .compact();
    }


    // Getting username from JWT token
    public String getUserNameFromJWTToken(String token) {

        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    // Generating signing key
    public SecretKey key() {

        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }


    // Validate JWT token
    public boolean validateJwtToken(String authToken) {

        try {

            System.out.println("Validate");

            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(authToken);

            return true;

        } catch (ExpiredJwtException e) {

            logger.error("JWT token is expired: {}", e.getMessage());

        } catch (MalformedJwtException e) {

            logger.error("Invalid JWT token: {}", e.getMessage());

        } catch (UnsupportedJwtException e) {

            logger.error("JWT token is unsupported: {}", e.getMessage());

        } catch (IllegalArgumentException e) {

            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}