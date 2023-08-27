package com.example.auth.util;

import com.example.auth.config.JwtProperties;
import com.example.auth.dto.request.TokenCreateRequest;
import com.example.auth.dto.response.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private static final String ROLE = "Role";
    private static final String USER_AGENT = "User-Agent";
    public static final String EMAIL = "EMAIL";
    private final JwtProperties jwtProperties;

    @PostConstruct
    public void init() {
        if (log.isDebugEnabled()) {
            log.debug("{} bean created", this.getClass().getName());
        }
    }

    public String generateToken(TokenCreateRequest customClaims) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE, List.of(customClaims.role()));
        claims.put(USER_AGENT, customClaims.userAgent());
        claims.put(EMAIL, customClaims.email());

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(customClaims.email())
                .addClaims(claims)
                .setExpiration(new Date(currentTimeMillis() +
                                        MINUTES.toMillis(jwtProperties.jwtExpireTimeInMinute())))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder()
                        .encode(jwtProperties.jwtSecretKey().getBytes()))
                .compact();
    }

    public TokenResponse validateToken(String token, String userAgent) {
        TokenResponse tokenResponse = new TokenResponse();

        if (StringUtils.isBlank(token)) {
            return tokenResponse;
        }

        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encode(jwtProperties.jwtSecretKey().getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException| UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error("Exception occured while parsing jwt token :: {}", e);
            return tokenResponse;
        }

        if (claims == null || claims.isEmpty()) {
            return tokenResponse;
        }

        if (StringUtils.isBlank(userAgent)) {
            return tokenResponse;
        }

        if (!(userAgent.equals(claims.get(USER_AGENT)))) {
            return tokenResponse;
        }


        List<String> authorities = (List<String>) claims.get(ROLE);

        if (authorities == null || authorities.isEmpty()){
            return tokenResponse;
        }

        tokenResponse.setValidated(true);
        tokenResponse.setEmail(claims.get(EMAIL , String.class));
        tokenResponse.setAuthorities(authorities);
        return tokenResponse;
    }


}
