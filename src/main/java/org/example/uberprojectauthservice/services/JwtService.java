package org.example.uberprojectauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService{

    // get the values from application.properties
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * This method will create new jwt token based on payload
     */

    public String createToken(Map<String, Object> payload, String email){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSignKey())
                .compact();

    }

    public String createToken(String email){
        return createToken(new HashMap<>(),email);
    }

    public Claims extractAllPayloads(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    /**
     * This method will check if the token expiry is before the current time or not
     * @param token JWT token
     * @return if token is expired ? true : false
     */

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // This method will validate the token
    public Boolean validateToken(String token, String email){
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(email)) && (!isTokenExpired(token)) ;
    }

    // This method will extract the requested payload
    public Object extractPayload(String token, String payloadKey){
        Claims claims = extractAllPayloads(token);
        return claims.get(payloadKey);
    }
}
