package com.example.auth.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class LoginResponse {

    private final String accessToken;
    private final String message;


    public LoginResponse(String accessToken , String message) {
        this.accessToken = accessToken;
        this.message = message;
    }

    public LoginResponse(String message) {
        this(null , message);
    }
}
