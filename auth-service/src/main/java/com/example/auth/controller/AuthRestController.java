package com.example.auth.controller;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.request.TokenCreateRequest;
import com.example.auth.dto.response.LoginResponse;
import com.example.auth.service.AuthService;
import com.example.auth.util.JwtUtil;
import com.example.repositoryservice.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthRestController {


    public static final String USER_AGENT = "User-Agent";
    private final JwtUtil jwtUtil;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/accessToken")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequestDto loginRequestDto , HttpServletRequest request){
        UserEntity user = this.authService.getUserByEmail(loginRequestDto.getEmail());
        TokenCreateRequest tokenCreateRequest = prepareTokenCreateRequest(loginRequestDto, request, user);

        if (!isPasswordValid(loginRequestDto, user)) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                     .body(new LoginResponse("Invalid Credentials"));
        }
        String accessToken = jwtUtil.generateToken(tokenCreateRequest);
        return ResponseEntity.ok(new LoginResponse(accessToken , "Login Successfully"));

    }

    private static TokenCreateRequest prepareTokenCreateRequest(LoginRequestDto loginRequestDto, HttpServletRequest request, UserEntity user) {
        return new TokenCreateRequest(loginRequestDto.getEmail(),
                user.getRole().getName(), request.getHeader(USER_AGENT));
    }

    private boolean isPasswordValid(LoginRequestDto loginRequestDto, UserEntity user) {
        return passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
    }

}
