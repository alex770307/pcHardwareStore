package org.pchardwarestore.service.accountService.authUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.pchardwarestore.service.exception.InvalidJwtException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private String jwtSecret = "984hg493gh0439rthr0429uruj2309yh937gc763fe87t3f89723gf";

    private long jwtLifeTime = 3600000;

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtLifeTime);
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new InvalidJwtException("Invalid JWT token: " + e.getMessage());
        }
        return true;
    }

    public String getUsernameFromJwt(String token){
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
