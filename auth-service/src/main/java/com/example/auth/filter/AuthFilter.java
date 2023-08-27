package com.example.auth.filter;


import com.example.auth.config.SecurityConfig;
import com.example.auth.dto.response.TokenResponse;
import com.example.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    public static final String API_TOKEN_PREFIX = "Bearer ";
    private static final String USER_AGENT = "User-Agent";
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("<<<<<< AuthFilter");
        String userAgent = request.getHeader(USER_AGENT);
        String token = extractTokenFromRequest(request);
        TokenResponse tokenResponse = null;
        if (!StringUtils.isBlank(token)) {
            tokenResponse = jwtUtil.validateToken(token , userAgent);
        }

        if (tokenResponse != null && tokenResponse.isValidated()) {
            fillSecurityContext(tokenResponse);
        } else {
            clearSecurityContext();
        }
        filterChain.doFilter(request, response);
        log.info("AuthFilter >>>>>");
    }

    private void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    private void fillSecurityContext(TokenResponse tokenResponse) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tokenResponse.getEmail(),
                null , tokenResponse.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isAbsent(bearerToken)) {
            return null;
        }
        return bearerToken.replace(API_TOKEN_PREFIX, "");
    }

    private boolean isAbsent(String bearerToken) {
        return bearerToken == null || !bearerToken.startsWith(API_TOKEN_PREFIX);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return stream(SecurityConfig.PUBLIC_URLS)
                .anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
    }

}
