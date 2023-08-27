package com.example.auth.dto.request;


public record TokenCreateRequest(String email, String role, String userAgent) {
}
