package com.brewery.services.auth.token.jwt;

import com.brewery.admin.auth.User;
import com.brewery.exceptions.JwtTokenMalformedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service(value = "jwtTokenService")
public class JwtTokenService {

    private static final Logger LOGGER = Logger.getLogger(JwtTokenService.class);

    public JwtTokenService() {
    }

    public User parseToken(String token) {
        LOGGER.info("token processing has been started");
        try {
            Claims body = Jwts.parser().setSigningKey(JwtTokenParams.SECRET).parseClaimsJws(token).getBody();
            String username = body.getSubject();
            boolean enabled = (Boolean) body.get("isEnabled");
            Set<GrantedAuthority> authorities = parseAuthorities((List<Map<String, String>>) body.get("authorities"));
            return new User(username, null, enabled, authorities);
        } catch (Exception e) {
            String message = "Token is not valid!";
            LOGGER.error(message);
            throw new JwtTokenMalformedException(message, e);
        }

    }

    public String buildToken(User user) throws UnsupportedEncodingException {
        LOGGER.info("token building has been started");
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("isEnabled", user.isEnabled());
        claims.put("authorities", user.getAuthorities());

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis + (JwtTokenParams.EXPIRATION_TIME * 60 * 1000));

        return Jwts.builder()
                .setClaims(claims).setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, JwtTokenParams.SECRET)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        long millis = System.currentTimeMillis();
        Date currentDate = new java.util.Date(millis + (JwtTokenParams.EXPIRATION_TIME * 60 * 1000));
        try {
            Claims body = Jwts.parser().setSigningKey(JwtTokenParams.SECRET).parseClaimsJws(token).getBody();
            return body.getExpiration().getTime() < currentDate.getTime();
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Set<GrantedAuthority> parseAuthorities(List<Map<String, String>> auths) {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        for (Map<String, String> authority : auths) {
            if (authority.containsKey("authority")) {
                String authName = authority.get("authority");
                grantedAuthoritySet.add(new SimpleGrantedAuthority(authName));
            }
        }
        return grantedAuthoritySet;
    }

}
