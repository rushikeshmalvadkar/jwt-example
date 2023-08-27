package com.example.auth.controller.handler;

import com.example.auth.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
        log.info("<<<<<<<<<<< handleAuthenticationException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(List.of("Invalid credentials"));
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        log.info("handleAuthenticationException >>>>>>>>");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex){
        log.info("<<<<<<<<<<< handleJwtException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(List.of("Invalid Token"));
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        log.info("handleJwtException >>>>>>>>");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException ex){
        log.info("<<<<<<<<<<< handleExpiredJwtException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(List.of("Token expired"));
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        log.info("handleExpiredJwtException >>>>>>>>");
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){
        log.info("<<<<<<<<<<< handleAccessDeniedException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessages(List.of("You don't have permission to access this resource"));
        errorResponse.setStatus(HttpStatus.FORBIDDEN);
        log.info("handleAccessDeniedException >>>>>>>>");
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }



}
