package org.example.uberprojectauthservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements CommandLineRunner {

    // get the values from application.properties
    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * This method will create new jwt token based on payload
     * @return
     */

    private String createToken(Map<String, Object> payload, String email){

        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(key)
                .compact();

    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Time to create the token");

        Map<String,Object> mp = new HashMap<>();
        mp.put("name","shubham");
        mp.put("phone","8957973898");

        String result = createToken(mp,"sinha@gmail.com");
        System.out.println("Generated token is : "+result);
    }
}
