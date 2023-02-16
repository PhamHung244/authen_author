package com.example.authen.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
@Component
public class JwtUltity implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(JwtUltity.class);
    private String jwtSecret = "secretkey";
    public String generateJwtToken(String userName){
        Claims claims = Jwts.claims().setSubject(userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+(100 * 60 * 60 * 24)))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public boolean validateJwtToken(String authToken) throws ExpiredJwtException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new SignatureException(e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new MalformedJwtException(e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new UnsupportedJwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        }catch (Exception e){
            return false;
        }
//        return false;
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
